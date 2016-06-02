package com.eco.pub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.eco.pub.aspect.annotation.LogAround;
import org.apache.log4j.Logger;

import com.eco.pub.filter.AuthExcludeFlagHolder;


/**
 * 权限认证过滤器
 * 此过滤器用于给url进行标记，以区分哪些url需要进行权限认证拦截，哪些不需要
 *
 * @author sunzhen
 */
public class AuthorizationFilter implements Filter {

    private Logger log = Logger.getLogger(AuthorizationFilter.class);

    protected FilterConfig filterConfig;


    public AuthorizationFilter() {
        filterConfig = null;
    }

    public void destroy() {
        filterConfig = null;
    }

    @LogAround
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        String requestUrl = httpRequest.getRequestURI();

        String ctx = httpRequest.getSession().getServletContext().getContextPath();
        requestUrl = requestUrl.replace(ctx, "");

        String excludeUrlStr = filterConfig.getInitParameter("excludeUrl");

        String[] requestPathArr = requestUrl.split("\\/");

        // URL不参与权限认证的标记
        boolean exculdeFlag = false;

        if (excludeUrlStr != null) {
            String[] excludeUrlArr = excludeUrlStr.split(";");
            for (int i = 0; i < excludeUrlArr.length; i++) {
                String excludeUrl = excludeUrlArr[i];

                String[] excludePathArr = excludeUrl.split("\\/");
                if (excludePathArr.length > requestPathArr.length) {
                    continue;
                }

                // 看看每一个路径是不是都相符
                Boolean tempFlag = true;
                for (int j = 0; j < excludePathArr.length; j++) {
                    if (!excludePathArr[j].equals(requestPathArr[j])) {
                        tempFlag = false;
                    }
                }

                // 如果存在路径都相符的情况，设置排除标记为true并且跳出循环
                if (tempFlag) {
                    exculdeFlag = true;
                    break;
                }
            }
        }

        if (exculdeFlag) {
            AuthExcludeFlagHolder.setExcludeFlag(true);
        } else {
            AuthExcludeFlagHolder.setExcludeFlag(false);
        }

        chain.doFilter(req, resp);

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

}
