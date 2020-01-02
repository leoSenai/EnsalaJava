package modelo;

import java.io.Serializable;
//import java.util.HashSet;
//import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.persistence.OneToOne;

import cfg.enums.Permissao;
import cfg.enums.StatusModelo;

@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public final class Professor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public long cpf;
	public String nome;
	public String email;
	public String telefone;
	public String senha;
	@Enumerated(EnumType.STRING)
	public Permissao permissao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;

	@OneToMany(mappedBy = "professor")
	public Set<Modelo> modelo;
	@OneToOne(cascade=CascadeType.ALL)
	public Disponibilidade disponibilidade;
	@Override 
	public String toString() {
		return "Professor [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone
				+ ", senha=" + senha + ", permissao=" + permissao + ", status=" + status + ", modelo=" + modelo
				+ ", disponibilidade=" +disponibilidade + "]";
	}

	
	public void novaDisponibilidade() {
		disponibilidade = new Disponibilidade();
		disponibilidade.setValoresIniciais();
		
	}


}
