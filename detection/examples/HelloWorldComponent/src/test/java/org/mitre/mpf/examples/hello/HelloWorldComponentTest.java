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

package org.mitre.mpf.examples.hello;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mitre.mpf.component.api.detection.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The test class provides the framework for developing Java components.  Test cases can be prepared for a variety
 * of input conditions, and can cover successful executions as well as error conditions.  In most cases, if the
 * getDetections() and support() methods are correctly implemented, the component will work properly.  In cases where
 * the init() or close() methods are overridden, those also should be tested.
 */
public class HelloWorldComponentTest {

    private HelloWorldComponent helloWorldComponent;

    @Before
    public void setUp() {
        helloWorldComponent = new HelloWorldComponent();
        helloWorldComponent.init();
    }

    @After
    public void tearDown() {
        helloWorldComponent.close();
    }

    @Test
    public void testGetDetectionsImage() throws Exception {
        String uri = "RandomImageFile";

        Map<String, String> jobProperties = new HashMap<String, String>();
        Map<String, String> mediaProperties = new HashMap<String, String>();

        MPFImageJob imageJob = new MPFImageJob("TestImageJob", uri, jobProperties, mediaProperties);
        try {
            List<MPFImageLocation> locations = helloWorldComponent.getDetections(imageJob);
            System.out.println(String.format("Number of image locations = %d.", locations.size()));
            assertEquals("Number of image locations does not match.", 1, locations.size());

            for (int i = 0; i < locations.size(); i++) {
                MPFImageLocation location = locations.get(i);
                System.out.println(String.format("Image location number %d", i));
                System.out.println(String.format("  x left upper = %d", location.getXLeftUpper()));
                System.out.println(String.format("  y left upper = %d", location.getYLeftUpper()));
                System.out.println(String.format("  width = %d", location.getWidth()));
                System.out.println(String.format("  height = %d", location.getHeight()));
                System.out.println(String.format("  confidence = %f", location.getConfidence()));
                System.out.println(String.format("  metadata = %s", location.getDetectionProperties().get("METADATA")));


                assertEquals("x left upper does not match.", 0, location.getXLeftUpper());
                assertEquals("y left upper does not match.", 0, location.getYLeftUpper());
                assertEquals("width does not match.", 100, location.getWidth());
                assertEquals("height does not match.", 100, location.getHeight());
                assertEquals("confidence does not match.", 0.8f, location.getConfidence(), 0.01f);
                assertEquals("metadata does not match.", "extra image location info", location.getDetectionProperties().get("METADATA"));
            }
        } catch (MPFComponentDetectionError e) {
            System.err.println(String.format("An error occurred of type %s", e.getDetectionError().name()));
            fail(String.format("An error has occurred of type %s.", e.getDetectionError().name()));
        }
    }

    @Test
    public void testGetDetectionsVideo() throws Exception {
        String uri = "RandomVideoFile";

        Map<String, String> jobProperties = new HashMap<String, String>();
        Map<String, String> mediaProperties = new HashMap<String, String>();

        int startFrame = 100;
        int stopFrame = 199;
        String frameInterval = "5";
        jobProperties.put("FRAME_INTERVAL", frameInterval);

        MPFVideoJob job = new MPFVideoJob("TestVideoJob", uri, jobProperties, mediaProperties, startFrame, stopFrame);

        try {
            List<MPFVideoTrack> tracks = helloWorldComponent.getDetections(job);

            assertEquals("Number of tracks is not as expected.", 1, tracks.size());
            System.out.println(String.format("Number of video tracks = %d.", tracks.size()));

            for (int i = 0; i < tracks.size(); i++) {
                MPFVideoTrack track = tracks.get(i);
                System.out.println(String.format("Video track number %d", i));
                System.out.println(String.format("  start frame = %d", track.getStartFrame()));
                System.out.println(String.format("  stop frame = %d", track.getStopFrame()));
                System.out.println(String.format("  confidence = %f", track.getConfidence()));
                System.out.println(String.format("  metadata = %s", track.getDetectionProperties().get("METADATA")));
                assertEquals("confidence does not match.", 0.8f, track.getConfidence(), 0.01f);
                assertEquals("start frame does not match.", startFrame, track.getStartFrame());
                assertEquals("stop frame does not match.", stopFrame, track.getStopFrame());
                assertEquals("metadata does not match.", "extra video track info", track.getDetectionProperties().get("METADATA"));
                assertEquals("number of locations does not match.", 1, track.getFrameLocations().size());

                for (MPFImageLocation location : track.getFrameLocations().values()) {
                    assertEquals("x left upper does not match.", 0, location.getXLeftUpper());
                    assertEquals("y left upper does not match.", 0, location.getYLeftUpper());
                    assertEquals("width does not match.", 100, location.getWidth());
                    assertEquals("height does not match.", 100, location.getHeight());
                    assertEquals("confidence does not match.", 0.8f, location.getConfidence(), 0.01f);
                    assertEquals("metadata does not match.", "extra image location info", location.getDetectionProperties().get("METADATA"));
                }
            }
        } catch (MPFComponentDetectionError e) {
            System.err.println(String.format("An error occurred of type ", e.getDetectionError().name()));
            fail(String.format("An error occurred of type ", e.getDetectionError().name()));
        }
    }

