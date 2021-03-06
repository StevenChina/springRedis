package com.akcomejf.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig<DefaultZSetOperations> {
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return container;
	}
//	@Bean  
//    public RedisTemplate redisTemplate(RedisConnectionFactory cf) {  
//        RedisTemplate redisTemplate = new RedisTemplate();  
//        redisTemplate.setConnectionFactory(cf);  
//        return redisTemplate;  
//    }  
	/**
	 * 消息监听适配器
	 * @param receiver 委托对象（指定执行方法）
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
//	@Bean
//	JedisClusterConnection clusterConnection(){
//		HostAndPort ip = new HostAndPort("192.168.82.252", 7001);
//		JedisCluster node = new JedisCluster(ip);
//		JedisClusterConnection cluster = new JedisClusterConnection(node);
//		return cluster;
//	}
	
	@Bean
	JedisConnectionFactory factory(){
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("192.168.82.252");
		factory.setPort(6379);
		return factory;
	}

	@Bean
	StringRedisTemplate stringTemplate(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
	
//	@Bean
//	RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
//		RedisTemplate redisTemplate = new RedisTemplate();
//		redisTemplate.setConnectionFactory(connectionFactory);
//		return redisTemplate;
//	}
	
	
}
