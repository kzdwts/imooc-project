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
 * @Description: mp查询
 * @author: kangyong
 * @date: 2020/5/13 12:47
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user = userMapper.selectById(123L);
        System.out.println(user);
    }

    /**
     * 名字中包含雨并且年龄小于40
     */
    @Test
    public void queryWrapper() {

    }

    /**
     * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     */
    @Test
    public void queryWrapper2() {

    }

    /**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     */
    @Test
    public void queryWrapper3() {

    }

    @Test
    public void queryWrapper4() {

    }


    @Test
    public void queryWrapper5() {

    }


    @Test
    public void queryWrapper6() {

    }


    @Test
    public void queryWrapper7() {

    }


    @Test
    public void queryWrapper8() {

    }


    @Test
    public void queryWrapper9() {

    }


}
