package com.ws.service.impl;

import com.ws.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@WebService(serviceName = "MessageService",
        targetNamespace = "http://service.ws.com", // 接口命名空间
        endpointInterface = "com.ws.service.IMessageService") // 接口的名称
@Service
public class MessageServiceImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "echo:" + msg;
    }
}
