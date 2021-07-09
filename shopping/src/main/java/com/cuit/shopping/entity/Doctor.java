package com.cuit.shopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 孟庆阔
 */
@Data
@TableName("out_doctor")
public class Doctor {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
    * 工号
    */
    private String username;
    private String name;
    private String department;
    private String sex;
    @TableLogic
    private int del;
}
