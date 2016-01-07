package fafa.api

import fafa.api.Board._
import fafa.api.Role.{Knight, Rook}

/**
  * Created by mac on 04.01.16.
  */
class KnightTest extends ChessApiTest {

  "Knight" should "threat pieces in range" in {
    val actor =
      """
        |
        |
        | p
        |
        |     p
        |   N
        |   p
        |
      """.actorAt(Pos.D3).get

    actor.role should be(Knight)

    capturingMoves(actor) should be(Set(
      Move(Pos.D3, Pos.F4, capturing = Some(Pos.F4))
    ))
  }

  it should "move to allowed positions" in {
    val actor =
      """
        |
        |
        | p
        |
        |     p
        |   N
        |   p
        |
      """.actorAt(Pos.D3).get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.D3, Pos.E5), Move(Pos.D3, Pos.C5),
      Move(Pos.D3, Pos.B4), Move(Pos.D3, Pos.B2),
      Move(Pos.D3, Pos.C1), Move(Pos.D3, Pos.E1),
      Move(Pos.D3, Pos.F2)
    ))
  }
}
