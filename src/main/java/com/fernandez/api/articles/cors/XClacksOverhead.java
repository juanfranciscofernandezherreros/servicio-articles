package com.fernandez.api.articles.cors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class XClacksOverhead implements Filter {

    @Override
    public void doFilter ( final ServletRequest req , final ServletResponse res ,
                           final FilterChain chain ) throws IOException, ServletException {

        final HttpServletResponse response = ( HttpServletResponse ) res;
        response.setHeader ( "Access-Control-Allow-Origin" , "*" );
        response.setHeader ( "Access-Control-Expose-Headers" , "Content-Disposition" );
        response.setHeader ( "Access-Control-Allow-Methods" , "GET,POST,PATCH,DELETE,PUT,OPTIONS" );
        response.setHeader ( "Access-Control-Allow-Headers" , "*" );
        response.setHeader ( "Access-Control-Max-Age" , "86400" );
        chain.doFilter ( req , res );
    }

    @Override
    public void destroy ( ) {
    }

    @Override
    public void init ( final FilterConfig arg0 ) {
    }

}
