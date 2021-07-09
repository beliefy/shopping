package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.service.DoctorLoginService;
import com.cuit.shopping.service.DoctorService;
import com.cuit.shopping.entity.Doctor;
import com.cuit.shopping.entity.DoctorLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 五组
 */
@Controller
@RequestMapping("/doctorLogin")
public class DoctorLoginController {
    @Autowired
    DoctorLoginService doctorLoginService;
    @Autowired
    DoctorService doctorService;

    /**
     * 医生修改密码
     * @param newPsw 新密码
     * @param id 修改医生的id
     * @param session
     * @param map
     * @return 医生登录页
     */
    @PostMapping(value = "/updatePwd")
    String updatePwd(@RequestParam("newpsw") String newPsw,
                     @RequestParam("id") String id,
                     HttpSession session, Map<String, Object> map){
        DoctorLogin doctorLogin = doctorLoginService.getById(id);
        doctorLogin.setPassword(newPsw);
        doctorLoginService.updateById(doctorLogin);
        session.invalidate();
        map.put("msg", "已修改密码，请重新登录");
        return "doctor_login";
    }

    /**
     * 医生登录
     * @param username 用户名
     * @param password 密码
     * @param map
     * @param session
     * @return 账号密码错误或账号不存在会返回到医生登录页，账号密码正确，跳转到医生主页
     */
    @PostMapping(value = "/doctor_login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session){
        QueryWrapper<DoctorLogin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username", username);
        DoctorLogin doctorLogin = doctorLoginService.getOne(queryWrapper1);
        if(doctorLogin==null){
            map.put("msg", "该账号不存在");
            return "doctor_login";
        }
        else if(doctorLogin.getPassword().equals(password)){
            QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            Doctor doctor = doctorService.getOne(queryWrapper);
            session.setAttribute("doctor", doctor);
            session.setAttribute("user",doctorLogin);
            return "redirect:/doctor/index";
        }
        map.put("msg", "用户名密码错误");
        return "doctor_login";
    }

    /**
     * 退出登录，清空session
     * @param session
     * @return 返回初始页面
     */
    @GetMapping("/logout")
    public String toLogout(HttpSession session){
        session.invalidate();
        return "redirect:/start";
    }
}
