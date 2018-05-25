package com.inspur.tax;

import com.inspur.tax.common.redis.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private RedisTemplate<Object, Object> template;

	@Test
	public void contextLoads() {
		User user = new User(1,"象拔蚌");
		template.opsForValue().set(user.getId()+"",user);
		//原本opsForValue()是只能操作字符串的.现在就可以操作对象了
		User result = (User) template.opsForValue().get(user.getId()+"");
		System.out.println(result.toString());
	}

	@Test
	public void test1(){
		template.opsForValue().set("key","hello");
		template.opsForValue().set("key","666");
		System.out.println(template.opsForValue().get("key"));
	}

	@Test
	public void test2(){
		/*Map<String, String> maps = new HashMap();
		maps.put("muti1", "hello");
		maps.put("muti2", "world");
		maps.put("muti3", "!");
		template.opsForValue().multiSet(maps);*/
		template.delete("string_array");
		String[] string_array = new String[]{"1","2","3"};
		template.opsForList().rightPushAll("string_array",string_array);
		System.out.println(template.opsForList().range("string_array",0,-1));
	}

	@Test
	public void test3(){
		Map<String,String> maps = new HashMap<String, String>();
		maps.put("multi1","multi11");
		maps.put("multi22","multi22");
		maps.put("multi33","multi33");
		Map<String,String> maps2 = new HashMap<String, String>();
		maps2.put("multi1","multi1");
		maps2.put("multi2","multi2");
		maps2.put("multi3","multi3");
		System.out.println(template.opsForValue().multiSetIfAbsent(maps));
		System.out.println(template.opsForValue().multiSetIfAbsent(maps2));
	}
}
