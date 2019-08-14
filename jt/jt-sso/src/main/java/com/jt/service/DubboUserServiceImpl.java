package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.servive.DubboUserService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService {
      
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	
	/**
	 * 入库操作时步骤如下
	 * 	1.密码应该加密 MD5 MD5HASH
	 * 	2.页面中暂时没有传递email 暂时使用电话代替
	 *  3.添加用户操作的时间
	 *  4.需要控制事务
	 */

	@Transactional
	@Override
	public void saveUser(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);

		    
	}




	@Override
	public String doLogin(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		String token = null;
		if(userDB != null) {
			token = "JT_TICKET_"+System.currentTimeMillis()+userDB.getUsername();
			token = DigestUtils.md5DigestAsHex(token.getBytes());
			//数据比较敏感时需要做脱敏处理
			userDB.setPassword("123456");
			String userJSON = ObjectMapperUtil.toJSON(userDB);
			jedisCluster.setex(token,7*24*3600,userJSON);
		}
		return token;
	}

}

	

