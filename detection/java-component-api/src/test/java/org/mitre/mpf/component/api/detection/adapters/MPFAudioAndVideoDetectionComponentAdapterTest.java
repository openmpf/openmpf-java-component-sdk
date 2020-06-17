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

package org.mitre.mpf.component.api.detection.adapters;

import junit.framework.TestCase;
import org.junit.Test;
import org.mitre.mpf.component.api.detection.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MPFAudioAndVideoDetectionComponentAdapterTest extends TestCase {

    @Test
    public void testGetDetectionsFromVideo() throws Exception {
        MPFAudioAndVideoDetectionComponentAdapter component = new TestInstanceMPFAudioAndVideoDetectionComponentAdapter();

        HashMap<String,String> mediaProperties = new HashMap<>();
        mediaProperties.put("DURATION","2000");
        mediaProperties.put("FPS","20");
        List<MPFVideoTrack> tracks = component.getDetections(new MPFVideoJob("TEST", "test", new HashMap<>(), mediaProperties, 0, 600));
        assertEquals(2, tracks.size());
        MPFVideoTrack track1 = tracks.get(0);
        assertEquals(10, track1.getStartFrame());
        assertEquals(24, track1.getStopFrame());
        // Audio detection on video files generate 1 detection at the first frame of the track.
        assertEquals(1, track1.getFrameLocations().size());
    }

    @Test
    public void testGetDetectionsFromVideoWithError() throws Exception {
        MPFAudioAndVideoDetectionComponentAdapter component = new TestInstanceMPFAudioAndVideoDetectionComponentAdapter();

        HashMap<String,String> mediaProperties = new HashMap<>();
        mediaProperties.put("DURATION","300");
        mediaProperties.put("FPS","20");
        List<MPFVideoTrack> tracks = new LinkedList<>();
        try {
            tracks = component.getDetections(new MPFVideoJob("TEST", "TESTERROR", new HashMap<>(), mediaProperties, 0, 600));
            fail("getDetections was expected to throw an exception, and did not.");
        } catch (MPFComponentDetectionError e) {
            assertEquals(MPFDetectionError.MPF_DETECTION_FAILED, e.getDetectionError());
        }
        assertEquals(0, tracks.size());
    }


    private class TestInstanceMPFAudioAndVideoDetectionComponentAdapter extends MPFAudioAndVideoDetectionComponentAdapter {

        @Override
        public List<MPFAudioTrack> getDetections(MPFAudioJob job) throws MPFComponentDetectionError {
            List<MPFAudioTrack> tracks = new LinkedList<>();
            tracks.add(new MPFAudioTrack(500, 1200, 1.0f, Collections.emptyMap()));
            tracks.add(new MPFAudioTrack(3000, 5000, 2.0f, Collections.emptyMap()));
            if (job.getDataUri().equals("TESTERROR")) {
                throw new MPFComponentDetectionError(MPFDetectionError.MPF_DETECTION_FAILED);
            }
            return tracks;
        }

        @Override
        public String getDetectionType() {
            return "TEST";
        }

    }

}