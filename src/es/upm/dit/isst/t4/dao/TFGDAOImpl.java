package es.upm.dit.isst.t4.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.t4.model.TFG;

public class TFGDAOImpl implements TFGDAO {
	private static TFGDAOImpl instance;
	private TFGDAOImpl(){
		
	}

	public static TFGDAOImpl getInstance(){
		if(instance == null)
			instance = new TFGDAOImpl();
		return instance;
	}

	@Override
	public TFG create(String autor, String titulo, String resumen, String tutor, String secretario, String fichero,
			int estado) {
		TFG tfg = null;
		EntityManager em = EMFService.get().createEntityManager();
		tfg = new TFG(autor,titulo,resumen,tutor,secretario,fichero, estado);
		em.persist(tfg);
		em.close();
		return tfg;
	}

	@Override
	public void delete(String autor) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			TFG tfgToDelete = em.find(TFG.class, autor);
 			em.remove(tfgToDelete);
		}
		finally{
			em.close();
		}
	}

	@Override
	public TFG update(TFG tfg) {
		EntityManager em = EMFService.get().createEntityManager();
		TFG updatedTfg = em.merge(tfg);
		em.close();
		return updatedTfg;
	}

	@Override
	public List<TFG> read() {
		EntityManager em = EMFService.get().createEntityManager();
		Query query= em.createQuery("select m from TFG m");
		List<TFG> tfgList = query.getResultList();
		em.close();
		return tfgList;
	}

	@Override
	public List<TFG> readSameStateTFG(int estado) {
		EntityManager em = EMFService.get().createEntityManager();
		List<TFG> tfgList = Collections.EMPTY_LIST;
		try{
			Query query= em.createQuery("select m from TFG m where m.estado = :estado");
			query.setParameter("estado", estado);
			System.out.println("query = " + query);
			tfgList = query.getResultList();
		}
		finally{
			em.close();
			return tfgList;
		}
	}

	@Override
	public List<TFG> readAutorTFG(String autor) {
		EntityManager em = EMFService.get().createEntityManager();
		List<TFG> tfgList = Collections.EMPTY_LIST;
		try{
			Query query= em.createQuery("select m from TFG m where m.autor =:autor");
			query.setParameter("autor", autor);
			tfgList = query.getResultList();
		}
		finally{
			em.close();
			return tfgList;
		}
	}

	@Override
	public List<TFG> readTutorTFG(String tutor) {
		EntityManager em = EMFService.get().createEntityManager();
		List<TFG> tfgList = Collections.EMPTY_LIST;
		try{
			Query query= em.createQuery("select m from TFG m where m.tutor =:tutor");
			query.setParameter("tutor", tutor);
			tfgList = query.getResultList();
		}
		finally{
			em.close();
			return tfgList;
		}
	}

	@Override
	public List<TFG> readSecretarioTFG(String secretario) {
		EntityManager em = EMFService.get().createEntityManager();
		List<TFG> tfgList = Collections.EMPTY_LIST;
		try{
			Query query= em.createQuery("select m from TFG m where m.secretario =secretario");
			query.setParameter("secretario", secretario);
			tfgList = query.getResultList();
		}
		finally{
			em.close();
			return tfgList;
		}
	}
	
	

}
