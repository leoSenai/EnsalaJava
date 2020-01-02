package cfg;
import cfg.enums.Validacao;
public interface AbstractMethodsRest {
	
	abstract Class<?> getNameClass();
	abstract String getNomeTabela(); 
	abstract Object getInstance();
	abstract void getValidacao(final Object o, final Validacao val) throws Exception;
}
