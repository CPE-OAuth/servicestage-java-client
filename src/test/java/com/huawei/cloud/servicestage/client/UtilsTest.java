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

import java.time.LocalDateTime;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void testStringToDate() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime l1 = Util.stringToDate("2018-08-23T21:40:09.922000Z");
        LocalDateTime l2 = Util.stringToDate("2018-08-23T20:40:09.922000Z");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l1.compareTo(l2));
        assertTrue(l1.compareTo(l2) > 0);

        l2 = Util.stringToDate("2018-08-23T21:40:09.922000Z");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l1.compareTo(l2));
        assertTrue(l1.compareTo(l2) == 0);

        l2 = Util.stringToDate("2018-08-23T22:40:09.922000Z");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l1.compareTo(l2));
        assertTrue(l1.compareTo(l2) < 0);
    }

}
