package rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

import cfg.Authorize;
import cfg.Rest;
import cfg.enums.Permissao;
import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import modelo.AreaConhecimento;
import modelo.Disponibilidade;
import modelo.Modalidade;
import modelo.Professor;
import modelo.UnidadeCurricular;
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
	@Path("/buscarItem/{id}/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response buscarItem(@PathParam("id") final long i, @PathParam("token") final String token) {
		if(!Authorize.verifica(token)) return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Professor o = (Professor) crud.em.find(Professor.class, i);
			ResultadoProfessor resProf = new ResultadoProfessor();

			resProf.data = o.disponibilidade.data.toString();
			resProf.id = o.id;
			resProf.nome = o.nome;
			resProf.email = o.email;
			resProf.matricula = o.matricula;
			resProf.tipo = o.tipo;
			resProf.disponibilidade = o.disponibilidade;

			res = UtilRest.buildResponse(resProf);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DisponibilidadeRest -> buscarItem ---> Erro na hora de buscar item.");
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		}
		return res;
	}

	@POST
	@Path("/inserirCheck/{token}")
	@Consumes("application/*")
	public final Response inserirCheck(final String o, @PathParam("token") final String token) {
		if(!Authorize.verifica(token)) return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			crud.begin();
			final Professor classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Professor.class);

			Professor obj = (Professor) crud.em.find(Professor.class, classe.id);

			if (obj.disponibilidade != null)
				classe.disponibilidade.id = obj.disponibilidade.id;
			obj.disponibilidade = classe.disponibilidade;

			Professor resProf = (Professor) crud.alterar(obj).commit().em.find(Professor.class, obj.id);

			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DisponibilidadeRest -> inserirCheck ---> Erro na hora de inserirCheck");
			// TODO: handle exception
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		} finally {
			crud.close();
		}
		return res;
	}
 
	@POST
	@Path("/listadisponibilidade/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response listadisponibilidade(String o, @PathParam("token") final String token) {
		if(!Authorize.verifica(token)) return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			PesquisaDisponibilidade pesquisa = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), PesquisaDisponibilidade.class);
			
			List<ResultadoProfessor> response = new ArrayList();
			
			List<Professor> lista = crud.query("from Professor p where status=" + StatusModelo.ATIVO.ordinal()
					+ " and permissao=\'" + Permissao.PROFESSOR.toString() + "\' " +getFiltroDisponibilidade(pesquisa)  +" order by nome ASC" ).listar();
			
			for (int i = 0; i < lista.size(); i++) {
				Professor professor = lista.get(i);
				ResultadoProfessor resProf = new ResultadoProfessor();
				
				resProf.id = professor.id;
				resProf.nome = professor.nome;
				resProf.email = professor.email;
				resProf.matricula = professor.matricula;
				resProf.tipo = professor.tipo;
				resProf.telefone = professor.telefone;
				resProf.disponibilidade = professor.disponibilidade;
				resProf.modalidade = professor.modalidade;
				resProf.areaconhecimento= professor.areaconhecimento;
				resProf.unidadecurricular = professor.unidadecurricular;
				response.add(resProf);
			}
			res = UtilRest.buildResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DisponibilidadeRest -> listadisponibilidade ---> Erro na hora de listar.");
		}

		return res;
	}

	public final String getFiltroDisponibilidade(PesquisaDisponibilidade pesquisa) {
		String where = "";
		if (pesquisa.manha){
			where += " and (p.disponibilidade.segM = true or p.disponibilidade.terM = true or p.disponibilidade.quaM = true or p.disponibilidade.quiM = true or p.disponibilidade.sexM = true or p.disponibilidade.sabM = true or p.disponibilidade.domM = true)";
		}
		if (pesquisa.tarde){
			where += " and (p.disponibilidade.segT = true or p.disponibilidade.terT = true or p.disponibilidade.quaT = true or p.disponibilidade.quiT = true or p.disponibilidade.sexT = true or p.disponibilidade.sabT = true or p.disponibilidade.domT = true)";
		}
		if (pesquisa.noite){
			where += " and (p.disponibilidade.segN = true or p.disponibilidade.terN = true or p.disponibilidade.quaN = true or p.disponibilidade.quiN = true or p.disponibilidade.sexN = true or p.disponibilidade.sabN = true or p.disponibilidade.domN = true)";
		}
		if (pesquisa.segunda) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.segM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.segT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.segN = true)";
			}else{
				where += " and (p.disponibilidade.segM = true or p.disponibilidade.segT = true or p.disponibilidade.segN = true)";
			}
		}
		if (pesquisa.terca) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.terM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.terT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.terN = true)";
			}else{
				where += " and (p.disponibilidade.terM = true or p.disponibilidade.terT = true or p.disponibilidade.terN = true)";
			}
		}
		if (pesquisa.quarta) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.quaM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.quaT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.quaN = true)";
			}else{
				where += " and (p.disponibilidade.quaM = true or p.disponibilidade.quaT = true or p.disponibilidade.quaN = true)";
			}
		}
		if (pesquisa.quinta) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.quiM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.quiT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.quiN = true)";
			}else{
				where += " and (p.disponibilidade.quiM = true or p.disponibilidade.quiT = true or p.disponibilidade.quiN = true)";
			}
		}
		if (pesquisa.sexta) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.sexM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.sexT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.sexN = true)";
			}else{
				where += " and (p.disponibilidade.sexM = true or p.disponibilidade.sexT = true or p.disponibilidade.sexN = true)";
			}
		}
		if (pesquisa.sabado) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.sabM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.sabT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.sabN = true)";
			}else{
				where += " and (p.disponibilidade.sabM = true or p.disponibilidade.sabT = true or p.disponibilidade.sabN = true)";
			}
		}
		if (pesquisa.domingo) {
			if(pesquisa.manha){
				where += " and (p.disponibilidade.domM = true)";
			}else if(pesquisa.tarde){
				where += " and (p.disponibilidade.domT = true)";
			}else if(pesquisa.noite){
				where += " and (p.disponibilidade.domN = true)";
			}else{
				where += " and (p.disponibilidade.domM = true or p.disponibilidade.domT = true or p.disponibilidade.domN = true)";
			}
		}
		if(!pesquisa.pesquisa.isEmpty()) {
			where += " and p.nome like \'"+pesquisa.pesquisa+"%\'";
		}
		return where;
	}
}

