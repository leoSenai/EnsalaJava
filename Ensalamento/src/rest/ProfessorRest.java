package rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import utils.Email;
import utils.UtilRest;
import utils.Utilidades;
import cfg.Authorize;
import cfg.Rest;
import cfg.Server;
import cfg.enums.Permissao;
import cfg.enums.RestResponse;
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
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT))
			throw new IllegalArgumentException(val.toString()
					+ " ->Objeto: Professor utiliza outro método para validação -> getValidacaoEstatica");
	}

	@POST
	@Path("/alterarSenha")
	@Consumes("application/*")
	public final Response alterarSenha(final String o) throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.ALTERAR_ERROR;
		try {
			final AlterarMinhaSenha classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o),
					AlterarMinhaSenha.class);

			if (!Authorize.verifica(classe.token))
				return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

			String email = Authorize.getSubject(classe.token);
			System.out.println(email);
			if (Utilidades.isEmail(email)) {

				final Professor objProf = (Professor) crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and email='" + email + "'").selecionar();

				objProf.senha = classe.senha;
				validaSenha(objProf, Validacao.PUT);
				crud.begin().alterar(objProf).commit();
				res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
			} else {
				throw new IllegalArgumentException(
						" ->Objeto: Professor -> Este token não possui um e-mail como subject");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ProfessorRest -> alterarSenha ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@POST
	@Path("/inserirProfessor/{token}/{id_usuario}")
	@Consumes("application/*")
	public final Response inserirProfessor(final String o, @PathParam("token") final String token,
			@PathParam("id_usuario") final String id_usuario)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.INSERIR_ERROR;
		if (!token.equals("telaInicial") && !id_usuario.equals("professor"))
			if (!Authorize.verifica(token))
				return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());

			// seta permissão no cadastro da tela inicial
			if (token.equals("telaInicial")) {
				Professor objeto = ((Professor) classe);
				objeto.permissao = Permissao.PROFESSOR;
			}
			ObjectMapper objectMapper = new ObjectMapper();

			setStatus(classe, StatusModelo.ATIVO);
			getValidacaoEstatica(classe, Validacao.POST, id_usuario);

			String jwtToken = Authorize.geraToken(objectMapper.writeValueAsString(classe), 1440L);

			enviarEmailCadastro(jwtToken);
			res = UtilRest.buildResponse(jwtToken);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ProfessorRest -> inserirProfessor ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@GET
	@Path("/enviarEmailCadastro/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response enviarEmailCadastro(@PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			String objJson = Authorize.getSubject(token);
			final Professor classe = (Professor) new ObjectMapper().readValue(objJson, getNameClass());
			StringBuilder corpoEmail = new StringBuilder();
			String path = Server.URL_CLIENT_DEFAULT + "finalizarCadastro/" + token;
			corpoEmail.append("É necessário que valide seu cadastro por este link.").append("<br/>")
					.append("O link contém o token de validação usado para finalizar seu cadastro.")
					.append("Caso não complete esse processo, você não terá acesso ao nosso site.").append("<br/>")
					.append("<br/>").append("<a href=\"" + path + "\" target=\"_blank\">" + path + "</a>")
					.append("<br/>").append("<br/>")
					.append("Esse link tem a duração de apenas 1 dia, após passar esse tempo, ele não terá mais validade, será necessário refazer seu cadastro e habilitar um novo token.");

			Email.email.enviar(classe.email, "Confirmação de e-mail", corpoEmail.toString());
			res = UtilRest.buildResponse(RestResponse.EMAIL_ENVIADO_SUCESSO.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res = UtilRest.buildErrorResponse(RestResponse.ERRO_ENVIAR_EMAIL.toString());
			System.out.println("GerenciarEmailsRest -> enviarEmail ---> Erro na hora enviar Email");
		}
		return res;
	}

	@GET
	@Path("/finalizarCadastro/{token}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public final Response finalizarCadastro(@PathParam("token") final String token) {
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			String objJson = Authorize.getSubject(token);
			final Professor classe = (Professor) new ObjectMapper().readValue(objJson, getNameClass());
			boolean f = false;
			try {
				crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal()
						+ " and email = '" + classe.email + "' and permissao = '" + classe.permissao.toString() + "'")
						.selecionar();
				res = UtilRest.buildErrorResponse(RestResponse.USUARIO_JA_CADASTRADO.toString());
			} catch (Exception e) {
				f = true;
			}
			if (f) {
				crud.begin().inserir(classe).commit();
				res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = UtilRest.buildErrorResponse(RestResponse.INSERIR_ERROR.toString());
			System.out.println("ProfessorRest -> finalizarCadastro ---> Erro na hora de finalizar cadastro.");
		}
		return res;
	}

	@POST
	@Path("/validarCadastro/{token}/{id_usuario}")
	@Consumes("application/*")
	public final Response validarCadastro(final String o, @PathParam("token") final String token,
			@PathParam("id_usuario") final String id_usuario)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.INSERIR_ERROR;
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());
		try {
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());

			// seta permissão no cadastro da tela inicial
			if (token.equals("telaInicial")) {
				Professor objeto = ((Professor) classe);
				objeto.permissao = Permissao.PROFESSOR;
			}

			setStatus(classe, StatusModelo.ATIVO);
			getValidacaoEstatica(classe, Validacao.POST, id_usuario);
			crud.begin().inserir(classe).commit();
			res = UtilRest.buildResponse(RestResponse.INSERIR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ProfessorRest -> inserirProfessor ---> Erro na hora de inserir.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}

		return res;
	}

	@PUT
	@Path("/alterarProfessor/{token}/{id_usuario}")
	@Consumes("application/*")
	public final Response alterarProfessor(final String o, @PathParam("token") final String token,
			@PathParam("id_usuario") final String id_usuario)
			throws JsonParseException, JsonMappingException, IOException {
		erro = RestResponse.ALTERAR_ERROR;
		if (!Authorize.verifica(token))
			return UtilRest.buildErrorResponse(RestResponse.SEM_AUTORIZACAO.toString());

		try {
			final Object classe = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), getNameClass());
			setStatus(classe, StatusModelo.ATIVO);
			getValidacaoEstatica(classe, Validacao.PUT, id_usuario);
			crud.begin().alterar(classe).commit();

			res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ProfessorRest -> alterarProfessor ---> Erro na hora de editar.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	@GET
	@Path("/reativarProfessor/{i}/{token}/{id_usuario}")
	@Consumes("application/*")
	public final Response reativarProfessor(@PathParam("i") final int i, @PathParam("token") final String token,
			@PathParam("id_usuario") final String id_usuario)
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

			getValidacaoEstatica(o, Validacao.OUTRO, id_usuario);

			boolean f = false;
			try {
				Professor classe = ((Professor) o);
				crud.query("from " + getNomeTabela() + " where status=" + StatusModelo.ATIVO.ordinal()
						+ " and email = '" + classe.email + "'").selecionar();
				res = UtilRest.buildErrorResponse(RestResponse.IMPOSSIVEL_REATIVAR.toString());
			} catch (Exception e) {
				f = true;
			}
			if (f) {
				crud.begin().alterar(o).commit();
				res = UtilRest.buildResponse(RestResponse.ALTERAR.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Rest -> alterar ---> Erro na hora de editar.");
			res = UtilRest.buildErrorResponse(erro.toString());
		} finally {
			crud.close();
		}
		return res;
	}

	public final void getValidacaoEstatica(final Object o, final Validacao val, final String id_usuario)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Professor objeto = ((Professor) o);
		final String contexto = val.toString() + " ->Objeto: Professor, Campo: " + objeto.nome;
		if (val.equals(Validacao.POST) || val.equals(Validacao.PUT)) {
			objeto.novaDisponibilidade();
			if (objeto.nome.isEmpty())
				throw new IllegalArgumentException(contexto + "nome Está vazio!");
			if (!objeto.telefone.isEmpty())
				if (objeto.telefone.length() < 14 || objeto.telefone.length() > 15)
					throw new IllegalArgumentException(
							contexto + "telefone tem menos que 14 ou mais que 15 caracteres!");

			if (objeto.matricula.isEmpty() || objeto.matricula.equals("SELECIONE")) {
				throw new IllegalArgumentException(contexto + "matricula Está vazio!");
			} else {
				boolean f = true;
				try {
					final Professor objProf = (Professor) crud.query("from " + getNomeTabela() + " where status="
							+ StatusModelo.ATIVO.ordinal() + " and matricula='" + objeto.matricula + "'").selecionar();

					if (val.equals(Validacao.PUT)
							&& (objProf.matricula.equals(objeto.matricula) && objProf.id == objeto.id)) {
						f = false;
					}
				} catch (Exception e) {
					f = false;
				}

				if (f) {
					erro = RestResponse.MATRICULA_USO_ERROR;
					throw new IllegalArgumentException(
							val.toString() + " ->Objeto: Professor, Campo: Esta matrícula ja foi utilizada");
				}

			}
			if (objeto.tipo.isEmpty())
				throw new IllegalArgumentException(contexto + "tipo Está vazio!");

			validaSenha(objeto, val);
			if (!Utilidades.isEmail(objeto.email))
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: email Não é um e-mail!");
			if (!Utilidades.validaPermissao(clazz, "permissao", o))
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: permissao Permissao Está vazio ou null!");

		}
		if (val.equals(Validacao.POST)) {
			boolean f = true;
			try {
				final Professor objProf = (Professor) crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and email='" + objeto.email + "' group by email")
						.selecionar();

			} catch (Exception e) {
				f = false;
			}
			if (f) {
				erro = RestResponse.EMAIL_EM_USO_ERROR;
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: Esse email ja foi utilizado");
			}
		}
		if (val.equals(Validacao.PUT)) {

			boolean f = false;
			try {
				final Professor objProf = (Professor) crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and id = " + objeto.id).selecionar();

				if (!objProf.email.equals(objeto.email)) {
					f = true;
				}
			} catch (Exception e) {
				f = true;
			}
			if (f) {
				erro = RestResponse.EMAIL_ERRADO;
				throw new IllegalArgumentException(
						val.toString() + " ->Objeto: Professor, Campo: Esse email ja foi utilizado");
			}

			if (Utilidades.validaPermissao(clazz, "permissao", o)) {
				if (!id_usuario.equals("professor")) {
					final Professor objProf = (Professor) crud.query("from " + getNomeTabela() + " where status="
							+ StatusModelo.ATIVO.ordinal() + " and id = " + id_usuario).selecionar();
					if (objProf.permissao == Permissao.PROFESSOR) {
						if (objeto.permissao != objProf.permissao) {
							throw new IllegalArgumentException(val.toString()
									+ " ->Objeto: Professor, Campo: permissao O professor não pode alterar sua permissão");
						}
					}
				}
			}
		}

	}

	public void validaSenha(final Professor objeto, final Validacao val)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (val.equals(Validacao.POST)) {
			if (objeto.senha.isEmpty())
				throw new IllegalArgumentException(val.toString() + " ->Objeto: Professor, Campo: senha Está vazio!");
			else
				objeto.senha = Utilidades.crip.md5.desorganizar(objeto.senha);
		} else if (val.equals(Validacao.PUT))
			if (objeto.senha.isEmpty()) {
				final Professor objSenha = (Professor) crud.query("from " + getNomeTabela() + " where status="
						+ StatusModelo.ATIVO.ordinal() + " and id = " + objeto.id).selecionar();
				objeto.senha = objSenha.senha;
			} else {
				objeto.senha = Utilidades.crip.md5.desorganizar(objeto.senha);
			}
	}

}

class AlterarMinhaSenha {
	public String senha;
	public String token;
}
