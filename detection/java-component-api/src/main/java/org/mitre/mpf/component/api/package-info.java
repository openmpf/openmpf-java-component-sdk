/******************************************************************************
 * NOTICE                                                                     *
 *                                                                            *
 * This software (or technical data) was produced for the U.S. Government     *
 * under contract, and is subject to the Rights in Data-General Clause        *
 * 52.227-14, Alt. IV (DEC 2007).                                             *
 *                                                                            *
 * Copyright 2024 The MITRE Corporation. All Rights Reserved.                 *
 ******************************************************************************/

/******************************************************************************
 * Copyright 2024 The MITRE Corporation                                       *
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


/**
 * The Java Component API provides a framework for creating new MPF components in Java.  These components will be
 * deployable via the component registration process in MPF.
 * <BR><BR>
 * The component API is designed to expand to support multiple job types.  In the current version, the only job type
 * which is supported is "DETECTION".  There are a separate suite of classes for handling detection jobs, found in
 * {@link org.mitre.mpf.component.api.detection org.mitre.mpf.component.org.mitre.mpf.component.api.detection}.
 * <BR><BR>
 * The component API contains both the model classes for components and the interfaces which should be extended in order
 * to create a new component.  The main component interface is
 * {@link org.mitre.mpf.component.api.MPFComponentInterface MPFComponentInterface}, which will be extended for each
 * supported component type.  Currently, the only valid interface for new components is the detection type,
 * {@link org.mitre.mpf.component.api.detection.MPFDetectionComponentInterface MPFDetectionComponentInterface}.  All
 * detection components should have an entry point which extends the
 * {@link org.mitre.mpf.component.api.detection.MPFDetectionComponentInterface MPFDetectionComponentInterface}.
 */
package org.mitre.mpf.component.api;