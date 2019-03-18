package com.service;

import com.common.Result;
import com.entity.User;

import java.util.List;

public interface UserService {

    public Result<List<User>> queryUserList();
}
