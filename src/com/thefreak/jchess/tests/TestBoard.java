package com.thefreak.jchess.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.Board.Builder;
import com.thefreak.jchess.engine.board.BoardUtils;
import com.thefreak.jchess.engine.board.Move;
import com.thefreak.jchess.engine.board.Move.MoveFactory;
import com.thefreak.jchess.engine.board.Tile;
import com.thefreak.jchess.engine.pieces.Bishop;
import com.thefreak.jchess.engine.pieces.King;
import com.thefreak.jchess.engine.pieces.Knight;
import com.thefreak.jchess.engine.pieces.Pawn;
import com.thefreak.jchess.engine.pieces.Piece;
import com.thefreak.jchess.engine.pieces.Queen;
import com.thefreak.jchess.engine.pieces.Rook;
import com.thefreak.jchess.engine.player.MoveTransition;

public class TestBoard {

    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertTrue(board.currentPlayer().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        assertTrue(board.whitePlayer().toString().equals("White"));
        assertTrue(board.blackPlayer().toString().equals("Black"));

        final Iterable<Piece> allPieces = board.getAllPieces();
        final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
        for(final Move move : allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastlingMove());
            assertEquals(MoveUtils.exchangeScore(move), 1);
        }

        assertEquals(Iterables.size(allMoves), 40);
        assertEquals(Iterables.size(allPieces), 32);
        assertFalse(BoardUtils.isEndGame(board));
        assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
        assertEquals(new StandardBoardEvaluator().evaluate(board, 0), 0);
        assertEquals(board.getTile(35).getPiece(), null);
        assertEquals(board.getTile(35).getTileCoordinate(), 35);

    }

    @Test
    public void testPlainKingMove() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();
        System.out.println(board);

        assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        BoardEvaluator evaluator = new StandardBoardEvaluator();
        System.out.println(evaluator.evaluate(board, 0));
        assertEquals(new StandardBoardEvaluator().evaluate(board, 0), 0);

        final MoveTransition moveTransition = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f1")));

        assertTrue(moveTransition.getMoveStatus().isDone());
        assertEquals(moveTransition.getToBoard().whitePlayer().getPlayerKing().getPiecePosition(), 61);
        System.out.println(moveTransition.getToBoard());

    }

    @Test
    public void testBoardConsistency() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer(), board.whitePlayer());

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t14.getToBoard().whitePlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(), Alliance.WHITE));
        assertTrue(t14.getToBoard().blackPlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(), Alliance.BLACK));

    }

    @Test(expected=RuntimeException.class)
    public void testInvalidBoard() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        builder.build();
    }

    @Test
    public void testAlgebreicNotation() {
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(0), "a8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(1), "b8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(2), "c8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(3), "d8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(4), "e8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(5), "f8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(6), "g8");
        assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(7), "h8");
    }

    @Test
    public void tt() {

        final Board.Builder builder = new Board.Builder();
        //BLACK LAYOUT
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        //WHITE LAYOUT
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
    }


    private static int calculatedActivesFor(final Board board,
                                            final Alliance alliance) {
        int count = 0;
        for (final Tile t : board.getGameBoard()) {
            if (t.isTileOccupied() && t.getPiece().getPieceAllegiance().equals(alliance)) {
                count++;
            }
        }
        return count;
    }

}
