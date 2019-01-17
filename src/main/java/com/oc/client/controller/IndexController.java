package com.oc.client.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Demo 展示
 *
 * @author SxL
 * @since 1.0.0
 * Created on 1/17/2019 2:12 PM.
 */
@RestController
public class IndexController {

    @Value("${cas.server.url-prefix}")
    private String casServerUrl;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        AttributePrincipal attributePrincipal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> attributes = attributePrincipal.getAttributes();
        return "<h1>您好，" + attributes.get("givenName") + "!</h1>" +
                "<a href='" + casServerUrl + "/logout'> 点此登出 </a>";
    }
}
