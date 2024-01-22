package com.ws.config;

import com.ws.interceptor.WebServiceAuthInterceptor;
import com.ws.service.IMessageService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfig {

    //    @Autowired
//    private Bus bus; //注入Bus接口实例
    @Autowired
    private IMessageService messageService; // 注入业务实力
    @Autowired
    private WebServiceAuthInterceptor interceptor; // 注入拦截实例

    @Bean
    public ServletRegistrationBean getRegistrationBean() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean
    public Endpoint messageEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(getBus(), messageService);
        endpoint.publish("/MessageService");
        endpoint.getInInterceptors().add(this.interceptor); // 访问拦截器
        return endpoint;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public Bus getBus() {
        return new SpringBus();
    }

}
