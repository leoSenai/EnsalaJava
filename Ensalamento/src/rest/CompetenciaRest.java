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

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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

@Path("competencia")
public class CompetenciaRest extends Rest {

	@Override
	public Class<?> getNameClass() {
		return null;
	}

	@Override
	public String getNomeTabela() {
		return "";
	}

	@Override
	public Object getInstance() {
		return null;
	}

	@Override
	public final void getValidacao(final Object o, final Validacao val) throws Exception {
		// TODO Auto-generated method stub

	}

	@GET
	@Path("/pegamodalidades/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response getModalidade(@PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final List lista = crud
					.query("from Modalidade where status=" + StatusModelo.ATIVO.ordinal() + " order by nome ASC")
					.listar();
			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> getModalidade ---> Erro na hora de listar.");
		}

		return res;

	}

	@GET
	@Path("/pegaunidadecurriculares/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response getUnidadeCurricular(@PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final List lista = crud
					.query("from UnidadeCurricular where status=" + StatusModelo.ATIVO.ordinal() + " order by nome ASC")
					.listar();

			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> getUnidadeCurricular ---> Erro na hora de listar.");
		}

		return res;

	}

	@GET
	@Path("/pegaareaconhecimentos/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response getAreaConhecimento(@PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final List lista = crud
					.query("from AreaConhecimento where status=" + StatusModelo.ATIVO.ordinal() + " order by nome ASC")
					.listar();

			res = UtilRest.buildResponse(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> getAreaConhecimento ---> Erro na hora de listar.");
		}

		return res;

	}

	@POST
	@Path("/inseremodalidade/{token}")
	@Consumes("application/*")
	public final Response inseremodalidade(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			final Professor classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Professor.class);
			System.out.println(Utilidades.crip.base64.organizar(o));
			Professor professor = (Professor) crud.em.find(Professor.class, classe.id);
			professor.modalidade = classe.modalidade;

			crud.begin().alterar(professor).commit();

			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> inseremodalidade ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(RestResponse.INSERIR_ERROR.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@POST
	@Path("/insereareaconhecimento/{token}")
	@Consumes("application/*")
	public final Response insereareaconhecimento(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Professor classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Professor.class);
			Professor professor = (Professor) crud.em.find(Professor.class, classe.id);
			professor.areaconhecimento = classe.areaconhecimento;
			crud.begin().alterar(professor).commit();
			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> insereareaconhecimento ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(RestResponse.INSERIR_ERROR.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@POST
	@Path("/insereunidadecurricular/{token}")
	@Consumes("application/*")
	public final Response insereunidadecurricular(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Professor classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Professor.class);
			Professor professor = (Professor) crud.em.find(Professor.class, classe.id);
			professor.unidadecurricular = classe.unidadecurricular;
			crud.begin().alterar(professor).commit();

			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> insereunidadecurricular ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(RestResponse.INSERIR_ERROR.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@GET
	@Path("/buscarItem/{id}/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response buscarItem(@PathParam("id") final long i, @PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Professor o = (Professor) crud.em.find(Professor.class, i);
			ResultadoCompetencia resProf = new ResultadoCompetencia();

			resProf.id = o.id;
			resProf.nome = o.nome;
			resProf.email = o.email;
			resProf.matricula = o.matricula;
			resProf.tipo = o.tipo;
			resProf.modalidade = o.modalidade;
			resProf.areaconhecimento = o.areaconhecimento;
			resProf.unidadecurricular = o.unidadecurricular;

			res = UtilRest.buildResponse(resProf);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> buscarItem ---> Erro na hora de buscar item.");
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		}
		return res;
	}

	@POST
	@Path("/listarItens/{token}")
	@Consumes("application/*")
	public final Response listarItens(String o, @PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			PesquisaCompetentes pesquisa = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o),
					PesquisaCompetentes.class);

			List<ListaCompetentes> response = new ArrayList();

			String query = "select p from Professor p" + " inner join p.modalidade m"
					+ " inner join p.areaconhecimento a" + " inner join p.unidadecurricular u" + " where p.status="
					+ StatusModelo.ATIVO.ordinal() + " and m.status=" + StatusModelo.ATIVO.ordinal() + " and a.status="
					+ StatusModelo.ATIVO.ordinal() + " and u.status=" + StatusModelo.ATIVO.ordinal()
					+ " and p.permissao=\'" + Permissao.PROFESSOR.toString() + "\'";

			if (pesquisa.modalidade.size() != 0) {
				query += " and (";

				for (Modalidade obj : pesquisa.modalidade) {
					if (pesquisa.modalidade.size() > 1) {
						query += " m.id = " + obj.id + " or ";
						continue;
					}
					query += " m.id = " + obj.id;
				}

				if (query.substring(query.length() - 4, query.length()).contains("or"))
					query = query.substring(0, query.length() - 4);
				query += ")";
			}
			if (pesquisa.areaconhecimento.size() != 0) {
				query += " and (";
				for (AreaConhecimento obj : pesquisa.areaconhecimento) {
					if (pesquisa.areaconhecimento.size() > 1) {
						query += " a.id = " + obj.id + " or ";
						continue;
					}
					query += " a.id = " + obj.id;
				}

				if (query.substring(query.length() - 4, query.length()).contains("or"))
					query = query.substring(0, query.length() - 4);
				query += ")";
			}

			if (pesquisa.unidadecurricular.size() != 0) {
				query += " and (";
				for (UnidadeCurricular obj : pesquisa.unidadecurricular) {
					if (pesquisa.unidadecurricular.size() > 1) {
						query += " u.id = " + obj.id + " or ";
						continue;
					}
					query += " u.id = " + obj.id;
				}

				if (query.substring(query.length() - 4, query.length()).contains("or"))
					query = query.substring(0, query.length() - 4);
				query += ")";
			}

			query += " group by p.id order by p.nome ASC";

			crud.query(query);

			List<Professor> lista = (List<Professor>) crud.listar();

			for (int i = 0; i < lista.size(); i++) {
				Professor professor = (Professor) lista.get(i);
				ListaCompetentes resProf = new ListaCompetentes();

				resProf.id = professor.id;
				resProf.nome = professor.nome;
				resProf.email = professor.email;
				resProf.matricula = professor.matricula;
				resProf.tipo = professor.tipo;
				resProf.telefone = professor.telefone;
				resProf.disponibilidade = professor.disponibilidade;
				resProf.modalidade = professor.modalidade;
				resProf.areaconhecimento = professor.areaconhecimento;
				resProf.unidadecurricular = professor.unidadecurricular;

				response.add(resProf);
			}
			res = UtilRest.buildResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CompetenciaRest -> listarItens ---> Erro na hora de listar itens.");
			res = UtilRest.buildErrorResponse(RestResponse.SELECIONAR_ERROR.toString());
		}
		return res;
	}

}

