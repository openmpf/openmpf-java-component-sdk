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

package org.mitre.mpf.component.api.detection;

import org.mitre.mpf.component.api.MPFAudioJob;
import org.mitre.mpf.component.api.MPFComponentInterface;
import org.mitre.mpf.component.api.MPFImageJob;
import org.mitre.mpf.component.api.MPFVideoJob;
import org.mitre.mpf.component.api.exceptions.MPFComponentDetectionError;

import java.util.List;

/**
 * Every Java detection component must define a "component" bean which implements the MPFComponentInterface.
 * This interface defines the contract between the MPF component executor and the Java components.
 */
public interface MPFDetectionComponentInterface extends MPFComponentInterface {

    /**
     * Gets the set of detections from a video file.  Videos may be segmented for parallel processing.
     * Only frames within the specified range will be processed.
     *
     * @param job     The job to be run.
     *
     * @return  A list of video tracks generated by the component.
     * @throws  MPFComponentDetectionError If an error occurs while processing the job.  The exception will contain
     *                                     an MPFDetectionError.
     */
    List<MPFVideoTrack> getDetections(MPFVideoJob job) throws MPFComponentDetectionError;

    /**
     * Gets the set of detections from an audio file.  Audio files may be segmented for parallel processing.
     * Only the specified range will be processed.
     *
     * @param job     The job to be run.
     *
     * @return  A list of audio tracks generated by the component.
     * @throws  MPFComponentDetectionError If an error occurs while processing the job.  The exception will contain
     *                                     an MPFDetectionError.
     */
    List<MPFAudioTrack> getDetections(MPFAudioJob job) throws MPFComponentDetectionError;

    /**
     * Gets the set of detections from an image file.
     *
     * @param job     The job to be run.
     *
     * @return  A list of image locations generated by the component.
     * @throws  MPFComponentDetectionError If an error occurs while processing the job.  The exception will contain
     *                                     an MPFDetectionError.
     */
    List<MPFImageLocation> getDetections(MPFImageJob job) throws MPFComponentDetectionError;

    /**
     * Checks whether the component supports a given data type.
     *
     * @param dataType The data type to check.
     * @return  True if the data type is supported by this component.
     */
    boolean supports(MPFDataType dataType);

    /**
     * Gets the detection type for the component.
     *
     * @return The detection type.
     */
    String getDetectionType();
}