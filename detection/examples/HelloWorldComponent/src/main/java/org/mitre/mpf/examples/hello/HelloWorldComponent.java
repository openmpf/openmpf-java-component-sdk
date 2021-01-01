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

package org.mitre.mpf.examples.hello;

import org.mitre.mpf.component.api.detection.*;

import java.util.*;

public class HelloWorldComponent extends MPFDetectionComponentBase {

    // Handles the case where the media is a video type.
    public List<MPFVideoTrack> getDetections(MPFVideoJob mpfVideoJob) throws MPFComponentDetectionError {

        // The MPFVideoJob object contains all of the details needed to
        // process a video.
        System.out.println(
                String.format("[%s] Processing %s from frame %d to frame %d.",
                        mpfVideoJob.getJobName(),
                        mpfVideoJob.getDataUri(),
                        mpfVideoJob.getStartFrame(),
                        mpfVideoJob.getStopFrame()));

        // The MPFVideoJob object contains two Map entries, one
        // that contains job-specific properties, and one that contains
        // media-specific properties.  The frame processing interval is one
        // example of a job-specific property.
        System.out.println(
                String.format("[%s] Job properties contains FRAME_INTERVAL with a value of %s.",
                        mpfVideoJob.getJobName(),
                        mpfVideoJob.getJobProperties().get("FRAME_INTERVAL")));

        // ==========================
        // Detection logic goes here.
        // ==========================

        // A video track is made up of a series of image detections.  First, we build the image set.
        //------------------------------------------------------------------------------------------

        float imageConfidence = 0.80f;

        // The MPFImageLocation object contains a Map of properties
        // that can be used to return component-specific information
        // about the image in a particular frame. Here we add
        // "METADATA", which might be used, for example, to return the
        // pose of the object detected in the frame.
        Map<String, String> imageDetectionProperties = new HashMap<String, String>();
        imageDetectionProperties.put("METADATA", "extra image location info");

        // Do something with the feed forward track if it exists
        MPFVideoTrack feedForwardTrack = mpfVideoJob.getFeedForwardTrack();
        if (feedForwardTrack != null) {
            Integer locationKey = (Integer) feedForwardTrack.getFrameLocations().keySet().toArray()[0];
            if (locationKey != null) {
                MPFImageLocation feedForwardLocation = feedForwardTrack.getFrameLocations().get(locationKey);
                int feedForwardCount = 1;
                if (feedForwardLocation.getDetectionProperties().containsKey("FEED_FORWARD_COUNT")){
                    feedForwardCount += Integer.parseInt(feedForwardLocation.getDetectionProperties().get("FEED_FORWARD_COUNT"));
                }
                imageDetectionProperties.put("FEED_FORWARD_COUNT", Integer.toString(feedForwardCount));
            }
        }

        MPFImageLocation imageLocation = new MPFImageLocation(0, 0, 100, 100, imageConfidence, imageDetectionProperties);

        Map<Integer, MPFImageLocation> frameLocations = new HashMap<Integer, MPFImageLocation>();
        frameLocations.put(mpfVideoJob.getStartFrame(), imageLocation);

        float videoConfidence = 0.8f;

        // The MPFVideoTrack object also contains a Map of properties that
        // can be used to return component-specific information about the
        // track. Here we add "METADATA", which might be used, for
        // example, to return the type of the object that is tracked.
        Map<String, String> detectionProperties = new HashMap<String, String>();
        detectionProperties.put("METADATA", "extra video track info");

        // Do something with the feed forward track if it exists
        if (feedForwardTrack != null) {
            int feedForwardCount = 1;
            if (feedForwardTrack.getDetectionProperties().containsKey("FEED_FORWARD_COUNT")) {
                feedForwardCount += Integer.parseInt(feedForwardTrack.getDetectionProperties().get("FEED_FORWARD_COUNT"));
            }
            detectionProperties.put("FEED_FORWARD_COUNT", Integer.toString(feedForwardCount));
        }

        // Construct the track from the assembled parameter objects.
        // ---------------------------------------------------------

        MPFVideoTrack videoTrack = new MPFVideoTrack(
                mpfVideoJob.getStartFrame(),
                mpfVideoJob.getStopFrame(),
                frameLocations,
                videoConfidence,
                detectionProperties
                );

        List<MPFVideoTrack> tracks = new LinkedList<MPFVideoTrack>();
        tracks.add(videoTrack);

        System.out.println(
                String.format("[%s] Processing complete. Generated %d dummy video tracks.",
                        mpfVideoJob.getJobName(),
                        tracks.size()));

        return tracks;
    }

