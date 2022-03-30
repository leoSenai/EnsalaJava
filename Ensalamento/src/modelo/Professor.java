package modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import cfg.enums.Permissao;
import cfg.enums.StatusModelo;


@Entity
@Table(name = "Professor", indexes = { @Index(columnList = "id", name = "professor_id") })
public final class Professor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String matricula;
	public String tipo;
	public String nome;
	public String email;
	public String telefone;
	public String senha;
	@Enumerated(EnumType.STRING)
	public Permissao permissao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;

	@OneToOne(cascade = CascadeType.ALL)
	public Disponibilidade disponibilidade;

	@ManyToMany
	@JoinTable(name = "Professor_Modalidade", joinColumns = @JoinColumn(name = "professor_id"), inverseJoinColumns = @JoinColumn(name = "modalidade_id"), indexes = {
			@Index(name = "idx_professor_id", columnList = "professor_id"),
			@Index(name = "idx_modalidade_id", columnList = "modalidade_id") })
	public List<Modalidade> modalidade;
	@ManyToMany
	@JoinTable(name = "Professor_Areaconhecimento", joinColumns = @JoinColumn(name = "professor_id"), inverseJoinColumns = @JoinColumn(name = "areaconhecimento_id"), indexes = {
			@Index(name = "idx_professor_id", columnList = "professor_id"),
			@Index(name = "idx_areaconhecimento_id", columnList = "areaconhecimento_id") })
	public List<AreaConhecimento> areaconhecimento;
	@ManyToMany
	@JoinTable(name = "Professor_Unidadecurricular", joinColumns = @JoinColumn(name = "professor_id"), inverseJoinColumns = @JoinColumn(name = "unidadecurricular_id"), indexes = {
			@Index(name = "idx_professor_id", columnList = "professor_id"),
			@Index(name = "idx_unidadecurricular_id", columnList = "unidadecurricular_id") })
	public List<UnidadeCurricular> unidadecurricular;

	@Override
	public String toString() {
		return "Professor [id=" + id + ", matricula=" + matricula + ", tipo=" + tipo + ", nome=" + nome + ", email="
				+ email + ", telefone=" + telefone + ", senha=" + senha + ", permissao=" + permissao + ", status="
				+ status + ", disponibilidade=" + disponibilidade + ", modalidade=" + modalidade + ", areaconhecimento="
				+ areaconhecimento + ", unidadecurricular=" + unidadecurricular + "]";
	}

	public void novaDisponibilidade() {
		disponibilidade = new Disponibilidade();
		disponibilidade.setValoresIniciais();
	}

}
