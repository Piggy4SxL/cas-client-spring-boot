package com.oc.client.config;

import com.google.common.collect.Maps;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;
import java.util.Map;

/**
 * 配置 CAS Client，对应 servlet 的 web.xml
 *
 * @author SxL
 * @since 1.0.0
 * Created on 1/17/2019 5:14 PM.
 */
@Configuration
public class CasClientConfiguration {

    @Value("${cas.server.url-prefix}")
    private String casServerUrlPrefix;

    @Value("${cas.client.url}")
    private String serverName;

    /**
     * 登录过滤器
     */
    @Bean
    public FilterRegistrationBean filterSingleRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SingleSignOutFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String,String> initParameters = Maps.newHashMap();
        initParameters.put("casServerUrlPrefix", casServerUrlPrefix);
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * 校验过滤器
     */
    @Bean
    public FilterRegistrationBean filterValidationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // CAS 协议
        registration.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String,String>  initParameters = Maps.newHashMap();
        initParameters.put("casServerUrlPrefix", casServerUrlPrefix);
        initParameters.put("serverName", serverName);
        initParameters.put("useSession", "true");
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * 验证过滤器
     */
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter());
        //设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String,String>  initParameters = Maps.newHashMap();
        initParameters.put("casServerLoginUrl", casServerUrlPrefix);
        initParameters.put("serverName", serverName);
        initParameters.put("ignorePattern", ".*");
        initParameters.put("ignoreUrlPatternType", "com.oc.client.config.SimpleUrlPatternMatcherStrategy");

        registration.setInitParameters(initParameters);
        registration.setOrder(1);
        return registration;
    }

    /**
     * Wrapper 过滤器
     */
    @Bean
    public FilterRegistrationBean filterWrapperRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * 登出监听器
     */
    @Bean
    public ServletListenerRegistrationBean<EventListener> singleSignOutListenerRegistration(){
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new SingleSignOutHttpSessionListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
