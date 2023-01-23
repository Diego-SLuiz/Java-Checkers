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

    public void updateTurn() {
        if (currentTurn == 1) {
            currentTurn = 2;
        }
        else if (currentTurn == 2) {
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

    public void move(Cell sourceCell, Cell targetCell) {
        if (targetCell.getColor() == CellColor.BROWN) {
            Piece sourcePiece = sourceCell.getPiece();
            Piece targetPiece = targetCell.getPiece();
            Cell previousTargetCell;

            Boolean isNorthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
            Boolean isNortheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
            Boolean isSouthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();
            Boolean isSoutheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();

            Integer startPositionX = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer stopPositionX = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
            Integer pieceDistanceX = stopPositionX - startPositionX;

            Integer startPositionY = Math.min(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer stopPositionY = Math.max(sourceCell.getPositionY(), targetCell.getPositionY());
            Integer pieceDistanceY = stopPositionY - startPositionY;

            Boolean isRedMovementValid = sourcePiece.getColor() == PieceColor.RED && pieceDistanceX == 1 && pieceDistanceY == 1 && (isNorthwest || isNortheast) && currentTurn == 1;
            Boolean isBlackMovementValid = sourcePiece.getColor() == PieceColor.BLACK && pieceDistanceX == 1 && pieceDistanceY == 1 && (isSouthwest || isSoutheast) && currentTurn == 2;

            if (pieceDistanceX == 2 && pieceDistanceY == 2) {
                if (isNorthwest) {
                    previousTargetCell = cells[targetCell.getPositionX() + 1][targetCell.getPositionY() + 1];
                } else if (isNortheast) {
                    previousTargetCell = cells[targetCell.getPositionX() - 1][targetCell.getPositionY() + 1];
                } else if (isSouthwest) {
                    previousTargetCell = cells[targetCell.getPositionX() + 1][targetCell.getPositionY() - 1];
                } else {
                    previousTargetCell = cells[targetCell.getPositionX() - 1][targetCell.getPositionY() - 1];
                }

                Piece previousPiece = previousTargetCell.getPiece();
                Boolean isRedCapturing = previousPiece != null && previousPiece.getColor() == PieceColor.BLACK && sourcePiece.getColor() == PieceColor.RED && currentTurn == 1;
                Boolean isBlackCapturing = previousPiece != null && previousPiece.getColor() == PieceColor.RED && sourcePiece.getColor() == PieceColor.BLACK && currentTurn == 2;

                if (isRedCapturing || isBlackCapturing) {
                    previousTargetCell.removePiece();
                    previousPiece.setVisible(false);
                    targetCell.setPiece(sourcePiece);
                    sourceCell.removePiece();
                    sourcePiece.setPositionX(targetCell.getPositionX());
                    sourcePiece.setPositionY(targetCell.getPositionY());
                    validateQueen(sourcePiece);
                    updateTurn();
                    return;
                }
            }

            if (isRedMovementValid || isBlackMovementValid) {
                targetCell.setPiece(sourceCell.getPiece());
                sourceCell.removePiece();
                sourcePiece.setPositionX(targetCell.getPositionX());
                sourcePiece.setPositionY(targetCell.getPositionY());
                updateTurn();
                validateQueen(sourcePiece);
            }
        }
    }

    public void moveQueen(Cell sourceCell, Cell targetCell) {
        Piece sourcePiece = sourceCell.getPiece();
        Integer startPos = Math.min(sourceCell.getPositionX(), targetCell.getPositionX());
        Integer stopPos = Math.max(sourceCell.getPositionX(), targetCell.getPositionX());
        Integer distance = stopPos - startPos;
        Integer piecesInPath = 0;

        for (int i = 1; i <= distance; i++) {
            Boolean isNorthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
            Boolean isNortheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
            Boolean isSouthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();
            Boolean isSoutheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();
            Cell pathCell;

            if (isNorthwest) {
                pathCell = cells[sourceCell.getPositionX() - i][sourceCell.getPositionY() - i];
            } else if (isNortheast) {
                pathCell = cells[sourceCell.getPositionX() + i][sourceCell.getPositionY() - i];
            } else if (isSouthwest) {
                pathCell = cells[sourceCell.getPositionX() - i][sourceCell.getPositionY() + i];
            } else {
                pathCell = cells[sourceCell.getPositionX() + i][sourceCell.getPositionY() + i];
            }

            if (pathCell.getPiece() != null) {
                piecesInPath++;
            }
        }

        if (distance == 0) {
            piecesInPath = -1;
        }

//        TODO criar metodo de captura
        Boolean isNorthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
        Boolean isNortheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() > targetCell.getPositionY();
        Boolean isSouthwest = sourceCell.getPositionX() > targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();
        Boolean isSoutheast = sourceCell.getPositionX() < targetCell.getPositionX() && sourceCell.getPositionY() < targetCell.getPositionY();
        Cell previousTargetCell;

        if (isNorthwest) {
            previousTargetCell = cells[targetCell.getPositionX() + 1][targetCell.getPositionY() + 1];
        } else if (isNortheast) {
            previousTargetCell = cells[targetCell.getPositionX() - 1][targetCell.getPositionY() + 1];
        } else if (isSouthwest) {
            previousTargetCell = cells[targetCell.getPositionX() + 1][targetCell.getPositionY() - 1];
        } else {
            previousTargetCell = cells[targetCell.getPositionX() - 1][targetCell.getPositionY() - 1];
        }

        Piece previousPiece = previousTargetCell.getPiece();
        Boolean isRedCapturing = previousPiece != null && previousPiece.getColor() == PieceColor.BLACK && sourcePiece.getColor() == PieceColor.RED && currentTurn == 1;
        Boolean isBlackCapturing = previousPiece != null && previousPiece.getColor() == PieceColor.RED && sourcePiece.getColor() == PieceColor.BLACK && currentTurn == 2;

        if (piecesInPath == 1 && (isRedCapturing || isBlackCapturing)) {
            previousTargetCell.removePiece();
            previousPiece.setVisible(false);
            targetCell.setPiece(sourcePiece);
            sourceCell.removePiece();
            sourcePiece.setPositionX(targetCell.getPositionX());
            sourcePiece.setPositionY(targetCell.getPositionY());
            validateQueen(sourcePiece);
            updateTurn();
            return;
        }

        if (piecesInPath == 0 && currentTurn == 1 && sourcePiece.getColor() == PieceColor.RED && targetCell.getColor() == CellColor.BROWN) {
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.removePiece();
            currentTurn = 2;
        } else if (piecesInPath == 0 && currentTurn == 2 && sourcePiece.getColor() == PieceColor.BLACK && targetCell.getColor() == CellColor.BROWN) {
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.removePiece();
            currentTurn = 1;
        }
    }

}
