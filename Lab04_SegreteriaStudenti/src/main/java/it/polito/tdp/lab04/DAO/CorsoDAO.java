package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	private StudenteDAO studenteDao = new StudenteDAO();
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				
				corsi.add(c);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato uno studente, ottengo i corsi di quello studente
	 */
	public List<Corso> getCorsiStudente(Studente studente) {
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM iscrizione AS i, corso AS c " + 
				"WHERE c.codins = i.codins AND i.matricola = ?";
		
		List<Corso> corsi = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				
				corsi.add(c);
				
			}
			
			conn.close();
			
		}catch(SQLException e) {
				throw new RuntimeException("Errore Db", e);
			}
		
		return corsi;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return studenteDao.getIscrittiCorso(corso.getCodins());
	}

	/*
	 * Data una matricola ed il codice insegnamento, cerca lo studente all'interno del corso.
	 */
	public boolean cercaStudenteNelCorso(Studente studente, Corso corso) {
		
		String sql = "SELECT * FROM iscrizione " + 
				"WHERE codins = ? AND matricola = ?";
		
		boolean iscritto = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				iscritto = true;
			}
			
			conn.close();
			
		}catch(SQLException e) {
				throw new RuntimeException("Errore Db", e);
			}
		
		return iscritto;
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {

		String sql = "INSERT INTO iscrizione (matricola, codins) " + 
				"VALUES (?, ?)";
		
		boolean iscritto = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());			
			
			int numRighe = st.executeUpdate();
			
			if(numRighe == 1) {
				System.out.println(numRighe);
				iscritto = true;
			}
			
			conn.close();
			
		}catch(SQLException e) {
				throw new RuntimeException("Errore Db", e);
		}
		
		return iscritto;
	}


	public boolean disiscriviStudenteDalCorso(Studente studente, Corso corso) {

		String sql = "DELETE FROM iscrizione " + 
				"WHERE matricola=? AND codins = ?";
		
		boolean disiscritto = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());			
			
			int numRighe = st.executeUpdate();
			
			if(numRighe == 1) {
				disiscritto = true;
			}
			
			conn.close();
			
		}catch(SQLException e) {
				throw new RuntimeException("Errore Db", e);
		}
		
		return disiscritto;
	}

}
