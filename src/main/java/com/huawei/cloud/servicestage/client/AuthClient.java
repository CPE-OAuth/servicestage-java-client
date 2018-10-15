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
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Client for getting Authentication Token.<br>
 * 
 * @see <a
 *      href=""https://support.huaweicloud.com/en-us/api-as/en-us_topic_0045219163.html">https://support.huaweicloud.com/en-us/api-as/en-us_topic_0045219163.html</a>
 * @author Farhan Arshad
 */
public class AuthClient implements Constants {
    /**
     * Gets Authentication Token by calling
     * {@value ConfigProperties#AUTH_API_URL}, replacing region value.
     * 
     * @param region
     *            region value as it should appear in the url, e.g. cn-north-1
     * @param username
     * @param password
     * @return a Token object if Authentication Token was successfully
     *         retrieved, null otherwise
     * @throws IOException
     */
    public static Token getAuthToken(String region, String username,
            String password) throws IOException {
        String requestUrl = String.format(ConfigProperties.getProperties()
                .getProperty(ConfigProperties.AUTH_API_URL), region);

        HttpPost request = new HttpPost(requestUrl);

        // body
        StringEntity body = new StringEntity(
                getRequestBody(region, username, password));
        body.setContentType(CONTENT_TYPE_HEADER_VALUE);
        request.setEntity(body);

        // headers
        request.setHeader(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
        request.setHeader(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE);

        // proxy (if needed)
        Util.setProxy(request);

        // send request
        CloseableHttpResponse response = HttpClients.createDefault()
                .execute(request);

        try {
            HttpEntity entity = response.getEntity();

            // read response - to be returned by method
            String content = Util.convertStreamToString(entity.getContent());

            // consume remaining stream, if any
            EntityUtils.consume(entity);

            if (response.getStatusLine()
                    .getStatusCode() == HttpStatus.SC_CREATED) {
                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(content).getAsJsonObject();
                JsonObject tokenObj = root.getAsJsonObject("token");
                JsonObject projectObj = tokenObj.getAsJsonObject("project");

                // token.expires_at
                String expiresAt = tokenObj.get("expires_at").getAsString();

                // token.project.id
                String tenantId = projectObj.get("id").getAsString();

                // x-subject-token header
                String token = response.getFirstHeader(AUTH_TOKEN_HEADER)
                        .getValue();

                return new Token(username, region, token, tenantId, expiresAt);
            } else {
                throw new IOException(response.getStatusLine().getStatusCode()
                        + ": " + content);
            }
        } finally {
            response.close();
        }
    }

    private static String getRequestBody(String region, String username,
            String password) {
        AuthTokenRequestBody r = AuthTokenRequestBody.newEmptyInstance();

        r.auth.identity.methods.add("password");
        r.auth.identity.password.user.name = username;
        r.auth.identity.password.user.password = password;
        r.auth.identity.password.user.domain.name = username;
        r.auth.scope.project.name = region;

        return new Gson().toJson(r);
    }

}
