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
package org.apache.sqoop.validation.validators;

import org.apache.sqoop.validation.Message;
import org.apache.sqoop.validation.Status;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 */
public class TestNotEmpty {

  AbstractValidator validator = new NotEmpty();

  @Test
  public void test() {
    List<Message> messages;
    assertEquals(0, validator.getMessages().size());

    validator.validate("Non empty");
    assertEquals(0, validator.getMessages().size());

    validator.validate("");
    assertEquals(1, validator.getMessages().size());
    messages = validator.getMessages();
    assertEquals(Status.ERROR, messages.get(0).getStatus());

    validator.reset();
    assertEquals(0, validator.getMessages().size());

    validator.validate(null);
    assertEquals(1, validator.getMessages().size());
    messages = validator.getMessages();
    assertEquals(Status.ERROR, messages.get(0).getStatus());
  }
}
