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

import org.mitre.mpf.component.api.detection.MPFDetectionError;

/**
 * An exception that occurs in a component.  The exception must contain a reference to a valid MPFDetectionError.
 */
public class MPFComponentDetectionError extends Exception {

    private MPFDetectionError error;

    public MPFComponentDetectionError(MPFDetectionError error) {
        super();
        this.error = error;
    }

    public MPFComponentDetectionError(MPFDetectionError error, String msg) {
        super(msg);
        this.error = error;
    }

    public MPFComponentDetectionError(MPFDetectionError error, String msg, Exception e) {
        super(msg, e);
        this.error = error;
    }

    public MPFComponentDetectionError(MPFDetectionError error, Exception e) {
        super(e);
        this.error = error;
    }

    public MPFDetectionError getDetectionError() {
        return error;
    }
}
