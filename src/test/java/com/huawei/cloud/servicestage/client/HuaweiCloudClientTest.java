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
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Farhan Arshad
 */
public class HuaweiCloudClientTest {
    private static Token token;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        token = AuthHelper.getInstance().getToken();
    }

    @Test
    public void testGetRegions() throws IOException {
        assertFalse(HuaweiCloudClient.getRegions(token).isEmpty());
    }

    @Test
    public void testGetCCEClusters() throws IOException {
        assertFalse(HuaweiCloudClient.getCCEClusters(token).isEmpty());
    }

    @Test
    public void testGetELBs() throws IOException {
        assertFalse(HuaweiCloudClient.getELBs(token).isEmpty());
    }

    @Test
    public void testGetVPCs() throws IOException {
        Map<String, String> vpcs = HuaweiCloudClient.getVPCs(token);

        assertFalse(vpcs.isEmpty());

        for (String vpc : vpcs.keySet()) {
            assertFalse(HuaweiCloudClient.getSubnets(token, vpc).isEmpty());
        }
    }

    @Test
    public void testGetDCSInstances() throws IOException {
        assertFalse(HuaweiCloudClient.getDCSInstances(token).isEmpty());
    }

    @Test
    public void testGetRDSInstances() throws IOException {
        assertFalse(HuaweiCloudClient.getRDSInstances(token).isEmpty());
    }
}
