package com.xiaoxinblog.service;

import com.xiaoxinblog.po.User;

public interface UserService {

	User checkUser(String username, String password);
}
