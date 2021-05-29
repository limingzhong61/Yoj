package com.yoj.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.model.entity.User;
import com.yoj.model.pojo.util.UserDetailsImpl;
import com.yoj.model.vo.Msg;
import com.yoj.service.UserService;
import com.yoj.utils.auth.UserUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/user")
public class UserAdminController {
    @Autowired
    UserService userService;
    @Autowired
    private UserUtil currentUserUtil;
    /**
     * 获取管理员修改列表
     * @param pageNumber
     * @param user
     * @return
     */
    @GetMapping("/set/{pageNumber}")
    public Msg getUserSet(@PathVariable("pageNumber") Integer pageNumber, User user) {
        PageHelper.startPage(pageNumber, 10);
        List<User> users = userService.getAdminUserList(user);
        PageInfo<User> page = new PageInfo<User>(users, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 获取管理员修改列表
     * @param pageNumber
     * @param user
     * @return
     */
    @DeleteMapping("/{userId}")
    public Msg deleteUser(@PathVariable("userId") Integer userId) {
        UserDetailsImpl userDetail = currentUserUtil.getUserDetail();
        if(userDetail.getUserId() == userId) return Msg.fail();
        if(!userService.deleteByUserId(userId)){
            return Msg.fail();
        }
        return Msg.success();
    }
}
