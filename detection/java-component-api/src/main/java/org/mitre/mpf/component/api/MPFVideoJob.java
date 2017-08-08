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

package org.mitre.mpf.component.api;

import java.util.Map;
import org.mitre.mpf.component.api.detection.MPFVideoTrack;

/**
 * A job description for a video job.  The component will act on a single video file, with a start and stop frame to
 * define the specific range within the file to attend to.
 */
public class MPFVideoJob extends MPFJob {

    private final int startFrame;
    private final int stopFrame;
    private final boolean hasFeedForwardTrack;
    private MPFVideoTrack feedForwardTrack;

    /**
     *  The first frame of the relevant range within the video. Frames before the startFrame will not be processed
     *  by the component.
     *
     *  @return The first frame to process in this job, inclusive.
     */
    public int getStartFrame() {
        return startFrame;
    }

    /**
     *  The last frame of the relevant range within the video. Frames after the stopFrame will not be processed
     *  by the component.
     *
     *  @return The last frame to process in this job, inclusive.
     */
    public int getStopFrame() {
        return stopFrame;
    }

    /**
     *  A VideoJob may contain a feed-forward track from a previous stage
     *  in the job pipeline.
     *
     * @return true if the job contains a valid feed-forward track; otherwise, false.
     */
    public boolean hasFeedForwardTrack() {
	return hasFeedForwardTrack;
    }

    /**
     *  A VideoJob may contain a feed-forward track from a previous stage
     *  in the job pipeline.
     *
     * @return the feed-forward track
     */
    public MPFVideoTrack getFeedForwardTrack() {
	return feedForwardTrack;
    }

    /**
     * Create a new job object for a video job that has no feed-forward track.
     *
     * @param jobName     The name of the job being run.  Useful for logging purposes
     * @param dataUri     The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  Video files have
     *                    "DURATION" and "FPS" (frames per second) properties.
     * @param startFrame  The first frame of the relevant range within the video. Frames before
     *                    the startFrame will not be processed by the component.
     * @param stopFrame   The last frame of the relevant range within the video. Frames after the
     *                    stopFrame will not be processed by the component.
     */
    public MPFVideoJob(String jobName, String dataUri, final Map<String, String> jobProperties,
                       final Map <String, String> mediaProperties, int startFrame, int stopFrame) {
        super(jobName, dataUri, jobProperties, mediaProperties);
        this.startFrame=startFrame;
        this.stopFrame=stopFrame;
	this.hasFeedForwardTrack=false;
    }

    /**
     * Create a new job object for a video job that has a feed-forward track.
     *
     * @param jobName     The name of the job being run.  Useful for logging purposes
     * @param dataUri     The URI for the piece of media being processed.
     * @param jobProperties  Values for any properties the component requires.
     * @param mediaProperties   Properties about the media being processed.  Video files have
     *                    "DURATION" and "FPS" (frames per second) properties.
     * @param startFrame  The first frame of the relevant range within the video. Frames before
     *                    the startFrame will not be processed by the component.
     * @param stopFrame   The last frame of the relevant range within the video. Frames after the
     *                    stopFrame will not be processed by the component.
     * @param track       An instance of an MPFVideoTrack.
     */
    public MPFVideoJob(String jobName,
		       String dataUri,
		       final Map<String, String> jobProperties,
                       final Map <String, String> mediaProperties,
		       int startFrame,
		       int stopFrame,
		       MPFVideoTrack track) {
        super(jobName, dataUri, jobProperties, mediaProperties);
        this.startFrame=startFrame;
        this.stopFrame=stopFrame;
	this.hasFeedForwardTrack=true;
	this.feedForwardTrack=track;
    }
}
