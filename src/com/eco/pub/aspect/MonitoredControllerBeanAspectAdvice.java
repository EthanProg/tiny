package com.eco.pub.aspect;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.eco.pub.model.JsonBean;

/**
 * 通用Controller层切面方法
 * 1.接口采用统一参数形式:
 * mparam:{
 *      dataMap:{},
 *      dataList:[]
 * }
 * 2.监控接口调用
 * Created by Ethan on 2016/1/21.
 */
@Component
@Aspect
public class MonitoredControllerBeanAspectAdvice {

    private static Log log = LogFactory.getLog(MonitoredControllerBeanAspectAdvice.class);

    /**
     * 解析通用接口参数
     * @param proceedingJoinPoint 切点
     * @return 返回执行
     * @throws Throwable
     */
    @Around(value = "@within(org.springframework.stereotype.Controller)")
    public Object monitorClass(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        HttpServletRequest req = null;

        for (Object o : args) {
            if (o instanceof HttpServletRequest) {
                req = (HttpServletRequest) o;
                break;
            }
        }
        
        if (req == null) {
			return doMonitor(proceedingJoinPoint, "Controller");
		}

        String data = req.getParameter("mparam");
        data = data == null ? "" : data;

        if (!"".equals(data)) {
            try {
                data = URLDecoder.decode(data, "UTF-8");
                Gson gson = new Gson();
                JsonBean jsonBean = gson.fromJson(data, JsonBean.class);
                req.setAttribute("mparam", jsonBean);
            } catch (UnsupportedEncodingException e) {
                log.error("MonitoredControllerBeanAspectAdvice---monitorClass---Exception: ", e);
            }
        }

        return doMonitor(proceedingJoinPoint, "Controller");
    }

    /**
     * 将来用于统计http接口的调用
     * @param proceedingJoinPoint 切点
     * @param label 标识
     * @return 返回执行
     * @throws Throwable
     */
    protected Object doMonitor(ProceedingJoinPoint proceedingJoinPoint, String label) throws Throwable {
        // MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // In case of proxies we split the ugly $$ part away
        // String className = proceedingJoinPoint.getTarget().getClass().getSimpleName().split(Pattern.quote("$$"))[0];
        // String methodName = signature.getMethod().getName();
        /*if (log.isDebugEnabled()){
            log.debug("MonitoredControllerBeanAspectAdvice--signature:" + signature + "className: " + className + ";methodName: " + methodName);
        }*/
        return proceedingJoinPoint.proceed();
    }
}
