package com.ws.service;

import javax.jws.WebService;

/**
 * 对外发布的接口
 */
@WebService
public interface HelloService {

    String sayHello(String name);
}
