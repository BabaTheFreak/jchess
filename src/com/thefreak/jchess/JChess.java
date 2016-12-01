package com.thefreak.jchess;

import com.thefreak.jchess.engine.board.Board;
import com.thefreak.jchess.gui.Table;

public class JChess {

    public static void main(String[] args) {
	Board board = Board.createStandardBoard();
	System.out.println(board.toString());
	
	new Table();

    }
}
