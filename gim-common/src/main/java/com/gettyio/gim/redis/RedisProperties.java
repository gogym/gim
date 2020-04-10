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
package com.gettyio.gim.redis;


/**
 * RedisProperties.java
 *
 * @description:redis属性配置
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class RedisProperties {

    private String host;

    private Integer port;

    private String password;

    private Integer connectionTimeout = 5000;

    private Integer maxTotal = 20;

    private Integer maxIdle = 20;

    private Integer minIdle = 5;

    private Long maxWaitMillis = 10000L;

    private Boolean testOnBorrow = true;

    private Boolean testOnReturn = true;

    private Boolean testWhileIdle = false;

    public String getHost() {

        return host;
    }

    public void setHost(String host) {

        this.host = host;
    }

    public Integer getPort() {

        return port;
    }

    public void setPort(Integer port) {

        this.port = port;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Integer getConnectionTimeout() {

        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {

        this.connectionTimeout = connectionTimeout;
    }

    public Integer getMaxTotal() {

        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {

        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {

        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {

        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {

        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {

        this.minIdle = minIdle;
    }

    public Long getMaxWaitMillis() {

        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {

        this.maxWaitMillis = maxWaitMillis;
    }

    public Boolean getTestOnBorrow() {

        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {

        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {

        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {

        this.testOnReturn = testOnReturn;
    }

    public Boolean getTestWhileIdle() {

        return testWhileIdle;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {

        this.testWhileIdle = testWhileIdle;
    }

}
