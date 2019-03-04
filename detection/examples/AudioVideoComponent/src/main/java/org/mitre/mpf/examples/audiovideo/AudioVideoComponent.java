/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2019 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2019 The MITRE Corporation                                       *
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

package org.mitre.mpf.examples.audiovideo;

import com.google.common.math.IntMath;
import org.mitre.mpf.component.api.detection.adapters.MPFAudioAndVideoDetectionComponentAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The AudioVideoComponent uses an adapter to provide a level of baseline behavior.
// In this example, the MPFAudioAndVideoDetectionComponentAdapter allows for audio
// processing on both audio files and video files by converting video jobs into
// audio jobs and converting audio tracks into video tracks.
//
// Since audio processing is invalid for image files, the adapter throws an unsupported
// exception for image files.
//
// Since the adapter manages these tasks for us, the only method the AudioVideoComponent
// needs to implement is getDetections(MPFAudioJob mpfAudioJob)
public class AudioVideoComponent extends MPFAudioAndVideoDetectionComponentAdapter {

    // The OpenMPF uses log4j2 for logging.  Each component may define its own log
    // file, to which log messages can be delivered.
    // See log4j2.xml for more information on log configuration.
    private static final Logger log = LoggerFactory.getLogger(AudioVideoComponent.class);

    // Handles the case where the media is an audio type.
    // Video media files will be converted into audio by the adapter.
    public List<MPFAudioTrack> getDetections(MPFAudioJob mpfAudioJob) throws MPFComponentDetectionError {

        // The MPFAudioJob object contains all of the details needed to
        // process an audio file.
        log.debug("{} Processing {} from start time {} ms to stop time {} ms.",
                        mpfAudioJob.getJobName(),
                        mpfAudioJob.getDataUri(),
                        mpfAudioJob.getStartTime(),
                        mpfAudioJob.getStopTime());

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

        int stopTime = IntMath.checkedAdd(mpfAudioJob.getStartTime(), 1);

        MPFAudioTrack audioTrack = new MPFAudioTrack(mpfAudioJob.getStartTime(), stopTime, confidence, audioDetectionProperties);

        List<MPFAudioTrack> tracks = new LinkedList<MPFAudioTrack>();
        tracks.add(audioTrack);

        log.info("{} Processing complete. Generated {} dummy audio tracks.",
                        mpfAudioJob.getJobName(),
                        tracks.size());

        return tracks;
    }

    public String getDetectionType() {
        return "AUDIOVIDEO";
    }
}
