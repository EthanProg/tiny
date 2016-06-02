package com.eco.pub.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.eco.pub.model.Constants;
import com.eco.pub.utils.PropertiesUtil;


/**
 * 功能：过滤器 -- 将一些公用的属性，写到request中
 *
 * @author 郑斌    2014年7月22日 上午10:42:07
 *         修改说明：
 */
public class CommonAttributeSetter implements Filter {

    private Logger log = Logger.getLogger(CommonAttributeSetter.class);

    private String homePageUrl = getHomePageUrl();

    protected FilterConfig filterConfig;


    public CommonAttributeSetter() {
        filterConfig = null;
    }

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {

        // 将上下文根写入request，方便在页面上的使用
        String contextPath = ((HttpServletRequest) req).getSession().getServletContext().getContextPath();

        req.setAttribute("ctx", contextPath);

        // 将首页的url写入request中，方便页面组织面包屑导航
        req.setAttribute("homePageUrl", homePageUrl);

        //获取session中存储的属性，放入下次请求的request中，专门为redirect来准备。
        HttpSession session = ((HttpServletRequest) req).getSession();
        Object o = session.getAttribute(Constants.REDIRECT_ATTR_KEY);
        //如果session中存储的Map存在并不为空
        if (o != null && (o instanceof Map) == true && !((Map) o).isEmpty()) {
            Set keySet = ((Map) o).keySet();
            for (Object k : keySet) {
                req.setAttribute((String) k, ((Map) o).get(k));
            }
            //属性放入request后，清除session中的属性
            ((Map) o).clear();
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    /**
     * 获取首页URL
     *
     * @param @return
     * @return String
     * @throws
     * @Title: getHomePageUrl
     * @Description:
     */
    private String getHomePageUrl() {
        String homePageUrl = PropertiesUtil.getString("homePage", "/plantform.properties");
        return homePageUrl;
    }

}
