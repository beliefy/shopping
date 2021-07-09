package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.service.CaseService;
import com.cuit.shopping.service.DoctorLoginService;
import com.cuit.shopping.service.DoctorService;
import com.cuit.shopping.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cuit.shopping.entity.Case;
import com.cuit.shopping.entity.Doctor;
import com.cuit.shopping.entity.DoctorLogin;
import com.cuit.shopping.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 五组
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorLoginService doctorLoginService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    PatientService patientService;
    @Autowired
    CaseService caseService;

    /**
     * 返回医生主页
     * @return 医生主页
     */
    @GetMapping("/index")
    public String toIndex(){return "doctor_index";}

    /**
     * 添加医生，同时添加医生登录信息，默认密码“123456”
     * @param doctor 添加的医生信息
     * @return “1” 表示医生工号存在
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public String addDoctor(Doctor doctor){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",doctor.getUsername());
        Doctor doctor1 = doctorService.getOne(queryWrapper);
        if(doctor1!=null){
            return "1";
        }
        doctorService.save(doctor);
        DoctorLogin doctorLogin = new DoctorLogin();
        doctorLogin.setUsername(doctor.getUsername());
        doctorLogin.setPassword("123456");
        doctorLoginService.save(doctorLogin);
        return "redirect:/admin/doctor";
    }

    /**
     * 删除医生
     * @param id 删除医生的ID
     * @return 返回管理员医生页面
     */
    @GetMapping("/del")
    public String delDoctor(int id){
        Doctor doctor = doctorService.getById(id);
        Map<String,Object> map = new HashMap<>(16);
        map.put("username",doctor.getUsername());
        doctorLoginService.removeByMap(map);
        doctorService.removeById(id);
        return "redirect:/admin/doctor";
    }

    /**
     * 查询一个医生，为之后修改医生做准备
     * @param id 查询医生的ID
     * @return 查到的医生信息
     * @throws JsonProcessingException
     */
    @PostMapping("/find")
    @ResponseBody
    public String findOne(int id) throws JsonProcessingException {
        Doctor doctor = doctorService.getById(id);
        String data = new ObjectMapper().writeValueAsString(doctor);
        return data;
    }

    /**
     * 管医院修改医生信息
     * @param editDoctor 修改医生的具体信息
     * @return “1” 表示医生工号存在。
     */
    @PostMapping("/edit")
    @ResponseBody
    public String editDoctor(Doctor editDoctor){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        String username = editDoctor.getUsername();
        queryWrapper.eq("username",username);
        Doctor doctor = doctorService.getById(editDoctor.getId());
        Doctor doctor1 = doctorService.getOne(queryWrapper);
        if(!doctor.getUsername().equals(username)&&doctor1!=null){
            return "1";
        }
        String oldUsername = doctor.getUsername();
        QueryWrapper<DoctorLogin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username",oldUsername);
        DoctorLogin doctorLogin = doctorLoginService.getOne(queryWrapper1);
        System.out.println(doctorLogin);
        doctorLogin.setUsername(username);
        doctorLoginService.updateById(doctorLogin);
        boolean bool = doctorService.updateById(editDoctor);
        if(bool){
            return "redirect:/admin/doctor";
        }
        return  null;
    }

    /**
     * 医生个人修改信息并通过session修改
     * @param doctor 医生信息
     * @param session
     * @return 医生的主页
     */
    @PostMapping("/editMsg")
    public String editMsg(Doctor doctor,HttpSession session){
        doctorService.updateById(doctor);
        session.setAttribute("doctor",doctor);
        return "redirect:/doctor/index";
    }

    /**
     * 批量删除医生
     * @param str 删除的医生编号，用“，”分隔
     * @return admin的医生表
     */
    @PostMapping("/delDoctors")
    @ResponseBody
    public String delDoctors(String str){
        String[] ids = str.split(",");
        for(int i = 0;i<ids.length;i++){
            Doctor doctor = doctorService.getById(ids[i]);
            Map<String,Object> map = new HashMap<>(16);
            map.put("username",doctor.getUsername());
            doctorLoginService.removeByMap(map);
            doctorService.removeById(ids[i]);
        }
        return "redirect:/admin/doctor";
    }

    /**
     * 查询所有患者，并且跳转到医生的患者表
     * @param model
     * @return
     */
    @GetMapping("/patient")
    public String patientList(Model model){
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        List<Patient> patients = patientService.list(queryWrapper);
        model.addAttribute("patients",patients);
        return "patient_table";
    }

    /**
     * 查询所有病历，并且跳转到医生的病历表
     * @param model
     * @return
     */
    @GetMapping("/case")
    public String caseList(Model model){
        QueryWrapper<Case> queryWrapper = new QueryWrapper<>();
        List<Case> cases = caseService.list(queryWrapper);
        model.addAttribute("cases",cases);
        return "case_table";
    }
}
