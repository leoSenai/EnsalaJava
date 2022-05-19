package modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PostUpdate;

import org.codehaus.jackson.annotate.JsonIgnore;

import cfg.enums.StatusModelo;
import utils.Banco;

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
	public StatusModelo status;
	@ManyToMany
	public List<AreaConhecimento> areaConhecimento;
	@JsonIgnore
	@ManyToMany(mappedBy = "modalidade")
	public List<Professor> professor;

	@PostUpdate
	public void corrigeProfessor() {
		if (this.status.equals(StatusModelo.INATIVO)) {
			Banco crud = new Banco();
			try {
				crud.begin();

				List<Professor> profLista = crud.query("from Professor where status=" + StatusModelo.ATIVO.ordinal())
						.listar();

				// remove areas com status inativos de todos os Professores
				for (int p = 0; p < profLista.size(); p++) {
					Professor prof = profLista.get(p);
					boolean f = false;
					for (int u = 0; u < prof.modalidade.size(); u++) {
						Modalidade modalidade = prof.modalidade.get(u);
						if (modalidade.id == this.id) {
							prof.modalidade.remove(u);
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
				System.out.println("erro trigger modelo modalidade");
			} finally {
				crud.close();
			}
		}
	}

	@Override
	public String toString() {
		return "Modalidade [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", status=" + status + ", areaConhecimento=" + areaConhecimento + "]";
	}

}
