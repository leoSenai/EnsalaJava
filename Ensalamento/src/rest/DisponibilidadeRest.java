package rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;

import cfg.Rest;
import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import modelo.Disponibilidade;
import modelo.Professor;
import utils.UtilRest;
import utils.Utilidades;

@Path("disponibilidade")
public class DisponibilidadeRest extends Rest {
	@Override
	public Class<?> getNameClass() {
		return Disponibilidade.class;
	}

	@Override
	public String getNomeTabela() {
		return "Disponibilidade";
	}

	@Override
	public Object getInstance() {
		return new Disponibilidade();
	}

	@Override
	public final void getValidacao(final Object o, final Validacao val) throws Exception {
	}
	
	@GET
	@Path("/buscarItem/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response buscarItem(@PathParam("id") final long i) {
		try {
			System.out.println("id -> " + i);
			final Professor o = crud.em.find(Professor.class, i);
			ResultadoProfessor resProf = new ResultadoProfessor();
			resProf.data = o.disponibilidade.data.toString();
			resProf.id = o.id;
			resProf.nome = o.nome;
			resProf.email = o.email;
			resProf.cpf = o.cpf;
			resProf.disponibilidade = o.disponibilidade;
			
			System.out.println(o.toString());
			res = UtilRest.buildResponse(resProf);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na hora de buscarItem.");
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		}
		return res;
	}
	
	@POST
	@Path("/inserirCheck")
	@Consumes("application/*")
	public final Response inserirCheck(final String o) {
		try {
			crud.begin();
			System.out.println(Utilidades.crip.base64.organizar(o));
			final Professor classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Professor.class);
			System.out.println("InserirCheck: " + classe.toString());

			Professor obj = crud.em.find(Professor.class, classe.id);
//			System.out.println(obj.toString());
			if (obj.disponibilidade != null)
				classe.disponibilidade.id = obj.disponibilidade.id;
			obj.disponibilidade = classe.disponibilidade;
				
				Professor resProf = crud.alterar(obj).commit().em.find(Professor.class, obj.id);
			Date oteste = (Date) crud.query("select p.data from professor.disponibilidade p where professor.id = " + obj.id).selecionar();
			System.out.println(oteste.toString());
			
//			ResultadoProfessor resProf = new ResultadoProfessor();	
//			
//			resProf.data = obj.disponibilidade.data.toString();
			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
			if(oteste != null)
				res = UtilRest.buildResponse(oteste.toString());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro na hora de inserirCheck");
			// TODO: handle exception
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	
	
}
class ResultadoProfessor implements Serializable {

	public long cpf;
	public Disponibilidade disponibilidade;
	public String email;
	public String nome;
	public long id;
	public String data;
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "ResultadoProfessor [cpf=" + cpf + ", disponibilidade=" + disponibilidade + ", email=" + email
				+ ", nome=" + nome + ", id=" + id + ", data=" + data + "]";
	}

	
}

