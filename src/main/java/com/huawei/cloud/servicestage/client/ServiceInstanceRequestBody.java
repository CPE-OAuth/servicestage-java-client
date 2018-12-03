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

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/*-
{
       "service_id": "SRV238CASUFGHI09314A",
        "plan_id": "cas-pay2go",
        "organization_guid": "abc_guid",
        "space_guid": "abc_guid",
        "context": {
            "order_id": "ord123"
        },
        "parameters": {
            "name": "name",
            "region": "cn-north-1",
            "version": "1.0",
            "type": "Tomcat8",
            "display_name": "my tomcat session app",
            "platform_type": "cce",
            "listener_port": 8080,
            "desc": "What my app does ...",
            "size": {
                "id": "SMALL-10G:1.0C:2G",
                "replica": 2
            },
            "source": {
                "repo_url": "https://github.com/user/project.git",
                "proj_branch": "master",
                "repo_namespace": "paulmchen",
                "secu_token": "token",
                "type": "GitHub",
                "artifact_namespace": "default",
                "template_name": null
            },
            "platforms": {
                "vpc": {
                    "id": "123id"
                },
                "cce": {
                    "id": "123id",
                    "parameters": {
                        "namespace": "default"
                    }
                },
                "elb": {
                    "id": "123id"
                }
                
            },
            "services": {
                "distributed_session": {
                    "id": "123id",
                    "desc": "Distributed caching service",
                    "parameters": {
                        "password": "password",
                        "cluster": "false"
                    }
                }
            }
        }
    }
*/
/**
 * Request body for performing a PUT/PATCH on
 * {@value ConfigProperties#API_URL}<br>
 * <br>
 * Used by {@link ServiceStageClient}
 * 
 * @author Farhan Arshad
 */
public class ServiceInstanceRequestBody {
    /**
     * Returns an empty {@link ServiceInstanceRequestBody} instance where all
     * children (and sub-children) have been initialized to empty instances.
     * This should only be used when you plan on setting all values of this
     * object as it will contain empty values.
     * 
     * @return
     */
    public static ServiceInstanceRequestBody newEmptyInstance() {
        ServiceInstanceRequestBody r = new ServiceInstanceRequestBody();

        r.context = new ServiceInstanceRequestBody.Context();

        r.parameters = new ServiceInstanceRequestBody.Parameters();

        r.parameters.size = new ServiceInstanceRequestBody.Parameters.Size();
        r.parameters.source = new ServiceInstanceRequestBody.Parameters.Source();
        r.parameters.platforms = new ServiceInstanceRequestBody.Parameters.Platforms();

        r.parameters.platforms.vpc = new ServiceInstanceRequestBody.Parameters.Platforms.VPC();
        r.parameters.platforms.vpc.parameters = new ServiceInstanceRequestBody.Parameters.Platforms.VPC.VPCParameters();
        r.parameters.platforms.vpc.parameters.subnet = new ServiceInstanceRequestBody.Parameters.Platforms.VPC.VPCParameters.VPCParametersSubnet();
        r.parameters.platforms.cce = new ServiceInstanceRequestBody.Parameters.Platforms.CCE();
        r.parameters.platforms.cce.parameters = new ServiceInstanceRequestBody.Parameters.Platforms.CCE.CCEParameters();
        r.parameters.platforms.elb = new ServiceInstanceRequestBody.Parameters.Platforms.ELB();

        r.parameters.services = new ServiceInstanceRequestBody.Parameters.Services();

        r.parameters.services.distributedSession = new ServiceInstanceRequestBody.Parameters.Services.DistributedSession();
        r.parameters.services.distributedSession.parameters = new ServiceInstanceRequestBody.Parameters.Services.DistributedSession.DistributedSessionParameters();

        r.parameters.services.relationalDatabase = new ServiceInstanceRequestBody.Parameters.Services.RelationalDatabase();
        r.parameters.services.relationalDatabase.parameters = new ServiceInstanceRequestBody.Parameters.Services.RelationalDatabase.RelationalDatabaseParameters();

        return r;
    }

