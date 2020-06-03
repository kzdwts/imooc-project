package com.imooc.mp1.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.imooc.mp1.dao.UserMapper;
import com.imooc.mp1.entity.User;
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
        queryWrapper.gt(User::getAge, 20);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄
     * 并且只取年龄总和小于500的组
     * select avg(age), min(age), max(age) from user
     * group by manager_id
     * having sum(age) < 500
     */
    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("AVG(age) avg_age", "MAX(age) max_age", "MIN(age) min_age");
        queryWrapper.groupBy("manager_id").having("SUM(age) < {0}", 500);
        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name").like("name", "雨");
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperCount() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.like(User::getName, "雨");
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void selectByWrapperOne() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.like(User::getName, "雨");
        queryWrapper.last("LIMIT 1");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void seleteLambda() {
        // 三种创建方式
        LambdaQueryWrapper<User> queryWrapper1 = new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> queryWrapper3 = Wrappers.<User>lambdaQuery();

        queryWrapper2.like(User::getName, "雨");
        List<User> userList = userMapper.selectList(queryWrapper2);
        userList.forEach(System.out::print);
    }

    /**
     * 姓名为王姓，并且（年龄小于40或邮箱不为空）
     */
    @Test
    public void seleteLambda2() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.likeRight(User::getName, "王");
        queryWrapper.and(i -> i.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 王姓
     */
    @Test
    public void selectLambda3() {
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper).likeRight(User::getName, "王").list();
        userList.forEach(System.out::println);
    }

    /**
     * 分页查询1
     */
    @Test
    public void selectPage() {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        // current当前页，size每页数量
        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        // 总记录数
        long total = iPage.getTotal();
        long pages = iPage.getPages();
        System.out.println("总记录数：" + total);
        System.out.println("总页数：" + pages);

        // 数据
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 分页查询2
     * 查询年龄小于40的人
     */
    @Test
    public void selectPage2() {
        Page<User> page = new Page<>(1, 3);
        IPage<User> iPage = userMapper.selectPage(page, Wrappers.<User>lambdaQuery().lt(User::getAge, 40));
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("当前页：" + iPage.getCurrent());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 分页查询3(是否查询总页数)
     * 年龄小于40
     */
    @Test
    public void selectPage3() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery();
        queryWrapper.lt(User::getAge, 40);

        /**
         * @param current 当前页
         * @param size 每页多少条数据
         * @param isSearchCount 是否查询总页数
         */
        Page<User> page = new Page<>(1, 2, true);
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总页数：" + iPage.getTotal());
        System.out.println("中记录数："+ iPage.getCurrent());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

}
