package com.ws.service.impl;

import com.ws.service.HelloService;

import javax.jws.WebService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }
}