    // Handles the case where the media is an audio type.
    public List<MPFAudioTrack> getDetections(MPFAudioJob mpfAudioJob) throws MPFComponentDetectionError {

        // The MPFAudioJob object contains all of the details needed to
        // process an audio file.
        System.out.println(
                String.format("[%s] Processing %s from start time %d ms to stop time %d ms.",
                        mpfAudioJob.getJobName(),
                        mpfAudioJob.getDataUri(),
                        mpfAudioJob.getStartTime(),
                        mpfAudioJob.getStopTime()));

        // NOTE: A stop_time parameter of -1 means process the whole file.

        // =========================
        // Detection logic goes here
        // =========================

        float confidence = 0.80f;

        // The MPFAudioTrack object contains a Map of properties that
        // can be used to return component-specific information about the
        // track. Here we add "METADATA", which might be used, for
        // example, to return the phrase recognized in the audio track.
        Map<String, String> audioDetectionProperties = new HashMap<String, String>();
        audioDetectionProperties.put("METADATA", "extra audio track info");

        // Do something with the feed forward track if it exists
        MPFAudioTrack feedForwardTrack = mpfAudioJob.getFeedForwardTrack();
        if (feedForwardTrack != null) {
            int feedForwardCount = 1;
            if (feedForwardTrack.getDetectionProperties().containsKey("FEED_FORWARD_COUNT")) {
                feedForwardCount += Integer.parseInt(feedForwardTrack.getDetectionProperties().get("FEED_FORWARD_COUNT"));
            }
            audioDetectionProperties.put("FEED_FORWARD_COUNT", Integer.toString(feedForwardCount));
        }

        MPFAudioTrack audioTrack = new MPFAudioTrack(mpfAudioJob.getStartTime(), mpfAudioJob.getStartTime() + 1, confidence, audioDetectionProperties);

        List<MPFAudioTrack> tracks = new LinkedList<MPFAudioTrack>();
        tracks.add(audioTrack);

        System.out.println(
                String.format("[%s] Processing complete. Generated %d dummy audio tracks.",
                        mpfAudioJob.getJobName(),
                        tracks.size()));

        return tracks;
    }

    // Handles the case where the media is an image type.
    public List<MPFImageLocation> getDetections(MPFImageJob mpfImageJob) throws MPFComponentDetectionError {

        // The MPFImageJob object contains all of the details needed to
        // process an image file.
        System.out.println(
                String.format("[%s] Processing %s.",
                        mpfImageJob.getJobName(),
                        mpfImageJob.getDataUri()));

        // =========================
        // Detection logic goes here
        // =========================

        float confidence = 0.80f;

        // The MPFImageLocation object contains a Map of properties that
        // can be used to return component-specific information about the
        // image. Here we add "METADATA", which might be used, for
        // example, to return the type of object detected in the image.
        Map<String, String> imageDetectionProperties = new HashMap<String, String>();
        imageDetectionProperties.put("METADATA", "extra image location info");

        // Do something with the feed forward location if it exists
        MPFImageLocation feedForwardLocation = mpfImageJob.getFeedForwardLocation();
        if (feedForwardLocation != null) {
            int feedForwardCount = 1;
            if (feedForwardLocation.getDetectionProperties().containsKey("FEED_FORWARD_COUNT")) {
                feedForwardCount += Integer.parseInt(feedForwardLocation.getDetectionProperties().get("FEED_FORWARD_COUNT"));
            }
            imageDetectionProperties.put("FEED_FORWARD_COUNT", Integer.toString(feedForwardCount));
        }

        MPFImageLocation imageLocation = new MPFImageLocation(0, 0, 100, 100, confidence, imageDetectionProperties);

        List<MPFImageLocation> locations = new LinkedList<MPFImageLocation>();
        locations.add(imageLocation);

        System.out.println(
                String.format("[%s] Processing complete. Generated %d dummy image locations.",
                        mpfImageJob.getJobName(),
                        locations.size()));

        return locations;
    }

    // Handles the case where the media is a generic type.
    public List<MPFGenericTrack> getDetections(MPFGenericJob mpfGenericJob) throws MPFComponentDetectionError {

        // The MPFGenericJob object contains all of the details needed to
        // process a generic file.
        System.out.println(
                String.format("[%s] Processing %s.",
                        mpfGenericJob.getJobName(),
                        mpfGenericJob.getDataUri()));

        // =========================
        // Detection logic goes here
        // =========================

        float confidence = 0.80f;

        // The MPFGenericTrack object contains a Map of properties that
        // can be used to return component-specific information about the
        // track. Here we add "METADATA", which might be used, for
        // example, to return information about the generic track.
        Map<String, String> genericDetectionProperties = new HashMap<String, String>();
        genericDetectionProperties.put("METADATA", "extra generic track info");

        // Do something with the feed forward track if it exists
        MPFGenericTrack feedForwardTrack = mpfGenericJob.getFeedForwardTrack();
        if (feedForwardTrack != null) {
            int feedForwardCount = 1;
            if (feedForwardTrack.getDetectionProperties().containsKey("FEED_FORWARD_COUNT")) {
                feedForwardCount += Integer.parseInt(feedForwardTrack.getDetectionProperties().get("FEED_FORWARD_COUNT"));
            }
            genericDetectionProperties.put("FEED_FORWARD_COUNT", Integer.toString(feedForwardCount));
        }

        MPFGenericTrack genericTrack = new MPFGenericTrack(confidence, genericDetectionProperties);

        List<MPFGenericTrack> tracks = new LinkedList<MPFGenericTrack>();
        tracks.add(genericTrack);

        System.out.println(
                String.format("[%s] Processing complete. Generated %d dummy generic tracks.",
                        mpfGenericJob.getJobName(),
                        tracks.size()));

        return tracks;
    }

    public boolean supports(MPFDataType mpfDataType) {
        // This HelloWorld example component supports all data types.
        if (mpfDataType != null &&
                (MPFDataType.AUDIO.equals(mpfDataType)
                 || MPFDataType.VIDEO.equals(mpfDataType)
                 || MPFDataType.IMAGE.equals(mpfDataType)
                 || MPFDataType.UNKNOWN.equals(mpfDataType))) {
            return true;
        }
        return false;
    }

    public String getDetectionType() {
        return "HELLO";
    }
}
