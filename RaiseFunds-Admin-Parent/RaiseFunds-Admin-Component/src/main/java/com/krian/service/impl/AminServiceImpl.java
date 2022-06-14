package com.krian.service.impl;

import com.krian.entity.Admin;
import com.krian.entity.AdminExample;
import com.krian.mapper.AdminMapper;
import com.krian.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
