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

public class Bishop extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -7, 7, 9 };

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
	super(PieceType.BISHOP, piecePosition, pieceAlliance, true);
    }
    
    public Bishop(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
	super(PieceType.BISHOP, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
	final List<Move> legalMoves = new ArrayList<>();
	for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
	    int candidateDestinationCoordinate = this.piecePosition;
	    while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
			|| isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
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
	return Piece.PieceType.BISHOP.toString();
    }

    private static boolean isFirstColumnExclusion(final int candidateDestinationCoordinate, final int currentCandidate) {
	return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate]
		&& ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnExclusion(final int candidateDestinationCoordinate, final int currentCandidate) {
	return BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate]
		&& ((currentCandidate == -7) || (currentCandidate == 9));
    }

    @Override
    public Bishop movePiece(final Move move) {
	return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

}
