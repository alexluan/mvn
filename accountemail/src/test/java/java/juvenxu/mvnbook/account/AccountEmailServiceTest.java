package java.juvenxu.mvnbook.account;
import static junit.framework.Assert.assertEquals;
import javax.mail.Message;

import com.juvenxu.mvnbook.account.email.AccountEmailException;
import com.juvenxu.mvnbook.account.email.AccountEmailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {
    private GreenMail greenMail;
    //在测试方法之前执行，启动邮件服务器
    @Before
    public void startMailServer()
            throws Exception    {
        greenMail = new GreenMail( ServerSetup.SMTP );
        greenMail.setUser( "326083325@qq.com", "wodemima2018" );
        greenMail.start();
        System.out.println("start email server");
    }
    @Test
    public void testSendMail()
//    {
            throws Exception, AccountEmailException {
        try {
            //根据classpath路径中account-email.xml配置创建ApplicationContext
            ApplicationContext ctx = new ClassPathXmlApplicationContext( "account-email.xml" );
            AccountEmailService accountEmailService =
                    (AccountEmailService) ctx.getBean( "accountEmailService" );
            String subject = "Test Subject";
            String htmlText = "<h3>Test</h3>";
            accountEmailService.sendMail( "326083325@qq.com", subject, htmlText );

            greenMail.waitForIncomingEmail( 2000, 1 );
            Message[] msgs = greenMail.getReceivedMessages();
            assertEquals( 1, msgs.length );
            assertEquals( subject, msgs[0].getSubject() );
            assertEquals( htmlText, GreenMailUtil.getBody( msgs[0] ).trim() );
        }catch (Exception e){
            throw new AccountEmailException("试试");
        }
    }
    //在测试方法时候执行，关闭邮件服务器
    @After
    public void stopMailServer()
            throws Exception    {
        greenMail.stop();
    }
}
