package com.eco;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：
 *
 * @author Ethan
 * @date 2016/6/2
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(HttpServletRequest req){
        return "test";
    }
}
