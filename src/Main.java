import controllers.GameController;
import game.GameScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameScene gameScene = new GameScene();

        Scene scene = new Scene(gameScene.getRootLayout(), 1300, 1000);

        primaryStage.setTitle("Mon Jeu de Tower Defense");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}