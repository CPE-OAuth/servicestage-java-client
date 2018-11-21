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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the properties from config.properties
 * 
 * @author Farhan Arshad
 */
public class ConfigProperties {
    public static final String API_URL = "API_URL";

    public static final String AUTH_API_URL = "AUTH_API_URL";

    public static final String UPLOAD_API_URL = "UPLOAD_API_URL";

    public static final String CONIFG_FILE_NAME = "config.properties";

    public static final String REGIONS_URL = "REGIONS_URL";

    public static final String CCE_CLUSTERS_URL = "CCE_CLUSTERS_URL";

    public static final String CCE_NAMESPACES_URL = "CCE_NAMESPACES_URL";

    public static final String ELBS_CLASSIC_URL = "ELBS_CLASSIC_URL";

    public static final String ELBS_ENHANCED_URL = "ELBS_ENHANCED_URL";

    public static final String VPCS_URL = "VPCS_URL";

    public static final String SUBNETS_URL = "SUBNETS_URL";

    public static final String DCS_INSTANCES_URL = "DCS_INSTANCES_URL";

    public static final String RDS_INSTANCES_URL = "RDS_INSTANCES_URL";

    private static Properties props = null;

    public ConfigProperties() {
    }

    public static Properties getProperties() throws IOException {
        if (props == null) {
            InputStream inputStream = ConfigProperties.class.getClassLoader()
                    .getResourceAsStream(CONIFG_FILE_NAME);

            try {
                props = new Properties();
                props.load(inputStream);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        return props;
    }
}
