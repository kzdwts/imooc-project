package com.imooc.mp1.demo;

import com.imooc.mp1.dao.UserMapper;
import com.imooc.mp1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/18 12:53
 * @version: v1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setAge(21);
        user.setName("凌家琴");
        user.setEmail("ljq@kangyong.top");
        user.setCreateTime(LocalDateTime.now());
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }
}
