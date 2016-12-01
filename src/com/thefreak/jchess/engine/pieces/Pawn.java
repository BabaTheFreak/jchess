package com.thefreak.jchess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.BoardUtils;
import com.thefreak.jchess.engine.board.Move;
import com.thefreak.jchess.engine.board.Move.*;

public class Pawn extends Piece {
    
    private final static int[] CANDIDATE_MOVE_COORDINATE = { 7, 8, 9, 16 };

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
	super(PieceType.PAWN, piecePosition, pieceAlliance, true);
    }
    
    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
	super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
	
	final List<Move> legalMoves = new ArrayList<>();

	for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
	    final int candidateDestinationCoordinate = this.piecePosition
		    + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
	    if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
		continue;
	    }
	    if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
		legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
	    } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                      ((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                      (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
                       final int behindCandidateDestinationCoordinate =
                               this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                       if (!board.getTile(candidateDestinationCoordinate).isTileOccupied() &&
                           !board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()) {
                           legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                       }
                   } else if (currentCandidateOffset == 7 && 
                	   !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                	     (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
		if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
		    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
		    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
			legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
		    }
		} else if(board.getEnPassantPawn() != null) {
		    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
			final Piece pieceOnCandidate = board.getEnPassantPawn();
			if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
			    legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
			}
		    }
		}
	    } else if (currentCandidateOffset == 9 && 
		      !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
		        (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
		if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
		    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
		    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
			legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
		    }
		} else if(board.getEnPassantPawn() != null) {
		    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
			final Piece pieceOnCandidate = board.getEnPassantPawn();
			if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
			    legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
			}
		    }
		}
	    }
	}
	return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public String toString() {
	return Piece.PieceType.PAWN.toString();
    }
    
    @Override
    public Pawn movePiece(final Move move) {
	return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
}
