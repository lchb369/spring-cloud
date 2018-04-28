package com.springcloud.lab.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RequestLogFilter extends ZuulFilter{

    private static Logger logger= LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public String filterType(){
        return "pre";
    }

    @Override
    public int filterOrder(){
        return 1;
    }

    @Override
    public boolean shouldFilter(){
        return true;
    }


    @Override
    public Object run(){
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();

        String log=String.format("Send %s request to %s ",request.getMethod(),request.getRequestURL().toString());
        RequestLogFilter.logger.info(log);

        return null;
    }


}
