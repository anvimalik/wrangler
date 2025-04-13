
# Wrangler: Enhanced with Byte Size and Time Duration Parsers

This fork of the [CDAP Wrangler](https://github.com/data-integrations/wrangler) enhances the core parsing capabilities of the Wrangler library by adding support for **Byte Size** (e.g., `10KB`, `1.5MB`) and **Time Duration** (e.g., `150ms`, `2.1s`) tokens. Additionally, it introduces a new directive `aggregate-stats` that can perform aggregation over columns containing these units.

## ✨ New Features

### ✅ Byte Size Parser
- Supports inputs like: `10B`, `5KB`, `1.5MB`, `2GB`, `3.2TB`
- Canonical unit: **Bytes**
- Java Class: `ByteSize.java`

### ✅ Time Duration Parser
- Supports inputs like: `100ns`, `250µs`, `3ms`, `2s`, `1.5min`, `0.5h`
- Canonical unit: **Nanoseconds**
- Java Class: `TimeDuration.java`

Both token types are parsed, validated, and exposed via utility methods (`getBytes()`, `getNanoseconds()`, etc.) for seamless recipe integration.

---

## 🧠 New Directive: `aggregate-stats`

### 📌 Usage
```wrangler
aggregate-stats :byte_column :time_column :target_bytes :target_time

🔧 Arguments
:byte_column – Column with byte size values (e.g., "10KB", "1.2MB")

:time_column – Column with time duration values (e.g., "100ms", "2s")

:target_bytes – Column name for aggregated byte result (in MB)

:target_time – Column name for aggregated time result (in seconds)

⚙️ Behavior
Converts all values to canonical units.

Aggregates across rows using ExecutorContext.getGroupStore().

Outputs a single row with:

Total Size in MB

Total Time in Seconds

🧪 Testing
✅ Unit Tests
ByteSizeTest.java – Verifies parsing of various byte size formats.

TimeDurationTest.java – Verifies parsing of time duration formats.

✅ Integration Test
AggregateStatsDirectiveTest.java – Full directive pipeline tested using TestingRig.

🔬 Sample Assertion
java
Copy code
Assert.assertEquals(1, results.size());
Assert.assertEquals(1.43, results.get(0).getValue("total_size_mb"), 0.001);
Assert.assertEquals(2.35, results.get(0).getValue("total_time_sec"), 0.001);
🛠 Files Modified / Added
Grammar:
Directives.g4 – Added BYTE_SIZE, TIME_DURATION lexer rules and updated parser rules.

API:
ByteSize.java, TimeDuration.java – New token types under wrangler-api.

Core:
AggregateStats.java – New directive under wrangler-core.

Tests:
AggregateStatsDirectiveTest.java, ByteSizeTest.java, TimeDurationTest.java

🤖 AI Tooling Used
This project was developed with the assistance of ChatGPT for:

Grammar updates

ANTLR rule formulation

Java class structures

Test case design

Documentation formatting

Prompts used are included in prompts.txt in the root directory.

👤 Author
Anvi Malik
Computer Science Engineering Student, Chitkara University
GitHub: @anvimalik

📄 License
This project is licensed under the Apache License 2.0.
You are free to use, modify, and distribute this work in accordance with the terms of the license.