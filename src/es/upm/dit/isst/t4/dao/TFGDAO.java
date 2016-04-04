package es.upm.dit.isst.t4.dao;

import java.util.List;

import es.upm.dit.isst.t4.model.TFG;

public interface TFGDAO {

	public TFG create(String autor, String titulo, String resumen,
			String tutor, String secretario, String fichero, int estado);
	public void delete(String autor);
	public TFG update(TFG tfg);
	public List<TFG> read();
	public List<TFG> readSameStateTFG(int estado);
	public List<TFG> readAutorTFG(String autor);
	public List<TFG> readTutorTFG(String tutor);
	public List<TFG> readSecretarioTFG(String secretario);



	
		
}
