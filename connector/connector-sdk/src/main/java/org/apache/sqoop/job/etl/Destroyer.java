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
package org.apache.sqoop.job.etl;

import org.apache.sqoop.classification.InterfaceAudience;
import org.apache.sqoop.classification.InterfaceStability;

/**
 * This allows connector to define work to complete execution, for example,
 * resource cleaning.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract class Destroyer<LinkConfiguration, JobConfiguration> {

  /**
   * Callback to clean up after job execution
   *
   * @param context Destroyer context
   * @param linkConfiguration link configuration object
   * @param jobConfiguration job configuration object for the FROM and TO
   *        In case of the FROM destroyer this will represent the FROM job configuration
   *        In case of the TO destroyer this will represent the TO job configuration
   */
  public abstract void destroy(DestroyerContext context,
                               LinkConfiguration linkConfiguration,
                               JobConfiguration jobConfiguration);

  /** Callback to update configuration objects given values in context.
   *
   * This callback will be called only after successful execution of the job. It will
   * might be executed on different machine then other callbacks and hence it should not
   * use any state stored within the instance.
   *
   * This method is designed to update configuration objects for next job run, so that
   * user can move data in incremental fashion. Sqoop framework will update the configuration
   * objects in repository after calling this method.
   *
   * @param context Destroyer context
   * @param linkConfiguration Link configuration object
   * @param jobConfiguration Job configuration object
   */
  public void updateConfiguration(DestroyerContext context,
                                  LinkConfiguration linkConfiguration,
                                  JobConfiguration jobConfiguration) {
    // Default implementation does nothing
  }
}
