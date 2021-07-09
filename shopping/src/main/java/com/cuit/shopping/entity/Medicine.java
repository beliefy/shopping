package com.cuit.shopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 汪凯
 */
@Data
@TableName("out_medicine")
public class Medicine {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private double price;
    private int num;
    @TableLogic
    private int del;
}
