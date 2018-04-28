package com.springcloud.lab.feedservice.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * jsonp支持
 */
@ControllerAdvice(basePackages = "com.springcloud.lab.feedservice.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{  

    public JsonpAdvice() {
        super("callback","jsonp");  
    }  
}  

