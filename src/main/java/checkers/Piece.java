package checkers;

import checkers.enumeration.PieceColor;
import checkers.util.ImageLoader;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class Piece extends Label {
    private static final Integer width = 72;
    private static final Integer height = 72;
    private final PieceColor color;
    private Integer positionX;
    private Integer positionY;
    private Boolean selected;

    public Piece(PieceColor color, Integer positionX, Integer positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.selected = false;
        createPiece();
    }

    private void createPiece() {
        String imagePath = color == PieceColor.BLACK ? "checker-black.png" : "checker-red.png";
        setGraphic(ImageLoader.load(imagePath, width, height));
    }

     public Boolean isSelected() {
        return selected;
    }

    public void select() {
        if (selected) {
            selected = false;
            System.out.println("Dessecionado x " + positionX + " y " + positionY);
        }
        else {
            selected = true;
            System.out.println("Selecionado x " + positionX + " y " + positionY);
        }
    }




}
