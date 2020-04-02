package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public List<Studente> getIscrittiCorso(String codins) {
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " + 
				"FROM studente AS s , iscrizione AS i " + 
				"WHERE s.matricola = i.matricola and i.codins = ? " + 
				"GROUP BY s.matricola, s.cognome, s.nome, s.CDS";
		
		List<Studente> studenti = new ArrayList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s);
			}
			
			conn.close();
			
		}catch(SQLException e){
			throw new RuntimeException("Errore Db", e);
		}
		
		return studenti;
	}
	
	public Studente getStudente(int matricola) {
		
		String sql = "SELECT * FROM studente WHERE matricola = ?";
		
		Studente studente = null;
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			}
			
			conn.close();
			
		}catch(SQLException e){
			throw new RuntimeException("Errore Db", e);
		}
		
		return studente;
	}
}
