package modelo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import cfg.enums.StatusModelo;

@Entity
public final class UnidadeCurricular implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String nome;
	public String descricao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;
	@OneToMany(mappedBy = "unidadeCurricular")
	public Set<Modelo> modelo;

	@Override
	public String toString() {
		return "UnidadeCurricular [id=" + id + ", nome=" + nome 
				+ ", descricao=" + descricao + ", status=" + status + ", modelo=" + modelo + "]";
	}

}
