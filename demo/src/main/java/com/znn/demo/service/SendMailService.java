package com.znn.demo.service;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SendMailService {
    void sendMail();
    void sendSimpleMail();

    void sendMail(String to, String subject, String context);
}
