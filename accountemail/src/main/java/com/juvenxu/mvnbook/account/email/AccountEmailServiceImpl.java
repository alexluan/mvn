package com.juvenxu.mvnbook.account.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.AccountException;

public class AccountEmailServiceImpl implements AccountEmailService {

    //来自于Spring Framework的帮助简化邮件发送的工具类型，对应该字段有一组getter()和setter()
    //方法，用来帮助实现依赖注入
    private JavaMailSender javaMailSender;
    private String systemEmail;
    public void sendMail( String to, String subject, String htmlText )
            throws AccountEmailException
    {
        try
        {
            //邮件服务器配置信息通过外部的配置注入到javaMailSender中
            //创建一个MimeMessage，对应了将要发送的邮件
            MimeMessage msg = javaMailSender.createMimeMessage();
            //用于设置该邮件的发送地址、收件地址、主题以及内容
            MimeMessageHelper msgHelper = new MimeMessageHelper( msg );
            msgHelper.setFrom( systemEmail );
            msgHelper.setTo( to );
            msgHelper.setSubject( subject );
            //true表示邮件的内容为html格式
            msgHelper.setText( htmlText, true );
            //发送邮件
            javaMailSender.send( msg );
        }
        catch ( MessagingException e )
        {
            throw new AccountEmailException( "Faild to send mail.", e );
        }
    }
    public JavaMailSender getJavaMailSender()
    {
        return javaMailSender;
    }
    public void setJavaMailSender( JavaMailSender javaMailSender )
    {
        this.javaMailSender = javaMailSender;
    }
    public String getSystemEmail()
    {
        return systemEmail;
    }
    public void setSystemEmail( String systemEmail )
    {
        this.systemEmail = systemEmail;
    }
}
