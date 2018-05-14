package com.michael.mail;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailText {
	//发件人地址
    public static String senderAddress = "385933488@qq.com";
    //收件人地址
    public static String recipientAddress = "tengxingjie@madhouse-inc.com";
    //发件人账户名
    public static String senderAccount = "385933488@qq.com";
    //发件人账户密码
    public static String senderPassword = "";
     
    public static void main(String[] args) throws Exception {
    	Session session = generateSession();
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session);
//        //4、根据session对象获取邮件传输对象Transport
//        Transport transport = session.getTransport();
//        //设置发件人的账户名和密码
//        transport.connect(senderAccount, senderPassword);
//        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
//        transport.sendMessage(msg,msg.getAllRecipients());
//         
//        //如果只想发送给指定的人，可以如下写法
//        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
//         
//        //5、关闭邮件连接
//        transport.close();
        
        // 获取Store对象
        Store store = session.getStore("imap");
        store.connect("imap.qq.com", senderAccount, senderPassword); // 服务器的登陆认证
     // 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
        folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限
        
//		folder.addMessageCountListener(new MessageCountListener() {
//			
//			public void messagesAdded(MessageCountEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println(e.toString());
//			}
//
//			public void messagesRemoved(MessageCountEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println(e.toString());
//			}
//		});
		
		int count = folder.getMessageCount();
		int diff = 0;
		while(!Thread.interrupted()){
			int newCount = folder.getMessageCount();
			diff = newCount - count;
			if(diff > 0){
				Message[] messages = folder.getMessages(newCount - diff + 1, newCount); 
				processMessages(messages);
			}
			count = newCount;
			System.out.println(count);
        	Thread.sleep(5000);
        	
        	
        	folder.close(false);
        	folder = store.getFolder("INBOX");// 获得用户的邮件帐户
	        folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限
        }
//        if(folder instanceof POP3Folder){
//        	IMAPFolder inbox = (IMAPFolder) folder; 
//        	Message[] messages = inbox.getMessages(); 
//        	for (int i = 0; i < messages.length; i++) { 
//
//	        	MimeMessage mimeMessage = (MimeMessage) messages[i]; 
//	
//	        	String uid = mimeMessage.getSubject(); 
//	        	System.out.println("==============" + uid);
//	
//        	}
//        }
        System.out.println(folder.getNewMessageCount());
        Message[] messages = folder.getMessages();// 得到邮箱帐户中的所有邮件
        System.out.println(messages[messages.length-1].getSubject());
//        System.out.println(messages[0].getFlags());
//        for (Message message : messages) {
////        	System.out.println(message.getFlags().toString());
////        	if(message.getFlags().toString().equals("javax.mail.Flags@01")){
////        		System.out.println("new message");
////        	}
//            String subject = message.getSubject();// 获得邮件主题
//            Address from = (Address) message.getFrom()[0];// 获得发送者地址
//            System.out.println("邮件的主题为: " + subject + "\t发件人地址为: " + from);
//            System.out.println("邮件的内容为：");
////            message.writeTo(System.out);// 输出邮件内容到控制台
//        }

        folder.close(false);// 关闭邮件夹对象
        store.close(); // 关闭连接对象
        
//        store.addStoreListener(new StoreListener() {
//			
//			public void notification(StoreEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("========"+e.getMessage());
//			}
//		});
//        
//        store.close();
    }
    
    private static void processMessages(Message[] messages) throws MessagingException {
    	for (Message message : messages) {
//    	System.out.println(message.getFlags().toString());
//    	if(message.getFlags().toString().equals("javax.mail.Flags@01")){
//    		System.out.println("new message");
//    	}
	        String subject = message.getSubject();// 获得邮件主题
	        Address from = (Address) message.getFrom()[0];// 获得发送者地址
	        if(from.toString().indexOf("tengxingjie")!=-1 ||from.toString().indexOf("wujunfeng")!=-1){
	        	System.out.println("邮件的主题为: " + subject + "\t发件人地址为: " + from);
	        }
//        message.writeTo(System.out);// 输出邮件内容到控制台
    	}
	}

	private static Session generateSession() throws GeneralSecurityException {
		// TODO Auto-generated method stub
    	//1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //设置ssl加密信息
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(props);
        //设置调试信息在控制台打印出来
//        session.setDebug(true);
        return session;
	}
     
    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("邮件主题","UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
         
        return msg;
    }
    
    private static class MyStore extends Store{
    	
		protected MyStore(Session session, URLName urlname) {
			super(session, urlname);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Folder getDefaultFolder() throws MessagingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Folder getFolder(String name) throws MessagingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Folder getFolder(URLName url) throws MessagingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void notifyFolderListeners(int type, Folder folder) {
			// TODO Auto-generated method stub
			System.out.println("ddd===================");
			super.notifyFolderListeners(type, folder);
		}
    	
    }
}
