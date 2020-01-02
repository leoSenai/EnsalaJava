package utils;

import java.util.Arrays;

import cfg.enums.Permissao;
import cfg.enums.StatusModelo;

public final class Utilidades {
	public final static Crip crip = new Crip();

	public final static boolean StringVazio(final Class<?> clazz, final String nome, final Object o)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		System.out.println(clazz.getField(nome).get(o).toString());
		return clazz.getField(nome).get(o).toString().isEmpty();
	}

	public final static boolean isEmail(String param)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String email = param;
		if (!email.contains("@"))
			return false;
		final String usuario = email.substring(0, email.indexOf("@"));
		final String dominio = email.substring(email.indexOf("@") + 1, email.length());
		return ((usuario.length() >= 1) && (dominio.length() >= 3) && (usuario.indexOf("@") == -1)
				&& (dominio.indexOf("@") == -1) && (usuario.indexOf(" ") == -1) && (dominio.indexOf(" ") == -1)
				&& (dominio.indexOf(".") != -1) && (dominio.indexOf(".") >= 1)
				&& (dominio.lastIndexOf(".") < dominio.length() - 1));

	}

	public final static boolean validaPermissao(final Class<?> clazz, final String nome, final Object o)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return !((Permissao) clazz.getField(nome).get(o)).toString().equals(Permissao.SELECIONE.toString())
				|| clazz.getField(nome).get(o) != null;
	}

	public final static boolean jaExisteNoBanco(final Banco<?> crud, final Long id, final String tabela) {
		try {
			crud.query("from " + tabela + " where status=" + StatusModelo.ATIVO.ordinal() + " and id = " + id)
					.selecionar();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
}
