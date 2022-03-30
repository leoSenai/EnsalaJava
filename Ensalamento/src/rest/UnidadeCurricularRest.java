package rest;


import javax.ws.rs.Path;

import cfg.Rest;
import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import modelo.UnidadeCurricular;
import utils.Utilidades;

@Path("unidadeCurricular")
public class UnidadeCurricularRest extends Rest {

	@Override
	public Class<?> getNameClass() {
		return UnidadeCurricular.class;
	}

	@Override
	public String getNomeTabela() {
		return "UnidadeCurricular";
	}

	@Override
	public Object getInstance() {
		return new UnidadeCurricular();
	}

	@Override
	public final void getValidacao(final Object o, final Validacao val) throws Exception {
		// TODO Auto-generated method stub
		UnidadeCurricular objeto = ((UnidadeCurricular) o);

		final String contexto = val.toString() + " ->Objeto: UnidadeCurricular, Campo: ";
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {

			if (objeto.nome.isEmpty())
				throw new IllegalArgumentException(contexto + "nome Está vazio!");

			if (!Utilidades.jaExisteNoBanco(crud, objeto.id, getNomeTabela()) && val.equals(Validacao.PUT))
				throw new IllegalArgumentException(val.toString()
						+ " -> Objeto: AreaConhecimento, Objeto não existe no banco! -> " + getNomeTabela());
			
			boolean f = true;
			try {				
				final UnidadeCurricular objProf = (UnidadeCurricular) crud.query("from " + getNomeTabela() + " where status="
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
						val.toString() + " ->Objeto: UnidadeCurricular, Campo: Esse nome ja foi utilizado");
			}
		}
	}
}
