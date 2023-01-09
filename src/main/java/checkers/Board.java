package checkers;

import checkers.enumeration.CellColor;
import checkers.enumeration.PieceColor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class Board extends GridPane {
    private final Integer sizeX;
    private final Integer sizeY;
    private final Cell[][] cells;
    private Cell sourceCell;

    public Board() {
        sizeX = 8;
        sizeY = 8;
        cells = new Cell[sizeX][sizeY];
        sourceCell = null;
        createBoard();
    }

    private void createBoard() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                CellColor cellColor;
                cellColor = x % 2 == 0 ? CellColor.BEIGE : CellColor.BROWN;
                cellColor = y % 2 != 0 ? x % 2 == 0 ? CellColor.BROWN : CellColor.BEIGE : cellColor;
                Cell cell = new Cell(cellColor, x, y);
                cells[x][y] = cell;

                EventHandler<MouseEvent> onCellSelector = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Cell targetCell = (Cell) mouseEvent.getSource();
                        System.out.printf("Cell %d %d\n", targetCell.getPositionX(), targetCell.getPositionY());

                        if (sourceCell != null && targetCell.getPiece() == null) {
                            targetCell.setPiece(sourceCell.getPiece());
                            sourceCell.removePiece();
                            sourceCell = targetCell;
                        }
                        else {
                            sourceCell = targetCell;
                        }
                    }
                };

                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, onCellSelector);

                if (y < 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.BLACK);
                    cell.setPiece(piece);
                }
                else if (y >= cells.length - 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.RED);
                    cell.setPiece(piece);
                }

                add(cell, x, y);
            }
        }
    }



}
