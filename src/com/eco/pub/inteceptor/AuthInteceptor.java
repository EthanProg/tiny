package com.eco.pub.inteceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eco.pub.model.UcInfo;
import com.eco.pub.model.UcInfoHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.eco.pub.filter.AuthExcludeFlagHolder;
import com.eco.pub.model.Constants;

/**
 * 类描述：认证通用拦截器
 *
 * @author Ethan
 * @date 2016年3月3日
 * <p/>
 * 修改描述：
 * @modifier
 */
public class AuthInteceptor implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp,
                            Object handler, Exception arg3) throws Exception {

    }

    public void postHandle(HttpServletRequest req, HttpServletResponse resp,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    /**
     * 功能：在Action之前执行，本方法中进行了权限过滤。
     *
     * @param req
     * @param resp
     * @param arg2
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws ServletException, IOException, Exception {

        // 在AuthorizationFilter中识别了一些不需要权限认证的url，在此需跳过
        if (AuthExcludeFlagHolder.getExcludeFlag() == true) {
            return true;
        }

        // 获取会话中的登录用户
        Object loginUser = req.getSession().getAttribute(Constants.SESSION_KEY_STLOGINUSER);

        if (loginUser == null) {
            resp.sendRedirect(req.getSession().getServletContext().getContextPath() + "/index.jsp");
            return false;
        } else {
            // 将用户信息放入线程变量中
            UcInfo ucInfo = (UcInfo) loginUser;
            UcInfoHolder.setUcInfo(ucInfo);
            req.setAttribute("userName", ucInfo.getUserName());
        }

        // 判断改用户角色是否拥有该url的访问权限
//        if (true) {
//            return true;
//        } else {
//            // 这个自定义错误会在MVC的错误控制里被捕获并处理
//            throw new Exception("没有权限访问这个页面");
//        }
        return true;
    }
}
