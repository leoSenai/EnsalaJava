package rest;

import java.lang.reflect.Field;

import javax.ws.rs.Path;
import utils.Utilidades;
import cfg.Rest;
import cfg.enums.StatusModelo;
import cfg.enums.Validacao;
import modelo.Professor;

@Path("professor")
public class ProfessorRest extends Rest {
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

	@Override
	public final void getValidacao(final Object o, final Validacao val)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Professor obj = ((Professor)o);
		final String contexto = val.toString() + " ->Objeto: Professor, Campo: " + obj.nome;
		if(obj.nome.isEmpty()) throw new IllegalArgumentException(contexto + " Está vazio!");
		if(val.equals(Validacao.POST)) iniciarDisponibilidade(o);

		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {
			for (Field valor : clazz.getDeclaredFields()) {
				final String nome = valor.getName();
				if (nome.equals("nome") || nome.equals("telefone"))
					if (Utilidades.StringVazio(clazz, nome, o))
						throw new IllegalArgumentException(contexto + " Está vazio!");
				if (nome.equals("telefone"))
					if (clazz.getField(nome).get(o).toString().length() < 14
							|| clazz.getField(nome).get(o).toString().length() > 15)
						throw new IllegalArgumentException(contexto + " tem menos que 14 ou mais que 15 caracteres!");
				if (nome.equals("cpf"))
					if (clazz.getField(nome).get(o).toString().length() < 11
							|| clazz.getField(nome).get(o).toString().length() > 11)
						throw new IllegalArgumentException(contexto + " tem menos que 11 ou mais que 11 caracteres!");

				if (nome.equals("senha")) {
					validaSenha(nome, o, val);
				}
			}
			if (!Utilidades.isEmail(clazz.getField("email").get(o).toString()))
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: email Não é um e-mail!");
			if (!Utilidades.validaPermissao(clazz, "permissao", o))
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: permissao Permissao Está vazio ou null!");

		}
		if (!Utilidades.jaExisteNoBanco(crud, clazz.getField("id").getLong(o), getNomeTabela())
				&& val.equals(Validacao.PUT))
			throw new IllegalArgumentException(
					val.toString() + " ->Objeto: Professor, Objeto já existe no banco! -> " + getNomeTabela());

		System.out.println(val.toString() + " -> Validacao feita com sucesso");
	}

	public void validaSenha(final String nome, final Object o, final Validacao val)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String senha = clazz.getField(nome).get(o).toString();
		if (val.equals(Validacao.POST)) {
			if (Utilidades.StringVazio(clazz, nome, o))
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: " + nome + " Está vazio!");
			else
				clazz.getField(nome).set(o, Utilidades.crip.md5.desorganizar(senha));
		} else if (val.equals(Validacao.PUT))
			if (Utilidades.StringVazio(clazz, nome, o))
				clazz.getField(nome).set(o, Utilidades.crip.md5.desorganizar(senha));
			else {
				final Object objSenha = crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and id = " + clazz.getField("id").get(o).toString()).selecionar();
				clazz.getField(nome).set(o, clazz.getField(nome).get(objSenha).toString());
			}
		System.out.println("depois ----> " + senha);
	}

	public void iniciarDisponibilidade(final Object o) {
		((Professor)o).novaDisponibilidade();
	}

}
