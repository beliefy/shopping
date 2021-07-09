package com.cuit.shopping.controller;

import com.cuit.shopping.service.PatientService;
import com.cuit.shopping.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author 高泽
 */
@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    /**
     * 添加患者
     * @param name 添加患者的姓名
     * @param sex 添加患者的性别
     * @param birthday 添加患者的生日
     * @param tel 添加患者的电话
     * @return
     */
    @PostMapping("/add")
    public String addPatient(String name,
                             String sex,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                             String tel){
        Patient patient = new Patient();
        patient.setBirthday(birthday);
        patient.setName(name);
        patient.setPhone(tel);
        patient.setSex(sex);
        patientService.save(patient);
        return "redirect:/doctor/patient";
    }

    /**
     * 修改患者
     * @param id 修改患者的ID
     * @param name 修改的名字
     * @param sex 修改的性别
     * @param birthday 修改的生日
     * @param tel 修改的电话
     * @return 医生的患者表
     */
    @PostMapping("/edit")
    public String editPatient(int id,String name,
                             String sex,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                             String tel){
        Patient patient = new Patient();
        patient.setId(id);
        patient.setBirthday(birthday);
        patient.setName(name);
        patient.setPhone(tel);
        patient.setSex(sex);
        patientService.updateById(patient);
        return "redirect:/doctor/patient";
    }

    /**
     * 查询患者
     * @param id 查询患者的ID
     * @return 查询到的患者信息
     */
    @PostMapping("/find")
    @ResponseBody
    private Patient findOne(int id){
        Patient patient = patientService.getById(id);
        return patient;
    }

    /**
     * 删除患者
     * @param id 删除患者的ID
     * @return 医生的删除患者页
     */
    @GetMapping("/del")
    private String delPatient(int id){
        patientService.removeById(id);
        return "redirect:/doctor/patient";
    }

    /**
     * 批量删除患者
     * @param str 删除患者的ID字符串，用“，”分隔。
     * @return
     */
    @PostMapping("/delPatients")
    @ResponseBody
    private String delPatients(String str){
        String[] ids = str.split(",");
        for(int i = 0;i<ids.length;i++)
            patientService.removeById(ids[i]);
        return "1";
    }
}
