package fafa.api

/**
  * Created by mac on 06.01.16.
  */
class PerftTest extends BaseTest {

  "Board" should "resolve all possible moves for initial position" in {
    Board.initialSet.allPossibleMoves.size shouldBe 20
  }

}
