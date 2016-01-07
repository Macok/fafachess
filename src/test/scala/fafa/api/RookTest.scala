package fafa.api

import fafa.api.Board._
import fafa.api.Role.Rook

/**
  * Created by mac on 04.01.16.
  */
class RookTest extends ChessApiTest {

  "Rook" should "threat pieces at the same rank or file" in {
    val actor =
      """
        |   p
        |   p
        |
        |
        |
        |   R   p
        |
        |
      """.actorAt(Pos.D3).get

    actor.role should be(Rook)

    capturingMoves(actor) should be(Set(
      Move(Pos.D3, Pos.H3, capturing = Some(Pos.H3)),
      Move(Pos.D3, Pos.D7, capturing = Some(Pos.D7))
    ))
  }

  it should "move to any position at the same rank or file" in {
    val actor =
      """
        |   p
        |   p
        |
        |
        |
        |   R   p
        |   p
        |
      """.actorAt(Pos.D3).get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.D3, Pos.E3), Move(Pos.D3, Pos.F3), Move(Pos.D3, Pos.G3),
      Move(Pos.D3, Pos.C3), Move(Pos.D3, Pos.B3), Move(Pos.D3, Pos.A3),
      Move(Pos.D3, Pos.D4), Move(Pos.D3, Pos.D5), Move(Pos.D3, Pos.D6)
    ))
  }
}
