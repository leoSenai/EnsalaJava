package modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import cfg.enums.StatusModelo;
import utils.Banco;

@Entity
@Table(name = "UnidadeCurricular", indexes = { @Index(columnList = "id", name = "unidadecurricular_id") })
public final class UnidadeCurricular implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String nome;
	public String descricao;
	@Enumerated(EnumType.ORDINAL)
	public StatusModelo status;
	@JsonIgnore
	@ManyToMany(mappedBy = "unidadeCurricular")
	public List<AreaConhecimento> areaConhecimento;
	@JsonIgnore
	@ManyToMany(mappedBy = "unidadecurricular")
	public List<Professor> professor;

	/*
	 * Sincero amor a todos os níveis de gambiarra!
	 */
	@PostUpdate
	public void corrigeUnidadeCurricular() {
		if (this.status.equals(StatusModelo.INATIVO)) {
			Banco crud = new Banco();
			try {
				crud.begin();

				List<AreaConhecimento> areaLista = crud
						.query("from AreaConhecimento where status=" + StatusModelo.ATIVO.ordinal()).listar();

				List<Professor> profLista = crud.query("from Professor where status=" + StatusModelo.ATIVO.ordinal())
						.listar();

				// remove unidades com status inativos de todas as Areas Conhecimentos
				for (int a = 0; a < areaLista.size(); a++) {
					AreaConhecimento area = areaLista.get(a);
					boolean f = false;
					for (int u = 0; u < area.unidadeCurricular.size(); u++) {
						UnidadeCurricular unidadecurricular = area.unidadeCurricular.get(u);
						if (unidadecurricular.id == this.id) {
							area.unidadeCurricular.remove(u);
							f = true;
							break;
						}
					}
					if (f)
						crud.alterar(area);
				}

				// remove unidades com status inativos de todos os Professores
				for (int p = 0; p < profLista.size(); p++) {
					Professor prof = profLista.get(p);
					boolean f = false;
					for (int u = 0; u < prof.unidadecurricular.size(); u++) {
						UnidadeCurricular unidadecurricular = prof.unidadecurricular.get(u);
						if (unidadecurricular.id == this.id) {
							prof.unidadecurricular.remove(u);
							f = true;
							break;
						}
					}
					if (f)
						crud.alterar(prof);
				}

				crud.commit();

			} catch (Exception e) {
				crud.rollback();
				System.out.println("erro trigger modelo UnidadeCurricular");
			} finally {
				crud.close();
			}
		}
	}

	@Override
	public String toString() {
		return "UnidadeCurricular [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", status=" + status
				+ ", areaConhecimento=" + areaConhecimento + ", professor=" + professor + "]";
	}

}
