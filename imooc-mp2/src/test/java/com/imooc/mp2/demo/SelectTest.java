package com.imooc.mp2.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.mp2.dao.UserMapper;
import com.imooc.mp2.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/6/11 13:09
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserList() {
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        userList.forEach(System.out::println);
    }
}
