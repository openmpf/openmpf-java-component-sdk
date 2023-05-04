/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2023 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2023 The MITRE Corporation                                       *
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
 * The output object for a video detection component.  A single job may produce zero, one, or many video tracks.
 * Each track represents a chain of individual detections, one per frame in the track.  It is possible to have
 * overlapping video tracks within a single job, but every track can have a maximum of one detection per frame.  If
 * there are multiple detections in a single frame, each needs its own track.
 * <br><br>
 * A video track that does not contain any image locations is not meaningful to MPF.
 */
public class MPFVideoTrack {

    private final int startFrame;
    private final int stopFrame;
    private final Map<Integer, MPFImageLocation> frameLocations;
    private final float confidence;
    private final Map<String, String> detectionProperties;

    /**
     * Returns the first frame of the track, inclusive.  In most cases, this should be the frame of the first
     * {@link MPFImageLocation MPFImageLocation}.
     * @return  The start frame for the track.
     */
    public int getStartFrame() {
        return startFrame;
    }

    /**
     *  Returns the last frame of the track, inclusive.  This will not always be the location of the last detection.
     *  In some jobs, not every frame is processed, as in jobs with a FRAME_INTERVAL > 1, which indicates that frames
     *  are skipped in processing.  In those cases, the stop frame is either the end location of the job or the frame
     *  before the first frame where the detection is not found.
     *  <BR><BR>
     *  As an example, if the job only looks at every fifth frame, and a detection is found at frames 5, 10, 15, and 20,
     *  but not 25, the end of the track should be 24, even though there is no evidence that the detection exists from
     *  frames 21-24.  If no end to the detection is found, the stop frame should be the stop frame for the
     *  {@link MPFVideoJob MPFVideoJob}.
     */
    public int getStopFrame() {
        return stopFrame;
    }

    /**
     * Gets the individual detections as a map with the frame number as key and the
     * {@link MPFImageLocation MPFImageLocation} as the value.
     * @return The map of individual detections that make up the track.
     */
    public Map<Integer, MPFImageLocation> getFrameLocations() {
        return frameLocations;
    }

    /**
     * Sets the frame locations for the track.  These are represented as a map with the frame number as key and the
     * {@link MPFImageLocation MPFImageLocation} as the value.  If there are existing frame locations, they will be
     * cleared by this function.
     *
     * @param frameLocations The map of individual detections that make up the track.
     */
    public void setFrameLocations(Map<Integer, MPFImageLocation> frameLocations) {
        this.frameLocations.clear();
        if (frameLocations==null) {
            this.frameLocations.putAll(frameLocations);
        }
    }

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
     * @param startFrame      The first frame of the track.
     * @param stopFrame       The last frame of the track.
     * @param frameLocations  A map of individual detections.  The key for each map entry is the frame where the
     *                        detection was generated, and the value is a {@link MPFImageLocation MPFImageLocation}
     *                        calculated as if that frame was a still image.
     * @param confidence      The confidence level of the track, or -1 if confidence is not calculated.
     * @param detectionProperties  A map of properties for the component.  Property names should be in all-caps.
     */
    public MPFVideoTrack(
        int startFrame,
        int stopFrame,
        Map<Integer, MPFImageLocation> frameLocations,
        float confidence,
        Map<String, String> detectionProperties
    ) {
        this.startFrame = startFrame;
        this.stopFrame = stopFrame;
        this.frameLocations =  new HashMap<>();
        if (frameLocations!=null) {
            this.frameLocations.putAll(frameLocations);
        }
        this.confidence = confidence;
        this.detectionProperties = new HashMap<>();
        if (detectionProperties!=null) {
            this.detectionProperties.putAll(detectionProperties);
        }
    }
}
