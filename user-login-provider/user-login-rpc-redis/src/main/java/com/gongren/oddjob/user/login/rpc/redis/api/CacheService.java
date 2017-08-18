package com.gongren.oddjob.user.login.rpc.redis.api;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * Created by jqwang on 15/10/21.
 */
public interface CacheService {

    void remove(@NotBlank final String key);

    void putObject(@NotBlank final String key, @NotBlank Object fieldVal, Long expireTime);

    void putObject(@NotBlank final String key, @NotBlank Object fieldVal);

    Object getObject(@NotBlank final String key);

    Boolean existsKey(@NotBlank String key);

    Long getExpire(@NotBlank String key);

    void remove(@NotBlank String[] keys);

    Integer removePattern(@NotBlank String pattern);

    Set<String> getKeys(@NotBlank String pattern);

    Long leftPush(@NotBlank final String key, @NotBlank final Object value, Long expireTime);

    Long leftPush(@NotBlank final String key, @NotBlank final Object value);

    Long rightPush(@NotBlank final String key, @NotBlank final Object value, Long expireTime);

    Long rightPush(@NotBlank final String key, @NotBlank final Object value);

    Object leftPop(@NotBlank final String key);

    Object rightPop(@NotBlank final String key);

    Object indexPop(@NotBlank final String key, @NotBlank final Long index);

    Long listSize(@NotBlank final String key);

    Boolean  setNx(final String key, final String value);

    String getSet(final String key, final String value);

}