    @SerializedName("service_id")
    public String serviceId;

    @SerializedName("plan_id")
    public String planId;

    @SerializedName("organization_guid")
    public String organizationGuid;

    @SerializedName("space_guid")
    public String spaceGuid;

    public static class Context {
        @SerializedName("order_id")
        public String orderId;
    }

    @SerializedName("context")
    public Context context;

    public static class Parameters {
        @SerializedName("name")
        public String name;

        @SerializedName("region")
        public String region;

        @SerializedName("version")
        public String version;

        @SerializedName("type")
        public String type;
        
        @SerializedName("platform_type")
        public String platformType;

        @SerializedName("display_name")
        public String displayName;

        @SerializedName("listener_port")
        public Integer listenerPort;

        @SerializedName("desc")
        public String desc;

        public static class Size {
            @SerializedName("id")
            public String id;

            @SerializedName("replica")
            public Integer replica;
        }

        @SerializedName("size")
        public Size size;

        public static class Source {
            @SerializedName("repo_url")
            public String repoUrl;

            @SerializedName("proj_branch")
            public String projBranch;

            @SerializedName("repo_namespace")
            public String repoNamespace;

            @SerializedName("secu_token")
            public String secuToken;

            @SerializedName("type")
            public String type;

            @SerializedName("artifact_namespace")
            public String artifactNamespace;

            @SerializedName("template_name")
            public String templateName;
        }

        @SerializedName("source")
        public Source source;

        public static class Platforms {
            public static class VPC {
                @SerializedName("id")
                public String id;

                public static class VPCParameters {
                    public static class VPCParametersSubnet {
                        @SerializedName("id")
                        public String id;
                    }

                    @SerializedName("subnet")
                    public VPCParametersSubnet subnet;
                }

                @SerializedName("parameters")
                public VPCParameters parameters;
            }

            @SerializedName("vpc")
            public VPC vpc;

            public static class CCE {
                @SerializedName("id")
                public String id;

                public static class CCEParameters {
                    @SerializedName("namespace")
                    public String namespace;
                }

                @SerializedName("parameters")
                public CCEParameters parameters;
            }

            @SerializedName("cce")
            public CCE cce;

            public static class ELB {
                @SerializedName("id")
                public String id;

                @SerializedName("namespace")
                public String namespace;
            }

            @SerializedName("elb")
            public ELB elb;
        }

        @SerializedName("platforms")
        public Platforms platforms;

        public static class Services {
            public static class DistributedSession {
                @SerializedName("desc")
                public String desc;

                @SerializedName("id")
                public String id;

                public static class DistributedSessionParameters {
                    @SerializedName("cluster")
                    public String cluster;

                    @SerializedName("password")
                    public String password;

                    @SerializedName("version")
                    public String version;

                    @SerializedName("type")
                    public String type;
                }

                public DistributedSessionParameters parameters;
            }

            @SerializedName("distributed_session")
            public DistributedSession distributedSession;

            public static class RelationalDatabase {
                @SerializedName("desc")
                public String desc;

                @SerializedName("id")
                public String id;

                @SerializedName("host")
                public String host;

                @SerializedName("port")
                public String port;

                public static class RelationalDatabaseParameters {
                    @SerializedName("ddl_path")
                    public String ddlPath;

                    @SerializedName("ddl_src")
                    public String ddlSrc;

                    @SerializedName("db_name")
                    public String dbName;

                    @SerializedName("db_user")
                    public String dbUser;

                    @SerializedName("db_type")
                    public String dbType;

                    @SerializedName("jndi_name")
                    public String jndiName;

                    @SerializedName("connection_type")
                    public String connectionType;

                    @SerializedName("password")
                    public String password;
                }

                public RelationalDatabaseParameters parameters;
            }

            @SerializedName("relational_database")
            public RelationalDatabase relationalDatabase;
        }

        public Services services;
    }

    public Parameters parameters;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
