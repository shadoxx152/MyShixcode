package com.shadoxx.Server;

import com.shadoxx.Dao.UserDao;
import com.shadoxx.QQCommon.User;

public class UserVerifyServer {
    UserDao userDao = new UserDao();

    public User verify(String uid, String password) {
        String sql = "select * from user where uid = ? and password = md5(?)";
        return userDao.querySingle(sql, User.class, uid, password);
    }
}
