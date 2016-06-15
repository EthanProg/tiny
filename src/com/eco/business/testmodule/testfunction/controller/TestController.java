package com.eco.business.testmodule.testfunction.controller;

import com.eco.business.testmodule.testfunction.service.ITestService;
import com.github.jscookie.javacookie.Cookies;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 *
 * @author Ethan
 * @date 2016/6/2
 */
@Controller
public class TestController {

    private static Log log = LogFactory.getLog(TestController.class);

    @Autowired
    private ITestService testService;

    @RequestMapping("/login")
    @ResponseBody
    public List test1(HttpServletRequest req, HttpServletResponse rep){
        Cookies cookies = Cookies.initFromServlet( req, rep );
        cookies.set("name", "ethan");
        return testService.getUsers(new HashMap());
    }
}
