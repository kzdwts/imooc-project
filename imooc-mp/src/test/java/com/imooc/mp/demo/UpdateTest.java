package com.imooc.mp.demo;

import com.imooc.mp.dao.UserMapper;
import com.imooc.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/18 12:56
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1262245678839611393L);
        user.setAge(20);
        user.setName("凌家琴雨");
        user.setManagerId(1088248166370832385L);
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }

}
