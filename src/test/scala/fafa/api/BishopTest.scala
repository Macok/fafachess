package fafa.api

import fafa.api.Role.{Pawn, Bishop, Rook}
import fafa.api.Board._

/**
  * Created by mac on 05.01.16.
  */
class BishopTest extends ChessApiTest {
  "Bishop" should "threat pieces at the same diagonal" in {
    val board =
      """
        | p
        |    p
        |   p
        |
        |
        |B
        |
        |  P
      """

    val actor = board actorAt Pos.A3 get
    val p = board actorAt Pos.D6 get

    actor.role should be(Bishop)
    p.role should be(Pawn)

    capturingMoves(actor) should be(Set(
      Move(Pos.A3, Pos.D6, capturing = Some(Pos.D6))
    ))
  }

  it should "move to any position at the same diagonal" in {
    val actor =
      """
        | p
        |    p
        |   p
        |
        |
        |B
        |
        |  P
      """ actorAt Pos.A3 get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.A3, Pos.B4), Move(Pos.A3, Pos.C5), Move(Pos.A3, Pos.B2)
    ))
  }
}
