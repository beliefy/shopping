package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.service.MedicineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cuit.shopping.entity.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 五组
 * 订单的controller
 */
@Controller
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    MedicineService medicineService;

    /**
     * 添加订单
     * @param medicine 订单信息
     * @return admin的订单列表
     */
    @PostMapping("/add")
    @ResponseBody
    String addMedicine(Medicine medicine){
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",medicine.getName());
        Medicine medicine1 = medicineService.getOne(queryWrapper);
        if (medicine1!=null){
            return  "1";
        }
        else{
            medicineService.save(medicine);
            return "redirect:/admin/medicine";
        }
    }

    /**
     * 查找订单信息，为修改订单信息做准备
     * @param id 订单ID
     * @return 查到的数据，以JSON字符串返回
     * @throws JsonProcessingException
     */
    @PostMapping("/find")
    @ResponseBody
    String findOneMedicine(int id) throws JsonProcessingException {
        Medicine medicine = medicineService.getById(id);
        String data = new ObjectMapper().writeValueAsString(medicine);
        return data;
    }

    /**
     * 修改订单信息
     * @param editMedicine 修改的订单信息
     * @return admin的订单列表
     */
    @PostMapping("/edit")
    @ResponseBody
    String editMedicine(Medicine editMedicine){
        String name = editMedicine.getName();
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Medicine medicine1 = medicineService.getOne(queryWrapper);
        Medicine medicine = medicineService.getById(editMedicine.getId());
        if(!medicine.getName().equals(name)&&medicine1!=null){
            return "1";
        }
        boolean bool = medicineService.updateById(editMedicine);
        if(bool){
            return "redirect:/admin/medicine";
        }
        return null;
    }

    /**
     * 删除订单信息
     * @param id 删除的订单编号
     * @return admin的订单列表
     */
    @GetMapping("/del")
    String delMedicine(int id){
        medicineService.removeById(id);
        return "redirect:/admin/medicine";
    }

    /**
     * 批量删除订单信息
     * @param str 要删除的订单ID以‘，’作为分隔
     * @return admin的订单列表
     */
    @PostMapping("/delMedicines")
    String delMedicine(String str){
        String[] ids = str.split(",");
        for(int i = 0;i<ids.length;i++){
            medicineService.removeById(ids[i]);
        }
        return "redirect:/admin/medicine";
    }
}