    @Test
    public void testGetDetectionsAudio() throws Exception {
        String uri = "RandomAudioFile";

        // Start and stop times are both inclusive.
        int startTime = 10000;
        int stopTime = 19999;

        Map<String, String> jobProperties = new HashMap<String, String>();
        Map<String, String> mediaProperties = new HashMap<String, String>();

        MPFAudioJob audioJob = new MPFAudioJob("TestAudioJob", uri, jobProperties, mediaProperties, startTime, stopTime);

        try {
            List<MPFAudioTrack> tracks = helloWorldComponent.getDetections(audioJob);
            System.out.println(String.format("Number of audio tracks = %d.", tracks.size()));

            for (int i = 0; i < tracks.size(); i++) {
                MPFAudioTrack track = tracks.get(i);
                System.out.println(String.format("Audio track number %d", i));
                System.out.println(String.format("  start time = %d", track.getStartTime()));
                System.out.println(String.format("  stop time = %d", track.getStopTime()));
                System.out.println(String.format("  confidence = %f", track.getConfidence()));
                System.out.println(String.format("  metadata = %s", track.getDetectionProperties().get("METADATA")));
                assertEquals("confidence does not match.", 0.8f, track.getConfidence(), 0.01f);
                assertEquals("start time does not match.", startTime, track.getStartTime());
                assertEquals("stop time does not match.", startTime + 1, track.getStopTime());
                assertEquals("metadata does not match.", "extra audio track info", track.getDetectionProperties().get("METADATA"));
            }
        } catch (MPFComponentDetectionError e) {
            System.err.println(String.format("An error occurred of type ", e.getDetectionError().name()));
            fail(String.format("An error occurred of type ", e.getDetectionError().name()));
        }
    }

    @Test
    public void testGetDetectionsGeneric() throws Exception {
        String uri = "RandomGenericFile";

        Map<String, String> jobProperties = new HashMap<String, String>();
        Map<String, String> mediaProperties = new HashMap<String, String>();

        MPFGenericJob genericJob = new MPFGenericJob("TestGenericJob", uri, jobProperties, mediaProperties);

        try {
            List<MPFGenericTrack> tracks = helloWorldComponent.getDetections(genericJob);
            System.out.println(String.format("Number of generic tracks = %d.", tracks.size()));

            for (int i = 0; i < tracks.size(); i++) {
                MPFGenericTrack track = tracks.get(i);
                System.out.println(String.format("Generic track number %d", i));
                System.out.println(String.format("  confidence = %f", track.getConfidence()));
                System.out.println(String.format("  metadata = %s", track.getDetectionProperties().get("METADATA")));
                assertEquals("confidence does not match.", 0.8f, track.getConfidence(), 0.01f);
                assertEquals("metadata does not match.", "extra generic track info", track.getDetectionProperties().get("METADATA"));
            }
        } catch (MPFComponentDetectionError e) {
            System.err.println(String.format("An error occurred of type ", e.getDetectionError().name()));
            fail(String.format("An error occurred of type ", e.getDetectionError().name()));
        }
    }
}
