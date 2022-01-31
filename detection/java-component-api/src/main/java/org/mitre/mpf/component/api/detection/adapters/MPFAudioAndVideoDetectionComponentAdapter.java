/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2021 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2021 The MITRE Corporation                                       *
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

package org.mitre.mpf.component.api.detection.adapters;

import org.mitre.mpf.component.api.detection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class MPFAudioAndVideoDetectionComponentAdapter extends MPFDetectionComponentBase {

    private static final Logger LOG = LoggerFactory.getLogger(MPFAudioAndVideoDetectionComponentAdapter.class);

    public List<MPFVideoTrack> getDetections(MPFVideoJob job) throws MPFComponentDetectionError {
        LOG.debug("jobName = {}, startFrame = {}, stopFrame = {}, dataUri = {}, size of jobProperties = {}, size of mediaProperties = {}",
                job.getJobName(), job.getStartFrame(), job.getStopFrame(), job.getDataUri(),
                job.getJobProperties().size(), job.getMediaProperties().size());

        List<MPFVideoTrack> tracks = new LinkedList<>();
        Map<String,String> mediaProperties = job.getMediaProperties();

        Integer frameCount;
        try {
            if (mediaProperties.get("FRAME_COUNT") == null) {
                LOG.error("Could not obtain video frame count.");
                throw new MPFComponentDetectionError(MPFDetectionError.MPF_MISSING_PROPERTY, "Could not obtain video frame count.");
            }
            frameCount = Integer.valueOf(mediaProperties.get("FRAME_COUNT"));
        } catch (NumberFormatException ex) {
            LOG.error("Could not obtain video frame count.");
            throw new MPFComponentDetectionError(MPFDetectionError.MPF_INVALID_PROPERTY, "FRAME_COUNT property is not an integer value.");
        }

        Float fps;
        try {
            if (mediaProperties.get("FPS") == null) {
                LOG.error("Could not obtain video frame rate.");
                throw new MPFComponentDetectionError(MPFDetectionError.MPF_MISSING_PROPERTY, "Could not obtain video frame rate.");
            }
            fps = Float.valueOf(mediaProperties.get("FPS"));
        } catch (NumberFormatException ex) {
            LOG.error("Could not obtain video frame rate.");
            throw new MPFComponentDetectionError(MPFDetectionError.MPF_INVALID_PROPERTY, "FPS property is an invalid floating-point value.");
        }

        Integer duration;
        try {
            if (mediaProperties.get("DURATION") == null) {
                LOG.error("Could not obtain duration.");
                throw new MPFComponentDetectionError(MPFDetectionError.MPF_MISSING_PROPERTY, "Could not obtain duration.");
            }
            duration = Integer.valueOf(mediaProperties.get("DURATION"));
        }  catch (NumberFormatException ex) {
            LOG.error("Could not obtain duration.");
            throw new MPFComponentDetectionError(MPFDetectionError.MPF_INVALID_PROPERTY, "DURATION property is not an integer value.");
        }

        // determine actual start and stop frames

        int startFrame = job.getStartFrame();
        int stopFrame = job.getStopFrame();

        float fpms =  fps / 1000;
        int startTime = (int) (startFrame / fpms);
        int stopTime;

        // The WFM will pass a job stop frame equal to FRAME_COUNT-1 for the last video segment.
        // We want to use the detected DURATION in such cases instead to ensure we process the entire audio track.
        // Only use the job stop frame if it differs from FRAME_COUNT-1.
        if (stopFrame < frameCount - 1) {
            stopTime = (int) (stopFrame / fpms);
        } else if (duration > 0) {
            stopTime = duration;
        } else if (frameCount > 0) {
            stopTime = (int) (frameCount / fpms);
        } else {
            stopTime = 0;
        }

        // get audio tracks

        List<MPFAudioTrack> audioTracks = getDetections(new MPFAudioJob(job.getJobName(), job.getDataUri(), job.getJobProperties(), mediaProperties, startTime, stopTime));
        LOG.info("Results: {}", audioTracks.size());

        // convert audio tracks to video tracks
        for (MPFAudioTrack audioTrack : audioTracks) {
            LOG.debug("Track start time: {}, track stop time: {}", audioTrack.getStartTime(), audioTrack.getStopTime());
            int trackStartFrame = (int) Math.floor(fpms * audioTrack.getStartTime());
            int trackStopFrame  = (int) Math.ceil(fpms * audioTrack.getStopTime());

            MPFVideoTrack videoTrack = new MPFVideoTrack(trackStartFrame, trackStopFrame,
                    new HashMap<>(), audioTrack.getConfidence(), audioTrack.getDetectionProperties());

            videoTrack.getFrameLocations().put(trackStartFrame, new MPFImageLocation(0, 0, 0, 0, audioTrack.getConfidence(), audioTrack.getDetectionProperties()));

            tracks.add(videoTrack);
            LOG.info("Added video track: startFrame {}, stopFrame {}, location count {}", trackStartFrame, trackStopFrame, videoTrack.getFrameLocations().size());
        }

        return tracks;
    }

    public abstract List<MPFAudioTrack> getDetections(MPFAudioJob job) throws MPFComponentDetectionError;

    public List<MPFImageLocation> getDetections(MPFImageJob job) throws MPFComponentDetectionError {
        throw new MPFComponentDetectionError(MPFDetectionError.MPF_UNSUPPORTED_DATA_TYPE, "Image detection not supported.");
    }

    public List<MPFGenericTrack> getDetections(MPFGenericJob job) throws MPFComponentDetectionError {
        throw new MPFComponentDetectionError(MPFDetectionError.MPF_UNSUPPORTED_DATA_TYPE, "Generic detection not supported.");
    }

    public boolean supports(MPFDataType dataType) {
        return (MPFDataType.AUDIO == dataType) || (MPFDataType.VIDEO == dataType);
    }

    public abstract String getDetectionType();
}
