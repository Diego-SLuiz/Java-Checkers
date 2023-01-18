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

    private Integer currentTurn;

    public Board() {
        sizeX = 8;
        sizeY = 8;
        cells = new Cell[sizeX][sizeY];
        sourceCell = null;
        currentTurn = 1;
        createBoard();
        createPieces();
    }

    private void createBoard() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                CellColor cellColor;
                cellColor = x % 2 == 0 ? CellColor.BEIGE : CellColor.BROWN;
                cellColor = y % 2 != 0 ? x % 2 == 0 ? CellColor.BROWN : CellColor.BEIGE : cellColor;
                Cell cell = new Cell(cellColor, x, y);
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, onCellSelector);
                cells[x][y] = cell;
                add(cell, x, y);
            }
        }
    }

    private void createPieces() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                Cell cell = cells[x][y];
                CellColor cellColor = cells[x][y].getColor();
                if (y < 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.BLACK, x, y);
                    cell.setPiece(piece);
                } else if (y >= cells.length - 3 && cellColor == CellColor.BROWN) {
                    Piece piece = new Piece(PieceColor.RED, x, y);
                    cell.setPiece(piece);
                }
            }
        }
    }

    public void move(Cell sourceCell, Cell targetCell) {
        if (targetCell.getColor() == CellColor.BROWN) {
            Piece sourcePiece = sourceCell.getPiece();
            Integer redPieceDistance = sourceCell.getPositionY() - targetCell.getPositionY();
            Integer blackPieceDistance = targetCell.getPositionY() - sourceCell.getPositionY();
            Boolean isRedMovementValid = sourceCell.getPiece().getColor() == PieceColor.RED && redPieceDistance == 1;
            Boolean isBlackMovementValid = sourcePiece.getColor() == PieceColor.BLACK && blackPieceDistance == 1;

            if (currentTurn == 1 && isRedMovementValid) {
                targetCell.setPiece(sourceCell.getPiece());
                sourceCell.removePiece();
                sourcePiece.setPositionX(targetCell.getPositionX());
                sourcePiece.setPositionY(targetCell.getPositionY());
                validateQueen(sourcePiece);
                currentTurn = 2;
            } else if (currentTurn == 2 && isBlackMovementValid) {
                targetCell.setPiece(sourceCell.getPiece());
                sourceCell.removePiece();
                sourcePiece.setPositionX(targetCell.getPositionX());
                sourcePiece.setPositionY(targetCell.getPositionY());
                validateQueen(sourcePiece);
                currentTurn = 1;
            }
        }
    }

    public void moveQueen(Cell sourceCell, Cell targetCell) {
        Boolean isNorthDirection = sourceCell.getPositionY() > targetCell.getPositionY();
        Boolean isSouthDirection = sourceCell.getPositionY() < targetCell.getPositionY();
        Boolean isWestDirection = sourceCell.getPositionX() > targetCell.getPositionX();
        Boolean isEastDirection = sourceCell.getPositionX() < targetCell.getPositionX();
        Boolean isValidMovement = false;

        if (isNorthDirection && isWestDirection) {
            Integer movementDistance = sourceCell.getPositionX() - targetCell.getPositionX();
            isValidMovement = targetCell.getPositionY() == sourceCell.getPositionY() - movementDistance;
            Integer startPositionX = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer stopPositionX = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer startPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer stopPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());

            for (int x = startPositionX; x <= stopPositionX; x++) {
                for (int y = startPositionY; y <= stopPositionY; y++) {
                    if (cells[x][y].getPiece() != null) {
                        isValidMovement = false;
                    }
                }
            }
        }
        if (isNorthDirection && isEastDirection) {
            Integer movementDistance = targetCell.getPositionX() - sourceCell.getPositionX();
            isValidMovement = targetCell.getPositionY() == sourceCell.getPositionY() - movementDistance;

            Integer startPositionX = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer stopPositionX = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer startPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer stopPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());

            for (int x = startPositionX; x <= stopPositionX; x++) {
                for (int y = startPositionY; y <= stopPositionY; y++) {
                    if (cells[x][y].getPiece() != null) {
                        isValidMovement = false;
                    }
                }
            }
        }
        if (isSouthDirection && isWestDirection) {
            Integer movementDistance = sourceCell.getPositionX() - targetCell.getPositionX();
            isValidMovement = targetCell.getPositionY() == sourceCell.getPositionY() + movementDistance;

            Integer startPositionX = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer stopPositionX = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer startPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer stopPositionY = Math.max(sourceCell.getPositionY(), targetCell.getPositionY());

            for (int x = startPositionX; x <= stopPositionX; x++) {
                for (int y = startPositionY; y <= stopPositionY; y++) {
                    if (cells[x][y].getPiece() != null) {
                        isValidMovement = false;
                    }
                }
            }
        }
        if (isSouthDirection && isEastDirection) {
            Integer movementDistance = targetCell.getPositionX() - sourceCell.getPositionX();
            isValidMovement = targetCell.getPositionY() == sourceCell.getPositionY() + movementDistance;

            Integer startPositionX = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer stopPositionX = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer startPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer stopPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());

            for (int x = startPositionX; x <= stopPositionX; x++) {
                for (int y = startPositionY; y <= stopPositionY; y++) {
                    if (cells[x][y].getPiece() != null) {
                        isValidMovement = false;
                    }
                }
            }
        }

        if (isValidMovement && sourceCell.getPiece().getColor() == PieceColor.RED && currentTurn == 1) {
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.removePiece();
            currentTurn = 2;
        } else if (isValidMovement && sourceCell.getPiece().getColor() == PieceColor.BLACK && currentTurn == 2) {
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.removePiece();
            currentTurn = 1;
        }

    }

    public void validateQueen(Piece piece) {
        Integer boardStart = 0;
        Integer boardEnd = 7;

        if (piece.getColor() == PieceColor.RED && piece.getPositionY() == boardStart) {
            piece.turnQueen();
        } else if (piece.getColor() == PieceColor.BLACK && piece.getPositionY() == boardEnd) {
            piece.turnQueen();
        }

    }

    EventHandler<MouseEvent> onCellSelector = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Cell targetCell = (Cell) mouseEvent.getSource();
            System.out.printf("Cell %d %d\n", targetCell.getPositionX(), targetCell.getPositionY());

            if (sourceCell != null && sourceCell.getPiece() != null && targetCell.getPiece() == null) {
                if (sourceCell.getPiece().getQueen()) {
                    moveQueen(sourceCell, targetCell);
                } else {
                    move(sourceCell, targetCell);
                }
            } else {
                sourceCell = targetCell;
            }
        }
    };

}
