package rest;


import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cfg.Authorize;
import cfg.Rest;
import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import modelo.AreaConhecimento;
import utils.UtilRest;
import utils.Utilidades;

@Path("areaConhecimento")
public class AreaConhecimentoRest extends Rest {

	@Override
	public Class<?> getNameClass() {
		return AreaConhecimento.class;
	}

	@Override
	public String getNomeTabela() {
		return "AreaConhecimento";
	}

	@Override
	public Object getInstance() {
		return new AreaConhecimento();
	}

	@POST
	@Path("/relacionar/{token}")
	@Consumes("application/*")
	public final Response relacionar(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		if(!Authorize.verifica(token)) return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final AreaConhecimento classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), AreaConhecimento.class);
			AreaConhecimento areaConhecimento = (AreaConhecimento) crud.em.find(AreaConhecimento.class, classe.id);
			areaConhecimento.unidadeCurricular = classe.unidadeCurricular;
			getValidacao(classe, Validacao.PUT);
			crud.begin().alterar(areaConhecimento).commit();
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
	
	
	@Override
	public final void getValidacao(final Object o, final Validacao val) throws Exception {
		// TODO Auto-generated method stub
		AreaConhecimento objeto = ((AreaConhecimento) o);
		final String contexto = val.toString() + " ->Objeto: AreaConhecimento, Campo: ";
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {

			if (objeto.nome.isEmpty())
				throw new IllegalArgumentException(contexto + "nome Está vazio!");

			if (!Utilidades.jaExisteNoBanco(crud, objeto.id, getNomeTabela()) && val.equals(Validacao.PUT))
				throw new IllegalArgumentException(val.toString()
						+ " -> Objeto: AreaConhecimento, Objeto não existe no banco! -> " + getNomeTabela());
			
			boolean f = true;
			try {				
				final AreaConhecimento objProf = (AreaConhecimento) crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and nome='"+objeto.nome+"' group by nome").selecionar();
				
				if (val.equals(Validacao.PUT) && (objProf.nome.equals(objeto.nome) && objProf.id == objeto.id)) {
					f = false;
				}
			} catch (Exception e) {
				f=false;
			}
			if(f) {
				erro = RestResponse.NOME_JA_CADASTRADO;
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: AreaConhecimento, Campo: Esse nome ja foi utilizado");
			}
		}
	}
}
