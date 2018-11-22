/**
 * Copyright 2016 - 2018 Huawei Technologies Co., Ltd. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huawei.cloud.servicestage.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Farhan Arshad
 */
public class ServiceStageClientTest {

    private static Token token;

    // REPLACE WITH A VALID ID BEFORE RUNNING TESTS
    private static final String INSTANCE_ID = "abc-def";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        token = AuthHelper.getInstance().getToken();
    }

    @Test
    public void testGetAppTShirtSizes() throws IOException {
        assertFalse(
                new ServiceStageClient().getAppTShirtSizes(token).isEmpty());
    }

    @Test
    public void testGetApplicationTypes() throws IOException {
        assertFalse(
                new ServiceStageClient().getApplicationTypes(token).isEmpty());
    }

    @Test
    public void testGetApplicationInfo() throws IOException {
        assertTrue(new ServiceStageClient().getApplicationInfo("abc", token)
                .isOk());
    }

    @Test
    public void testGetApplicationStatus() throws IOException {
        assertNotNull("Status was null", new ServiceStageClient()
                .getApplicationStatus(INSTANCE_ID, token));
    }

    @Test
    public void testGetApplicationTaskLogs() throws IOException {
        SimpleResponse response = new ServiceStageClient()
                .getApplicationTaskLogs(INSTANCE_ID, token);

        assertNotNull("Logs response was null", response);

        System.out.println(response);
    }
}
