package utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cfg.ConfigBanco;

import java.util.HashSet;
import java.util.List;

public class Banco<T> {
	public EntityManager em;
	String table;
	Query query;
	String nomeThread;

	public Banco() {
		em = ConfigBanco.getEntityManager();
	}

	public Banco<T> inserir(T o) {
		em.persist(o);
		return this;
	}

	public Banco<T> alterar(T o) {
		em.merge(o);
		return this;
	}

	public Banco<T> removerToObject(T o) {
		em.remove(o);
		return this;
	}

	public Banco<T> remover(int id) {
		em.createNativeQuery("delete from " + table + " where id = :id").setParameter("id", id).executeUpdate();
		return this;
	}

	public List<T> listar() {
		return query.getResultList();
	}
	
	public HashSet<T> listarHashSet() {
		return new HashSet<T>(query.getResultList());
	}
	
	public T selecionar() {
		return (T) query.getSingleResult();
	}

	public Banco<T> query(String i) {
		query = em.createQuery(i);
		return this;
	}

	public Banco<T> setTable(String table) {
		this.table = table;
		return this;
	}

	public Banco<T> begin() {
		em.getTransaction().begin();
		return this;
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public Banco<T> close() {
		em.close();
		return this;
	}

	public Banco<T> commit() {
		em.getTransaction().commit();
		return this;
	}

}