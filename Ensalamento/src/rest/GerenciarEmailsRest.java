package rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import cfg.Rest;
import cfg.enums.Permissao;
import cfg.enums.StatusEmail;
import cfg.enums.StatusModelo;
import modelo.Professor;
import utils.Email;
import utils.UtilRest;
import utils.Utilidades;

@Path("gerenciarEmailsProfessores")
public class GerenciarEmailsRest extends Rest {
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
	@Path("/listarProfessores")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listarProfessores() throws ClassNotFoundException, SQLException {
		try {
			final List lista = crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal()
					+ " and permissao=\'" + Permissao.PROFESSOR.toString() + "\'").listar();
			System.out.println(lista);
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de listar.");
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
	@Path("/enviarEmail")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response enviarEmail(final String o) {
		System.out.println(Utilidades.crip.base64.organizar(o));
		try {
			final EmailDefault clazz = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), EmailDefault.class);
			System.out.println(clazz.toString());
			Email.email.enviar(clazz.lista, clazz.assunto, clazz.corpoEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora enviar Email");
		}

		return verificaEmails();
	}

	@POST
	@Path("/enviarTodosEmails")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response enviarTodosEmails(final String o) {
		try {
			System.out.println(Utilidades.crip.base64.organizar(o));
			final EmailDefault clazz = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), EmailDefault.class);
			Email.email.enviar(
					crud.query("from Professor where status=" + StatusModelo.ATIVO.ordinal()
					+ " and permissao=\'" + Permissao.PROFESSOR.toString() + "\'").listar(),
					clazz.assunto,
					clazz.corpoEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de enviar todos Emails.");
		}

		return verificaEmails();
	}

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