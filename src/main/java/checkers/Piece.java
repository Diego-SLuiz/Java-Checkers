package checkers;

import checkers.enumeration.PieceColor;
import checkers.util.ImageLoader;
import javafx.scene.control.Label;


public class Piece extends Label {
    private static final Integer width = 72;
    private static final Integer height = 72;
    private final PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
        createPiece();
    }

    private void createPiece() {
        String imagePath = color == PieceColor.BLACK ? "checker-black.png" : "checker-red.png";
        setGraphic(ImageLoader.load(imagePath, width, height));
    }

    public PieceColor getColor() {
        return color;
    }
}
