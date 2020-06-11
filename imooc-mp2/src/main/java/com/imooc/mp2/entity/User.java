package com.imooc.mp2.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/25 22:55
 * @version: v1.0
 */
@Data
public class User {

    private Long id;
    private String name;

    @TableField(fill = FieldFill.UPDATE)
    private Integer age;

    private String email;
    private Long managerId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Version
    private Integer version;

    /**
     * TableLogic 表示当前字段是删除标志
     * TableField(select=false) 表示查询的时候忽略当前字段
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
