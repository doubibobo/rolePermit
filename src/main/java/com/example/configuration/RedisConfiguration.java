package com.example.configuration;

public class RedisConfiguration {
    public static final String REDIS_ADDRESS = "39.108.154.79";

    public static final Integer REDIS_PORT = 6379;

    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个Redis实例，则此时pool的状态为exhausted(耗尽)
    public static final Integer REDIS_MAX_TOTAL = 1024;

    //控制一个pool最多有多少个状态为idle(空闲)的Redis实例，默认值是8
    public static final Integer REDIS_MAX_IDLE = 200;

    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出RedisConnectionException
    public static final Integer REDIS_MAX_WAIT_MILLIS = 10000;

    public static final Integer REDIS_TIMEOUT = 10000;

    public static final Integer REDIS_SECONDS = 3600;

    //在borrow(用)一个Redis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的Redis实例均是可用的
    public static final Boolean REDIS_TEST_ON_BORROW = true;
}
