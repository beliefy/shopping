package com.cuit.shopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 张圣旭
 */
@Data
@TableName("out_patient")
public class Patient {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    private String sex;
    private String phone;
    @TableLogic
    private int del;
}
