package cfg;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
//requisi�oes
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
//parametros enviados
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//configura�ao
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import utils.Banco;
import utils.UtilRest;
import utils.Utilidades;

public class Rest implements AbstractMethodsRest {
	final protected Banco crud = new Banco();
	protected Response res;
	protected RestResponse erro;
	final protected Class<?> clazz = getNameClass();
	protected boolean needCrud = true;

	@GET
	@Path("/buscar/{id}/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response buscar(@PathParam("id") final int i, @PathParam("token") final String token) {
		erro = RestResponse.SELECIONAR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			final Object o = crud.query(
					"from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal() + " and id = " + i)
					.selecionar();

			getValidacao(o, Validacao.GET);
			res = UtilRest.buildResponse(o);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> buscar ---> Erro na hora de selecionar.");
			res = UtilRest.buildErrorResponse(erro.toString());
		}
		return res;
	}

	@GET
	@Path("/listar/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listar(@PathParam("token") final String token) throws ClassNotFoundException, SQLException {
		erro = RestResponse.SELECIONAR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			final List lista = crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal())
					.listar();
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> listar ---> Erro na hora de listar.");
		}

		return res;
	}

	@GET
	@Path("/listar/{status}/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listarStatus(@PathParam("token") final String token, @PathParam("status") final String status)
			throws ClassNotFoundException, SQLException {
		erro = RestResponse.SELECIONAR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			StatusModelo statusModelo = status.equals(StatusModelo.ATIVO.toString()) ? StatusModelo.ATIVO
					: StatusModelo.INATIVO;
			final List lista = crud.query("from " + getNomeTabela() + " where status=" + statusModelo.ordinal())
					.listar();
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> listar ---> Erro na hora de listar.");
		}

		return res;
	}

	@POST
	@Path("/inserir/{token}")
	@Consumes("application/*")
	public final Response inserir(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.INSERIR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());
			setStatus(classe, StatusModelo.ATIVO);
			getValidacao(classe, Validacao.POST);
			crud.begin().inserir(classe).commit();
			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> inserir ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@PUT
	@Path("/alterar/{token}")
	@Consumes("application/*")
	public final Response alterar(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.ALTERAR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			System.out.println(Utilidades.crip.base64.organizar(o));
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());
			setStatus(classe, StatusModelo.ATIVO);
			getValidacao(classe, Validacao.PUT);
			crud.begin().alterar(classe).commit();
			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> alterar ---> Erro na hora de editar.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	@GET
	@Path("/reativar/{i}/{token}")
	@Consumes("application/*")
	public final Response reativar(@PathParam("i") final int i, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.ALTERAR_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			final Object o = crud.query(
					"from " + getNomeTabela() + " where status=" + StatusModelo.INATIVO.ordinal() + " and id = " + i)
					.selecionar();
			setStatus(o, StatusModelo.ATIVO);

			getValidacao(o, Validacao.PUT);
			crud.begin().alterar(o).commit();
			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> alterar ---> Erro na hora de editar.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	@DELETE
	@Path("/remover/{i}/{token}")
	@Consumes("application/*")
	public final Response remover(@PathParam("i") final int i, @PathParam("token") final String token) {
		erro = RestResponse.REMOVER_ERROR;

		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		if (!needCrud)
			return UtilRest.buildErrorResponse(erro.toString());

		try {
			final Object o = crud.query(
					"from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal() + " and id = " + i)
					.selecionar();
			setStatus(o, StatusModelo.INATIVO);

			getValidacao(o, Validacao.DELETE);
			crud.begin().alterar(o).commit();
			res = UtilRest.buildResponse(RestResponse.REMOVER.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> remover ---> Erro na hora de remover.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	protected final void setStatus(final Object o, final StatusModelo sm) {
		try {
			clazz.getField("status").set(o, sm);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Caso for public.. getField retorna um campo publico e procura na hierarquia
	}

	public Class<?> getNameClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNomeTabela() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getInstance() {
		return null;
	}

	public void getValidacao(final Object o, final Validacao val) throws Exception {
		// TODO Auto-generated method stub
	}

}
