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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * RedisProxyImp.java
 *
 * @description:实现为Redis服务代理
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public class RedisProxyImp implements IRedisProxy {

    private JedisPool jedisPool;

    private static RedisProxyImp mInstance;

    private RedisProxyImp() {
    }

    private RedisProxyImp(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    /**
     * 单例模式
     *
     * @param jedisPool
     * @return
     */
    public static RedisProxyImp getInstance(JedisPool jedisPool) {

        if (mInstance == null) {
            synchronized (RedisProxyImp.class) {//保证异步处理安全操作
                if (mInstance == null) {
                    mInstance = new RedisProxyImp(jedisPool);
                }
            }
        }
        return mInstance;
    }


    // ----------------------业务实现-------------------------------


    @Override
    public Long decr(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long decrBy(String key, long integer) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.decrBy(key, integer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = null;
        Boolean ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.expire(key, seconds);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.getSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long incrBy(String key, long integer) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.incrBy(key, integer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }


    @Override
    public Long lpush(String key, String string) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lpush(key, string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }


    @Override
    public List<String> brpop(int timeout, String key) {

        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.brpop(timeout, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;

    }


    @Override
    public Long sadd(String key, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.sadd(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long sadd(String key, String... member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.sadd(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }


    @Override
    public String set(String key, String value) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.setnx(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }


    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> ret = jedis.smembers(key);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


    @Override
    public Long srem(String key, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.srem(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long srem(String key, String... member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.srem(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    @Override
    public Long batchSrem(String key) {
        Jedis jedis = null;
        Long ret = null;
        // 分批删除
        try {
            jedis = jedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            // 每次删除 500 条
            scanParams.count(500);
            String cursor = "";
            while (!cursor.equals("0")) {
                ScanResult<String> scanResult = jedis.sscan(key, cursor, scanParams);
                // 返回0 说明遍历完成
                cursor = scanResult.getStringCursor();
                List<String> result = scanResult.getResult();
                long t1 = System.currentTimeMillis();
                for (int m = 0; m < result.size(); m++) {
                    String element = result.get(m);
                    jedis.srem(key, element);
                }
                long t2 = System.currentTimeMillis();
                return (t2 - t1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }


    @Override
    public Long ttl(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

}
