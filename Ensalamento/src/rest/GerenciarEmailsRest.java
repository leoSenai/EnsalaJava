package rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import cfg.Authorize;
import cfg.Rest;
import cfg.Server;
import cfg.enums.Permissao;
import cfg.enums.RestResponse;
import cfg.enums.StatusEmail;
import cfg.enums.StatusModelo;
import modelo.Professor;
import utils.Email;
import utils.UtilRest;
import utils.Utilidades;

@Path("gerenciarEmails")
public class GerenciarEmailsRest extends Rest {

	public GerenciarEmailsRest() {
		needCrud = false;
	}

	@Override
	public Class<?> getNameClass() {
		return Professor.class;
	}

	@Override
	public String getNomeTabela() {
		return "Professor";
	}

	@Override
	public Object getInstance() {
		return new Professor();
	}

	@GET
	@Path("/listarProfessores/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listarProfessores(@PathParam("token") final String token)
			throws ClassNotFoundException, SQLException {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final List lista = crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal()
					+ " and permissao=\'" + Permissao.PROFESSOR.toString() + "\'").listar();
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GerenciarEmailsRest -> listarProfessores ---> Erro na hora de listar.");
		}
		return res;
	}

	@GET
	@Path("/verificarEmails")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response verificaEmails() {
		try {
			res = UtilRest.buildResponse(Email.email.checkEmailsEnviando().toString());
		} catch (Exception e) {
			res = UtilRest.buildErrorResponse(StatusEmail.FAIL.toString());
		}
		return res;
	}

	@POST
	@Path("/esqueciMinhaSenha")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response esqueciMinhaSenha(final String o) {
		try {
			final EsqueciMinhaSenha clazz = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o),
					EsqueciMinhaSenha.class);
			crud.query(
					"from Professor where status=" + StatusModelo.ATIVO.ordinal() + " and email='" + clazz.email + "'")
					.selecionar();
			try {
				String jwtToken = Authorize.geraToken(clazz.email, 1440L);
				
				StringBuilder corpoEmail = new StringBuilder();
				String path = Server.URL_CLIENT_DEFAULT+"alterarSenha/"+jwtToken;
				corpoEmail.append("Poderá refazer sua senha por este link.")
						.append("<br/>")
						.append("<br/>")
						.append("<a href=\""+path+"\" target=\"_blank\">" + path + "</a>")
						.append("<br/>")
						.append("<br/>")
						.append("Esse link tem a duração de apenas 1 dia, após passar esse tempo, ele não terá mais validade.");

				Email.email.enviar(clazz.email, "Recuperação de senha", corpoEmail.toString());
				res = UtilRest.buildResponse(RestResponse.EMAIL_ENVIADO_SUCESSO.toString());
			} catch (Exception e) {
				e.printStackTrace();
				erro = RestResponse.ERRO_ENVIAR_EMAIL;
			}
		} catch (Exception e) {
			e.printStackTrace();
			erro = RestResponse.EMAIL_NAO_ENCONTRADO;
		}

		if (erro != null)
			res = UtilRest.buildErrorResponse(erro.toString());

		return res;
	}

	@POST
	@Path("/enviarEmail/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response enviarEmail(final String o, @PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			final EmailDefault clazz = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o),
					EmailDefault.class);
			Email.email.enviar(clazz.lista, clazz.assunto, clazz.corpoEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GerenciarEmailsRest -> enviarEmail ---> Erro na hora enviar Email");
		}

		return verificaEmails();
	}

	@POST
	@Path("/enviarTodosEmails/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response enviarTodosEmails(final String o, @PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final EmailDefault clazz = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o),
					EmailDefault.class);
			Email.email.enviar(crud.query("from Professor where status=" + StatusModelo.ATIVO.ordinal()
					+ " and permissao=\'" + Permissao.PROFESSOR.toString() + "\'").listar(), clazz.assunto,
					clazz.corpoEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GerenciarEmailsRest -> enviarTodosEmails ---> Erro na hora de enviar todos Emails.");
		}

		return verificaEmails();
	}

}

class EsqueciMinhaSenha implements Serializable {
	public String email;
}

class EmailDefault implements Serializable {

	private static final long serialVersionUID = 1L;
	public String assunto = "";
	public String corpoEmail = "";
	public List<Professor> lista;

	@Override
	public String toString() {
		return "EmailDefault [assunto=" + assunto + ", corpoEmail=" + corpoEmail + ", lista=" + lista + "]";
	}

}