package katarem.lol.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    private RootController r = new RootController();
    @Override public void start(Stage stage) throws Exception {

        stage.setTitle("LoLPoints by Katarem v1.0");
        stage.setScene(new Scene(r.getView()));
        // stage.setScene(new Scene(r2.getView()));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/logo.png")));
        stage.setMinWidth(891);
        stage.setMinHeight(439);
        stage.show();
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());

    }
    public static void main(String[] args){
        launch(args);
    }
}
