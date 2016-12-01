package com.thefreak.jchess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.BoardUtils;
import com.thefreak.jchess.engine.board.Move;
import com.thefreak.jchess.engine.board.Tile;
import com.thefreak.jchess.engine.board.Move.MajorMove;

public class Queen extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(final Alliance pieceAlliance, final int piecePosition) {
	super(PieceType.QUEEN, piecePosition, pieceAlliance, true);
    }
    
    public Queen(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
	super(PieceType.QUEEN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
	final List<Move> legalMoves = new ArrayList<>();
	for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
	    int candidateDestinationCoordinate = this.piecePosition;
	    while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
			|| isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
		    break;
		}
		candidateDestinationCoordinate += candidateCoordinateOffset;
		if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
		    if (!candidateDestinationTile.isTileOccupied()) {
			legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
		    } else {
			final Piece pieaceAtDestination = candidateDestinationTile.getPiece();
			final Alliance pieceAlliance = pieaceAtDestination.getPieceAlliance();
			if (this.pieceAlliance != pieceAlliance) {
			    legalMoves.add(
				    new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieaceAtDestination));
			}
			break;
		    }
		}
	    }
	}
	return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public String toString() {
	return Piece.PieceType.QUEEN.toString();
    }

    private static boolean isFirstColumnExclusion(final int candidatePosition, final int currentPosition) {
	return BoardUtils.FIRST_COLUMN[candidatePosition]
		&& ((currentPosition == -9) || (currentPosition == -1) || (currentPosition == 7));
    }

    private static boolean isEightColumnExclusion(final int candidatePosition, final int currentPosition) {
	return BoardUtils.EIGHTH_COLUMN[candidatePosition]
		&& ((currentPosition == -7) || (currentPosition == 1) || (currentPosition == 9));
    }
    
    @Override
    public Queen movePiece(final Move move) {
	return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
}
