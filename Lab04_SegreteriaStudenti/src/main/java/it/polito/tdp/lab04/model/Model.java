package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi(){
		return corsoDao.getTuttiICorsi();
	}
	
	public List<Studente> getIscrittiCorso(Corso c){
		return corsoDao.getStudentiIscrittiAlCorso(c);
	}
	
	public Studente getStudente(int matricola) {
		Studente studente = studenteDao.getStudente(matricola);
		
		if(studente!=null)
			return studente;
		else
			return null;
	}
	
	public List<Corso> getCorsiStudente(int matricola){
		Studente studente = this.getStudente(matricola);
		if(studente == null) {
			return null;
		}
		return corsoDao.getCorsiStudente(studente);
	}
	
	public boolean cercaStudenteNelCorso(int matricola, Corso corso) {
		Studente studente = this.getStudente(matricola);
		if(studente == null) {
			return false;
		}
		return corsoDao.cercaStudenteNelCorso(studente, corso);
	}

	public boolean iscriviStudenteACorso(int matricola, Corso c) {
		Studente studente = this.getStudente(matricola);
		return corsoDao.iscriviStudenteACorso(studente, c);
	}

	public boolean disiscriviStudenteDalCorso(int matricola, Corso c) {
		Studente studente = this.getStudente(matricola);
		return corsoDao.disiscriviStudenteDalCorso(studente, c);
	}
}
