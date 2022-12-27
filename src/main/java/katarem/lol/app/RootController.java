package katarem.lol.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import katarem.lol.objs.Divisiones;
import katarem.lol.objs.Elos;

public class RootController implements Initializable{

    @FXML private GridPane root;

    @FXML private ChoiceBox<Elos> eloActual, eloDeseado;
    
    @FXML private ChoiceBox<Divisiones> divisionActual, divisionDeseada;

    @FXML private Button boton, themeButton;

    @FXML private TextField lpsGame, lpActuales;

    @FXML private Label winsLabel, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7;

    @FXML private Text lbl8;

    @FXML private Spinner<String> langSelector;

    @FXML private HBox cajita;

    private boolean isDark = true;

    private ImageView darkIcon, lightIcon;

    public RootController(){
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/interfaz.fxml"));
            l.setController(this);
            l.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Añadimos opciones a cada uno de los choiceBox
        //elos:
        eloActual.getItems().addAll(Elos.values());
        eloDeseado.getItems().addAll(Elos.values());
        eloActual.getItems().remove(Elos.CHALLENGER);

        //divisiones:
        divisionActual.getItems().addAll(Divisiones.values());
        divisionDeseada.getItems().addAll(Divisiones.values());
        
        eloDeseado.setOnAction(e -> updateDeseado());
        eloActual.setOnAction(e -> updateActual());

        lpsGame.setOnAction(e -> checkAll());
        lpActuales.setOnAction(e -> checkAll());

        darkIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/dark-icon.png")));
        darkIcon.setFitHeight(32);
        darkIcon.setFitWidth(32);

        lightIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgs/light-icon.png")));
        lightIcon.setFitHeight(32);
        lightIcon.setFitWidth(32);

        themeButton.setGraphic(darkIcon);

        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(getClass().getResourceAsStream("/lang/langs.props"), Charset.forName("UTF8")));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ObservableList<String> langs = FXCollections.observableArrayList();
        for (String string : p.stringPropertyNames()) {
            langs.add(string);
        }
        SpinnerValueFactory<String> sv = new SpinnerValueFactory.ListSpinnerValueFactory<String>(langs);
        sv.setValue("english");
        langSelector.setValueFactory(sv);

