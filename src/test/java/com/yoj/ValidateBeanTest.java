package com.yoj;

import com.yoj.web.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateBeanTest {

    public void valid(@Valid User user, BindingResult bindingResult){
        System.out.println(bindingResult);
    }

    @Test
    public  void test() {
//        User user = new User();
    }
}
