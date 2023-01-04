package checkers;

import checkers.enumeration.CellColor;
import checkers.util.ImageLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


public class Cell extends StackPane {
    private static final Integer width = 72;
    private static final Integer height = 72;
    private final Label background;
    private final CellColor color;
    private Piece piece;

    public Cell(CellColor color) {
        this.background = new Label();
        this.color = color;
        createCell();
    }

    private void createCell() {
        String imagePath = color == CellColor.BROWN ? "board-cell-dark.jpg" : "board-cell-light.jpg";
        background.setGraphic(ImageLoader.load(imagePath, width, height));
        getChildren().add(background);
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
        getChildren().add(piece);
    }

}
