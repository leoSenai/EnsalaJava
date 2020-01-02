package cfg;

import javax.ws.rs.core.Response;
//requisi�oes
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
//parametros enviados
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//configura�ao
import javax.ws.rs.Produces;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cfg.enums.StatusModelo;
import cfg.Rest;
import cfg.enums.Validacao;
import cfg.enums.RestResponse;
import utils.Banco;
import utils.UtilRest;
import utils.Utilidades;

public class Rest implements AbstractMethodsRest {
	final protected Banco crud = new Banco();
	protected Response res;
	final protected Class<?> clazz = getNameClass();

	@GET
	@Path("/buscar/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response buscar(@PathParam("id") final int i) {
		try {
			System.out.println("id -> " + i);
			final Object o = crud.query(
					"from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal() + " and id = " + i)
					.selecionar();

			getValidacao(o, Validacao.GET);
			System.out.println(o.toString());
			res = UtilRest.buildResponse(o);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de selecionar.");
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		}
		return res;
	}

	@GET
	@Path("/listar")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listar() throws ClassNotFoundException, SQLException {

		try {
			final List lista = crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal())
					.listar();
			System.out.println(lista);
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de listar.");
		}

		return res;
	}

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public final Response inserir(final String o) throws JsonParseException, JsonMappingException, IOException {
		try {
			System.out.println(o);
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());
			setStatus(classe, StatusModelo.ATIVO);
			System.out.println("Inserir: " + classe.toString());
			getValidacao(classe, Validacao.POST);
			crud.begin().inserir(classe).commit();
			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(RestResponse.INSERIR_ERROR.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public final Response alterar(final String o) throws JsonParseException, JsonMappingException, IOException {
		try {
			System.out.println("antes -> "+o);
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());
			setStatus(classe, StatusModelo.ATIVO);
			System.out.println("update: " + classe.toString());
			getValidacao(classe, Validacao.PUT);
			crud.begin().alterar(classe).commit();

			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de editar.");
			res = UtilRest.buildErrorResponse(RestResponse.ALTERAR_ERROR.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	@DELETE
	@Path("/remover/{i}")
	@Consumes("application/*")
	public final Response remover(@PathParam("i") final int i) {
		try {
			final Object o = crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal() + " and id = " + i).selecionar();
			setStatus(o, StatusModelo.INATIVO);
			System.out.println("Remover: " + o.toString());

			getValidacao(o, Validacao.DELETE);
			crud.begin().alterar(o).commit();
			res = UtilRest.buildResponse(RestResponse.REMOVER.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de remover.");
			res = UtilRest.buildErrorResponse(RestResponse.REMOVER_ERROR.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	final void setStatus(final Object o, final StatusModelo sm) {
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
