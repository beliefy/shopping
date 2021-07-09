package com.cuit.shopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuit.shopping.entity.Admin;
import com.cuit.shopping.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 张圣旭
 */
@Controller
public class StartController {
    @Autowired
    AdminsService adminsService;

    /**
     * 返回起始页面
     * @return
     */
    @GetMapping("/start")
    public String start(){ return "start";}

    /**
     * 医生的登录页面
     * @return
     */
    @GetMapping("/doctorLogin")
    public String toDoctorLogin(){return "doctor_login";}

    /**
     * 管理员的登录页面
     * @return
     */
    @GetMapping("/adminLogin")
    public String toAdminLogin(){return "admin_login";}

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @param map
     * @param session
     * @return 账号密码正确返回管理员界面，否则返回管理员登陆界面
     */
    @PostMapping(value = "/admin_login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        //System.out.println(username);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminsService.getOne(queryWrapper);
        if (admin == null) {
            map.put("msg", "该账号不存在");
            return "admin_login";
        } else if (admin.getPassword().equals(password)) {

            session.setAttribute("admin", admin);
            return "redirect:/admin/index";
        }
        map.put("msg", "用户名密码错误");
        return "admin_login";
    }
}
