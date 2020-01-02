package modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import cfg.enums.StatusModelo;

@Entity
public final class Modalidade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String nome;
	public String descricao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;//	@JsonIgnore
	@OneToMany(mappedBy = "modalidade")
	public Set<Modelo> modelo;

	@Override
	public String toString() {
		return "Modalidade [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", status=" + status + "]";
	}

}
