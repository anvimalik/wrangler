/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

 package io.cdap.wrangler.test.java;

 import java.util.List;

 import io.cdap.wrangler.Row;
 import io.cdap.wrangler.test.TestingRig;
 import io.cdap.wrangler.test.api.TestRecipe;

 import org.junit.Assert;
 import org.junit.Test;
 /*
  * this is a test for AggregateStats
  */
 public class AggregateStatsDirectiveTest {
 
   @Test
   public void testAggregateStatsForInteger() throws Exception {
     TestRecipe recipe = new TestRecipe();
     recipe.add("aggregate-stats int_field");
 
     Row row1 = new Row("int_field", 10);
     Row row2 = new Row("int_field", 20);
     Row row3 = new Row("int_field", 30);
 
     List<Row> results = TestingRig.execute(recipe.toArray(), row1, row2, row3);
 
     Assert.assertEquals(1, results.size());
 
     Row aggregated = results.get(0);
     Assert.assertEquals(3L, aggregated.getValue("int_field_count"));
     Assert.assertEquals(60L, aggregated.getValue("int_field_sum"));
     Assert.assertEquals(10L, aggregated.getValue("int_field_min"));
     Assert.assertEquals(30L, aggregated.getValue("int_field_max"));
     Assert.assertEquals(20L, aggregated.getValue("int_field_avg"));
   }
 
   @Test
   public void testAggregateStatsForByteSize() throws Exception {
     TestRecipe recipe = new TestRecipe();
     recipe.add("aggregate-stats size_field");
 
     Row row1 = new Row("size_field", 1024L); // 1KB
     Row row2 = new Row("size_field", 2048L); // 2KB
     Row row3 = new Row("size_field", 3072L); // 3KB
 
     List<Row> results = TestingRig.execute(recipe.toArray(), row1, row2, row3);
 
     Assert.assertEquals(1, results.size());
 
     Row aggregated = results.get(0);
     Assert.assertEquals(3L, aggregated.getValue("size_field_count"));
     Assert.assertEquals(6144L, aggregated.getValue("size_field_sum"));
     Assert.assertEquals(1024L, aggregated.getValue("size_field_min"));
     Assert.assertEquals(3072L, aggregated.getValue("size_field_max"));
     Assert.assertEquals(2048L, aggregated.getValue("size_field_avg"));
   }
 }
 