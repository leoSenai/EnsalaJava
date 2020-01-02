package modelo;

import java.io.Serializable; 
import java.util.Set;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import cfg.enums.StatusModelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class AreaConhecimento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String nome;
	public String descricao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;
	@OneToMany(mappedBy = "areaConhecimento")
	public Set<Modelo> modelo;

	@Override
	public String toString() {
		return "AreaConhecimento [id=" + id + ", descricao=" + descricao + ", nome="
				+ nome + ", status=" + status + ", modelo=" + modelo + "]";
	}

}
