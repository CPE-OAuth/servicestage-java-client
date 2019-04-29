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
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Farhan Arshad
 */
public class UploadClientTest {

    private static Token token;

    private static String domain;

    private static String namespace;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        token = AuthHelper.getInstance().getToken();
        domain = token.getUsername();
        namespace = domain;
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetNamespaces() throws IOException {
        Set<String> namespaces = new UploadClient().getNamespaces(domain,
                token);
        System.out.println(namespaces);
        assertFalse("namespaces empty", namespaces.isEmpty());
    }

    /**
     * Test method for {@link com.huawei.cloud.servicestage.client.new
     * UploadClient()#getRepos(com.huawei.cloud.servicestage.client.Token)}.
     * 
     * @throws IOException
     */
    @Test
    public void testGetReposPackagesVersions() throws IOException {
        Set<String> repos = new UploadClient().getRepos(domain, namespace,
                token);
        System.out.println(repos);
        assertFalse("repos empty", repos.isEmpty());

        String repo = repos.iterator().next();
        Set<String> packages = new UploadClient().getPackages(domain, namespace,
                repo, token);
        System.out.println(packages);
        assertFalse("packages empty", packages.isEmpty());

        String packageName = packages.iterator().next();
        Set<String> versions = new UploadClient().getVersions(domain, namespace,
                repo, packageName, token);
        System.out.println(versions);
        assertFalse("versions empty", versions.isEmpty());
    }

    /**
     * Test method for {@link com.huawei.cloud.servicestage.client.new
     * UploadClient()#upload(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, com.huawei.cloud.servicestage.client.Token)}.
     * 
     * @throws IOException
     */
    @Test
    public void testUpload() throws IOException {
        String localFilePath = "/tmp/sample.war";
        String domain = "hwcse";
        String namespace = domain;
        String repo = "war-test";
        String packageName = "war-test01";
        String version = "1.0.2";
        String name = "sample.war";

        SimpleResponse response = new UploadClient().upload(localFilePath,
                domain, namespace, repo, packageName, version, name, token);

        System.out.println(response.getMessage());
        assertTrue(response.isOk());

        String url = new UploadClient().getExternalUrl(domain, namespace, repo,
                packageName, version, name, token);
        System.out.println(url);
        assertNotNull(url);
        assertFalse(url.isEmpty());
    }

}
