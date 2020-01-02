package utils;

import java.io.StringWriter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.codehaus.jackson.map.ObjectMapper;

public final class UtilRest {
	
	/*
	 * Abaixo o m�todo respons�vel por enviar a resposta ao cliente sobre a
	 * transa��orealizada, inclus�o, consulta, edi��o ou exclus�o, realizadas com
	 * sucesso. repare que o m�todo em quest�o aguarda que seja repassado um
	 * conte�do que ser� referenciado por um objeto chamado result.
	 */
	public final static Response buildResponse(Object result) {
		/*
		 * Cria a inst�ncia da classe StringWriter para o objeto fw. isto que este
		 * objeto [e quem estar� referenciando o conte�do repassado como resposta para o
		 * lado cliente.
		 */
		StringWriter fw = new StringWriter();
		try {
			/*
			 * Cria a inst�ncia da classe ObjectMapper par ao objeto mapper.
			 */
			ObjectMapper mapper = new ObjectMapper();
			/*
			 * Acessa o m�todo writeValue, por meio do objeto mapper passando como parametro
			 * o objeto fw e o conte�do do objeto result, na realidade est� criando um
			 * mapeamento de dados onde o objeto fw � a chave do valor de um conte�do
			 * referenciado pelo objeto result. result pode conter a mensagem, "cadastro
			 * efetuado com sucesso", ou "Exclus�o efetuada com sucesso" ou outra qualquer
			 * dependendo da transa��o realizada.
			 */
			mapper.writeValue(fw, result);
			/*
			 * Monta o objeto de resposta com status 200(ok), junto com o objeto result
			 * convertido para JSON pelo objeto fw para o cliente no format String.
			 */
			return Response.ok(fw.toString()).build();
		} catch (Exception ex) {
			return buildErrorResponse(ex.getMessage());
		}
	}

	/*
	 * Abaixo o m�todo respons�vel por enviar a resposta ao cliente sobre a
	 * transa��o realizada, inclus�o, consulta, edi��o ou exclus�o ao cliente, n�o
	 * realizadas com sucesso, ou seja, que contenha algum erro. repare que o m�todo
	 * em quest�o aguarda que seja repassado um conte�do que ser� referenciado pelo
	 * por um objeto chamado rb.
	 */
	public final static Response buildErrorResponse(String str) {
		// Abaixo o objeto rb recebe o status do erro
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		/*
		 * define a entidade(objeto), que nesse caso � uma mensagem que ser� retornado
		 * para o cliente.
		 */
		rb = rb.entity(str);
		/*
		 * Define o tipo de retorno desta entidade (objeto), no caso � definido como
		 * texto simples.
		 */
		rb = rb.type("text/plain");
		/*
		 * Retorna o objeto de resposta com status 500(erro), junto com a String
		 * contendo a mensagem de erro.
		 */
		return rb.build();
	}
}
