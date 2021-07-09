package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.service.CaseService;
import com.cuit.shopping.service.PatientService;
import com.cuit.shopping.entity.Case;
import com.cuit.shopping.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author 五组
 */
@Controller
@RequestMapping("/case")
public class CaseController {
    @Autowired
    CaseService caseService;
    @Autowired
    PatientService patientService;

    /**
     * 添加病历信息
     * @param patient_name 患者姓名
     * @param ill 病因
     * @param doctor_id 添加医生ID
     * @param date 添加日期
     * @param remark 备注
     * @return “1” 说明患者不存在
     */
    @PostMapping("/add")
    @ResponseBody
    String addCase(String patient_name,
                   String ill,
                   int doctor_id,
                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                   String remark){
        Case case1 = new Case();
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",patient_name);
        Patient patient = patientService.getOne(queryWrapper,false);
        if(patient==null){
            return "1";
        }
        case1.setDate(date);
        case1.setDoctorId(doctor_id);
        case1.setIll(ill);
        case1.setRemark(remark);
        case1.setPatientName(patient_name);
        caseService.save(case1);
        return null;
    }

    /**
     * 删除病历
     * @param id 病历ID
     * @return 医生的病历列表
     */
    @GetMapping("/del")
    String delCase(int id){
        caseService.removeById(id);
        return "redirect:/doctor/case";
    }

    /**
     * 查找病历，为接下来修改病历做准备
     * @param id 查找病历的ID
     * @return 查到的病历信息
     */
    @PostMapping("/find")
    @ResponseBody
    Case findOne(int id){
        Case case1 = caseService.getById(id);
        return case1;
    }

    /**
     * 修改病历信息
     * @param name 修改姓名
     * @param ill 修改的病因
     * @param doctorId 修改病历的医生ID
     * @param id 修改病历的ID
     * @param date 修改的时间
     * @param remark 修改的备注
     * @return 返回医生的病历表
     */
    @PostMapping("/edit")
    public String editPatient(String name,
                              String ill,
                              int doctorId,
                              int id,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                              String remark){
        Case case1 = new Case();
        case1.setPatientName(name);
        case1.setIll(ill);
        case1.setDoctorId(doctorId);
        case1.setRemark(remark);
        case1.setDate(date);
        case1.setId(id);
        caseService.updateById(case1);
        return "redirect:/doctor/case";
    }

    /**
     * 批量删除病历
     * @param str 删除病历的ID串，通过“，”分隔
     * @return
     */
    @PostMapping("/delCases")
    @ResponseBody
    String delCases(String str){
        String[] ids = str.split(",");
        for (int i = 0;i<ids.length;i++)
            caseService.removeById(ids[i]);
        return "1";
    }
}
