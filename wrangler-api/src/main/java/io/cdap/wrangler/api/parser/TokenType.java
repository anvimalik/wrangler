/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

 package io.cdap.wrangler.api.parser;

 import java.io.Serializable;
 
 import io.cdap.wrangler.api.annotations.PublicEvolving;
 
 /**
  * The TokenType enum provides the types of tokens supported by the grammar.
  *
  * <p>Each enumerated type is typically associated with a specific token class,
  * such as {@code DIRECTIVE_NAME} being represented by the {@code DirectiveName} class.</p>
  */
 @PublicEvolving
 public enum TokenType implements Serializable {
 
   /**
    * Token type for directive names in the recipe.
    */
   DIRECTIVE_NAME,
 
   /**
    * Token type for a single column name.
    */
   COLUMN_NAME,
 
   /**
    * Token type for quoted text values.
    */
   TEXT,
 
   /**
    * Token type for numeric values (integers or real numbers).
    */
   NUMERIC,
 
   /**
    * Token type for boolean values ("true" or "false").
    */
   BOOLEAN,
 
   /**
    * Token type for a list of column names separated by commas.
    */
   COLUMN_NAME_LIST,
 
   /**
    * Token type for a list of quoted text strings separated by commas.
    */
   TEXT_LIST,
 
   /**
    * Token type for a list of numeric values separated by commas.
    */
   NUMERIC_LIST,
 
   /**
    * Token type for a list of boolean values separated by commas.
    */
   BOOLEAN_LIST,
 
   /**
    * Token type for expressions or conditions.
    */
   EXPRESSION,
 
   /**
    * Token type for key-value property pairs.
    */
   PROPERTIES,
 
   /**
    * Token type for a collection of range mappings in the format:
    * <start>:<end>=value[,<start>:<end>=value]*
    */
   RANGES,
 
   /**
    * Token type for identifiers with restricted characters.
    */
   IDENTIFIER,
 
   /**
    * Token type for values expressed in byte size format (e.g. 10KB, 5MB).
    */
   BYTE_SIZE,
 
   /**
    * Token type for time duration values (e.g. 30s, 5m, 2h).
    */
   TIME_DURATION,
 
   /**
    * Token type specifically for integer numeric values.
    */
   INTEGER,
 
   /**
    * Token type specifically for double precision floating-point values.
    */
   DOUBLE
 }
 
 