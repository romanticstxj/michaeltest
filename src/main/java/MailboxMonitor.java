import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.imap.IMAPFolder;

/***********************************************************************************************************************
 * FileName - MailboxMonitor.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 21, 2019 $
 **********************************************************************************************************************/

public class MailboxMonitor {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
 
            @Override
            public void run() {
                // TODO Auto-generated method stub
                monitorQQMailbox();
            }
        }, 1000,1000*60);//延迟1000毫秒开始第一次执行，60*1000毫秒执行一次
    }
    
    public static void sendMail(){
        //做链接前的准备工作  也就是参数初始化
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
//        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("385933488@qq.com","tzwygkrprrlgcadg");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("385933488@qq.com"));

        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("120634115@qq.com"));//收件人
        //设置主题
        message.setSubject("验证码");
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent("2569658","text/html;charset=UTF-8");
        //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("385933488@qq.com","tzwygkrprrlgcadg");
            Transport.send(message);
            System.out.println("执行了");
    } catch (MessagingException e) {
        e.printStackTrace();
    }finally {

        }

    }
    
    public static void monitorQQMailbox(){
        System.out.println("Hello World!");
        // 准备连接服务器的会话信息
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imap");
        properties.setProperty("mail.imap.host", "imap.qq.com");
        properties.setProperty("mail.imap.port", "993");//根据邮件服务器情况设定
//        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.imap.ssl.enable", "true");
 
        // 创建Session实例对象
        Session session = Session.getInstance(properties);
        try {
            // 创建IMAP协议的Store对象
            Store store = session.getStore("imap");
 
            // 连接邮件服务器
            store.connect("imap.qq.com","385933488@qq.com", "tzwygkrprrlgcadg");
            // 获得收件箱
            Folder folder = (IMAPFolder)store.getFolder("INBOX");
            // 以读写模式打开收件箱
            folder.open(Folder.READ_WRITE);
 
            // 获得收件箱的邮件列表
            Message[] messages = folder.getMessages();
 
        // 打印不同状态的邮件数量
            System.out.println("收件箱中共" + messages.length + "封邮件!");
            System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
            System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
            System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");
            folder.close(false);
            store.close();
        }catch (Exception  e){
            System.out.println("testEx, catch exception"+e.getStackTrace());
            //throw e;
        }
    }

}
