/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.study.processor;

import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyProcessorTest {

    private TestRunner testRunner;

    @BeforeEach
    public void init() {
        testRunner = TestRunners.newTestRunner(MyProcessor.class);
    }

    @Test
    public void testProcessor() {
        testRunner.addConnection(MyProcessor.REL_SUCCESS);
        testRunner.setProperty(MyProcessor.MY_PROPERTY,"Value2");
        testRunner.assertValid();
        FlowFile file = new MockFlowFile(1L);
        testRunner.enqueue(file);
        testRunner.run();
        testRunner.assertAllFlowFilesTransferred(MyProcessor.REL_SUCCESS);

    }

}
