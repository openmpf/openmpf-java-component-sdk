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

package org.mitre.mpf.component.api.detection;

import java.util.HashMap;
import java.util.Map;

/**
 * The output object for a generic detection component.  A single job may produce zero, one, or many generic tracks.
 * Each track represents a single detection for generic media.
 */
public class MPFGenericTrack {

    private final float confidence;
    private final Map<String, String> detectionProperties;

    /**
     * Returns confidence level for this track.  Confidence represents the level of certainty about the detection.
     * Higher confidence levels represent a higher degree of certainty.  If no confidence is calculated, this should
     * be set to -1.
     *
     * @return The confidence level of the track, or -1 if confidence is not calculated.
     */
    public float getConfidence() {
        return confidence;
    }

    /**
     * Gets the map of properties generated by the component for this track.  These vary depending on the component.
     * Keys should be in all caps.
     *
     * @return  A map of properties for this track.
     */
    public Map<String, String> getDetectionProperties() {
        return detectionProperties;
    }

    /**
     * Constructor for the class.
     *
     * @param confidence  The confidence level of the track, or -1 if confidence is not calculated.
     * @param detectionProperties  A map of properties for the component.  Property names should be in all-caps.
     */
    public MPFGenericTrack(
        float confidence,
        Map<String, String> detectionProperties
    ) {
        this.confidence = confidence;
        this.detectionProperties = new HashMap<String,String>();
        if (detectionProperties!=null) {
            this.detectionProperties.putAll(detectionProperties);
        }
    }
}
