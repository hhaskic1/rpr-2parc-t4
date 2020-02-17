package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;
    private Drzava drzava;
    private ObservableList<Grad> listGradovi;

    public RadioButton tglDrzava=new RadioButton();
    public RadioButton tglKraljevina=new RadioButton();
    public RadioButton tglRepublika=new RadioButton();

    public ToggleGroup toggleGroup=new ToggleGroup();

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.drzava = drzava;
        listGradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {

        tglRepublika.setToggleGroup(toggleGroup);
        tglDrzava.setToggleGroup(toggleGroup);
        tglKraljevina.setToggleGroup(toggleGroup);

        tglDrzava.setSelected(true);
        tglKraljevina.setSelected(false);
        tglRepublika.setSelected(false);


        choiceGrad.setItems(listGradovi);
        if (drzava != null) {
            fieldNaziv.setText(drzava.getNaziv());
            //choiceGrad.getSelectionModel().select(drzava.getGlavniGrad());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            if(drzava instanceof Drzava){
                tglDrzava.setSelected(true);
                tglRepublika.setSelected(false);
                tglKraljevina.setSelected(false);
            }else if(drzava instanceof Kraljevina){
                tglDrzava.setSelected(false);
                tglRepublika.setSelected(false);
                tglKraljevina.setSelected(true);
            }else{
                tglDrzava.setSelected(false);
                tglRepublika.setSelected(true);
                tglKraljevina.setSelected(false);
            }

            for(int i=0; i < listGradovi.size(); i++)
                if (listGradovi.get(i).getId() == drzava.getGlavniGrad().getId())
                    choiceGrad.getSelectionModel().select(i);
        } else {
            choiceGrad.getSelectionModel().selectFirst();
            tglDrzava.setSelected(true);
            tglKraljevina.setSelected(false);
            tglRepublika.setSelected(false);
        }
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        int oldId=0;
        if(drzava!=null) oldId=drzava.getId();

        if(tglDrzava.isSelected()){
            drzava=new Drzava();
        }else if(tglRepublika.isSelected()){
            drzava=new Republika();
        }else {
            drzava=new Kraljevina();
        }
        if(drzava!=null) {
            drzava.setId(oldId);
        }
        drzava.setNaziv(fieldNaziv.getText());
        drzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