class PesquisaDisponibilidade implements Serializable {
	private static final long serialVersionUID = 1L;
	public String pesquisa = "";
	public boolean segunda = false;
	public boolean terca = false;
	public boolean quarta = false;
	public boolean quinta = false;
	public boolean sexta = false;
	public boolean sabado = false;
	public boolean domingo = false;
	public boolean manha = false;
	public boolean tarde = false;
	public boolean noite = false;

	@Override
	public String toString() {
		return "pesquisa [pesquisa=" + pesquisa + ", segunda=" + segunda + ", terca=" + terca + ", quarta=" + quarta
				+ ", quinta=" + quinta + ", sexta=" + sexta + ", sabado=" + sabado + ", domingo=" + domingo + ", manha="+ manha +", tarde="+ tarde +", noite="+noite+"]";
	}

}

class ResultadoProfessor implements Serializable {
	public String matricula;
	public String tipo;
	public Disponibilidade disponibilidade;
	public String email;
	public String nome;
	public String telefone;
	public long id;
	public String data;
	private static final long serialVersionUID = 1L;
	
	public List<Modalidade> modalidade;
	public List<AreaConhecimento> areaconhecimento;
	public List<UnidadeCurricular> unidadecurricular;
	@Override
	public String toString() {
		return "ResultadoProfessor [matricula=" + matricula + ", tipo=" + tipo + ", disponibilidade=" + disponibilidade
				+ ", email=" + email + ", nome=" + nome + ", telefone=" + telefone + ", id=" + id + ", data=" + data
				+ ", modalidade=" + modalidade + ", areaconhecimento=" + areaconhecimento + ", unidadecurricular="
				+ unidadecurricular + "]";
	}
	


}
