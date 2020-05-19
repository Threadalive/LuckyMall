package com.ruoyi.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 创建一个redis的工具类
 * @Author zhenxing.dong
 * @Date 2020/4/14 13:16
 */
@Component
public class RedisUtil {

    /**
     * 本地redis连接
     */
    private final Jedis jedis = new Jedis("localhost");

    private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @param indexdb 选择redis库 0-15
     * @return 成功返回value 失败返回null
     */
    public String get(String key, int indexdb) {
//        jedis = new Jedis("localhost");
        String value = null;
        try {
//            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);
            log.info(value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return value;
    }

    public Transaction multi() {
//        jedis = new Jedis("localhost");
        Transaction value = null;
        try {
            value = jedis.multi();
            log.info(value.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return value;
    }

    public Pipeline pipelined(){
//        jedis = new Jedis("localhost");
        Pipeline value = null;
        try {
            value = jedis.pipelined();
            log.info(value.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return value;
    }

    public void watch(String key) {
//        jedis = new Jedis("localhost");
        jedis.watch(key);
    }

    public void unWatch() {
//        jedis = new Jedis("localhost");
        jedis.unwatch();
    }

    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @param indexdb 选择redis库 0-15
     * @return 成功返回value 失败返回null
     */
    public byte[] get(byte[] key, int indexdb) {
//        jedis = new Jedis("localhost");
        byte[] value = null;
        try {
            jedis.select(indexdb);
            value = jedis.get(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return value;
    }

    /**
     * <p>
     * 向redis存入key和value,并释放连接资源
     * </p>
     * <p>
     * 如果key已经存在 则覆盖
     * </p>
     *
     * @param key
     * @param value
     * @param indexdb 选择redis库 0-15
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value, int indexdb) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "0";
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 向redis存入key和value,并释放连接资源
     * </p>
     * <p>
     * 如果key已经存在 则覆盖
     * </p>
     *
     * @param key
     * @param value
     * @param indexdb 选择redis库 0-15
     * @return 成功 返回OK 失败返回 0
     */
    public String set(byte[] key, byte[] value, int indexdb) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {

            log.error(e.getMessage());
            return "0";
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数组
     * </p>
     *
     * @param keys 一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数组
     * </p>
     *
     * @param indexdb 选择redis库 0-15
     * @param keys    一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(int indexdb, String... keys) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数组
     * </p>
     *
     * @param indexdb 选择redis库 0-15
     * @param keys    一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(int indexdb, byte[]... keys) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 通过key向指定的value值追加值
     * </p>
     *
     * @param key
     * @param str
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
     */
    public Long append(String key, String str) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.append(key, str);
        } catch (Exception e) {

            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 判断key是否存在
     * </p>
     *
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 清空当前数据库中的所有 key,此命令从不失败。
     * </p>
     *
     * @return 总是返回 OK
     */
    public String flushDB() {
//        jedis = new Jedis("localhost");
        try {
            return jedis.flushDB();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return null;
    }

    /**
     * <p>
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * </p>
     *
     * @param key
     * @param value 过期时间，单位：秒
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long expire(String key, int value, int indexdb) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.expire(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
    }

    /**
     * <p>
     * 以秒为单位，返回给定 key 的剩余生存时间
     * </p>
     *
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
     * 的剩余生存时间。 发生异常 返回 0
     */
    public Long ttl(String key, int indexdb) {
//        jedis = new Jedis("localhost");
        try {
            jedis.select(indexdb);
            return jedis.ttl(key);
        } catch (Exception e) {

            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }
    }


    /**
     * <p>
     * 通过下标 和key 获取指定下标位置的 value
     * </p>
     *
     * @param key
     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
     * @param endOffset
     * @return 如果没有返回null
     */
    public String getrange(String key, int startOffset, int endOffset) {
//        jedis = new Jedis("localhost");
        String res = null;
        try {
            res = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key
     * @return 加值后的结果
     */
    public Long incr(String key) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.incr(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     * </p>
     *
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, Long integer) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.incrBy(key, integer);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     * </p>
     *
     * @param key
     * @return
     */
    public Double incrByFloat(String key, Double increment) {
//        jedis = new Jedis("localhost");
        Double res = null;
        try {
            res = jedis.incrByFloat(key, increment);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }
    /**
     * <p>
     * 通过key给field设置指定的值,如果key不存在,则先创建
     * </p>
     *
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key, String field, String value) {
        Long res = null;
        try {
            res = jedis.hset(key, field, value);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
        }
        return res;
    }

    /**
     * <p>
     * 通过key同时设置 hash的多个field
     * </p>
     *
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public String hmset(String key, Map<String, String> hash, int indexdb) {
        String res = null;
        try {
            jedis.select(indexdb);
            res = jedis.hmset(key, hash);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
        }
        return res;
    }

    /**
     * <p>
     * 通过key 和 field 获取指定的 value
     * </p>
     *
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field) {
        String res = null;
        try {
            res = jedis.hget(key, field);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
        }
        return res;
    }



    /**
     * <p>
     * 通过key给指定的field的value加上给定的值
     * </p>
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrby(String key, String field, Long value) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.hincrBy(key, field, value);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key和field判断是否有指定的value存在
     * </p>
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
//        jedis = new Jedis("localhost");
        Boolean res = false;
        try {
            res = jedis.hexists(key, field);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回field的数量
     * </p>
     *
     * @param key
     * @return
     */
    public Long hlen(String key) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.hlen(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;

    }

    /**
     * <p>
     * 通过key 删除指定的 field
     * </p>
     *
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    public Long hdel(String key, String... fields) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.hdel(key, fields);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回所有的field
     * </p>
     *
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
//        jedis = new Jedis("localhost");
        Set<String> res = null;
        try {
            res = jedis.hkeys(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }


    /**
     * <p>
     * 通过key获取所有的field和value
     * </p>
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key, int indexdb) {
//        jedis = new Jedis("localhost");
        Map<String, String> res = null;
        try {
            jedis.select(indexdb);
            res = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key向list头部添加字符串
     * </p>
     *
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(int indexdb, String key, String... strs) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            jedis.select(indexdb);
            res = jedis.lpush(key, strs);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }
    public String ltrim(String key, long start,long end) {
//        jedis = new Jedis("localhost");
        String res = null;
        try {
            res = jedis.ltrim(key, start,end);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取list指定下标位置的value
     * </p>
     * <p>
     * 如果start 为 0 end 为 -1 则返回全部的list中的value
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end, int indexdb) {
//        jedis = new Jedis("localhost");
        List<String> res = null;
        try {
            jedis.select(indexdb);
            res = jedis.lrange(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 将列表 key 下标为 index 的元素的值设置为 value
     * </p>
     *
     * @param key
     * @param index
     * @param value
     * @return 操作成功返回 ok ，否则返回错误信息
     */
    public String lset(String key, long index, String value) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.lset(key, index, value);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return null;
    }

    /**
     * <p>
     * 返回给定排序后的结果
     * </p>
     *
     * @param key
     * @param sortingParameters
     * @return 返回列表形式的排序结果
     */
    public List<String> sort(String key, SortingParams sortingParameters) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.sort(key, sortingParameters);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return null;
    }

    /**
     * <p>
     * 返回排序后的结果，排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * </p>
     *
     * @param key
     * @return 返回列表形式的排序结果
     */
    public List<String> sort(String key) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.sort(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return null;
    }

    /**
     * <p>
     * 通过key向指定的set中添加value
     * </p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public Long sadd(String key, String... members) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.sadd(key, members);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key删除set中对应的value值
     * </p>
     *
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public Long srem(String key, String... members) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.srem(key, members);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中value的个数
     * </p>
     *
     * @param key
     * @return
     */
    public Long scard(String key) {

        Long res = null;
        try {
            res = jedis.scard(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
        }
        return res;
    }

    /**
     * <p>
     * 通过key判断value是否是set中的元素
     * </p>
     *
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
//        jedis = new Jedis("localhost");
        Boolean res = null;
        try {
            res = jedis.sismember(key, member);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }


    /**
     * <p>
     * 通过key获取set中所有的value
     * </p>
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
//        jedis = new Jedis("localhost");
        Set<String> res = null;
        try {
            res = jedis.smembers(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key向zset中添加value,score,其中score就是用来排序的
     * </p>
     * <p>
     * 如果该value已经存在则根据score更新元素
     * </p>
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.zadd(key, score, member);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 返回有序集 key 中，指定区间内的成员。min=0,max=-1代表所有元素
     * </p>
     *
     * @param key
     * @param min
     * @param max
     * @return 指定区间内的有序集成员的列表。
     */
    public Set<String> zrange(String key, long min, long max) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.zrange(key, min, max);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return null;
    }

    /**
     * <p>
     * 为哈希表 key 中的域 field 的值加上增量 increment 。增量也可以为负数，相当于对给定域进行减法操作。 如果 key
     * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。本操作的值被限制在 64 位(bit)有符号数字表示之内。
     * </p>
     * <p>
     * 将名称为key的hash中field的value增加integer
     * </p>
     *
     * @param key
     * @param value
     * @param increment
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field的值。异常返回0
     */
    public Long hincrBy(String key, String value, long increment) {
//        jedis = new Jedis("localhost");
        try {
            return jedis.hincrBy(key, value, increment);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
//            jedis.close();
        }

    }

    /**
     * <p>
     * 通过key删除在zset中指定的value
     * </p>
     *
     * @param key
     * @param members 可以使一个string 也可以是一个string数组
     * @return
     */
    public Long zrem(String key, String... members) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.zrem(key, members);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key增加该zset中value的score的值
     * </p>
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
//        jedis = new Jedis("localhost");
        Double res = null;
        try {
            res = jedis.zincrby(key, score, member);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key将获取score从start到end中zset的value
     * </p>
     * <p>
     * socre从大到小排序
     * </p>
     * <p>
     * 当start为0 end为-1时返回全部
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
//        jedis = new Jedis("localhost");
        Set<String> res = null;
        try {
            res = jedis.zrevrange(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    public Set<Tuple> zrevrangeWithScores(String key,long start,long end){
//        jedis = new Jedis("localhost");
        Set<Tuple> res = null;
        try {
            res = jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }


    /**
     * <p>
     * 通过key返回指定score内zset中的value
     * </p>
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangeByScore(String key, double max, double min) {
//        jedis = new Jedis("localhost");
        Set<String> res = null;
        try {
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 返回指定区间内zset中value的数量
     * </p>
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, String min, String max) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.zcount(key, min, max);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回zset中的value个数
     * </p>
     *
     * @param key
     * @return
     */
    public Long zcard(String key) {
//        jedis = new Jedis("localhost");
        Long res = null;
        try {
            res = jedis.zcard(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取zset中value的score值
     * </p>
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
//        jedis = new Jedis("localhost");
        Double res = null;
        try {
            res = jedis.zscore(key, member);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }


    /**
     * <p>
     * 返回满足pattern表达式的所有key
     * </p>
     * <p>
     * keys(*)
     * </p>
     * <p>
     * 返回所有的key
     * </p>
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
//        jedis = new Jedis("localhost");

        Set<String> res = null;
        try {
            res = jedis.keys(pattern);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    public Set<String> keysBySelect(String pattern, int database) {
//        jedis = new Jedis("localhost");
        Set<String> res = null;
        try {
            jedis.select(database);
            res = jedis.keys(pattern);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * <p>
     * 通过key判断值得类型
     * </p>
     *
     * @param key
     * @return
     */
    public String type(String key) {
//        jedis = new Jedis("localhost");
        String res = null;
        try {
            res = jedis.type(key);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
//            jedis.close();
        }
        return res;
    }

    /**
     * 序列化对象
     *
     * @param obj
     * @return 对象需实现Serializable接口
     */
    public static byte[] ObjTOSerialize(Object obj) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream byteOut = null;
        try {
            byteOut = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteOut);
            oos.writeObject(obj);
            byte[] bytes = byteOut.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return 对象需实现Serializable接口
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

//    /**
//     * 返还到连接池
//     *
//     * @param jedisPool
//     * @param jedis
//     */
//    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
}
