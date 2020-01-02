package cfg;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class SessionEmail {
	private static SessionEmail se;
	private static Session session;
	private SessionEmail() {
		final Properties props = new Properties();
 
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", 465);

		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			final protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("vinicios_a_oliveira@estudante.sc.senai.br", "FQDJ2156");
			}
		});

		session.setDebug(true);
	}

	public static SessionEmail getInstance() {
		
		if (se == null) {
			se = new SessionEmail();
		}
		return se;
	}
	
	public void enviarEmail(final String assunto, final String msg, final String email)
			throws AddressException, MessagingException {

		final Message message = new MimeMessage(session);
		final String remetente = "vinicios_a_oliveira@estudante.sc.senai.br";
		message.setFrom(new InternetAddress(remetente)); // Remetente
		final Address[] toUser = InternetAddress // Destinat�rio(s)
				.parse(email.trim().toLowerCase());

		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assunto);// Assunto
		message.setContent(msg, "text/html");
		/** M�todo para enviar a mensagem criada */
		Transport.send(message);

		System.out.println("Email enviado com sucesso !");
		System.out.println("__________________________________________________");

	}
	
}
