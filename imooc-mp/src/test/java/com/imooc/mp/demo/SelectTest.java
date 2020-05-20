package com.imooc.mp.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("name", "王").or().ge("age", 25)
                .orderByDesc("age").orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日并且直属上级名字王姓
     */
    @Test
    public void queryWrapper4() {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        queryWrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", "2019-02-14");
        queryWrapper.inSql("manager_id", "select id from user where name like CONCAT('王', '%') ");

        List<User> userList = userMapper.selectList(queryWrapper);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     */
    @Test
    public void queryWrapper5() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.likeRight(User::getName, "王");
        queryWrapper.and(i -> i.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList = userMapper.selectList(queryWrapper);
    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     */
    @Test
    public void queryWrapper6() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.likeRight(User::getName, "王");
        queryWrapper.or(i -> i.lt(User::getAge, 40).gt(User::getAge, 20).isNotNull(User::getEmail));
        List<User> userList = userMapper.selectList(queryWrapper);
    }

    /**
     * (年龄小于40或邮箱不为空)并且名字为王姓
     */
    @Test
    public void queryWrapper7() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.nested(i -> i.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        queryWrapper.likeRight(User::getName, "王");
        userMapper.selectList(queryWrapper);
    }

    /**
     * 年龄为30、31、34、35
     */
    @Test
    public void queryWrapper8() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.in(User::getAge, 30, 31, 34, 35);
        userMapper.selectList(queryWrapper);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     */
    @Test
    public void queryWrapper9() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.gt(User::getAge, 26);
        queryWrapper.last("LIMIT 1");

        userMapper.selectOne(queryWrapper);
    }

    /**
     * 名字中包含“雨”并且年龄小于40
     */
    @Test
    public void selectByWrapperSuper() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.like(User::getName, "雨").lt(User::getAge, 40);
        // 查询所有字段中，排除create_time和manager_id
        queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
        userMapper.selectList(queryWrapper);
    }

    /**
     * 非空加条件查询
     */
    @Test
    public void testCondition() {
        String name = "";
        String email = "w";
        condition(name, email);
    }

    /**
     * 条件查询
     *
     * @param name
     * @param email
     */
    private void condition(String name, String email) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();

//        方法一
//        if (ObjectUtils.isNotEmpty(name)) {
//            queryWrapper.likeRight(User::getName, name);
//        }
//        if (ObjectUtils.isNotEmpty(email)) {
//            queryWrapper.like(User::getEmail, email);
//        }

//        方法二
        queryWrapper.likeRight(ObjectUtils.isNotEmpty(name), User::getName, name);
        queryWrapper.like(ObjectUtils.isNotEmpty(email), User::getEmail, email);
        userMapper.selectList(queryWrapper);
    }

    /**
     * 根据实体类非空字段查询
     */
    @Test
    public void selectByWrapperEntity() {
        User user = new User();
        user.setName("王丽丽");
        // 根据实体类查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);

        userMapper.selectList(queryWrapper);
    }

    @Test
    public void selectByWrapperAllEq() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        params.put("age", null);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.allEq(params);
//        queryWrapper.allEq(params, ObjectUtils.isNotEmpty(params));
        queryWrapper.allEq((k, v) -> !k.equals("name") && !k.equals("age"), params);

        userMapper.selectList(queryWrapper);
    }

    /**
     * 返回List<Map<String, Object>>
     */
    @Test
    public void selectByWrapperMaps() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.gt(User::getAge, 26);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }


}
