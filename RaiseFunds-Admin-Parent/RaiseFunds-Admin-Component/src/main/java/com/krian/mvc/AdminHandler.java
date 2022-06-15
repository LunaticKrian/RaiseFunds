package com.krian.mvc;

import com.krian.constant.RaiseFundsConstant;
import com.krian.entity.Admin;
import com.krian.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        // 调用Service方法执行登录数据校验：
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的Admin对象保存到Session域中：
        session.setAttribute(RaiseFundsConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        // 执行重定向操作：
        return "redirect:/admin/to/main/page.html";
    }
}
