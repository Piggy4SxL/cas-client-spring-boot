package com.oc.client.config;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

/**
 * 拦截的 URL
 *
 * @author SxL
 * @since 1.0.0
 * Created on 1/17/2019 5:22 PM.
 */
public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
    @Override
    public boolean matches(String url) {
        return url.contains("logout");
    }

    @Override
    public void setPattern(String s) {

    }
}
