package com.imooc.mp.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imooc.mp.dao.UserMapper;
import com.imooc.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
    public void selectList() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }

    @Test
    public void selectByIds() {
//        List<Long> idList = new ArrayList<>();
//        idList.add(1088248166370832385L);
//        idList.add(1087982257332887553L);
        List<Long> idList = Arrays.asList(1087982257332887553L, 1088248166370832385L);
        List<User> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "王天风");
        columnMap.put("age", 26);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }


    /**
     * 名字中包含雨并且年龄小于40
     */
    @Test
    public void selectByWrapper() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     */
    @Test
    public void queryWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").isNotNull("email");
//        queryWrapper.ge("age", 20).le("age", 40);
        queryWrapper.between("age", 20, 40);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
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
