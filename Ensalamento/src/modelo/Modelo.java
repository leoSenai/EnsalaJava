package modelo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cfg.enums.Periodos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public final class Modelo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	@ManyToOne(targetEntity = Professor.class)
	@JoinColumn(name = "cpfProfessor", referencedColumnName = "id", nullable = false)
	public long professor;
	@ManyToOne(targetEntity = Modalidade.class)
	@JoinColumn(name = "idModalidade", referencedColumnName = "id", nullable = false)
	public long modalidade;
	@ManyToOne(targetEntity = AreaConhecimento.class)
	@JoinColumn(name = "idAreaConhecimento", referencedColumnName = "id", nullable = false)
	public long areaConhecimento;
	@ManyToOne(targetEntity = UnidadeCurricular.class)
	@JoinColumn(name = "idUnidadeCurricular", referencedColumnName = "id", nullable = false)
	public long unidadeCurricular;
	
	@Enumerated(EnumType.STRING)
	public Periodos periodo;
	
	
}
