package katarem.lol.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    private RootController r = new RootController();
    @Override public void start(Stage stage) throws Exception {
        stage.setTitle("LoLPoints by Katarem v1.1");
        stage.setScene(new Scene(r.getView()));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/logo.png")));
        stage.setMinWidth(891);
        stage.setMinHeight(439);
        stage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
