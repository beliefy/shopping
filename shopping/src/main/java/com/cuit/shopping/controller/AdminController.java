package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.service.AdminsService;
import com.cuit.shopping.service.DoctorService;
import com.cuit.shopping.service.MedicineService;
import com.cuit.shopping.entity.Admin;
import com.cuit.shopping.entity.Doctor;
import com.cuit.shopping.entity.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 五组
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminsService adminsService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    MedicineService medicineService;

    /**
     * 返回管理员主页
     * @return
     */
    @GetMapping("/index")
    public String toIndex(){
        return "admin_index";
    }

    /**
     * 修改密码
     * @param newpsw 新密码
     * @param id 修改用户ID
     * @param session
     * @param map
     * @return 管理员登录页
     */
    @PostMapping("/updatepwd")
    public String updatePwd(@RequestParam("newpsw") String newpsw,
                            @RequestParam("id") String id,
                            HttpSession session, Map<String, Object> map){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Admin admin = adminsService.getOne(queryWrapper);
        admin.setPassword(newpsw);
        adminsService.updateById(admin);
        session.invalidate();
        map.put("msg", "已修改密码，请重新登录");
        return "admin_login";
    }

    /**
     * 医生页面
     * @param model
     * @return 医生列表
     */
    @GetMapping("/doctor")
    public String doctorList(Model model){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        List<Doctor> doctors = doctorService.list(queryWrapper);
        model.addAttribute("doctors",doctors);
        return "doctor_table";
    }

    /**
     * 查询所有药品，返回药品列表
     * @param model
     * @return 回到管理员的药品管理页面
     */
    @GetMapping("/medicine")
    public String medicineList(Model model){
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper();
        List<Medicine> medicines = medicineService.list(queryWrapper);
        model.addAttribute("medicines",medicines);
        return "medicine_table";
    }
}
