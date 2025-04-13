/*
 * Copyright © 2025 Anvi Malik
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

 package io.cdap.wrangler.api.parser;

 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 import com.google.gson.JsonElement;
 import com.google.gson.JsonPrimitive;
 
 /**
  * A {@link Token} implementation that parses and represents time durations
  * (e.g., "10s", "500 ms", "2.5 min").
  */
 public class TimeDuration implements Token {
   private final String original;
   private final long milliseconds;
 
   private static final Pattern TIME_PATTERN =
       Pattern.compile("(?i)^([0-9]*\\.?[0-9]+)\\s*(MS|S|SEC|SECONDS|M|MIN|MINUTES)?$");
 
   public TimeDuration(String value) {
     this.original = value;
     this.milliseconds = parseToMilliseconds(value);
   }
 
   private long parseToMilliseconds(String value) {
     Matcher matcher = TIME_PATTERN.matcher(value.trim());
     if (!matcher.matches()) {
       throw new IllegalArgumentException("Invalid time duration format: " + value);
     }
 
     double number = Double.parseDouble(matcher.group(1));
     String unit = matcher.group(2) != null ? matcher.group(2).toUpperCase() : "MS";
 
     switch (unit) {
       case "MS":
         return (long) number;
       case "S":
       case "SEC":
       case "SECONDS":
         return (long) (number * 1000);
       case "M":
       case "MIN":
       case "MINUTES":
         return (long) (number * 60 * 1000);
       default:
         throw new IllegalArgumentException("Unknown unit: " + unit);
     }
   }
 
   public long getMilliseconds() {
     return milliseconds;
   }
 
   @Override
   public Object value() {
     return milliseconds;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIME_DURATION;
   }
 
   @Override
   public JsonElement toJson() {
     return new JsonPrimitive(milliseconds);
   }
 
   @Override
   public String toString() {
     return String.format("TimeDuration{original='%s', ms=%d}", original, milliseconds);
   }
 }

 