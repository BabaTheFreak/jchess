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
import com.thefreak.jchess.engine.board.Move.AttackMove;
import com.thefreak.jchess.engine.board.Move.MajorMove;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = { -1, -7, -8, -9, 1, 7, 8, 9 };

    public King(final Alliance pieceAlliance, final int piecePosition) {
	super(PieceType.KING, piecePosition, pieceAlliance, true);
    }
    
    public King(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
	super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
	final List<Move> legalMoves = new ArrayList<>();
	for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
	    final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
	    if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
		    || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
		continue;
	    }
	    if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
		if (!candidateDestinationTile.isTileOccupied()) {
		    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
		} else {
		    final Piece pieaceAtDestination = candidateDestinationTile.getPiece();
		    final Alliance pieceAlliance = pieaceAtDestination.getPieceAlliance();
		    if (this.pieceAlliance != pieceAlliance) {
			legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieaceAtDestination));
		    }
		}
	    }
	}
	return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public String toString() {
	return Piece.PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
	return BoardUtils.FIRST_COLUMN[currentCandidate] && ((candidateDestinationCoordinate == -9)
		|| (candidateDestinationCoordinate == -1) || (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
	return BoardUtils.EIGHTH_COLUMN[currentCandidate] && ((candidateDestinationCoordinate == -7)
		|| (candidateDestinationCoordinate == 1) || (candidateDestinationCoordinate == 9));
    }
    
    @Override
    public King movePiece(final Move move) {
	return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
}
