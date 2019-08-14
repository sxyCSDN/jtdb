package com.jt.servive;

import com.jt.pojo.User;

public interface DubboUserService {

	void saveUser(User user);

	String doLogin(User user);
      
}
