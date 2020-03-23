package com.gettyio.gim.cluster.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis代理接口
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
     * 返回key所储存的值的类型
     *
     * @param key key
     * @return String
     */
    String type(String key);

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
     * 用value参数覆写(Overwrite)给定key所储存的字符串值，从偏移量offset开始。 不存在的key当作空白字符串处理。
     *
     * @param key    key
     * @param offset offset
     * @param value  value
     * @return long
     */
    long setrange(String key, long offset, String value);

    /**
     * 返回key中字符串值的子字符串，字符串的截取范围由startOffset和endOffset两个偏移量决定(
     * 包括startOffset和endOffset在内)。
     *
     * @param key         key
     * @param startOffset startOffset
     * @param endOffset   endOffset
     * @return 子字符串
     */
    String getrange(String key, long startOffset, long endOffset);

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
     * 如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
     * 如果key不存在，APPEND就简单地将给定key设为value，就像执行SET key value一样。
     *
     * @param key   key
     * @param value value
     * @return 值
     */
    Long append(String key, String value);

    /**
     * 截取子串
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return 子串
     */
    String substr(String key, int start, int end);

    /**
     * 将哈希表key中的域field的值设为value。 如果key不存在，一个新的哈希表被创建并进行HSET操作。
     * 如果域field已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return 值
     */
    Long hset(String key, String field, String value);

    /**
     * 返回哈希表key中给定域field的值。
     *
     * @param key   key
     * @param field field
     * @return 值
     */
    String hget(String key, String field);

    /**
     * 将哈希表key中的域field的值设置为value，当且仅当域field不存在。 若域field已经存在，该操作无效。
     * 如果key不存在，一个新哈希表被创建并执行HSETNX命令。
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return value
     */
    Long hsetnx(String key, String field, String value);

    /**
     * 同时将多个field - value(域-值)对设置到哈希表key中。 此命令会覆盖哈希表中已存在的域。
     *
     * @param key  key
     * @param hash hash
     * @return 值
     */
    String hmset(String key, Map<String, String> hash);

    /**
     * 返回哈希表key中，一个或多个给定域的值。
     *
     * @param key    key
     * @param fields fields
     * @return 值
     */
    List<String> hmget(String key, String... fields);

    /**
     * hincrBy
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return Long
     */
    Long hincrBy(String key, String field, long value);

    /**
     * 为哈希表key中的域field的值加上增量increment。 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果key不存在，一个新的哈希表被创建并执行HINCRBY命令。 如果域field不存在，那么在执行命令前，域的值被初始化为0。
     *
     * @param key   key
     * @param field field
     * @return 是否
     */
    Boolean hexists(String key, String field);

    /**
     * 移除给定的一个或多个key。 如果key不存在，则忽略该命令。
     *
     * @param key key
     * @return Long
     */
    Long del(String key);

    /**
     * 删除哈希表key中的一个指定域，不存在的域将被忽略
     *
     * @param key   key
     * @param field field
     * @return Long
     */
    Long hdel(String key, String field);

    /**
     * 返回哈希表key中域的数量。
     *
     * @param key key
     * @return Long
     */
    Long hlen(String key);

    /**
     * 返回哈希表key中的所有域
     *
     * @param key key
     * @return Set
     */
    Set<String> hkeys(String key);

    /**
     * 返回哈希表key中的所有值。
     *
     * @param key key
     * @return List
     */
    List<String> hvals(String key);

    /**
     * 返回哈希表key中，所有的域和值。 在返回值里，紧跟每个域名(field
     * name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     *
     * @param key key
     * @return Map
     */
    Map<String, String> hgetAll(String key);

    /**
     * 将一个值value插入到列表key的表尾。
     *
     * @param key    key
     * @param string string
     * @return Long
     */
    Long rpush(String key, String string);

    /**
     * 将一个值value插入到列表key的表头。并返回列表size
     *
     * @param key    key
     * @param string string
     * @return Long
     */
    Long lpush(String key, String string);

    /**
     * 返回列表key的长度。 如果key不存在，则key被解释为一个空列表，返回0. 如果key不是列表类型，返回一个错误。
     *
     * @param key key
     * @return Long
     */
    Long llen(String key);

    /**
     * 返回列表key中指定区间内的元素，区间以偏移量start和end指定。
     * 下标(index)参数start和end都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return List
     */
    List<String> lrange(String key, long start, long end);

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return String
     */
    String ltrim(String key, long start, long end);

    /**
     * 返回列表key中，下标为index的元素。
     *
     * @param key   key
     * @param index index
     * @return String
     */
    String lindex(String key, long index);

    /**
     * 将列表key下标为index的元素的值设置为value。 当index参数超出范围，或对一个空列表(key不存在)进行LSET时，返回一个错误。
     *
     * @param key   key
     * @param index index
     * @param value value
     * @return String
     */
    String lset(String key, long index, String value);

    /**
     * 根据参数count的值，移除列表中与参数value相等的元素。
     * <p>
     * count的值可以是以下几种：
     * <p>
     * count > 0: 从表头开始向表尾搜索，移除与value相等的元素，数量为count。
     * <p>
     * count < 0: 从表尾开始向表头搜索，移除与value相等的元素，数量为count的绝对值。
     * <p>
     * count = 0: 移除表中所有与value相等的值。
     *
     * @param key
     * @param count
     * @param value
     * @return Long
     */
    Long lrem(String key, long count, String value);

    /**
     * 移除并返回列表key的头元素。
     *
     * @param key key
     * @return String
     */
    String lpop(String key);

    /**
     * 移除并返回列表key的尾元素。
     *
     * @param key key
     * @return String
     */
    String rpop(String key);

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


    /**
     * 移除并返回集合中的一个随机元素。
     *
     * @param key key
     * @return String
     */
    String spop(String key);

    /**
     * 返回集合key的基数(集合中元素的数量)。
     *
     * @param key key
     * @return Long
     */
    Long scard(String key);

    /**
     * 判断member元素是否是集合key的成员。
     *
     * @param key    key
     * @param member member
     * @return 是否
     */
    Boolean sismember(String key, String member);

    /**
     * 返回集合中的一个随机元素。
     *
     * @param key key
     * @return String
     */
    String srandmember(String key);

    /**
     * 将一个member元素及其score值加入到有序集key当中。
     *
     * @param key    key
     * @param score  score
     * @param member member
     * @return Long
     */
    Long zadd(String key, double score, String member);

    /**
     * 返回有序集key中，指定区间内的成员。 其中成员的位置按score值递增(从小到大)来排序。
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Set
     */
    Set<String> zrange(String key, int start, int end);

    /**
     * 移除有序集key中的一个或多个成员，不存在的成员将被忽略。 当key存在但不是有序集类型时，返回一个错误。
     *
     * @param key    key
     * @param member member
     * @return Long
     */
    Long zrem(String key, String member);

    /**
     * 为有序集key的成员member的score值加上增量increment。
     *
     * @param key    key
     * @param score  score
     * @param member member
     * @return Double
     */
    Double zincrby(String key, double score, String member);

    /**
     * 返回有序集key中成员member的排名。其中有序集成员按score值递增(从小到大)顺序排列。
     * 排名以0为底，也就是说，score值最小的成员排名为0。
     *
     * @param key    key
     * @param member member
     * @return Long
     */
    Long zrank(String key, String member);

    /**
     * 返回有序集key中成员member的排名。其中有序集成员按score值递减(从大到小)排序。
     * 排名以0为底，也就是说，score值最大的成员排名为0。
     *
     * @param key    key
     * @param member member
     * @return Long
     */
    Long zrevrank(String key, String member);

    /**
     * 返回有序集key中，指定区间内的成员。 其中成员的位置按score值递减(从大到小)来排列。
     *
     * @param key   key
     * @param start
     * @param end
     * @return
     */
    Set<String> zrevrange(String key, int start, int end);

    /**
     * 返回有序集key的基数。
     *
     * @param key
     * @return
     */
    Long zcard(String key);

    /**
     * 返回有序集key中，成员member的score值。 如果member元素不是有序集key的成员，或key不存在，返回null。
     *
     * @param key
     * @param member
     * @return
     */
    Double zscore(String key, String member);

    /**
     * 排序
     *
     * @param key
     * @return
     */
    List<String> sort(String key);

    /**
     * 返回有序集key中，score值在min和max之间(默认包括score值等于min或max)的成员。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long zcount(String key, double min, double max);

    Set<String> zrangeByScore(String key, double min, double max);

    /**
     * 返回有序集key中，score值介于max和min之间(默认包括等于max或min)的所有的成员。有序集成员按score值递减(从大到小)
     * 的次序排列
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    Set<String> zrevrangeByScore(String key, double max, double min);

    /**
     * 返回有序集key中，所有score值介于min和max之间(包括等于min或max)的成员。有序集成员按score值递增(从小到大)次序排列。
     * 具有相同score值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<String> zrangeByScore(String key, double min, double max,
                              int offset, int count);

    /**
     * zrevrangeByScore
     *
     * @param key    key
     * @param max    max
     * @param min    min
     * @param offset offset
     * @param count  count
     * @return Set
     */
    Set<String> zrevrangeByScore(String key, double max, double min,
                                 int offset, int count);

    /**
     * zremrangeByRank
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Long
     */
    Long zremrangeByRank(String key, int start, int end);

    /**
     * zremrangeByScore
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return zremrangeByScore
     */
    Long zremrangeByScore(String key, double start, double end);
}
