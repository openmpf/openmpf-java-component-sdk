/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2020 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2020 The MITRE Corporation                                       *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *    http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package org.mitre.mpf.component.api;

/**
 * The MPFComponentInterface provides a baseline interface for all potential components.  It should not be directly
 * implemented, because no mechanism exists for launching components based off of it.  Instead, it defines the contract
 * that all types of components must follow.  Currently, the only supported type of component is "DETECTION", so all
 * components must implement the
 * {@link org.mitre.mpf.component.api.detection.MPFDetectionComponentInterface MPFDetectionComponentInterface}.
 */
public interface MPFComponentInterface {

    /**
     * Returns the type of component this is.
     * @return The {@link MPFComponentType MPFComponentType} value for the component.
     */
    public MPFComponentType getComponentType();

    /**
     * Performs any necessary startup tasks for the component.  This will be executed once, on component startup, and
     * not for every job.
     */
    public void init();

    /**
     * Performs any necessary shutdown tasks for the component.  This will be executed once, on component shutdown, and
     * not for every job.
     */
    public void close();

    /**
     * Sets the directory in which the component should run.
     * @param runDirectory The complete path name for the run directory.
     */
    public void setRunDirectory(String runDirectory);

    /**
     * Gets the directory in which the component is running.
     * @return  The complete path name for the run directory.
     */
    public String getRunDirectory();

}
