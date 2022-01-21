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

package org.mitre.mpf.component.api.detection;

/**
 * All possible exit statuses for detection jobs.
 */
public enum MPFDetectionError {
        MPF_DETECTION_SUCCESS,
        MPF_OTHER_DETECTION_ERROR_TYPE,
        MPF_DETECTION_NOT_INITIALIZED,
        MPF_UNSUPPORTED_DATA_TYPE,
        MPF_COULD_NOT_OPEN_DATAFILE,
        MPF_COULD_NOT_READ_DATAFILE,
        MPF_FILE_WRITE_ERROR,
        MPF_BAD_FRAME_SIZE,
        MPF_DETECTION_FAILED,
        MPF_INVALID_PROPERTY,
        MPF_MISSING_PROPERTY,
        MPF_GPU_ERROR,
        MPF_NETWORK_ERROR,
        MPF_COULD_NOT_OPEN_MEDIA,
        MPF_COULD_NOT_READ_MEDIA
}
