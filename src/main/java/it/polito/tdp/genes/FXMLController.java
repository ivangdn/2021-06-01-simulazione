/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interaction;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	model.creaGrafo();
    	txtResult.appendText("Grafo creato con "+model.nVertici()+" vertici e "+model.nArchi()+" archi");
    	
    	cmbGeni.getItems().clear();
    	cmbGeni.getItems().addAll(model.getVertici());
    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Genes g = cmbGeni.getValue();
    	if(g==null) {
    		txtResult.setText("Selezionare un gene");
    		return;
    	}
    	
    	List<Interaction> adiacenti = model.geniAdiacenti(g);
    	txtResult.appendText("Adiacenti a: "+g+"\n");
    	for(Interaction i : adiacenti) {
    		txtResult.appendText(i.getG2()+" "+i.getExpressionCorr()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Genes g = cmbGeni.getValue();
    	if(g==null) {
    		txtResult.setText("Selezionare un gene");
    		return;
    	}
    	
    	int N;
    	try {
    		N = Integer.parseInt(txtIng.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero di ingegneri valido");
    		return;
    	}
    	
    	Map<Genes, Integer> mapGeniIng = model.simula(N, g);
    	if(mapGeniIng==null) {
    		txtResult.appendText("ERRORE! Il gene selezionato è isolato");
    	} else {
    		for(Genes gene : mapGeniIng.keySet()) {
    			txtResult.appendText(gene+" "+mapGeniIng.get(gene)+"\n");
    		}
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
