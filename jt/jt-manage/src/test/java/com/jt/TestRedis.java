package com.jt;




import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.javassist.expr.NewArray;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.User;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;


public class TestRedis {
	
	//1.测试String类型

	@Test
	public void testString() throws InterruptedException {
		String host = "192.168.222.128";
		int port = 6379;
		Jedis jedis = new Jedis(host, port);
		jedis.set("1901","redis单台测试");
		jedis.pexpire("1901", 10000);
		Thread.sleep(2000);
		System.out.println("当前key还能存活"
		+ jedis.ttl("1901")+"秒");
		System.out.println(jedis.get("1901"));
	}
	//2.测试hash，保存对象
	/**
	 * 测试hash，保存对象，但是返回值需要手动转化
	 * setnx作用：
	 *  当进行setnx操作时
	 *    如果redis缓存中没有该数据，则进行set赋值
	 *    如果已经有该数据，则set操作省略
	 *    作用：在高并发下，减少脏数据
	 * @throws InterruptedException
	 */
	@Test
	public void testHash() throws InterruptedException {
		String host = "192.168.222.128";
		int port = 6379;
		Jedis jedis = new Jedis(host, port);
		jedis.hsetnx("student", "id", "101");
		jedis.hsetnx("student", "age", "18");
		jedis.hsetnx("student", "age", "20");
		System.out.println(jedis.hget("student", "id")+"学号");
		Map<String,String> map =jedis.hgetAll("student");
		System.out.println(map);
	
	
	}
	@Test
	public void test2() {
		Jedis jedis =new Jedis("192.168.222.128",6379);
		Long number =jedis.lpush("list", "a","b","c","d","e","f");
		System.out.println("获取数据"+number);
		List<String> list=jedis.lrange("list",0, -1);
		System.out.println("获取参数："+list);
	}
	@Test
	public void testTx() {
		String host="192.168.222.128";
		int port=6379;
		Jedis jedis =new Jedis(host,port);
		Transaction transtion =jedis.multi();
		try {
		transtion.set("qqq", "qqqq");  
		transtion.set("WWW", "wwwwww");
		transtion.exec();//事务提交
		} catch(Exception e){
		e.printStackTrace();
		transtion.discard();//事务回滚
		}
		
	}
	
	
	
	
	
	//1.对象如何与redis进行交互？
	//1.有对象的格式要求2.本身就是String串 JSON
	@SuppressWarnings("unchecked")
	@Test
	public void testObject() throws IOException {
		String host = "192.168.222.128";
		int port = 6379;
		Jedis jedis = new Jedis(host, port);
		//实现对象与json互转objectmapper对象
		ObjectMapper mapper =new ObjectMapper();
		//1.对象转化为json
		
		
		User user =new User();
		user.setId(1).setAge(19).setName("tomcat").setSex("男");
		String userJSON= mapper.writeValueAsString(user);
				System.out.println(userJSON);
		//user----json------redis
		//redis ---json-------user
				//2.json转化为对象
				User user2=mapper.readValue(userJSON, User.class);
				System.out.println(user2);
				//3.将List集合与json互转
				List<User> userList = new ArrayList<User>();
				User user3 = new User();
				user3.setId(1).setAge(19).setName("tomcat猫").setSex("公");
				User user4 = new User();
				user4.setId(2).setAge(20).setName("tomcat猫").setSex("公");
				userList.add(user3);
				userList.add(user4);
				
				String listJSON = mapper.writeValueAsString(userList);
				System.out.println(listJSON);
				
				//4.json串转化为List集合
				List<User> userList2 = 
				mapper.readValue(listJSON,userList.getClass());
				System.out.println(userList2);
			}
	@Test
	//1.对象转化为json串
	public void  myselftest() throws IOException {
		String host ="192.168.222.128";
		int port=6379;
		Jedis jedis =new Jedis(host,port);
		ObjectMapper  mapper =new ObjectMapper();
		User user =new User();
		 user.setName("jidiandeba").setId(12015).setSex("男").setAge(18);
		String userJson=mapper.writeValueAsString(user);
		 System.out.println(userJson);
		
		 //2.将json串转化为对象
		User user2= mapper.readValue(userJson,user.getClass());
		 System.out.println(user2);
		 
		 //3.将List集合转化为json串
		 List<User> list =new ArrayList() ;
		 User user3 =new User();
		 User user4 =new User();
		 user3.setAge(18).setId(22).setName("Sdadw").setSex("nan");
		 user4.setAge(79).setId(1).setName("sds").setSex("boy");
		 list.add(user3);
		 list.add(user4);
		 
		 String JsonList =mapper.writeValueAsString(list);
		 System.out.println(JsonList);
		 //4.将json串转化为List集合
	List<User> list2=	 mapper.readValue(JsonList,list.getClass());
		 System.out.println(list2);
	}
	
	
	//实现redis分片用户使用redis是一个整体
	@Test
	public void testShards() {
		List<JedisShardInfo> shards =new ArrayList();
		JedisShardInfo info1 =new JedisShardInfo("192.168.222.128", 6379);
		JedisShardInfo info2 =new JedisShardInfo("192.168.222.128", 6380);
		JedisShardInfo info3 =new JedisShardInfo("192.168.222.128", 6381);
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		ShardedJedis jedis =new ShardedJedis(shards);
		for(int i=0;i<10;i++) {
			jedis.set(""+i, ""+i);
		}
	}
	@Test
	public void testRedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.222.128",7000));
		nodes.add(new HostAndPort("192.168.222.128",7001));
		nodes.add(new HostAndPort("192.168.222.128",7002));
		nodes.add(new HostAndPort("192.168.222.128",7003));
		nodes.add(new HostAndPort("192.168.222.128",7004));
		nodes.add(new HostAndPort("192.168.222.128",7005));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("1901", "redis集群搭建完成!!!!");
		System.out.println(jedisCluster.get("1901"));
	}

}

