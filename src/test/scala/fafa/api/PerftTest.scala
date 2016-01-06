package fafa.api

/**
  * Created by mac on 06.01.16.
  */
class PerftTest extends BaseTest {

  "Board" should "resolve all possible moves for initial position" in {
    possibleMovesCount(1, List(Board.initialSet)) shouldBe 20
    possibleMovesCount(2, List(Board.initialSet)) shouldBe 400
    possibleMovesCount(3, List(Board.initialSet)) shouldBe 8902
  }

  def possibleMovesCount(plies: Int, boards: List[Board]): Int = {
    val boardsNextPly = boards flatMap { board => board.allPossibleMoves map { move => board.move(move) } }

    if (plies == 1) boardsNextPly.size
    else possibleMovesCount(plies - 1, boardsNextPly)
  }
}
