package rest;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cfg.Authorize;
import cfg.Rest;
import cfg.enums.Permissao;
import cfg.enums.RestResponse;
import cfg.enums.StatusModelo;

import modelo.Professor;
import utils.UtilRest;
import utils.Utilidades;

@Path("login")
public class LoginRest extends Rest {
	
	
	public LoginRest() {
		needCrud = false;		 
	}
	
	@POST
	@Path("/autenticacao")
	@Consumes("application/*")
	public final Response login(final String o)
			throws JsonParseException, JsonMappingException, IOException {

		Usuario usuario = new ObjectMapper().readValue(Utilidades.crip.base64.organizar(o), Usuario.class);

		usuario.senha = Utilidades.crip.md5.desorganizar(usuario.senha);
		try {	
			final Professor prof = (Professor) crud.query(
					"from Professor where status=" + StatusModelo.ATIVO.ordinal() + " and email='" + usuario.usuario+"' and senha='"+usuario.senha+"'")
					.selecionar();
			
			Long permissionTime = 0L;
			if(prof.permissao == Permissao.ADMINISTRADOR) {
				permissionTime = 480L;
			}
			if(prof.permissao == Permissao.PROFESSOR) {
				permissionTime = 90L;
			}
			
			String jwtToken = Authorize.geraToken(usuario.usuario, permissionTime);
		
			Resultado resultado = new Resultado();
			resultado.professor = prof;
			resultado.token = jwtToken;
			return UtilRest.buildResponse(resultado);
			
		}catch(NoResultException e) {
			System.out.println("LoginRest -> login ---> sem resultado na busca");
			res = UtilRest.buildErrorResponse(RestResponse.LOGIN_ERRO.toString());
		//	e.printStackTrace();
		}

		return res;
	}
}

class Resultado{
	public Professor professor;
	public String token;
}

class Usuario{
	public String usuario;
	public String senha;
}
