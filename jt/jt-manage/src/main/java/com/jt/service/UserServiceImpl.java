package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	 @Autowired
     private UserMapper usermapper;
	

	@Override
	public List<User> findAll() {

		//select * from user 
				//return usermapper.selectList(null);
				return usermapper.findAll();
	}



}
