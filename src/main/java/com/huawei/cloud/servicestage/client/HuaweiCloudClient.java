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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Farhan Arshad
 */
public class HuaweiCloudClient implements Constants {
    private static Logger logger = Util.logger;

    protected static SimpleResponse performGet(String requestUrl, Token token)
            throws IOException {
        HttpGet request = new HttpGet(requestUrl);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        logger.info(requestUrl);

        // headers
        request.setHeader(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
        request.setHeader(X_AUTH_TOKEN_HEADER_KEY, token.getToken());
        request.setHeader(X_LANGUAGE_HEADER_KEY, X_LANGUAGE_HEADER_VALUE);

        // proxy (if needed)
   		Util.setProxy(request);


        // bypass SSL cert 
        SSLContext sslContext;
		try {
			sslContext = new SSLContextBuilder()
				      .loadTrustMaterial(null, (certificate, authType) -> true).build();

			client = HttpClients.custom()
        	      .setSSLContext(sslContext)
        	      .setSSLHostnameVerifier(new NoopHostnameVerifier())
        	      .build();
		} catch (Exception e) {
            throw new NoHttpResponseException("Failed in HTTP client creation.");
        }
        
        // send request
		response = client.execute(request);

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

    public static Map<String, String> getRegions(Token token)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.REGIONS_URL);

        SimpleResponse response = performGet(apiUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("regions").forEach(e -> {
            JsonObject region = e.getAsJsonObject();
            String id = region.get("id").getAsString();

            JsonObject locales = region.getAsJsonObject("locales");
            String name = locales.get("en-us").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;

    }

    public static Map<String, String> getCCEClusters(Token token)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.CCE_CLUSTERS_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        SimpleResponse response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("items").forEach(e -> {
            JsonObject item = e.getAsJsonObject();

            String kind = item.get("kind").getAsString();

            if (kind.equals("Cluster")) {
                JsonObject metadata = item.getAsJsonObject("metadata");

                String uid = metadata.get("uid").getAsString();
                String name = metadata.get("name").getAsString();

                map.put(uid, name);
            }
        });

        logger.info(map.toString());

        return map;
    }

    public static Map<String, String> getELBs(Token token) throws IOException {
        // classic elbs
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.ELBS_CLASSIC_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        SimpleResponse response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("loadbalancers").forEach(e -> {
            JsonObject loadbalancer = e.getAsJsonObject();

            String id = loadbalancer.get("id").getAsString();
            String name = loadbalancer.get("name").getAsString();

            map.put(id, name);
        });

        // enhanced elbs
        apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.ELBS_ENHANCED_URL);

        requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        root = new JsonParser().parse(response.getMessage()).getAsJsonObject();

        root.getAsJsonArray("loadbalancers").forEach(e -> {
            JsonObject loadbalancer = e.getAsJsonObject();

            String id = loadbalancer.get("id").getAsString();
            String name = loadbalancer.get("name").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    public static Map<String, String> getVPCs(Token token) throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.VPCS_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        SimpleResponse response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("vpcs").forEach(e -> {
            JsonObject vpc = e.getAsJsonObject();

            String id = vpc.get("id").getAsString();
            String name = vpc.get("name").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    public static Map<String, String> getSubnets(Token token, String vpcId)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.SUBNETS_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId(), vpcId);

        SimpleResponse response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("subnets").forEach(e -> {
            JsonObject subnet = e.getAsJsonObject();

            String id = subnet.get("id").getAsString();
            String name = subnet.get("cidr").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    public static Set<String> getNamespaces(Token token, String clusterId)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.CCE_NAMESPACES_URL);

        String requestUrl = String.format(apiUrl, clusterId, token.getRegion());

        SimpleResponse response = performGet(requestUrl, token);

        if (!response.isOk()) {
            return Collections.emptySet();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Set<String> set = new HashSet<>();

        root.getAsJsonArray("items").forEach(e -> {
            JsonObject namespace = e.getAsJsonObject();
            JsonObject metadata = namespace.get("metadata").getAsJsonObject();

            String name = metadata.get("name").getAsString();

            set.add(name);
        });

        logger.info(set.toString());

        return set;
    }

    public static Map<String, String> getDCSInstances(Token token)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.DCS_INSTANCES_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        SimpleResponse response = performGet(requestUrl, token);
        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("instances").forEach(e -> {
            JsonObject dcs = e.getAsJsonObject();

            String id = dcs.get("instance_id").getAsString();
            String name = dcs.get("name").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }

    public static Map<String, String> getRDSInstances(Token token)
            throws IOException {
        String apiUrl = ConfigProperties.getProperties()
                .getProperty(ConfigProperties.RDS_INSTANCES_URL);

        String requestUrl = String.format(apiUrl, token.getRegion(),
                token.getTenantId());

        SimpleResponse response = performGet(requestUrl, token);
        if (!response.isOk()) {
            return Collections.emptyMap();
        }

        JsonObject root = new JsonParser().parse(response.getMessage())
                .getAsJsonObject();

        Map<String, String> map = new LinkedHashMap<>();

        root.getAsJsonArray("instances").forEach(e -> {
            JsonObject rds = e.getAsJsonObject();

            String id = rds.get("id").getAsString();
            String name = rds.get("name").getAsString();

            map.put(id, name);
        });

        logger.info(map.toString());

        return map;
    }
}
