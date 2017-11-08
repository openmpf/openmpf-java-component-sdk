/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2017 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2017 The MITRE Corporation                                       *
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

package org.mitre.mpf.component.api.detection;

import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a job to be performed by the component.
 */
public abstract class MPFJob {

    private final String jobName;
    private final String dataUri;
    private final Map<String, String> jobProperties = new HashMap<>();
    private final Map<String, String> mediaProperties = new HashMap<>();

    /**
     * Returns a unique name for the job.  This is used mainly for logging purposes.
     * @return The unique job name.
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * The URI for the input medium which will be processed by the job.
     * @return  The string URI for the medium.
     */
    public String getDataUri() {
        return dataUri;
    }

    /**
     * A set of all configurable properties which will be used in the job.  All job properties should have an
     * all-caps key.
     * @return  The map of properties.
     */
    public Map<String, String> getJobProperties() {
        return jobProperties;
    }

    /**
     * A set of all properties which were detected from the media metadata.  The set of available properties will vary
     * depending on the media type.  All media properties will have an all-caps key.
     * @return The map of media properties for the job.
     */
    public Map<String, String> getMediaProperties() {
        return mediaProperties;
    }

    /**
     * Create a new job object.
     *
     * @param jobName    The name of the job being run.  Useful for logging purposes
     * @param dataUri    The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  The set of available properties
     *                          depends on the data type of the media.
     */
    protected MPFJob(String jobName, String dataUri, final Map<String, String> jobProperties,
                     final Map <String, String> mediaProperties) {
        this.jobName = jobName;
        this.dataUri = dataUri;
        this.jobProperties.putAll(jobProperties);
        this.mediaProperties.putAll(mediaProperties);
    }


}

