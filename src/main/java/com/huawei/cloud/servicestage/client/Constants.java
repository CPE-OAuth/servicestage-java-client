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

/**
 * @author Farhan Arshad
 */
public interface Constants {
    public static final String AUTH_TOKEN_HEADER = "x-subject-token";

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";

    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json";

    public static final String ACCEPT_HEADER_KEY = "ACCEPT";

    public static final String ACCEPT_HEADER_VALUE = "application/json";

    public static final String X_BROKER_API_VERSION_HEADER_KEY = "X-Broker-API-Version";

    public static final String X_BROKER_API_VERSION_HEADER_VALUE = "2.13";

    public static final String X_AUTH_TOKEN_HEADER_KEY = "X-Auth-Token";

    public static final String X_SWR_OVERRIDE_HEADER_KEY = "X-SWR-Override";

    public static final String X_SWR_OVERRIDE_HEADER_VALUE = "1";

    public static final String X_SWR_DECOMPRESS_HEADER_KEY = "X-SWR-Decompress";

    public static final String X_SWR_DECOMPRESS_HEADER_VALUE = "0";

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    public static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";

    public static final String X_LANGUAGE_HEADER_KEY = "X-Language";

    public static final String X_LANGUAGE_HEADER_VALUE = "en-us";

}
