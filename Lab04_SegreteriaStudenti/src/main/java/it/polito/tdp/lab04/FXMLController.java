package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;
	private int matricolaDaRicordare;
	private Corso corsoDaRicordare;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> cbxCorsi;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;
    
    @FXML
    private Button btnCompila;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnCercaStudenteNelCorso;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    
    @FXML
    void doCompilazioneAutomatica(ActionEvent event) {

    	txtCognome.clear();
    	txtNome.clear();
    	txtResult.clear();
    	
    	int matricola;
    	
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("La matricola puo' contenere solo valori numerici");
    		throw(nfe);
    	}
    	
    	Studente s = model.getStudente(matricola);
    	
    	if(s==null)
    		txtResult.setText("Nessuno studente trovato con la matricola inserita");
    	else {
	    	txtCognome.setText(s.getCognome());
	    	txtNome.setText(s.getNome());
    	}
    }

    @FXML
    void doReset(ActionEvent event) {

    	txtCognome.clear();
    	txtNome.clear();
    	txtMatricola.clear();
    	txtResult.clear();
    	cbxCorsi.setValue(null);
    }

    @FXML
    void doRicercaCorsi(ActionEvent event) {
    	
    	txtResult.clear();

    	List<Corso> corsi = new ArrayList<>();
    	
    	int matricola;
    	
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("La matricola puo' contenere solo valori numerici");
    		throw(nfe);
    	}
    	
    	if(model.getStudente(matricola)==null)
    		txtResult.setText("Nessuno studente trovato con la matricola inserita");
    	else {
	    	corsi = model.getCorsiStudente(matricola);
	    	
	    	if(corsi.isEmpty())
	    		txtResult.setText("Nessun corso trovato per lo studente con matricola "+matricola);
	    	else {
		    	for(Corso c : corsi) {
		    		txtResult.appendText(c+"\n");
		    	}
	    	}
    	}
    }

    @FXML
    void doRicercaIscrittiCorso(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if(cbxCorsi.getValue()==null)
    		txtResult.setText("Per visualizzare gli iscritti di un corso, sceglierne prima uno dalla lista dei corsi");
    	
    	List<Studente> studenti = new ArrayList<>();
    	
    	studenti = model.getIscrittiCorso(cbxCorsi.getValue());
    	
    	if(studenti.isEmpty())
    		txtResult.setText("Nessuno studente trovato per il corso selezionato");
    	else {
	    	for(Studente s : studenti) {
	    		txtResult.appendText(s+"\n");
	    	}
    	}
    }

    @FXML
    void doRicercaStudenteNelCorso(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	int matricola;
    	    	
    	Corso c = cbxCorsi.getValue();
    	
    	if(btnCercaStudenteNelCorso.getText().compareTo("Cerca")==0) {
    		
    		try {
        		matricola = Integer.parseInt(txtMatricola.getText());
        	}catch(NumberFormatException nfe) {
        		txtResult.setText("La matricola puo' contenere solo valori numerici");
        		throw(nfe);
        	}

	    	if(model.getStudente(matricola)==null)
	    		txtResult.setText("Nessuno studente trovato con la matricola inserita");
	    	else if(c==null)
	    		txtResult.setText("Per visualizzare se lo studente e' iscritto al corso, scegliere prima un corso dalla lista dei corsi");
	    	else {
	    		if(model.cercaStudenteNelCorso(matricola, c)) {
	        		txtResult.setText("Lo studente selezionato e' iscritto al corso selezionato");
	        		btnCercaStudenteNelCorso.setText("Disiscrivi");
	        		matricolaDaRicordare = matricola;
	        		corsoDaRicordare = c;
	        	}
	        	else {
	        		txtResult.setText("Lo studente selezionato NON e' iscritto al corso selezionato");
	        		btnCercaStudenteNelCorso.setText("Iscrivi");
	        		matricolaDaRicordare = matricola;
	        		corsoDaRicordare = c;
	        	}
	    	}
    	}
    	else if(btnCercaStudenteNelCorso.getText().compareTo("Iscrivi")==0){
    		
    		try {
        		matricola = Integer.parseInt(txtMatricola.getText());
        	}catch(NumberFormatException nfe) {
        		txtResult.setText("La matricola puo' contenere solo valori numerici");
        		throw(nfe);
        	}
    		
    		if(c.equals(corsoDaRicordare) && matricola==matricolaDaRicordare) {
    			if(model.iscriviStudenteACorso(matricola, c)) {
    				txtResult.setText("Nuovo studente con matricola "+matricola+" iscritto con successo al corso '"+c.getNome()+"'");
    				btnCercaStudenteNelCorso.setText("Cerca");
    			}
    		}
    		else {
    			txtResult.setText("Non effetturare cambiamenti nei campi 'corso' e 'matricola', prima di iscrivere lo studente");
    			btnCercaStudenteNelCorso.setText("Cerca");
    		}
    	}
    	else {
    		
    		try {
        		matricola = Integer.parseInt(txtMatricola.getText());
        	}catch(NumberFormatException nfe) {
        		txtResult.setText("La matricola puo' contenere solo valori numerici");
        		throw(nfe);
        	}
    		
    		if(c.equals(corsoDaRicordare) && matricola==matricolaDaRicordare) {
    			if(model.disiscriviStudenteDalCorso(matricola, c)) {
    				txtResult.setText("Studente con matricola "+matricola+" rimosso con successo dal corso '"+c.getNome()+"'");
    				btnCercaStudenteNelCorso.setText("Cerca");
    			}
    		}
    		else {
    			txtResult.setText("Non effetturare cambiamenti nei campi 'corso' e 'matricola', prima di disiscrivere lo studente");
    			btnCercaStudenteNelCorso.setText("Cerca");
    		}
    	}
    }

    @FXML
    void initialize() {
        assert cbxCorsi != null : "fx:id=\"cbxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCompila != null : "fx:id=\"btnCompila\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaStudenteNelCorso != null : "fx:id=\"btnCercaStudenteNelCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cbxCorsi.getItems().add(null);
    	cbxCorsi.getItems().addAll(this.model.getTuttiICorsi());
    	cbxCorsi.setValue(null);
    }
}
