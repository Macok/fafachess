package fafa.api

import Board._
import fafa.api.Role.Rook

/**
  * Created by mac on 04.01.16.
  */
class RookTest extends BaseTest {
  "Rook" should "attack pieces at the same rank or file" in {
    val board =
      """
        |   p
        |
        |
        |
        |
        |   R   p
        |
        |
      """

    val actor = board.actorAt(Pos.D3)
    assert(actor.isDefined)

    actor.get.role should be(Rook)
    val capturingMoves = actor.get.possibleMoves filter {
      _.capturing.isDefined
    }
  }
}
