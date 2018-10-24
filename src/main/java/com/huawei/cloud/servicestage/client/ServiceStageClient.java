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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Client for performing several ServiceStage operations
 * 
 * @author Farhan Arshad
 */
public class ServiceStageClient implements Constants {
    private Logger logger = Util.logger;

    private String apiUrl = null;

    /**
     * Constructs a new Client where the API Url is read from config.properties
     * 
     * @throws IOException
     */
    public ServiceStageClient() throws IOException {
        this.apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.API_URL);
    }

    public ServiceStageClient(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    private String getBaseApiUrl(Token token) {
        return String.format(this.apiUrl, token.getRegion()) + "/"
                 + token.getTenantId();
    }

    public SimpleResponse createService(String instanceId,
            ServiceInstanceRequestBody body, Token token) throws IOException {
        return createService(instanceId, new Gson().toJson(body), token);
    }

    public SimpleResponse createService(String instanceId, String requestBody,
            Token token) throws IOException {
        String requestUrl = getBaseApiUrl(token) + "/apps/service_instances/" + instanceId
                + "?accepts_incomplete=true";

        logger.info(requestUrl);

        HttpPost request = new HttpPost(requestUrl);

        // body
        StringEntity body = new StringEntity(requestBody);
        body.setContentType(CONTENT_TYPE_HEADER_VALUE);
        request.setEntity(body);

        // headers
        request.setHeader(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
        request.setHeader(X_BROKER_API_VERSION_HEADER_KEY,
                X_BROKER_API_VERSION_HEADER_VALUE);
        request.setHeader(X_AUTH_TOKEN_HEADER_KEY, token.getToken());

        // perform request
        CloseableHttpResponse response = HttpClients.createDefault()
                .execute(request);

        try {
            HttpEntity entity = response.getEntity();

            // read response
            String content = Util.convertStreamToString(entity.getContent());

            // try to pretty-fy if content is json
            try {
                JsonObject json = new JsonParser().parse(content)
                        .getAsJsonObject();
                content = new GsonBuilder().setPrettyPrinting().create()
                        .toJson(json);
            } catch (Exception e) {
            }

            // consume remaining stream, if any
            EntityUtils.consume(entity);

            logger.info(response.getStatusLine().getStatusCode() + "");
            logger.info(content);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return new SimpleResponse(true, content);
            } else {
                return new SimpleResponse(false, content);
            }
        } finally {
            response.close();
        }
    }

    public SimpleResponse updateService(String instanceId,
            ServiceInstanceRequestBody body, Token token) throws IOException {
        return updateService(instanceId, new Gson().toJson(body), token);
    }

    public SimpleResponse updateService(String instanceId, String requestBody,
            Token token) throws IOException {
        String requestUrl = getBaseApiUrl(token) + "/apps/service_instances/" + instanceId
                + "?accepts_incomplete=true";

        logger.info(requestUrl);

        HttpPut request = new HttpPut(requestUrl);

        // body
        StringEntity body = new StringEntity(requestBody);
        body.setContentType(CONTENT_TYPE_HEADER_VALUE);
        request.setEntity(body);

        // headers
        request.setHeader(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
        request.setHeader(X_BROKER_API_VERSION_HEADER_KEY,
                X_BROKER_API_VERSION_HEADER_VALUE);
        request.setHeader(X_AUTH_TOKEN_HEADER_KEY, token.getToken());

        // perform request
        CloseableHttpResponse response = HttpClients.createDefault()
                .execute(request);

        try {
            HttpEntity entity = response.getEntity();

            // read response
            String content = Util.convertStreamToString(entity.getContent());

            // try to pretty-fy if content is json
            try {
                JsonObject json = new JsonParser().parse(content)
                        .getAsJsonObject();
                content = new GsonBuilder().setPrettyPrinting().create()
                        .toJson(json);
            } catch (Exception e) {
            }

            // consume remaining stream, if any
            EntityUtils.consume(entity);

            logger.info(response.getStatusLine().getStatusCode() + "");
            logger.info(content);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return new SimpleResponse(true, content);
            } else {
                return new SimpleResponse(false, content);
            }
        } finally {
            response.close();
        }
    }

    /**
     * Performs a GET request against the requestEndpoint of the API at
     * {@value ConfigProperties#API_URL}
     * 
     * @param requestEndpoint
     *            for example, "/apps/metadata/types"
     * @param token
     *            valid token object
     * @return {@link SimpleResponse} with status indicating if request was
     *         successful, along with the response body
     * @throws IOException
     */
    public SimpleResponse performGet(String requestEndpoint, Token token)
            throws IOException {
        String requestUrl = getBaseApiUrl(token) + requestEndpoint;

        HttpGet request = new HttpGet(requestUrl);

        logger.info(requestUrl);

        // headers
        request.setHeader(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
        request.setHeader(X_BROKER_API_VERSION_HEADER_KEY,
                X_BROKER_API_VERSION_HEADER_VALUE);
        request.setHeader(X_AUTH_TOKEN_HEADER_KEY, token.getToken());

        // send request
        CloseableHttpResponse response = HttpClients.createDefault()
                .execute(request);

        try {
            HttpEntity entity = response.getEntity();

            // read response - to be returned by method
            String content = Util.convertStreamToString(entity.getContent());

            // consume remaining stream, if any
            EntityUtils.consume(entity);

            logger.info(response.getStatusLine().getStatusCode() + "");
            logger.info(content);

            return new SimpleResponse(response.getStatusLine()
                    .getStatusCode() == HttpStatus.SC_OK, content);
        } finally {
            response.close();
        }
    }

    public Map<String, String> getAppTShirtSizes(Token token)
            throws IOException {
        SimpleResponse response = performGet("/apps/metadata/sizes", token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonArray root = new JsonParser().parse(response.getMessage())
                .getAsJsonArray();

        Map<String, String> map = new LinkedHashMap<>();

        root.forEach(e -> {
            JsonObject eObj = e.getAsJsonObject();
            String id = eObj.get("flavor_id").getAsString();
            String name = eObj.get("label").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    public Map<String, String> getApplicationTypes(Token token)
            throws IOException {
        SimpleResponse response = performGet("/apps/metadata/types", token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonArray root = new JsonParser().parse(response.getMessage())
                .getAsJsonArray();

        Map<String, String> map = new LinkedHashMap<>();

        root.forEach(e -> {
            JsonObject type = e.getAsJsonObject();
            String name = type.get("display_name").getAsString();
            String id = type.get("type_name").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    /**
     * CURRENT_STATUS from the application info, e.g. FAILED, RUNNING, etc
     * 
     * @param instanceId
     * @param token
     * @return
     * @throws IOException
     */
    public String getApplicationStatus(String instanceId, Token token)
            throws IOException {
        SimpleResponse response = getApplicationInfo(instanceId, token);

        logger.info(response.toString());

        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(response.getMessage()).getAsJsonObject();

        boolean empty = root.entrySet().size() == 0;

        if (empty) {
            return null;
        }

        if (!response.isOk()) {
            throw new IOException("Error: " + response.getMessage());
        }

        JsonElement status = root.get("status");

        return status == null ? null : status.getAsString();
    }

    /**
     * Get the URL where the application is running
     * 
     * @param instanceId
     * @param token
     * @return
     * @throws IOException
     */
    public String getApplicationUrl(String instanceId, Token token)
            throws IOException {
        SimpleResponse response = getApplicationInfo(instanceId, token);

        if (!response.isOk()) {
            return response.getMessage();
        }

        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(response.getMessage()).getAsJsonObject();

        JsonElement url = root.get("url");

        logger.info(url.toString());

        return url == null ? "" : url.getAsString();
    }

    /**
     * Gets information about the application instance
     * 
     * @param project
     * @return
     * @throws IOException
     * @throws StorageException
     */
    public SimpleResponse getApplicationInfo(String instanceId, Token token)
            throws IOException {
        return performGet("/apps/service_instances/" + instanceId, token);
    }

    /**
     * Returns true of application exists, false otherwise
     * 
     * @param instanceId
     * @param token
     * @return
     * @throws IOException
     */
    public boolean applicationExists(String instanceId, Token token)
            throws IOException {
        SimpleResponse response = getApplicationInfo(instanceId, token);

        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(response.getMessage()).getAsJsonObject();

        boolean empty = root.entrySet().size() == 0;

        if (!response.isOk() || empty) {
            return false;
        }

        return true;
    }
}
