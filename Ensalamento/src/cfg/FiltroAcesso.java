package cfg;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Filter;

public class FiltroAcesso implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String context = request.getServletContext().getContextPath();

		try {
			/*
			 * O m�todo getSession � responsavel por pegar a sess�o ativa. Aqui
			 * foi necessario fazer um casting pois o objeto request � do tipo
			 * ServletRequest e n�o HttpServletRequest como no servlet onde voc�
			 * utiliza o m�todo sem o uso do casting.
			 */
			HttpSession session = ((HttpServletRequest) request).getSession();
			String usuario = null;
			if (session != null) {
				usuario = (String) session.getAttribute("login");
			}
 
		
			
			if (usuario == null) {
				
				System.out.println("filtrado");

					/*
					 * Aqui esta sendo setado um atributo na sess�o para que
					 * depois possamos exibir uma mensagem ao usu�rio.
					 */
					session.setAttribute("msg",
							"Você não está logado no sistema!");
					/*
					 * Utilizamos o m�todo sendRedirect que altera a URL do
					 * navegador para posicionar o usu�rio na tela do login, que
					 * neste caso � a p�gina index.html Note que n�o precisamos
					 * utilizar o recurso "../../" para informar o caminho da
					 * p�gina index.html, a vari�vel do contexto j� posiciona no
					 * inicio da URL.
					 */
//					((HttpServletResponse) response).sendRedirect(context
//							+ "/");

				
			} else {
				/*
				 * Caso exista um usu�rio valido (diferente de nulo) envia a
				 * requisi��o para a pagina que se deseja acessar, ou seja,
				 * permite o acesso, deixa passa.
				 */
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}