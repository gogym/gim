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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 提供缓存内容操作接口，包括key以及value的操作，本代理只提供缓存基本操作，不提供涉及连接等连接池操作。
 * <p>
 * 1、以H开头的为HASH操作命令；
 * <p>
 * 2、以L开头的为LIST操作命令；
 * <p>
 * 3、以S开头的为SET（集合）操作命令；
 * <p>
 * 4、以Z开头的为ZSET（有序集合）操作命令
 */


/**
 * IRedisProxy.java
 *
 * @description:redis代理接口
 * @author:gogym
 * @date:2020/4/10
 * @copyright: Copyright by gettyio.com
 */
public interface IRedisProxy {

    /**
     * toString
     *
     * @return 值
     */
    String toString();

    /**
     * 将字符串值value关联到key。 如果key已经持有其他值，SET就覆写旧值，无视类型。
     *
     * @param key   key
     * @param value value
     * @return string
     */
    String set(String key, String value);

    /**
     * 返回key所关联的字符串值
     * <p>
     * 假如key储存的值不是字符串类型，返回一个错误，因为GET只能用于处理字符串值
     *
     * @param key key
     * @return String
     */
    String get(String key);

    /**
     * 检查给定key是否存在。
     * <p>
     *
     * @param key key
     * @return 是否
     */
    Boolean exists(String key);

    /**
     * 为给定key设置生存时间。单位秒 当key过期时，它会被自动删除。
     *
     * @param key     key
     * @param seconds seconds
     * @return Long
     */
    Long expire(String key, int seconds);

    /**
     * 在某个时间点失效，以UNIX时间戳为key设置生存时间。 EXPIREAT命令接受的时间参数是UNIX时间戳(unix timestamp)。
     *
     * @param key      key
     * @param unixTime unixTime
     * @return Long
     */
    Long expireAt(String key, long unixTime);

    /**
     * 返回给定key的剩余生存时间(time to live)(以秒为单位)。
     *
     * @param key key
     * @return Long
     */
    Long ttl(String key);


    /**
     * 将给定key的值设为value，并返回key的旧值。 当key存在但不是字符串类型时，返回一个错误。
     *
     * @param key   key
     * @param value value
     * @return value
     */
    String getSet(String key, String value);

    /**
     * 设置过期时间
     *
     * @param key   key
     * @param value value
     * @return Long
     */
    Long setnx(String key, String value);

    /**
     * 将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。 如果key 已经存在，SETEX命令将覆写旧值。
     *
     * @param key     key
     * @param seconds seconds
     * @param value   value
     * @return 值
     */
    String setex(String key, int seconds, String value);

    /**
     * 将key所储存的值减去减量integer。 如果key不存在，以0为key的初始值，然后执行DECRBY操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在64位(bit)有符号数字表示之内。
     *
     * @param key     key
     * @param integer integer
     * @return 值
     */
    Long decrBy(String key, long integer);

    /**
     * 将key中储存的数字值减一。 如果key不存在，以0为key的初始值，然后执行DECR操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param key key
     * @return 值
     */
    Long decr(String key);

    /**
     * 将key所储存的值加上增量increment。 如果key不存在，以0为key的初始值，然后执行INCRBY命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     *
     * @param key     key
     * @param integer integer
     * @return 值
     */
    Long incrBy(String key, long integer);

    /**
     * 将key中储存的数字值增一。 如果key不存在，以0为key的初始值，然后执行INCR操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param key key
     * @return Long
     */
    Long incr(String key);


    /**
     * 移除给定的一个或多个key。 如果key不存在，则忽略该命令。
     *
     * @param key key
     * @return Long
     */
    Long del(String key);


    /**
     * 将一个值value插入到列表key的表头。并返回列表size
     *
     * @param key    key
     * @param string string
     * @return Long
     */
    Long lpush(String key, String string);

    /**
     * Description: 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param timeout 0表示一直阻塞
     * @param key
     * @return
     * @see
     */
    List<String> brpop(int timeout, String key);

    /**
     * 将一个或多个member元素加入到集合key当中，已经存在于集合的member元素将被忽略。
     * 假如key不存在，则创建一个只包含member元素作成员的集合。
     *
     * @param key    key
     * @param member member
     * @return Long
     */
    Long sadd(String key, String member);

    Long sadd(String key, String... member);

    /**
     * 返回集合key中的所有成员。
     *
     * @param key key
     * @return Set
     */
    Set<String> smembers(String key);

    /**
     * 移除集合key中的一个或多个member元素，不存在的member元素会被忽略。 当key不是集合类型，返回一个错误。
     *
     * @param key    key
     * @param member member
     * @return Long
     */
    Long srem(String key, String member);

    Long srem(String key, String... member);

    /**
     * Description: 批量删除集合
     *
     * @param key
     * @return
     * @see
     */
    Long batchSrem(String key);
}
