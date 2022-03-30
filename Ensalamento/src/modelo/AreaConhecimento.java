package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PostUpdate;

import org.codehaus.jackson.annotate.JsonIgnore;

import cfg.enums.Permissao;
import cfg.enums.StatusModelo;
import utils.Banco;

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
	@ManyToMany
	public List<UnidadeCurricular> unidadeCurricular;
	@JsonIgnore
	@ManyToMany(mappedBy = "areaconhecimento")
	public List<Professor> professor;

	@PostUpdate
	public void corrigeProfessor() {
		if (this.status.equals(StatusModelo.INATIVO)) {
			Banco crud = new Banco();
			try {
				crud.begin();

				List<Professor> profLista = crud.query("from Professor where permissao=\'"
						+ Permissao.PROFESSOR.toString() + "\' and status=" + StatusModelo.ATIVO.ordinal()).listar();

				List<AreaConhecimento> areaLista = crud
						.query("from AreaConhecimento where status=" + StatusModelo.ATIVO.ordinal()).listar();

				// remove areas com status inativos de todos os Professores
				for (int p = 0; p < profLista.size(); p++) {
					Professor prof = profLista.get(p);
					boolean f = false;
					for (int u = 0; u < prof.areaconhecimento.size(); u++) {
						AreaConhecimento areaconhecimento = prof.areaconhecimento.get(u);
						if (areaconhecimento.id == this.id) {
							prof.areaconhecimento.remove(u);
							f = true;
							break;
						}
					}
					if (f)
						crud.alterar(prof);

					// remove unidades com status inativos de todos os professores:
					// relacionamento professor x areaconhecimento
					boolean controle = false;
					List<MemoriaId> id = new ArrayList();

					for (int u = 0; u < prof.unidadecurricular.size(); u++) {
						UnidadeCurricular unidade = prof.unidadecurricular.get(u);

						f = false;
						controle = false;
						MemoriaId m = new MemoriaId();
						m.id = unidade.id;
						for (int a = 0; a < areaLista.size(); a++) {
							AreaConhecimento area = areaLista.get(a);

							for (int au = 0; au < area.unidadeCurricular.size(); au++) {
								UnidadeCurricular areaUni = area.unidadeCurricular.get(au);

								if (unidade.id == areaUni.id) {
									if (controle) {
										f = false;
										for (int i = 0; i < id.size(); i++) {
											MemoriaId memoria = id.get(i);
											if (memoria.id == m.id)
												id.remove(i);
										}
										break;
									}
									if (!controle) {
										id.add(m);
										f = true;
										controle = true;
									}
								}
							}

						}
						if (f && controle) {
							for (int i = 0; i < id.size(); i++) {
								MemoriaId memoria = id.get(i);
								for (int uc = 0; uc < prof.unidadecurricular.size(); uc++) {
									UnidadeCurricular atualiza = prof.unidadecurricular.get(uc);
									if (memoria.id == atualiza.id)
										prof.unidadecurricular.remove(uc);
								}
							}
							crud.alterar(prof);
						}
					}

				}

				crud.commit();
			} catch (Exception e) {
				crud.rollback();
				e.printStackTrace();
				System.out.println("erro trigger modelo AreaConhecimento");
			} finally {
				crud.close();
			}
		}
	}

	class MemoriaId {
		public long id;

		@Override
		public String toString() {
			return "MemoriaId [id=" + id + "]";
		}
	}

	@Override
	public String toString() {
		return "AreaConhecimento [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", status=" + status
				+ ", unidadeCurricular=" + unidadeCurricular + ", professor=" + professor + "]";
	}

}
