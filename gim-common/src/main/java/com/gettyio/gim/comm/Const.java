/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gettyio.gim.comm;

/**
 * Const.java
 *
 * @description:
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class Const {

    public static final Integer SUCCESS = 10000;
    /**
     * Protocol identify
     */
    public final static String IDENTIFY = "com/gettyio/gim";
    /**
     * Protocol version
     */
    public final static String VERSION = "1.0";
    /**
     * 消息延迟时长
     */
    public static final Long MSG_DELAY = 10 * 1000L;
    /**
     * 心跳检查间隔，默认30s
     */
    public static final Integer HEARTBEAT_INTERVAL = 30;


}
