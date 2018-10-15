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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;

public class Util {

    public static Logger logger = Logger.getLogger("servicestage-java-client");

    public static LocalDateTime stringToDate(String expiresAt) {
        DateTimeFormatter f = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX");
        return LocalDateTime.parse(expiresAt, f);
    }

    public static String convertStreamToString(InputStream is) {
        // closed by caller
        @SuppressWarnings("resource")
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Adds proxy information to the request if the system environment has set a
     * http proxy
     * 
     * @param request
     * @return
     * @throws MalformedURLException
     */
    public static void setProxy(HttpRequestBase request)
            throws MalformedURLException {
        String proxyUrl = System.getenv("HTTP_PROXY");

        if (proxyUrl == null || proxyUrl.isEmpty()) {
            return;
        }

        // remove trailing forward-slash, it causes issues with HttpHost
        if (proxyUrl.charAt(proxyUrl.length() - 1) == '/') {
            proxyUrl = proxyUrl.substring(0, proxyUrl.length() - 1);
        }

        HttpHost proxy = HttpHost.create(proxyUrl);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        // RequestConfig config = RequestConfig.custom().setProxy(proxy)
        // .setConnectTimeout(10 * 1000).build();
        request.setConfig(config);
    }
}
