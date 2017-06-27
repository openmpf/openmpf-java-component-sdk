/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2016 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2016 The MITRE Corporation                                       *
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

import java.util.Map;

/**
 * A job description for an image job.  The component will act on a single image medium.
 */
public class MPFImageJob extends MPFJob {

    /**
     * Create a new job object for an image job.
     *
     * @param jobName    The name of the job being run.  Useful for logging purposes
     * @param dataUri    The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  Image files with EXIF metadata will
     *                          have "ROTATION", "HORIZONTAL_FLIP", and "EXIF_ORIENTATION" flags.  If the image source
     *                          does not have EXIF metadata, no properties will be set.
     */
    public MPFImageJob(String jobName, String dataUri, final Map<String, String> jobProperties,
                      final Map <String, String> mediaProperties) {
        super(jobName, dataUri, jobProperties, mediaProperties);
    }
}
