package com.service.impl;

import com.common.Result;
import com.dao.UserDao;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Override
    public Result<List<User>> queryUserList() {
        List<User> userList=userDao.getUserList();
        Result<List<User>> result=new Result<List<User>>();
        if(userList.size()>0){
            result.data(userList);
            result.status(0);
        }else{
            result.status(1);
        }
        return result;
    }
}
