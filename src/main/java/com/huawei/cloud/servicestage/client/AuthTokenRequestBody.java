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

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

/*-
{
  "auth": {
    "identity": {
      "methods": [
        "password"
      ],
      "password": {
        "user": {
          "name": "username",
          "password": "password",
          "domain": {
            "name": "domainname"
          }
        }
      }
    },
    "scope": {
      "project": {
        "id": "0215ef11e49d4743be23dd97a1561e91"       
      }
    }
  }
}
 */

/**
 * Request body for performing a POST on
 * {@value ConfigProperties#AUTH_API_URL}<br>
 * <br>
 * Used by {@link AuthClient}
 * 
 * @author Farhan Arshad
 */
public class AuthTokenRequestBody {

    /**
     * Returns an empty {@link AuthTokenRequestBody} instance where all children
     * (and sub-children) have been initialized to empty instances. This should
     * only be used when you plan on setting all values of this object as it
     * will contain empty values.
     * 
     * @return
     */
    public static AuthTokenRequestBody newEmptyInstance() {
        AuthTokenRequestBody r = new AuthTokenRequestBody();
        r.auth = new AuthTokenRequestBody.Auth();

        r.auth.identity = new AuthTokenRequestBody.Auth.Identity();
        r.auth.identity.methods = new LinkedList<>();

        r.auth.identity.password = new AuthTokenRequestBody.Auth.Identity.Password();
        r.auth.identity.password.user = new AuthTokenRequestBody.Auth.Identity.Password.User();

        r.auth.identity.password.user.domain = new AuthTokenRequestBody.Auth.Identity.Password.User.Domain();

        r.auth.scope = new AuthTokenRequestBody.Auth.Scope();
        r.auth.scope.project = new AuthTokenRequestBody.Auth.Scope.Project();

        return r;
    }

    public static class Auth {
        public static class Identity {
            public List<String> methods;

            public Password password;

            public static class Password {
                public User user;

                public static class User {
                    public String name;

                    public String password;

                    public static class Domain {
                        public String name;
                    }

                    public Domain domain;
                }
            }
        }

        public Identity identity;

        public static class Scope {
            public static class Project {
                public String name;
            }

            public Project project;
        }

        public Scope scope;
    }

    public Auth auth;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
