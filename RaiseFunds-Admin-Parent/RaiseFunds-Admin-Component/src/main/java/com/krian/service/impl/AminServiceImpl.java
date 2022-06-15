package com.krian.service.impl;

import com.krian.constant.RaiseFundsConstant;
import com.krian.entity.Admin;
import com.krian.entity.AdminExample;
import com.krian.exception.LoginFailedException;
import com.krian.mapper.AdminMapper;
import com.krian.service.api.AdminService;
import com.krian.util.RaiseFundsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        int flag = adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return  admins;
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录的账户信息查询Admin对象：
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 在Criteria中去封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        // 调用AdminMapper的方法执行查询：
        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        // 2.判断从数据库中查询的Admin对象是否为null：
        if (adminList == null || adminList.size() == 0){
            throw new LoginFailedException(RaiseFundsConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1){
            throw new RuntimeException(RaiseFundsConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        // 3.如果Admin为null抛出异常：
        Admin admin = adminList.get(0);
        if (admin == null){
            throw new LoginFailedException(RaiseFundsConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4.如果Admin对象不为null则将数据库中的密码从Admin对象中取出：
        String userPswdFromDB = admin.getUserPswd();

        // 5.将表单提交的明文密码进行加密
        String userPwsdMd5 = RaiseFundsUtil.md5(userPswd);

        System.out.println(userPswd);
        System.out.println(userPswdFromDB);
        System.out.println(userPwsdMd5);

        // 6.对密码进行比较
        if (!Objects.equals(userPswdFromDB, userPwsdMd5)){
            // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(RaiseFundsConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8.如果比较一致返回Admin对象：
        return admin;
    }
}
