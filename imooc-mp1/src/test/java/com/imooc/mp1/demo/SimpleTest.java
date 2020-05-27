package com.imooc.mp1.demo;

import com.imooc.mp1.dao.UserMapper;
import com.imooc.mp1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 测试数据库连接是否成功
 * @author: kangyong
 * @date: 2020/5/12 12:55
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectDemo01() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
