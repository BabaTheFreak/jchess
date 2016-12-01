package com.thefreak.jchess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestPieces.class,
                     TestBoard.class,
                     TestStaleMate.class,
                     TestPlayer.class,
                     TestCheckmate.class,
                     TestMiniMax.class,
                     TestAlphaBeta.class,
                     TestCastling.class,
                     TestPawnStructure.class,
                     TestFENParser.class
                     /*TestPGNParser.class*/})
public class ChessTestSuite {
}
