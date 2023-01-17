package checkers;

import checkers.enumeration.PieceColor;
import checkers.util.ImageLoader;
import javafx.scene.control.Label;


public class Piece extends Label {
    private static final Integer width = 72;
    private static final Integer height = 72;
    private final PieceColor color;
    private Integer positionX;
    private Integer positionY;

    private Boolean isQueen;

    public Boolean getQueen() {
        return isQueen;
    }

    public void setQueen(Boolean queen) {
        isQueen = queen;
    }

    public Piece(PieceColor color, Integer positionX, Integer positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        isQueen = false;
        createPiece();
    }

    private void createPiece() {
        String imagePath = color == PieceColor.BLACK ? "checker-black.png" : "checker-red.png";
        setGraphic(ImageLoader.load(imagePath, width, height));
    }

    public void turnQueen() {
        String imagePath = color == PieceColor.BLACK ? "checker-black-king.png" : "checker-red-king.png";
        setGraphic(ImageLoader.load(imagePath, width, height));
        isQueen = true;
    }

    public PieceColor getColor() {
        return color;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

}
