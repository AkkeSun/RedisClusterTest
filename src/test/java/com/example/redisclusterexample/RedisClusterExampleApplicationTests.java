package com.example.redisclusterexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedisClusterExampleApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private CacheService cacheService;

	@Test
	void basicTest() {
		// given
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String key = "999";

		// when (set helloString data1)
		valueOperations.set(key, "data1");

		// then (get helloString)
		String value = valueOperations.get(key);
		assertThat(value).isEqualTo("data1");

	}

	@Test
	void defaultCacheTest() throws InterruptedException {

		System.out.println("1차 시도");
		MyData data1 = cacheService.test1("hello");

		Thread.sleep(5000);
		System.out.println("2차 시도");
		MyData data2 = cacheService.test1("hello");

		assertThat(data1.getId()).isEqualTo(data2.getId());
	}

	@Test
	void customCacheTest() throws InterruptedException {

		System.out.println("1차 시도");
		MyData data1 = cacheService.test2("hello");

		Thread.sleep(5000);
		System.out.println("2차 시도");
		MyData data2 = cacheService.test2("hello");

		assertThat(data1.getId()).isEqualTo(data2.getId());
	}

	@Test
	void getCacheDataTest() {
		MyData data1 = cacheService.test1("hello");
		System.out.println(data1.toString());
	}

}
