package com.example.redisclusterexample;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    // testCache1::#id 를 key로 갖는 json 타입의 string으로 데이터 저장
    @Cacheable(value="testCache1", key = "#id")
    public MyData test1(String id) {
        System.out.println("캐시를 거치지 않은 데이터 입니다");
        return MyData.builder().id(id).name("sun").build();
    }

    @Cacheable(value="testCache2", key = "#id")
    public MyData test2(String id) {
        System.out.println("캐시를 거치지 않은 데이터 입니다");
        return MyData.builder().id(id).name("sun").build();
    }

}
