/*
 * Copyright (C) 2016 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sqoop.connector.hdfs.configuration;

import java.io.Serializable;

import org.apache.sqoop.model.ConfigClass;
import org.apache.sqoop.model.Input;
import org.apache.sqoop.validation.Status;
import org.apache.sqoop.validation.validators.AbstractValidator;
import org.joda.time.DateTime;

@ConfigClass
public class IncrementalRead implements Serializable {
  @Input
  public IncrementalType incrementalType;

  @Input
  public DateTime lastImportedDate;

  public static class ConfigValidator extends AbstractValidator<IncrementalRead> {
    @Override
    public void validate(IncrementalRead conf) {
      if(conf.incrementalType != IncrementalType.NEW_FILES && conf.lastImportedDate != null) {
        addMessage(Status.ERROR, "Can't specify last imported date without enabling incremental import.");
      }
    }
  }
}
