package cfg;

public class Server {
	
	// URL + PORTA
	public final static String URL_BACKEND_DEFAULT = "https://ensalaserver.herokuapp.com/";
	public final static String URL_CLIENT_DEFAULT = "https://ensala.herokuapp.com/";
	public final static String URL_BACKEND_CONTEXT = "/rest/";
	
	// nome da referencia do banco para o hibernate
	public final static String DB_NAME = "ensalamento"; // padrão "ensalamento"

	// hash estático para geração de criptografia
	public final static String CRIP_HASH = "79C9B6F8E0B2417A84A9";
	public final static String TOKEN_HASH = "7f-j&CKk=fF13k.-24y7_4obMP?#TfcYq%fc00-@0sdfh2nc!lfGoZ|d?f&RNb2DHUX6";
	
	/**
	 *  Documentação do JAVAMAIL
	 	https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
	 
	 	PROTOCOLO
	 	Pop3 ou IMAP servem para recebimento de e-mail
	 	SMTP serve para envio de e-mail
	 	
	 	-------- Até o momento apenas enviamos e-mails ------------
	
	 	CERTIFICADO
	 	SSL - Secure Sockets Layer
	 	TLS - Transport Layer Security 
	 	
	 	GMAIL
	 	host: smtp.gmail.com
	 	Port for SSL: 465
		Port for TLS/STARTTLS: 587
		
		hotmail
		host: smtp.live.com
		Port for TLS/STARTTLS: 587
		
		
		Caso não seja hotmail ou gmail o correto é procurar na internet
		o host o certificado e a porta específica do seu provedor
	 */
	
	public final static String EMAIL_USER = "fabiola_professora@yahoo.com";
	public final static String EMAIL_PASS = "ezthqwbejpukihqs";
	public final static String EMAIL_HOST = "smtp.mail.yahoo.com";
	public final static String EMAIL_SOCKETFACTORY_PORT = "465";
	public final static String EMAIL_PORT = "587";
	public final static String EMAIL_CERTIFICADO = "TLS";  // SSL ou TLS
	
	
	public final static String EMAIL_PROTOCOLO = "SMTP";  
	public final static boolean EMAIL_AUTH = true; // padrão true
	public final static boolean EMAIL_DEBUG = true; // padrão true
	
	// TRUE envia os emails mesmo tendo algum endereço inválido na lista
	// FALSE não envia os emails se tiver algum endereço inválido
	public final static boolean EMAIL_SENDPARTIAL = true; // por padrão true, pois faz sentido na regra de negócio
}
