package com.ws.interceptor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

@Component
@Slf4j
public class WebServiceAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    // 用户名
    private static final String USER_NAME = "zjx";
    // 密码
    private static final String PASSWORD = "123456";

    // 创建拦截器
    private SAAJInInterceptor saa = new SAAJInInterceptor();

    public WebServiceAuthInterceptor() {
        super(Phase.PRE_PROTOCOL);
        // 添加拦截器
        super.getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        // 获取指定消息
        SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
        // 没有消息内容
        if (soapMessage == null) {
            // 直接走默认处理
            this.saa.handleMessage(message);
            // 尝试获取消息
            soapMessage = message.getContent(SOAPMessage.class);
        }
        // SOAP头信息
        SOAPHeader header = null;
        try {
            header = soapMessage.getSOAPHeader();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        // 没有头信息
        if (header == null) {
            throw new Fault(new IllegalAccessException("找不到Header信息，无法实现用户认证处理"));
        }
        // SOAP是给予XML文件结构进行传输的，所以如果要想获取认证信息就必须进行相关的结构约定
        NodeList usernameList = header.getElementsByTagName("username"); // 获取指定标签集合
        NodeList passwordList = header.getElementsByTagName("password"); // 获取DOM数据
        if (usernameList.getLength() == 0 || passwordList.getLength() == 0) {
            throw new Fault(new IllegalAccessException("找不到Header信息，无法实现用户认证处理"));
        }

        String username = usernameList.item(0).getTextContent().trim();
        String password = passwordList.item(0).getTextContent().trim();
        if (USER_NAME.equals(username) && PASSWORD.equals(password)) {
            log.info("用户访问认证成功！");
        } else {
            SOAPException soapException = new SOAPException("用户认证失败");
            log.info("用户认证失败");
            throw new Fault(soapException);
        }
    }
}