class PesquisaCompetentes implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<Modalidade> modalidade;
	public List<AreaConhecimento> areaconhecimento;
	public List<UnidadeCurricular> unidadecurricular;

	@Override
	public String toString() {
		return "PesquisaCompetentes [modalidade=" + modalidade + ", areaconhecimento=" + areaconhecimento
				+ ", unidadecurricular=" + unidadecurricular + "]";
	}

}

class ResultadoCompetencia implements Serializable {
	public List<Modalidade> modalidade;
	public List<AreaConhecimento> areaconhecimento;
	public List<UnidadeCurricular> unidadecurricular;
	public String email;
	public String nome;
	public String matricula;
	public String tipo;
	public long id;
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ResultadoCompetencia [modalidade=" + modalidade + ", areaconhecimento=" + areaconhecimento
				+ ", unidadecurricular=" + unidadecurricular + ", email=" + email + ", nome=" + nome + ", matricula="
				+ matricula + ", tipo=" + tipo + ", id=" + id + "]";
	}

}

class ListaCompetentes implements Serializable {
	public List<Modalidade> modalidade;
	public List<AreaConhecimento> areaconhecimento;
	public List<UnidadeCurricular> unidadecurricular;
	public Disponibilidade disponibilidade;
	public String email;
	public String nome;
	public String telefone;
	public String matricula;
	public String tipo;
	public long id;
	private static final long serialVersionUID = 1L;

}
