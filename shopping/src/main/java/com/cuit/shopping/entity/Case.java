package com.cuit.shopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 高泽
 */
@Data
@TableName("out_case")
public class Case {
    @TableId(type = IdType.AUTO)
    private int id;
    private LocalDate date;
    private String patientName;
    private int doctorId;
    /**
     * 病因
    */
    private String ill;
    /**
     * 备注
     */
    private String remark;
    @TableLogic
    private int del;
}
