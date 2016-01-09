package fafa.api

/**
  * Created by mac on 06.01.16.
  */
class PerftTest extends ChessApiTest {

  "Board" should "resolve all possible moves for initial position" in {
    possibleMovesCount(1, Board.initialSet) shouldBe 20
    possibleMovesCount(2, Board.initialSet) shouldBe 400
    possibleMovesCount(3, Board.initialSet) shouldBe 8902
    //possibleMovesCount(4, Board.initialSet) shouldBe 197281
  }

  def possibleMovesCount(plies: Int, board: Board): Int = {
    if (plies == 0) return 1

    var count = 0
    val moves = board.allPossibleMoves
    for (move <- moves) {
      val boardAfterMove = board.move(move)
      count += possibleMovesCount(plies - 1, boardAfterMove)
    }

    count
  }
}
