package com.yoj.web.service;

import com.yoj.web.bean.Privilege;
import com.yoj.web.dao.PrivilegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeMapper privilegeMapper;

    public List<Privilege> queryByUserId(Integer userId){
        return privilegeMapper.queryByUserId(userId);
    }
}
