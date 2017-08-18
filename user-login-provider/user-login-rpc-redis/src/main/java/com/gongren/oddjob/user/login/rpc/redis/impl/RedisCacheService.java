package com.gongren.oddjob.user.login.rpc.redis.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.gongren.oddjob.user.login.rpc.redis.api.CacheService;


@Service
public  class RedisCacheService implements CacheService {

    private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheService.class);


    public static final long EXPIRE_SECOND_DEFAULT_LONG = 4*60*60;//60*5;// 3600*24;

    @Autowired
    protected RedisTemplate<String, Serializable> redisTemplate1;

//    @Autowired
//    protected RedisTemplate<String, Serializable> redisTemplate2;

    int redisTemplateCount = 2;

    List<RedisTemplate<String, Serializable>> redisTemplateList = new ArrayList<RedisTemplate<String, Serializable>>(redisTemplateCount);

    /**
     * 按照key路由redis
     * @param key
     * @return
     */
    public RedisTemplate<String, Serializable> getRedisTemplate(String key){
        RedisTemplate<String, Serializable> template = null;
//        int code = key.hashCode();
//        if(code%redisTemplateCount==0){
            template = redisTemplate1;
//        }else {
//            template = redisTemplate2;
//        }
        return template;
    }

    private  List<RedisTemplate<String, Serializable>> getRedisTemplateList(){
        if(redisTemplateList.size()<redisTemplateCount){
            redisTemplateList.add(0,redisTemplate1);
//            redisTemplateList.add(1,redisTemplate2);
        }
        return redisTemplateList;
    }

    /**
     * 对象保存
     * @param key
     * @param value
     */
    public void putObject(@NotBlank final String key, @NotBlank final Object value) {
        putObject(key,value,EXPIRE_SECOND_DEFAULT_LONG);
    }


    /**
     * 对象保存
     * @param key
     * @param value
     * @param expireTime
     */
    public void putObject(@NotBlank final String key, @NotBlank final Object value, Long expireTime) {
        try{
            expireTime = (expireTime == null) ? EXPIRE_SECOND_DEFAULT_LONG : expireTime;
            BoundValueOperations<String, Serializable> opsObj = getRedisTemplate(key).boundValueOps(key);
            if (value instanceof Serializable) {
                opsObj.set((Serializable)value);
            }else if (value != null) {
                opsObj.set(serialize(value));
            }
            opsObj.expire(getExpireTime(expireTime), TimeUnit.SECONDS);
        }catch (Exception e){
            LOGGER.error("RedisCacheService putObject error:",e);
        }
    }

    public void putZSetObject(@NotBlank final String key, @NotBlank final Object value, long score){
        try{
            Long expireTime = null;
            expireTime = getExpireTime(expireTime);
            BoundZSetOperations<String, Serializable> opsObj = getRedisTemplate(key).boundZSetOps(key);
            if (value instanceof Serializable) {
                opsObj.add((Serializable)value,score);
            }else if (value != null) {
                opsObj.add(serialize(value),score);
            }
            opsObj.expire(getExpireTime(expireTime), TimeUnit.SECONDS);
        }catch (Exception e){
            LOGGER.error("RedisCacheService putObject error:",e);
        }
    }

    /**
     * 对象保存 永久有效
     * @param key
     * @param value
     */
    public void putObjectPermanent(@NotBlank final String key, @NotBlank final Object value) {
        BoundValueOperations<String, Serializable> opsObj = getRedisTemplate(key).boundValueOps(key);
        if (value instanceof Serializable) {
            opsObj.set((Serializable)value);
        }else if (value != null) {
            opsObj.set(serialize(value));
        }
    }

    /**
     * 操作list集合 向左追加
     * @param key
     * @param value
     * @param expireTime
     */
    public Long leftPush(@NotBlank final String key, @NotBlank final Object value, Long expireTime) {
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        opsList.expire(getExpireTime(expireTime), TimeUnit.SECONDS);
        return opsList.leftPush(serialize(value));
    }

    public Long leftPush(@NotBlank final String key, @NotBlank final Object value) {
        return leftPush(key, value, EXPIRE_SECOND_DEFAULT_LONG);
    }

    /**
     * list集合中向左出栈
     * @param key
     * @return
     */
    public Object leftPop(@NotBlank final String key) {
        Object obj;
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        obj = opsList.leftPop();
        if(obj !=null){
            obj = unserialize((byte[])obj);
        }
        return obj;
    }

    /**
     * 操作list集合 向右追加
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public Long rightPush(@NotBlank final String key, @NotBlank final Object value, Long expireTime) {
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        opsList.expire(getExpireTime(expireTime), TimeUnit.SECONDS);
        return opsList.rightPush(serialize(value));
    }

    public Long rightPush(@NotBlank final String key, @NotBlank final Object value) {
        return rightPush(key, value, EXPIRE_SECOND_DEFAULT_LONG);
    }

    /**
     * list集合中向右出站
     * @param key
     * @return
     */
    public Object rightPop(@NotBlank final String key) {
        Object obj;
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        obj = opsList.rightPop();
        if(obj !=null){
            obj = unserialize((byte[])obj);
        }
        return obj;
    }

    /**
     * 取list集合中的指定index的值
     * @param key
     * @param index
     * @return
     */
    public Object indexPop(@NotBlank final String key , @NotBlank final Long index) {
        Object obj;
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        obj = opsList.index(index);
        if(obj !=null){
            obj = unserialize((byte[])obj);
        }
        return obj;
    }

    /**
     * key list集合中的大小size
     * @param key
     * @return
     */
    public Long listSize(@NotBlank final String key){
        BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
        return  opsList.size();
    }




    /**
     * 取对象
     * @param key
     * @return
     */
    public  Object getObject(@NotBlank final String key) {

        Object resultObj = null;
        try{
            DataType type = getRedisTemplate(key).type(key);
            if(DataType.HASH.equals(type)){
                BoundHashOperations<String,String,String> opsMap = getRedisTemplate(key).boundHashOps(key);
                opsMap.get(key);
            }else if(DataType.LIST.equals(type)){
                BoundListOperations<String, Serializable> opsList = getRedisTemplate(key).boundListOps(key);
                resultObj = opsList.index(0);
            }else if(DataType.STRING.equals(type)){
                BoundValueOperations<String, Serializable> opsString = getRedisTemplate(key).boundValueOps(key);
                resultObj = opsString.get();
            }else if(DataType.ZSET.equals(type)){
                BoundZSetOperations<String, Serializable> opsString = getRedisTemplate(key).boundZSetOps(key);
                resultObj = opsString.range(0,-1);
            }else if(type!=null){
                BoundValueOperations<String, Serializable> opsObj = getRedisTemplate(key).boundValueOps(key);
                resultObj = opsObj.get();
                if(resultObj!=null && resultObj instanceof byte[]){
                    resultObj = unserialize(((byte[])resultObj));
                }
            }
        }catch(Exception e){
            LOGGER.error("RedisCacheService getObject error:",e);
        }

        return resultObj;
    }

    public void removeZSet(@NotBlank final String key, Object obj){
        try{
            BoundZSetOperations<String, Serializable> opsString = getRedisTemplate(key).boundZSetOps(key);
            opsString.remove((Serializable)obj);
        }catch(Exception e){
            LOGGER.error("RedisCacheService getObject error:",e);
        }
    }

    /**
     * key 是否存在
     * @param key
     * @return
     */
    public Boolean existsKey(@NotBlank String key){
        boolean hasKey = false;
        try{
            List<RedisTemplate<String, Serializable>> redisTemplateList =  getRedisTemplateList();
            for(RedisTemplate<String, Serializable> redisTemplate :redisTemplateList){
                boolean has = redisTemplate.hasKey(key);
                if(has){
                    hasKey = true;
                    break;
                }
            }
        }catch (Exception e){
            LOGGER.error("RedisCacheService existsKey error:",e);
        }
        return hasKey ;
    }

    /**
     * 过期时间
     * @param key
     * @return
     */
    public Long getExpire(@NotBlank String key){
        return getRedisTemplate(key).getExpire(key);
    }

    /**
     * 删除
     * @param key
     */
    public void remove(@NotBlank String key){
        try{
            redisTemplate1.delete(key);
//            redisTemplate2.delete(key);
        }catch (Exception e){
            LOGGER.error("RedisCacheService remove error:",e);
        }
    }

    /**
     * 删除
     * @param keys
     */
    public void remove(@NotBlank String[] keys){
        for(String key :keys){
            remove(key);
        }

    }

    /**
     * 删除缓存
     * @param pattern 匹配模式，支持*、?、[]
     * * 匹配所有 key 。
     * h?llo 匹配 hello ， hallo 和 hxllo 等。
     * h*llo 匹配 hllo 和 heeeeello 等。
     * h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     */
    public Integer removePattern(@NotBlank String pattern){
        Set<String> keySet = getKeys(pattern);
        for(String key :keySet){
            remove(key);
        }
        return keySet.size();
    }

    /**
     * 查到key
     * @param pattern 匹配模式，支持*、?、[]
     * * 匹配所有 key 。
     * h?llo 匹配 hello ， hallo 和 hxllo 等。
     * h*llo 匹配 hllo 和 heeeeello 等。
     * h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     */
    public Set<String> getKeys(@NotBlank String pattern){
        List<RedisTemplate<String, Serializable>> redisTemplateList =  getRedisTemplateList();
        Set<String> set = new HashSet<String>();
        for(RedisTemplate<String, Serializable> redisTemplate :redisTemplateList){
            Set<String> keySet = redisTemplate.keys(pattern);
            if(keySet!=null){
                set.addAll(keySet);
            }
        }
        return set;

    }

    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bytes = null;
        try {
            bytes = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bytes);
            oos.writeObject(object);
            return bytes.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    LOGGER.error("RedisCacheService serialize close ObjectOutputStream error:",e);
                }
            }
            if (bytes != null) {
                try {
                    bytes.close();
                } catch (IOException e) {
                    LOGGER.error("RedisCacheService serialize close ByteArrayOutputStream error:",e);
                }
            }
        }
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) return null;

        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    LOGGER.error("RedisCacheService serialize close ObjectOutputStream error:",e);
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    LOGGER.error("RedisCacheService serialize close ByteArrayOutputStream error:",e);
                }
            }
        }
        return null;
    }

    public Long getExpireTime(Long expireTime) {
        expireTime = (expireTime == null || expireTime.longValue() <= 0) ? EXPIRE_SECOND_DEFAULT_LONG : expireTime;
        return expireTime;
    }

    /**
     * SET if Not eXists
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(final String key, final String value) {
        final RedisTemplate<String, Serializable> template = getRedisTemplate(key);
        Boolean result = false;
        if (template != null) {
            result = template.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer keySerializer = template.getKeySerializer();
                    RedisSerializer valueSerializer = template.getValueSerializer();
                    byte[] keys = keySerializer.serialize(key);
                    byte[] values = valueSerializer.serialize(value);
                    return connection.setNX(keys,values);
                }
            });
        }
        return result;
    }

    /**
     * 获取上一个值并且赋新值
     * @param key
     * @param value
     * @return
     */
    public String getSet(final String key, final String value) {
        String result = null;
        final RedisTemplate<String, Serializable> template = getRedisTemplate(key);
        if (template != null) {
            result = template.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer keySerializer = template.getKeySerializer();
                    RedisSerializer valueSerializer = template.getValueSerializer();
                    byte[] keys = keySerializer.serialize(key);
                    byte[] values = valueSerializer.serialize(value);
                    byte[] oldValue = connection.getSet(keys,values);
                    if(oldValue != null)
                        return (String)valueSerializer.deserialize(oldValue);
                    return null;
                }
            });
        }
        return result;
    }
}
