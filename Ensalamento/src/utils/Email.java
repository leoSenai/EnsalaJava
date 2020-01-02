package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import cfg.SessionEmail;
import cfg.enums.StatusEmail;
import modelo.Professor;

public final class Email {

	public final static Email email = new Email();
	private static Future<String> future;
	private static Callable<String> callable;
	private final static  ExecutorService pool = Executors.newFixedThreadPool(10);

	private Email() {
	}

	private final static Callable<String> dispararLista(final List<Professor> lista, final String Assunto, final String msg) {
		return new Callable<String>() {
			@Override
			final public String call() throws Exception {
				final SessionEmail sessao = SessionEmail.getInstance();
				for (int i = 0; i < lista.size(); i++) {
					Professor p = lista.get(i);
					System.out.println("Enviando E-mail do " + p.nome + "\n E-mail: " + p.email);
					if (Utilidades.isEmail(p.email))
						sessao.enviarEmail(Assunto, msg, p.email);
				}
				return null;
			}
		};
	}

	public final void enviar(List<Professor> lista, String Assunto, String msg)
			throws InterruptedException, ExecutionException {
		callable = dispararLista(lista, Assunto, msg);
		future = pool.submit(callable);
	}

	public final StatusEmail checkEmailsEnviando() throws InterruptedException, ExecutionException {
		StatusEmail resposta = null;
		if (future == null) {
			List<Professor> Lista = new ArrayList<Professor>();
			Lista.add(new Professor());
			enviar(Lista, "Preparando", "Preparando para enviar emails.");
			resposta = StatusEmail.ERRO;
		} else if (future.isDone())
			resposta = StatusEmail.ACABOU;
		else if (!future.isDone())
			resposta = StatusEmail.ANDAMENTO;

		return resposta;
	}
}
