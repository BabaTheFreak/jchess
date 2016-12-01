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

public class Rook extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

    public Rook(final Alliance pieceAlliance, final int piecePosition) {
	super(PieceType.ROOK, piecePosition, pieceAlliance, true);
    }
    
    public Rook(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
	super(PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
	final List<Move> legalMoves = new ArrayList<>();
	for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
	    int candidateDestinationCoordinate = this.piecePosition;
	    while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		if (isColumnExclusion(candidateCoordinateOffset, candidateDestinationCoordinate)) {
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
	return Piece.PieceType.ROOK.toString();
    }

    private static boolean isColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
	return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] && (currentCandidate == -1))
		|| (BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] && (currentCandidate == 1));
    }
    
    @Override
    public Rook movePiece(final Move move) {
	return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
}
