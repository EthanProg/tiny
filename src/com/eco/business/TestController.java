package com.eco.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    private TestMapper testMapper;

    @RequestMapping("/test1")
    public String test1(HttpServletRequest req){
        return "test";
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Map test2(HttpServletRequest req){
        log.debug("just for test");
        Map resMap = new HashMap();
        resMap.put("key","1");
        resMap.put("users",testMapper.getUsers(new HashMap()));
        return resMap;
    }
}
