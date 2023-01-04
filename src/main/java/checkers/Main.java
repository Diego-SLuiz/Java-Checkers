package checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Board board = new Board();
        board.setMinWidth(576);
        board.setMinHeight(576);

        BorderPane layout = new BorderPane();
        layout.setCenter(board);

        Scene scene = new Scene(layout);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

}
