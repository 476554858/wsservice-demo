package com.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "MessageService", targetNamespace = "http://service.ws.com") // 与包名称相反
public interface IMessageService {

    @WebMethod // 进行WebService方法标注
    public String echo(@WebParam String msg);
}
