package com.ws.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

public class ClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private String username;
    private String password;

    public ClientLoginInterceptor(String username, String password) {
        super(Phase.PREPARE_SEND);
        this.username = username;
        this.password = password;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        // 获取全部请求头信息
        List<Header> headers = message.getHeaders();
        // 创建文档
        Document doc = DOMUtils.createDocument();
        // 认证数据信息
        Element auth = doc.createElement("authority");
        Element username = doc.createElement("username");
        Element password = doc.createElement("password");
        username.setTextContent(this.username);
        password.setTextContent(this.password);
        auth.appendChild(username);
        auth.appendChild(password);
        headers.add(0, new Header(new QName("authority"), auth));
    }
}
