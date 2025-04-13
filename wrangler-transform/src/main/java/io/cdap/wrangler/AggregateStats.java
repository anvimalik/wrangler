package io.cdap.wrangler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import io.cdap.wrangler.api.parser.Token;
import io.cdap.wrangler.api.parser.TokenType;

/**
 * Aggregates statistics like sum, min, max, count, and average
 * for numeric tokens: Integer, Double, ByteSize, and TimeDuration.
 */
public class AggregateStats {
    private final TokenType type;
    private final List<Number> values = new ArrayList<>();

    public AggregateStats(TokenType type) {
        this.type = type;
    }

    /**
     * Adds a numeric token to the statistics aggregation.
     *
     * @param token the token to be added
     * @throws IllegalArgumentException if the token type mismatches
     */
    public void add(Token token) {
        if (token == null || token.value() == null) {
            return;
        }
        if (token.type() != type) {
            throw new IllegalArgumentException(
                    String.format("Mismatched token type: expected %s but got %s", type, token.type())
            );
        }
        values.add((Number) token.value());
    }

    public int getCount() {
        return values.size();
    }

    public Number getSum() {
        switch (type) {  // Removed TokenType prefix here
            case INTEGER:
                long intSum = 0;
                for (Number n : values) {
                    intSum += n.longValue();
                }
                return intSum;

            case DOUBLE:
                double doubleSum = 0.0;
                for (Number n : values) {
                    doubleSum += n.doubleValue();
                }
                return doubleSum;

            case BYTE_SIZE:
            case TIME_DURATION:
                long longSum = 0;
                for (Number n : values) {
                    longSum += n.longValue();
                }
                return longSum;

            default:
                throw new UnsupportedOperationException("Unsupported token type for sum: " + type);
        }
    }

    public Number getMin() {
        if (values.isEmpty()) {
            return null;
        }
        Number min = values.get(0);
        for (Number n : values) {
            if (compare(n, min) < 0) {
                min = n;
            }
        }
        return min;
    }

    public Number getMax() {
        if (values.isEmpty()) {
            return null;
        }
        Number max = values.get(0);
        for (Number n : values) {
            if (compare(n, max) > 0) {
                max = n;
            }
        }
        return max;
    }

    public Number getAvg() {
        if (values.isEmpty()) {
            return null;
        }

        switch (type) {  // Removed TokenType prefix here
            case INTEGER:
            case BYTE_SIZE:
            case TIME_DURATION:
                return ((Long) getSum()) / getCount();

            case DOUBLE:
                return ((Double) getSum()) / getCount();

            default:
                throw new UnsupportedOperationException("Unsupported token type for avg: " + type);
        }
    }

    private int compare(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return Double.compare(a.doubleValue(), b.doubleValue());
        }
        return Long.compare(a.longValue(), b.longValue());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("count", getCount());
        if (!values.isEmpty()) {
            json.addProperty("sum", getSum());
            json.addProperty("min", getMin());
            json.addProperty("max", getMax());
            json.addProperty("avg", getAvg());
        }
        return json;
    }

    @Override
    public String toString() {
        return String.format("AggregateStats{type=%s, count=%d, sum=%s, min=%s, max=%s, avg=%s}",
                             type, getCount(), getSum(), getMin(), getMax(), getAvg());
    }
}
