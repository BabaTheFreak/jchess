package com.thefreak.jchess.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.Board.Builder;
import com.thefreak.jchess.engine.board.BoardUtils;
import com.thefreak.jchess.engine.board.Move.MoveFactory;
import com.thefreak.jchess.engine.pieces.King;
import com.thefreak.jchess.engine.pieces.Pawn;
import com.thefreak.jchess.engine.player.MoveTransition;

public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 21));
        builder.setPiece(new King(Alliance.BLACK, 36, false, false));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 29));
        builder.setPiece(new King(Alliance.WHITE, 31, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 39));
        // Set the current player
        builder.setMoveMaker(Alliance.BLACK);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 2, false, false));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 10));
        builder.setPiece(new King(Alliance.WHITE, 26, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("c5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 0, false, false));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 16));
        builder.setPiece(new King(Alliance.WHITE, 17, false, false));
        builder.setPiece(new Bishop(Alliance.WHITE, 19));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a7")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }
}