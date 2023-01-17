package katarem.lol.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import katarem.lol.objs.LpsInvalidosException;

public class RootController implements Initializable{

    @FXML private GridPane root;

    @FXML private ChoiceBox<Elos> eloActual, eloDeseado;
    
    @FXML private ChoiceBox<Divisiones> divisionActual, divisionDeseada;

    @FXML private Button boton, themeButton;

    @FXML private TextField lpsGame, lpActuales;

    @FXML private Label winsLabel, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, resLabel;

    @FXML private Text lbl8, checkText;

    @FXML private Spinner<String> langSelector;

    @FXML private HBox cajita;

    private String errorMessage = "";

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

        lpActuales.textProperty().addListener(e -> checkAll());
        lpsGame.textProperty().addListener(e -> checkAll());
        eloActual.valueProperty().addListener(e -> checkAll());
        eloDeseado.valueProperty().addListener(e -> checkAll());

        root.widthProperty().addListener((observable, oldValue, newValue) -> resize(root.getWidth(),root.getHeight()));
        root.heightProperty().addListener((observable, oldValue, newValue) -> resize(root.getWidth(),root.getHeight()));

        root.getStyleClass().add("root");
    }

    private void resize(double width, double height) {
        if(width<=1200 || height<=600){
            
            //Labels
            root.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(15)));

            //Texts
            root.getChildren().stream().filter(Text.class::isInstance).map(Text.class::cast).forEach(text -> text.setFont(Font.font(18)));

            //ChoiceBoxes
            root.getChildren().stream().filter(ChoiceBox.class::isInstance).map(ChoiceBox.class::cast).forEach(choice -> choice.setStyle("-fx-font-size:12px;"));

            //TextFields
            root.getChildren().stream().filter(TextField.class::isInstance).map(TextField.class::cast).forEach(txtfield -> txtfield.setStyle("-fx-font-size:12px;"));

            //VBox Labels
            cajita.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(15)));

            boton.setMinSize(250, 70);
            cajita.setMinSize(210, 50);

            lbl8.setWrappingWidth(500);
            langSelector.setMinSize(150, 25);
            langSelector.setStyle("-fx-font-size:12px;");
            
        }
        else if(width<1600 || height<900){
            
            //Labels
            root.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(20)));

            //Texts
            root.getChildren().stream().filter(Text.class::isInstance).map(Text.class::cast).forEach(text -> text.setFont(Font.font(22)));

            //ChoiceBox    
            root.getChildren().stream().filter(ChoiceBox.class::isInstance).map(ChoiceBox.class::cast).forEach(choice -> choice.setStyle("-fx-font-size:20px;"));

            //TextFields
            root.getChildren().stream().filter(TextField.class::isInstance).map(TextField.class::cast).forEach(txtfield -> txtfield.setStyle("-fx-font-size:20px"));

            //VBox Labels
            cajita.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(20)));    
            
            boton.setMinSize(300, 95);
            cajita.setMinSize(350,75);     
            
            lbl8.setWrappingWidth(500);
            langSelector.setMinSize(200, 40);
            langSelector.setStyle("-fx-font-size:20px;");
 
        }
        else {

            //Labels
            root.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(30)));

            //Texts
            root.getChildren().stream().filter(Text.class::isInstance).map(Text.class::cast).forEach(text -> text.setFont(Font.font(35)));

            //ChoiceBox
            root.getChildren().stream().filter(ChoiceBox.class::isInstance).map(ChoiceBox.class::cast).forEach(choice -> choice.setStyle("-fx-font-size:30px;"));
            
            //TextFields
            root.getChildren().stream().filter(TextField.class::isInstance).map(TextField.class::cast).forEach(txtfield -> txtfield.setStyle("-fx-font-size:30px"));

            //VBox Labels
            cajita.getChildren().stream().filter(Label.class::isInstance).map(Label.class::cast).forEach(label -> label.setFont(Font.font(30)));

            boton.setMinSize(350, 120);
            cajita.setMinSize(420, 100);

            lbl8.setWrappingWidth(750);
            langSelector.setMinSize(250, 55);
            langSelector.setStyle("-fx-font-size:30px;");
            
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
        if(errorCheck()){
            checkText.setFill(Color.RED);
            checkText.setText(errorMessage);
        }
        else{
            int lpsActuales = eloActual.getValue().elo;
            int lpsDeseados = eloDeseado.getValue().elo;
            int eloDiff = ((lpsDeseados - lpsActuales) / 400)*3;
            System.out.println(eloDiff);
            if(!lpActuales.getText().isEmpty())
                lpsActuales+=Integer.parseInt(lpActuales.getText());
            if(!divisionActual.isDisabled())
                lpsActuales+=divisionActual.getValue().div;
            if(!divisionDeseada.isDisabled())
                lpsDeseados+=divisionDeseada.getValue().div;
            

            long winsSinPromo = Math.round((double)(lpsDeseados - lpsActuales) / Double.parseDouble(lpsGame.getText()));
            long winsTotales = winsSinPromo + eloDiff;


            checkText.setText("Successful Operation");
            checkText.setFill(Color.GREEN);
            winsLabel.setText("" + winsTotales);
        }
    }

    public void changeTheme(){
        if(isDark){
            root.getStylesheets().clear();
            root.getStylesheets().add("/lightTheme.css");
            
            lbl8.setFill(Color.BLACK);
            themeButton.setGraphic(lightIcon);
            isDark = false;
        }
        else{
            root.getStylesheets().clear();
            root.getStylesheets().add("/darkTheme.css");
            root.setId("root");
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

    private boolean errorCheck(){
        try {
            int lps = Integer.parseInt(lpActuales.getText());
            int game = Integer.parseInt(lpsGame.getText());
            if(game==0 || lpsGame.getText()==null)
                throw new ArithmeticException();
            if(eloActual.getValue().elo<2400 && (divisionActual.getValue()==null))
                throw new NullPointerException();     
            if(eloDeseado.getValue().elo<2400 && (divisionDeseada.getValue()==null))
                throw new NullPointerException();   
            if(eloDeseado.getValue().elo<2400 && lps>99)
                throw new LpsInvalidosException();
            if(game>35)
                throw new LpsInvalidosException();            
        } catch (NumberFormatException e) {
            errorMessage = "Points per game and Actual points have to be a number";
            return true;
        } catch (NullPointerException e) {
            errorMessage = "Division can't be null in elos below master";
            return true;
        } catch (LpsInvalidosException e) {
            errorMessage = "Points per game is too high!";
            return true;
        } catch(ArithmeticException e) {
            errorMessage = "Points per game can't be 0 or null";
        }
            return false;
    }


    public GridPane getView() {
        return root;
    }

}
