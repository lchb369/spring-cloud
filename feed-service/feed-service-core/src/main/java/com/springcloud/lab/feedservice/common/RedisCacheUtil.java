package com.springcloud.lab.feedservice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存配置资料
 * //http://www.cnblogs.com/ityouknow/p/5748830.html
 */
@Component
public class RedisCacheUtil {

    @Autowired
    @Resource(name = "DefaultStringRedisTemplate")
    private StringRedisTemplate defaultStringRedisTemplate;

    @Autowired
    @Resource(name = "DefaultRedisTemplate")
    private RedisTemplate defalutRedisTemplate;

    //动态切换连接源
    private StringRedisTemplate stringRedisTemplate;

    //动态切换连接源
    private RedisTemplate redisTemplate;

    @Autowired
    public void RedisCacheUtil(){
        this.stringRedisTemplate=defaultStringRedisTemplate;
        this.redisTemplate=defalutRedisTemplate;
    }

    //从外部设置其它连接源
    public void setRedisTemplate(StringRedisTemplate stringRedisTemplate,RedisTemplate redisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
        this.redisTemplate=redisTemplate;
    }

    public void set(String key, String val) {

        try {
            stringRedisTemplate.opsForValue().set(key, val);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String key,Object val){
        try {
            redisTemplate.opsForValue().set(key,val);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getString(String key) {
        return  stringRedisTemplate.opsForValue().get(key);
    }


    public Object getObject(String key) {
        return  redisTemplate.opsForValue().get(key);
    }

    /**
     * 原子操作,如果key不存在，返回true,如果已经存在返回false
     * @param key
     * @param val
     * <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     * @return
     */
    public boolean setEx(String key,String val){
        return stringRedisTemplate.opsForValue().setIfAbsent(key,val);
    }

    /**
     * 原子操作,如果key不存在，返回true,如果已经存在返回false
     * @param key
     * @param val
     * <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     * @return
     */
    public boolean setEx(String key,Object val){
       return redisTemplate.opsForValue().setIfAbsent(key,val);
    }

    /**
     * 设置对像过期时间，单位秒
     * @param key
     * @param timeout
     * @return
     */
    public boolean setObjectExpire(String key,Integer timeout){
        return redisTemplate.expire(key,timeout,TimeUnit.SECONDS);
    }


    /**
     * 设置字符串过期时间，单位秒
     * @param key
     * @param timeout
     * @return
     */
    public boolean setStringExpire(String key,Integer timeout){
        return stringRedisTemplate.expire(key,timeout,TimeUnit.SECONDS);
    }


    /**
     * 集合SET
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key,String value){
        return stringRedisTemplate.opsForSet().add(key,value);
    }

    public long sadd(String key,Object value){
        return redisTemplate.opsForSet().add(key,value);
    }

    public Set<Object> sget(String key){
        return redisTemplate.opsForSet().members(key);
    }

    public void deleteString(String key){
        stringRedisTemplate.delete(key);
    }


    public void deleteObject(String key){
        redisTemplate.delete(key);
    }
    
    public void reverseRangeWithScores(String key, Long start, Long end) {
    	
    	redisTemplate.boundZSetOps(key).rangeWithScores(start, end);
    }
    
    /**
     * 添加有序集合
     * 
     * @param key
     * @param value
     * @return
     */
    public Long addZSet(String key, TreeSet<Object> value) {
        return redisTemplate.boundZSetOps(key).add(value);
    }
    
    /**
     * 添加有序集合
     * 默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean addZSet(String key, Object value, double score) {
        return redisTemplate.boundZSetOps(key).add(value, score);
    }
    
    /**
     * 通过分数(权值)获取ZSET集合 正序 -从小到大
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> getZSetRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).rangeByScore(start, end);
    }
    
    /**
     * 通过分数(权值)获取ZSET集合 倒序 -从大到小
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> getZSetReverseRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).reverseRangeByScore(start, end);
    }
    
    /**
     * 获取集合总数
     * 
     * @param key
     * @return
     */
    public Long getZSetCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }
    
    public Long getZSetCount(String key, long min, long max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }
    
    public Set<Object> getZSetRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).range(start, end);
    }
    
    public Set<Object> getZSetRevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }
    
    public Set<Object> getZSetRevRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }
    
    public void setHash(String cachekey, String hashKey, Object value) {
        redisTemplate.opsForHash().put(cachekey, hashKey, value);
    }
    
    public long delHash(String cachekey, String hashKey) {
        return redisTemplate.opsForHash().delete(cachekey, hashKey);
    }
    
    public Map<String, Object> getHash(String cachekey) {
    	return redisTemplate.boundHashOps(cachekey).entries();
    }
    
    /**
     * 从集合中移除某条记录
     * 
     * @param cachekey
     * @param values
     * @return
     */
    public long rmZSetByVal(String cachekey, Object values) {
    	return redisTemplate.boundZSetOps(cachekey).remove(values);
    }
    
    /**
     * 返回 记录的排名
     * 
     * @param cachekey
     * @param values
     * @return
     */
    public long getZSetRank(String cachekey, Object values) {
    	return redisTemplate.boundZSetOps(cachekey).rank(values);
    }
    
    public double decr(String key, long by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }
    
    public double incr(String key, long by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    public Object flushDB() {

        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
               connection.flushAll();
               return "ok";
            }
        });
    }
    
    public long addSet(String key, String values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    
    public boolean getSet(String key, String values) {
        return redisTemplate.opsForSet().isMember(key, values);
    }
    
    public long rmSet(String key, String values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

}
