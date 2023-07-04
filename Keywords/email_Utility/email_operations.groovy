package email_Utility

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.util.Properties
import java.util.regex.Pattern

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.Flags.Flag
import javax.mail.search.FlagTerm
import javax.mail.search.SearchTerm
import javax.mail.internet.*
import java.io.IOException
import java.util.List
import com.kms.katalon.core.configuration.RunConfiguration
import internal.GlobalVariable
import java.util.regex.*;


/*
 * keyword to send email with attachement from one user  to another 
 * Keyword is used for fax call scenario
 * Here fax number is used to send email
 */
public class email_operations {
	@Keyword
	def send_mail_with_attachement(String sender_email,String sender_password,String receiver_email) {
		final String username = sender_email;
		final String password = sender_password;

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.starttls.required", true);
		props.put("mail.smtp.startssl.enable", true);
		props.put("mail.smtp.startssl.required", true);
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver_email));
			message.setSubject("Testing Subject");
			message.setText("PFA");

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = RunConfiguration.getProjectDir() + "/Hard Data Files/Attachments/git-cheat-sheet-education.pdf"
			String fileName = "git-cheat-sheet-education.pdf";
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			System.out.println("Sending........");

			Transport.send(message);

			System.out.println("Email is sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * keyword to check the new email for fax status
	 */
	@Keyword
	def check_email(String host, String storeType, String user,String password,String sub) {
		try {

			//create properties field
			Properties properties = new Properties();
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "587");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			//create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");
			store.connect(host, user, password);
			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);



			Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flag.SEEN), false));

			Message message = emailFolder.getMessage(emailFolder.getMessageCount());
			String subject = message.getSubject()
			System.out.println("---------------------------------");
			System.out.println("IS SEEN : " + message.isSet(Flag.SEEN));
			System.out.println("Email Number " + emailFolder.getMessageCount());
			System.out.println("Subject: " + message.getSubject());
			System.out.println("From: " + message.getFrom()[0]);
			System.out.println("Text: " + message.getContent().toString());

			String fileName

			if(Pattern.matches(sub, subject)){
				println("Subject is verified and value is : "+subject)
				MimeMultipart multipart = (Multipart) message.getContent();
				String emailContent = getEmailContent(multipart)
				GlobalVariable.emailText = emailContent
				System.out.println("This message Contents " + emailContent );
				fileName=getEmailAttachment(multipart);
				System.out.println("The file name of this attachment is " + fileName);}
			else{
				println("Subject is not as expected")
			}
			emailFolder.close(false);
			store.close();
			return fileName



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	def String getEmailAttachment(MimeMultipart multipart)
	{
		for ( int i = 0; i < multipart.getCount(); i++) {
			MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				if( part.getFileName()!=null)
				{
					return part.getFileName();
				}

			}
		}
		return "";
	}

	def String getEmailContent(MimeMultipart multipart)
	{
		StringBuilder result=new StringBuilder();

		for ( int i = 0; i < multipart.getCount(); i++) {
			MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
			if(bodyPart.getContent() instanceof MimeMultipart)
			{
				result.append("\n").append(getEmailContent(bodyPart.getContent()))
			}
			else if(bodyPart.isMimeType("text/plain"))
			{
				result.append("\n").append(bodyPart.getContent())
			}
			else if(bodyPart.isMimeType("text/html"))
			{
				result.append("\n").append(org.jsoup.Jsoup.parse(bodyPart.getContent()).text())
			}
		}
		return result.toString();
	}
}
