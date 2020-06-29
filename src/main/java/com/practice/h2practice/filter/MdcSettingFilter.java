package com.practice.h2practice.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class MdcSettingFilter implements Filter {

    /**
     * MDC를 활용하여 request id와 session-id를 등록하고 이를 로그에 남기기 위하여 작성.
     * MDC.put의 key로 해당 value를 사용할 수 있다.
     * 비동기로 쌓이는 로그파일에서 요청 쓰레드를 편하게 추적하기 위함.
     * finally로 clear해야만 thread pool에서 같은 thread가 다시 사용될 때 오동작을 막을 수 있다.
     * logback.xml에서 %X{key}로 사용하면 된다.
     * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try{
            MDC.put("request-id", request.getRequestURI());
            MDC.put("session-id", request.getSession().getId());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}
