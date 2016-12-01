package com.thefreak.jchess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.Board.Builder;
import com.thefreak.jchess.engine.board.BoardUtils;
import com.thefreak.jchess.engine.board.Move;
import com.thefreak.jchess.engine.pieces.Bishop;
import com.thefreak.jchess.engine.pieces.King;
import com.thefreak.jchess.engine.pieces.Knight;
import com.thefreak.jchess.engine.pieces.Pawn;
import com.thefreak.jchess.engine.pieces.Queen;
import com.thefreak.jchess.engine.pieces.Rook;
import com.thefreak.jchess.engine.player.MoveTransition;

public class TestMiniMax {

    @Test
    public void testOpeningDepth1() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 1);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 20L);
    }

    @Test
    public void testOpeningDepth2() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 2);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 400L);
    }

    @Test
    public void testOpeningDepth3() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 3);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 8902L);
    }

    @Test
    public void testOpeningDepth4() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 4);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 197281L);
    }

    @Test
    public void testOpeningDepth5() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 5);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 4865609L);
    }

    @Test
    public void testOpeningDepth6() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 6);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 119060324L);
    }

    @Ignore
    @Test
    public void testOpeningDepth7() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 7);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 3195901860L);
    }

    @Test
    public void testKiwiPeteDepth1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Queen(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Bishop(Alliance.BLACK, 14));
        builder.setPiece(new Bishop(Alliance.BLACK, 16));
        builder.setPiece(new Knight(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Knight(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 47));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Knight(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 36));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Queen(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 1);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 48);
    }

    @Test
    public void testKiwiPeteDepth2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Queen(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Bishop(Alliance.BLACK, 14));
        builder.setPiece(new Bishop(Alliance.BLACK, 16));
        builder.setPiece(new Knight(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Knight(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 47));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Knight(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 36));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Queen(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 2);
        assertEquals(minMax.getNumBoardsEvaluated(), 2039);
    }

    @Test
    public void testKiwiPeteDepth2Bug2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(t1.getToBoard(), 1);
        assertEquals(minMax.getNumBoardsEvaluated(), 45);
    }

    @Test
    public void testChessDotComGame() {
        final Board board = FenUtilities.createGameFromFEN("rnbk1bnr/1pN2ppp/p7/3P2q1/3Pp3/8/PPP1QPPP/RN2KB1R w KQ - 18 10");
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 4);
    }

    @Test
    public void testKiwiPeteDebug() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Queen(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Bishop(Alliance.BLACK, 14));
        builder.setPiece(new Bishop(Alliance.BLACK, 16));
        builder.setPiece(new Knight(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Knight(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 47));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Knight(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 36));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Queen(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 48);
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a3")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 1);
        assertEquals(t1.getToBoard().currentPlayer().getLegalMoves().size(), 44);
    }

    @Test
    public void testPosition3Depth1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setPiece(new Rook(Alliance.BLACK, 31));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setPiece(new King(Alliance.BLACK, 39, false, false));
        // White Layout
        builder.setPiece(new King(Alliance.WHITE, 24, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 25));
        builder.setPiece(new Rook(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 1);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 14);
    }

    @Test
    public void testPosition3Depth2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setPiece(new Rook(Alliance.BLACK, 31));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setPiece(new King(Alliance.BLACK, 39, false, false));
        // White Layout
        builder.setPiece(new King(Alliance.WHITE, 24, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 25));
        builder.setPiece(new Rook(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax();
        minMax.execute(board, 2);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 191);
    }

}