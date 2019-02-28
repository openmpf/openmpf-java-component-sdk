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

package org.mitre.mpf.component.api.detection.util;

import java.util.Map;
import java.util.regex.Pattern;

public class MPFEnvironmentVariablePathExpander {

    private static final Map<String, String> sysEnv = System.getenv();

	private MPFEnvironmentVariablePathExpander() {
	}

	// Expands environment variables in a given path/filename.
    public static String expand(String path) {
        for (Map.Entry<String, String> entry : sysEnv.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            path = path.replaceAll("\\$" + Pattern.quote(key), value);
            path = path.replaceAll("\\$\\{" + Pattern.quote(key) + "\\}", value);

        }

        return path;
    }
}
