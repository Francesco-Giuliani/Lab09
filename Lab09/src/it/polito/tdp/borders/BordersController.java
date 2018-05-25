/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import it.polito.tdp.borders.model.NoGraphException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	private Model model;

	 @FXML
	 private ComboBox<Country> cmbCountries;
	 
	 @FXML
	 private Button btnTrovaVicini;

	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {

		String annoStr = this.txtAnno.getText();
		
		try {
			int anno = Integer.parseInt(annoStr);
			this.model.createBorderGraphUpToYear(anno);
			
			String situazione = model.printSituation();
			
			int numeroComponentiConnesse = this.model.getNumberOfConnectedComponents();
			this.cmbCountries.getItems().setAll(this.model.getGraph().vertexSet());
			
			String compConn = model.stampaComponentiConnesse();
			
			this.txtResult.appendText("La situazione per l'anno " + anno + " è:\n"+situazione+"\nCi sono "+
										numeroComponentiConnesse+ " componenti connesse\n.");
					
			this.txtResult.appendText("Componenti connesse: "+compConn+".\n");
			
		}catch(Exception e) {
			this.txtResult.appendText("Input non valido. Fornisci un input valido: anno intero tra 1816 e 2016.\n");
			e.printStackTrace();
			return;
		}
		
		//txtResult.setText("Todo!");
	}
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	try {
    		if(this.model.getGraph() == null || this.model.getGraph().vertexSet().isEmpty() && this.model.getGraph().edgeSet().isEmpty())
    			throw new NoGraphException();
    		
    		Country country = this.cmbCountries.getValue();
    		if(country == null) 
    			throw new NoCountrySelectedException();
    		
    		Set<Country> reachables = this.model.findAllReachableCountries(country);
    		
    		this.txtResult.appendText(reachables.toString());
    		
    	}catch(NoGraphException nge) {
    		this.txtResult.appendText("Nessun dato presente nel grafo o grafo inesistente. Inserire un anno e creare il grafo prima.\n");
    		nge.printStackTrace();
    		return;
    	}catch(NoCountrySelectedException ncse) {
    		this.txtResult.appendText("Nessun Paese seleionao. Selezionarne uno dalla lista.\n");
    		ncse.printStackTrace();
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert cmbCountries != null : "fx:id=\"cmbCountries\" was not injected: check your FXML file 'Borders.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }
	public void setModel(Model model) {
		this.model = model;
	}
}
