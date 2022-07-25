package com.znn.demo.mail;

import com.znn.demo.service.SendMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 周闹闹
 * @version 1.0
 */
@SpringBootTest
public class SendMailTest {

    @Autowired
    SendMailService sendMailService;

    @Test
    void sendMail(){
        sendMailService.sendMail();
    }
}
