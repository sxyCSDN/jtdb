package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
        @Autowired
        private UserMapper userMapper;

		@Override
		public List<User> findAll() {
			// TODO Auto-generated method stub
			return userMapper.selectList(null);
		}

/**
 * String cloumn="";
			  switch(type) {
			  case 1: 
				  cloumn="username";
				  break;
			  case 2:
				  cloumn="phone";
			  case 3:
				  cloumn="email";
			  }
			return false;
 * 返回值 true 已存在
 *      false 不存在
 *      
 * sql语句：
 *  select * from tb_user where username="xxxx";
 *  select * from tb_user where phone ="xxxx";
 *  select * from tb_user where email ="xxxx";
 * 
 * 		
 */
		
		
		@Override
		public boolean checkUser(String param, Integer type) {
			String column = (type==1)?"username":((type==2)?"phone":"email");
			QueryWrapper<User> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq(column, param);
			int count = userMapper.selectCount(queryWrapper);
			return count==0?false:true;
		}
	}

