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

import java.time.Clock;
import java.time.LocalDateTime;

import com.google.gson.Gson;

/**
 * Class to represent a unique token. Includes the token value, region,
 * username, tenantid, region and expire date
 * 
 * @author Farhan Arshad
 */
public class Token {
    private String username;

    private String region;

    private String token;

    private LocalDateTime expiresAt;

    private String tenantId;
    
    private String domain;

    public Token(String domain, String username, String region, String token, String tenantId,
            String expiresAt) {
        this(domain, username, region, token, tenantId, Util.stringToDate(expiresAt));
    }

    public Token(String domain, String username, String region, String token, String tenantId,
            LocalDateTime expiresAt) {
    	this.domain = domain;
        this.username = username;
        this.region = region;
        this.token = token;
        this.tenantId = tenantId;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getUsername() {
        return username;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getRegion() {
        return region;
    }
    
    public String getDomain() {
        return domain;
    }

    public boolean isExpired() {
        return this.getExpiresAt()
                .compareTo(LocalDateTime.now(Clock.systemUTC())) < 0;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public static Token fromString(String token) {
        return new Gson().fromJson(token, Token.class);
    }

}
