package com.yoj.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration 模拟整个生产环境，测试更真实、彻底，但是速度较慢
public class ContestControllerTest {
    @Autowired
    private WebApplicationContext wac; // 注入WebApplicationContext

    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。


    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getContestRank() {

    }

    @Test
    public void register() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "abc");
//        map.put("userName", ""); // string length == 0
//        map.put("userName", "不是正则规则所指的\\w+");
        map.put("emailCode", "测试");
        map.put("email", "1162314270@qq.com"); // checked
//        map.put("email", "not email string");
        map.put("imageCode", 50);
        map.put("password","123456");
//        map.put("password","1"); //
        String jsonString = JSONObject.toJSONString(map);
        System.out.println(jsonString);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/r/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
//                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}