        langSelector.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Properties l = new Properties();
                String idioma = (String) p.get(langSelector.getValue());
                try {
                    l.load(new InputStreamReader(getClass().getResourceAsStream(String.format("/lang/lang-%s.props", idioma)),Charset.forName("UTF8")));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                lbl1.setText((String)l.get("aElo"));
                lbl3.setText((String)l.get("wElo"));
                lbl2.setText((String)l.get("div"));
                lbl4.setText((String)l.get("div"));
                lbl5.setText((String)l.get("lpsGame"));
                lbl7.setText((String)l.get("aLps"));
                lbl8.setText((String)l.get("text"));
                lbl6.setText((String)l.get("needW"));
                boton.setText((String)l.get("btn"));
            }
            
        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> resize(root.getWidth(),root.getHeight()));
        root.heightProperty().addListener((observable, oldValue, newValue) -> resize(root.getWidth(),root.getHeight()));
    }

    private void resize(double width, double height) {
        if(width<=1200 && height<=600){
            lbl1.setFont(Font.font(15));
            lbl2.setFont(Font.font(15));
            lbl3.setFont(Font.font(15));
            lbl4.setFont(Font.font(15));
            lbl5.setFont(Font.font(15));
            lbl6.setFont(Font.font(15));
            lbl7.setFont(Font.font(15));
            lbl8.setFont(Font.font(18));
            lbl8.setWrappingWidth(500);
            langSelector.setMinSize(150, 25);
            langSelector.setStyle("-fx-font-size:12px;");
            eloActual.setStyle("-fx-font-size:12px;");
            eloDeseado.setStyle("-fx-font-size:12px;");
            divisionActual.setStyle("-fx-font-size:12px;");
            divisionDeseada.setStyle("-fx-font-size:12px;");
            lpActuales.setStyle("-fx-font-size:12px;");
            lpsGame.setStyle("-fx-font-size:12px;");
            boton.setMinSize(250, 70);
            cajita.setMinSize(210, 50);
        }
        else if(width<1600 && height<900){
            lbl1.setFont(Font.font(20));
            lbl2.setFont(Font.font(20));
            lbl3.setFont(Font.font(20));
            lbl4.setFont(Font.font(20));
            lbl5.setFont(Font.font(20));
            lbl6.setFont(Font.font(20));
            lbl7.setFont(Font.font(20));
            lbl8.setFont(Font.font(22));
            lbl8.setWrappingWidth(500);
            winsLabel.setFont(Font.font(30));
            langSelector.setMinSize(200, 40);
            langSelector.setStyle("-fx-font-size:20px;");
            eloActual.setStyle("-fx-font-size:20px;");
            eloDeseado.setStyle("-fx-font-size:20px;");
            divisionActual.setStyle("-fx-font-size:20px;");
            divisionDeseada.setStyle("-fx-font-size:20px;");
            lpActuales.setStyle("-fx-font-size:20px;");
            lpsGame.setStyle("-fx-font-size:20px;");
            boton.setMinSize(300, 95);
            cajita.setMinSize(350,75);  
        }
        else {
            lbl1.setFont(Font.font(30));
            lbl2.setFont(Font.font(30));
            lbl3.setFont(Font.font(30));
            lbl4.setFont(Font.font(30));
            lbl5.setFont(Font.font(30));
            lbl6.setFont(Font.font(30));
            lbl7.setFont(Font.font(30));
            lbl8.setFont(Font.font(35));
            lbl8.setWrappingWidth(750);
            winsLabel.setFont(Font.font(40));
            langSelector.setMinSize(250, 55);
            langSelector.setStyle("-fx-font-size:30px;");
            eloActual.setStyle("-fx-font-size:30px;");
            eloDeseado.setStyle("-fx-font-size:30px;");
            divisionActual.setStyle("-fx-font-size:30px;");
            divisionDeseada.setStyle("-fx-font-size:30px;");
            lpActuales.setStyle("-fx-font-size:30px;");
            lpsGame.setStyle("-fx-font-size:30px;");
            boton.setMinSize(350, 120);
            GridPane.setMargin(boton, new Insets(0, 20, 20, 0));
            cajita.setMinSize(420, 100);
        }
    }

    private void updateDeseado() {
        if(eloDeseado.getSelectionModel().getSelectedItem()==Elos.CHALLENGER || eloDeseado.getSelectionModel().getSelectedItem()==Elos.GRANDMASTER || eloDeseado.getSelectionModel().getSelectedItem()==Elos.MASTER){
            divisionDeseada.setDisable(true);
            divisionDeseada.setValue(null);
        }
        else
            divisionDeseada.setDisable(false);
    }

    private void updateActual() {
        if(eloActual.getSelectionModel().getSelectedItem()==Elos.CHALLENGER || eloActual.getSelectionModel().getSelectedItem()==Elos.GRANDMASTER || eloActual.getSelectionModel().getSelectedItem()==Elos.MASTER){
            divisionActual.setDisable(true);
            divisionActual.setValue(null);
        }
        else
            divisionActual.setDisable(false);
    }
    
    public void calcularWins(){
        int lpsActuales = eloActual.getValue().elo;
        if(!lpActuales.getText().isEmpty())
            lpsActuales+=Integer.parseInt(lpActuales.getText());
        if(!divisionActual.isDisabled())
            lpsActuales+=divisionActual.getValue().div;

        int lpsDeseados = eloDeseado.getValue().elo;
        if(!divisionDeseada.isDisabled())
            lpsDeseados+=divisionDeseada.getValue().div;
        
        int wins = (lpsDeseados - lpsActuales) / Integer.parseInt(lpsGame.getText());
        
        winsLabel.setText(String.valueOf(wins));
    }

    public void changeTheme(){
        if(isDark){
            root.setStyle("-fx-background-color:white;");
            lbl1.setStyle("-fx-text-fill:black;");
            lbl2.setStyle("-fx-text-fill:black;");
            lbl3.setStyle("-fx-text-fill:black;");
            lbl4.setStyle("-fx-text-fill:black;");
            lbl5.setStyle("-fx-text-fill:black;");
            lbl7.setStyle("-fx-text-fill:black;");
            lbl8.setFill(Color.BLACK);
            themeButton.setGraphic(lightIcon);
            isDark = false;
        }
        else{
            root.setStyle("-fx-background-color:black;");
            lbl1.setStyle("-fx-text-fill:white;");
            lbl2.setStyle("-fx-text-fill:white;");
            lbl3.setStyle("-fx-text-fill:white;");
            lbl4.setStyle("-fx-text-fill:white;");
            lbl5.setStyle("-fx-text-fill:white;");
            lbl7.setStyle("-fx-text-fill:white;");
            lbl8.setFill(Color.WHITE);
            themeButton.setGraphic(darkIcon);
            isDark = true;
        }
    }

    private void checkAll(){
        if(eloActual.getValue()!=null && eloDeseado.getValue()!=null && lpActuales.getText()!=null && lpsGame.getText()!=null){
            boton.setDisable(false);
        }
    }


    public GridPane getView() {
        return root;
    }

}
