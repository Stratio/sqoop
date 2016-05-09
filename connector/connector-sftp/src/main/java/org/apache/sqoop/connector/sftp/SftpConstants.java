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
package org.apache.sqoop.connector.sftp;

import java.io.Serializable;

import org.apache.sqoop.job.Constants;

/**
 * Constants for FTP connector.
 */
public final class SftpConstants extends Constants implements Serializable {

  /**
   * Name of resource bundle for configuring this connector.
   */
  public static final String RESOURCE_BUNDLE_NAME = "sftp-connector-config";

  /**
   * Default port for FTP.
   */
  public static final int DEFAULT_PORT = 22;
}
