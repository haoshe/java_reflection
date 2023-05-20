package javaMail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailDemo {
	
	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		
		props.setProperty("mail.transport.protocol", "smtp");//使用协议：smtp
		props.setProperty("mail.smtp.host", "smtp.office365.com");//协议地址
		props.setProperty("mail.smtp.port", "587");//协议端口
		props.setProperty("mail.smpt.auth", "true");//是否需要授权
		//if it's QQ mail, it needs SSL 安全认证
		//java support SSL security certification through "javax.net.ssl.SSLSocketFactory" class
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//only deals with mails have been certified by SSL security(只处理经过SSL安全认证的邮件，没有安全认证，一概不处理)
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.prot", "587");
		
		//visit mail server through Session class
		Session session = Session.getInstance(props);
		
		//open up the log prompt(开启日志提示)
		session.setDebug(true);
		
		//generate a mail
		MimeMessage message = createMimeMessage(session,"haoshecn@hotmail.com","haoshecn@gmail.com","katieshecn@gmail.com","danielshecn@gamil.com");
		
		//send the mail to the recipient through Transport class
		//create a connection object(建立连接对象)
		Transport transport = session.getTransport();
		
		//establish the connection(建立连接), the password might be written in the form of an authorization code(授权码)
		transport.connect("haoshecn@hotmail.com", "pafSis-nyvjej-vaqbi6");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	//MIME(Multipurpose Internet Mail Extensions):多用途互联网邮件扩展类型
	public static MimeMessage createMimeMessage(Session session, String sender, String recipient, String ccRecipient, String bccRecipient) throws Exception {
		MimeMessage message = new MimeMessage(session);
		//mail: subject(主题), sender（发件人）, content（正文）, recipient（收件人）
		
		//setFrom(Address address) needs an Address type, Address is an interface, can't do new Address(). How to find its implementation class?
		//write Address address = new Address(), click Address(), control+t, all its implementation classes will show up
		//we use InternetAddress() in this case
		Address address = new InternetAddress(sender,"senderName","charset=UTF-8");
		message.setFrom(address);
		message.setSubject("this is the subject","UTF-8");
		message.setContent("Hello", "UTF-8");
		
		//there are several types of recipients: To(普通收件人), CC(抄送)，BCC(密送)
		/*
		 * In email sending, CC is the abbreviation for “carbon copy.”
		 * If you’ve ever received a CCed email, you’ve probably noticed that it will be addressed to you and a list of other people who have also been CCed.
		 * BCC stands for “blind carbon copy.” Just like CC, BCC is a way of sending copies of an email to other people.
		 * The difference between the two is that, while you can see a list of recipients when CC is used, that’s not the case with BCC. 
		 * It’s called blind carbon copy because the other recipients won’t be able to see that someone else has been sent a copy of the email.
		 */
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient,"reciever A","UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(ccRecipient,"ccRecipient B","UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bccRecipient,"bccRecipient C","UTF-8"));
		
		//set the time of sending
		message.setSentDate(new Date());
		
		//save the mail
		message.saveChanges();
		return message;
	}
}
