package com.ws.boot;

import com.ws.interceptor.ClientLoginInterceptor;
import com.ws.service.IMessageService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class CXFClientProxy {

    public static void main(String[] args) {
        // WebService服务地址
        String address = "http://localhost:8000/ws/MessageService?wsdl";
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(address);
        // 映射接口
        factoryBean.setServiceClass(IMessageService.class);
        // 设置认证数据
        factoryBean.getOutInterceptors().add(new ClientLoginInterceptor("zjx", "123456"));
        // 远程接口映射
        IMessageService service = (IMessageService) factoryBean.create();
        String message = "小张";
        String res = service.echo(message);
        System.out.println(res);
    }
}
