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
import java.util.Properties;

import org.junit.Test;

public class ConfigPropertiesTest {

    @Test
    public void testgetProperties() throws IOException {
        Properties props = ConfigProperties.getProperties();
        assertNotNull(props.getProperty(ConfigProperties.AUTH_API_URL));
    }

}
