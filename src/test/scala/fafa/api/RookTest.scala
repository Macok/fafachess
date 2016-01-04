package fafa.api

import fafa.api.Board._
import fafa.api.Role.Rook

/**
  * Created by mac on 04.01.16.
  */
class RookTest extends BaseTest {
  "Rook" should "threat pieces at the same rank or file" in {
    val board =
      """
        |   p
        |   p
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

    capturingMoves.toSet should be(Set(
      Move(Pos.D3, Pos.H3, capturing = Some(Pos.H3)),
      Move(Pos.D3, Pos.D7, capturing = Some(Pos.D7))
    ))
  }
}
