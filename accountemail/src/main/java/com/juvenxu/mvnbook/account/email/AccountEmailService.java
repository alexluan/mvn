package com.juvenxu.mvnbook.account.email;

import javax.mail.MessagingException;
import javax.security.auth.login.AccountException;

public interface AccountEmailService {
    //to:接受地址；subject:邮件主题 htmlText:邮件内容
    //发送html格式的邮件，to为接收地址，subject为邮件主题，htmlText为邮件内容，如果发送邮件出错,
    //则抛出AccountEmailException异常
    void sendMail( String to, String subject, String htmlText )
            throws AccountEmailException;

}
