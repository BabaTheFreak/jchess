package com.thefreak.jchess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.thefreak.jchess.engine.Alliance;
import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.engine.board.Move;
import com.thefreak.jchess.engine.board.Tile;
import com.thefreak.jchess.engine.pieces.Piece;
import com.thefreak.jchess.engine.pieces.Rook;

public class BlackPlayer extends Player{

    public BlackPlayer(final Board board, 
	    	       final Collection<Move> whiteStandartLegalMoves,
	               final Collection<Move> blackStandartLegalMoves) {
	super(board, blackStandartLegalMoves, whiteStandartLegalMoves);	
    }

    @Override
    public Collection<Piece> getActivePieces() {
	return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
	return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
	return this.board.whitePlayer();
    }
    
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {	
	final List<Move> kingCastles = new ArrayList<>();	
	if(this.playerKing.isFirstMove() && !this.isInCheck()) {
	    //black king side castle
	    if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
		final Tile rookTile = this.board.getTile(7);
		if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
		    if(Player.calculateAttackOnTile(5, opponentsLegals).isEmpty() && 
			    Player.calculateAttackOnTile(6, opponentsLegals).isEmpty() && 
			    rookTile.getPiece().getPieceType().isRook()) {
				kingCastles.add(new Move.KingSideCastleMove(this.board, 
                                                                            this.playerKing, 
                                                                            6, 
                                                                            (Rook)rookTile.getPiece(), 
                                                                            rookTile.getTileCoordinate(), 
                                                                            5));
		    }		    
		}
	    }	    
	    if(!this.board.getTile(1).isTileOccupied() &&
	       !this.board.getTile(2).isTileOccupied() && 
	       !this.board.getTile(3).isTileOccupied()) {		
		final Tile rookTile = this.board.getTile(0);
		if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
			Player.calculateAttackOnTile(2, opponentsLegals).isEmpty() && 
			Player.calculateAttackOnTile(3, opponentsLegals).isEmpty() && 
			rookTile.getPiece().getPieceType().isRook()) {
		    kingCastles.add(new Move.QueenSideCastleMove(this.board, 
                                                                 this.playerKing, 
                                                                 2, 
                                                                 (Rook)rookTile.getPiece(), 
                                                                 rookTile.getTileCoordinate(), 
                                                                 3));
		}
	    }	    
	}	
	return ImmutableList.copyOf(kingCastles);
    }
}
