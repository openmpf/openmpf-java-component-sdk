/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2022 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2022 The MITRE Corporation                                       *
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

import java.util.Map;

/**
 * A job description for an audio job.  The component will act on a single audio file, with a start and stop time to
 * define the specific range within the file to attend to.
 */
public class MPFAudioJob extends MPFJob {

    private final int startTime;
    private final int stopTime;
    private MPFAudioTrack feedForwardTrack;

    /**
     *  An audio job may contain a feed-forward track from a previous stage
     *  in the job pipeline.
     *
     * @return the feed-forward track.
     */
    public MPFAudioTrack getFeedForwardTrack() {
        return feedForwardTrack;   // Could be null; be sure to check
    }

    /**
     * Create a new job object for an audio job that does not have a feed-forward track.
     *
     * @param jobName   The name of the job being run.  Useful for logging purposes
     * @param dataUri   The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  Audio files have a "DURATION" property,
     *                          represented in milliseconds.
     * @param startTime The beginning of the relevant range within the file. Nothing before the start time will be
     *                  detected.
     * @param stopTime  The end of the relevant range within the file. Nothing after the stop time  will be detected.
     */

    public MPFAudioJob(String jobName, String dataUri, final Map<String, String> jobProperties,
                       final Map <String, String> mediaProperties, int startTime, int stopTime) {
        super(jobName, dataUri, jobProperties, mediaProperties);
        this.startTime=startTime;
        this.stopTime=stopTime;
        this.feedForwardTrack = null;
    }

    /**
     * Create a new job object for an audio job that has a feed-forward track.
     *
     * @param jobName   The name of the job being run.  Useful for logging purposes
     * @param dataUri   The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  Audio files have a "DURATION" property,
     *                          represented in milliseconds.
     * @param startTime The beginning of the relevant range within the file. Nothing before the start time will be
     *                  detected.
     * @param stopTime  The end of the relevant range within the file. Nothing after the stop time  will be detected.
     *                    stopFrame will not be processed by the component.
     * @param track       An instance of an MPFAudioTrack.
     */

    public MPFAudioJob(String jobName,
                       String dataUri,
                       final Map<String, String> jobProperties,
                       final Map <String, String> mediaProperties,
                       int startTime,
                       int stopTime,
                       MPFAudioTrack track) {
        super(jobName, dataUri, jobProperties, mediaProperties);
        this.startTime=startTime;
        this.stopTime=stopTime;
        this.feedForwardTrack=track;
    }

    /**
     * The beginning of the relevant range within the audio file. Everything falling before the startTime will not be
     * processed by the component.
     *
     * @return The beginning of the relevant range for this job, in milliseconds.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * The end of the relevant range within the audio file. Everything falling after the stopTime will not be
     * processed by the component.
     *
     * @return The end of the relevant range for this job, in milliseconds.
     */
    public int getStopTime() {
        return stopTime;
    }
 }

