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
	//�����˵�ַ
    public static String senderAddress = "385933488@qq.com";
    //�ռ��˵�ַ
    public static String recipientAddress = "tengxingjie@madhouse-inc.com";
    //�������˻���
    public static String senderAccount = "385933488@qq.com";
    //�������˻�����
    public static String senderPassword = "";
     
    public static void main(String[] args) throws Exception {
    	Session session = generateSession();
        //3�������ʼ���ʵ������
        Message msg = getMimeMessage(session);
//        //4������session�����ȡ�ʼ��������Transport
//        Transport transport = session.getTransport();
//        //���÷����˵��˻���������
//        transport.connect(senderAccount, senderPassword);
//        //�����ʼ��������͵������ռ��˵�ַ��message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
//        transport.sendMessage(msg,msg.getAllRecipients());
//         
//        //���ֻ�뷢�͸�ָ�����ˣ���������д��
//        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
//         
//        //5���ر��ʼ�����
//        transport.close();
        
        // ��ȡStore����
        Store store = session.getStore("imap");
        store.connect("imap.qq.com", senderAccount, senderPassword); // �������ĵ�½��֤
     // ͨ��POP3Э����Store��������������ʱ���ʼ�������ֻ��ָ��Ϊ"INBOX"
        Folder folder = store.getFolder("INBOX");// ����û����ʼ��ʻ�
        folder.open(Folder.READ_WRITE); // ���ö��ʼ��ʻ��ķ���Ȩ��
        
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
        	folder = store.getFolder("INBOX");// ����û����ʼ��ʻ�
	        folder.open(Folder.READ_WRITE); // ���ö��ʼ��ʻ��ķ���Ȩ��
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
        Message[] messages = folder.getMessages();// �õ������ʻ��е������ʼ�
        System.out.println(messages[messages.length-1].getSubject());
//        System.out.println(messages[0].getFlags());
//        for (Message message : messages) {
////        	System.out.println(message.getFlags().toString());
////        	if(message.getFlags().toString().equals("javax.mail.Flags@01")){
////        		System.out.println("new message");
////        	}
//            String subject = message.getSubject();// ����ʼ�����
//            Address from = (Address) message.getFrom()[0];// ��÷����ߵ�ַ
//            System.out.println("�ʼ�������Ϊ: " + subject + "\t�����˵�ַΪ: " + from);
//            System.out.println("�ʼ�������Ϊ��");
////            message.writeTo(System.out);// ����ʼ����ݵ�����̨
//        }

        folder.close(false);// �ر��ʼ��ж���
        store.close(); // �ر����Ӷ���
        
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
	        String subject = message.getSubject();// ����ʼ�����
	        Address from = (Address) message.getFrom()[0];// ��÷����ߵ�ַ
	        if(from.toString().indexOf("tengxingjie")!=-1 ||from.toString().indexOf("wujunfeng")!=-1){
	        	System.out.println("�ʼ�������Ϊ: " + subject + "\t�����˵�ַΪ: " + from);
	        }
//        message.writeTo(System.out);// ����ʼ����ݵ�����̨
    	}
	}

	private static Session generateSession() throws GeneralSecurityException {
		// TODO Auto-generated method stub
    	//1�������ʼ��������Ĳ�������
        Properties props = new Properties();
        //�����û�����֤��ʽ
        props.setProperty("mail.smtp.auth", "true");
        //���ô���Э��
        props.setProperty("mail.transport.protocol", "smtp");
        //���÷����˵�SMTP��������ַ
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //����ssl������Ϣ
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //2��������������Ӧ�ó�������Ļ�����Ϣ�� Session ����
        Session session = Session.getDefaultInstance(props);
        //���õ�����Ϣ�ڿ���̨��ӡ����
//        session.setDebug(true);
        return session;
	}
     
    /**
     * ��ô���һ���ʼ���ʵ������
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception{
        //����һ���ʼ���ʵ������
        MimeMessage msg = new MimeMessage(session);
        //���÷����˵�ַ
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * �����ռ��˵�ַ���������Ӷ���ռ��ˡ����͡����ͣ�����������һ�д�����д����
         * MimeMessage.RecipientType.TO:����
         * MimeMessage.RecipientType.CC������
         * MimeMessage.RecipientType.BCC������
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //�����ʼ�����
        msg.setSubject("�ʼ�����","UTF-8");
        //�����ʼ�����
        msg.setContent("�򵥵Ĵ��ı��ʼ���", "text/html;charset=UTF-8");
        //�����ʼ��ķ���ʱ��,Ĭ����������
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
