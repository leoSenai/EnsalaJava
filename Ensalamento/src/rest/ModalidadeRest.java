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
import modelo.Modalidade;
import utils.UtilRest;
import utils.Utilidades;

@Path("modalidade")
public class ModalidadeRest extends Rest {
	
	@Override
	public Class<?> getNameClass() {
		return Modalidade.class;
	}

	@Override
	public String getNomeTabela() {
		return "Modalidade";
	}

	@Override
	public Object getInstance() {
		return new Modalidade();
	}

	@POST
	@Path("/relacionar/{token}")
	@Consumes("application/*")
	public final Response relacionar(final String o, @PathParam("token") final String token)
			throws JsonParseException, JsonMappingException, IOException {
		if(!Authorize.verifica(token)) return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Modalidade classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Modalidade.class);
			Modalidade modalidade = (Modalidade) crud.em.find(Modalidade.class, classe.id);
			modalidade.areaConhecimento = classe.areaConhecimento;
			getValidacao(classe, Validacao.PUT);
			crud.begin().alterar(modalidade).commit();
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
		Modalidade objeto = ((Modalidade) o);

		final String contexto = val.toString() + " ->Objeto: Modalidade, Campo: ";
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {

			if (objeto.nome.isEmpty())
				throw new IllegalArgumentException(contexto + "nome Está vazio!");


			if (!Utilidades.jaExisteNoBanco(crud, objeto.id, getNomeTabela()) && val.equals(Validacao.PUT))
				throw new IllegalArgumentException(val.toString()
						+ " -> Objeto: AreaConhecimento, Objeto não existe no banco! -> " + getNomeTabela());
			
			
			boolean f = true;
			try {				
				final Modalidade objProf = (Modalidade) crud.query("from " + getNomeTabela() + " where status="
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
						val.toString() + " ->Objeto: Modalidade, Campo: Esse nome ja foi utilizado");
			}
		}
	}
}
