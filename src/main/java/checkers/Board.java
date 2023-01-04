package checkers;

import checkers.enumeration.CellColor;
import checkers.enumeration.PieceColor;
import javafx.scene.layout.GridPane;


public class Board extends GridPane {
    private final Integer sizeX;
    private final Integer sizeY;
    private final Cell[][] cells;

    public Board() {
        sizeX = 8;
        sizeY = 8;
        cells = new Cell[sizeX][sizeY];
        createBoard();
    }

    private void createBoard() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                CellColor cellColor;
                cellColor = x % 2 == 0 ? CellColor.BEIGE : CellColor.BROWN;
                cellColor = y % 2 != 0 ? x % 2 == 0 ? CellColor.BROWN : CellColor.BEIGE : cellColor;
                Cell cell = new Cell(cellColor);
                cells[x][y] = cell;

                if (y < 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.BLACK);
                    cell.placePiece(piece);
                }
                else if (y >= cells.length - 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.RED);
                    cell.placePiece(piece);
                }

                add(cell, x, y);
            }
        }
    }

}
