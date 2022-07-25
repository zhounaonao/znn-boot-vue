package com.znn.demo.service.impl;

import com.znn.demo.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // 发送人
    private String from = "13028902352@163.com";
    // 接收人
    private String to = "1737381081@qq.com";
    // 标题
    private String subject = "测试邮件";
    // 正文
    private String context = "<a href='https://www.baidu.com'>点开有惊喜</a>" +
            "<img src='https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F0e2442a7d933c895d14379402c4264f082025aafaaf4&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659618477&t=0db88a89dab79a09b3e9c9ef58f1d317'/>";

    private File f1 = new File("D:\\素材存放\\图片\\《原神》壁纸系列\\同人图相关壁纸\\端午节贺图 2560x1440.jpg");
    private File f2 = new File("D:\\Project\\SpringBoot+Vue\\znn-boot-vue\\demo\\target\\demo-1.0-SNAPSHOT.jar");

    @Override
    public void sendSimpleMail() {
        // 简单邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(小甜甜)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
    }

    @Override
    public void sendMail() {
        // 复杂邮件
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // 发附件要加上 true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from+"(小甜甜)");
            helper.setTo(to);
            helper.setSubject(subject);
            // 发html要加上 true
            helper.setText(context, true);
            helper.addAttachment(f1.getName(), f1);
            helper.addAttachment("jar包.jar", f2);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMail(String to, String subject, String context) {
        // 复杂邮件
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // 发附件要加上 true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from+"(小周)");
            helper.setTo(to);
            helper.setSubject(subject);
            // 发html要加上 true
            helper.setText(context, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
