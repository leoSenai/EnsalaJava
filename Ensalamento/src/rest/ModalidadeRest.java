package rest;

import java.lang.reflect.Field;

import javax.ws.rs.Path;

import cfg.Rest;
import cfg.enums.Validacao;
import modelo.Modalidade;
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

	@Override
	public final void getValidacao(final Object o, final Validacao val) throws Exception {
		// TODO Auto-generated method stub
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {
			for (Field valor : clazz.getDeclaredFields()) {
				final String nome = valor.getName();
				final String contexto = val.toString() + " ->Objeto: Professor, Campo: " + nome;
				if (nome.equals("nome") || nome.equals("descricao"))
					if (Utilidades.StringVazio(clazz, nome, o)) {
						throw new IllegalArgumentException(contexto + "Está vazio!");
					}
			}
			if (!Utilidades.jaExisteNoBanco(crud, clazz.getField("id").getLong(o), getNomeTabela())
					&& val.equals(Validacao.PUT))
				throw new IllegalArgumentException(
						val.toString() + " -> Objeto: Modalidade, Objeto não existe no banco -> " + getNomeTabela());
		}
		System.out.println(val.toString() + " -> Validacao feita com sucesso");
	}
}
