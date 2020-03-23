package com.gettyio.gim.cluster.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 本实现为Redis服务代理
 */
public class RedisProxyImp implements IRedisProxy {

    private JedisPool jedisPool;

    private static RedisProxyImp mInstance;

    private RedisProxyImp() {
    }

    private RedisProxyImp(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    //单例模式
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

    public Long append(String key, String value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.append(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long hdel(String key, String field) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hexists(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        Map<String, String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long hlen(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hlen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hmget(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public List<String> hvals(String key) {
        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public String lindex(String key, long index) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lindex(key, index);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long llen(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String lpop(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lrem(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String lset(String key, long index, String value) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.lset(key, index, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String rpop(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public Long rpush(String key, String string) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.rpush(key, string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public Long scard(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public long setrange(String key, long offset, String value) {
        Jedis jedis = null;
        long ret = 0;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.setrange(key, offset, value);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public List<String> sort(String key) {
        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.sort(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String spop(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.spop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public String srandmember(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.srandmember(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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
                ScanResult<String> scanResult = jedis.sscan(key, cursor,
                        scanParams);
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

    public String substr(String key, int start, int end) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.substr(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

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

    public String type(String key) {
        Jedis jedis = null;
        String ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.type(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zadd(key, score, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zcard(String key) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zcard(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zcount(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        Double ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zincrby(key, score, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrangeByScore(String key, double min, double max,
                                     int offset, int count) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zrank(String key, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrank(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zrem(String key, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrem(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zremrangeByRank(String key, int start, int end) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min,
                                        int offset, int count) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zrevrank(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        Double ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.zscore(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

}
