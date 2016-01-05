package fafa.api

import fafa.api.Board._
import fafa.api.Role.{King, Queen}

/**
  * Created by mac on 04.01.16.
  */
class KingTest extends BaseTest {

  "King" should "threat pieces next to it" in {
    val actor =
      """
        |
        |
        |
        |
        |pp
        |K p
        |P
        |
      """ actorAt Pos.A3 get

    actor.role should be(King)

    capturingMoves(actor) should be(Set(
      Move(Pos.A3, Pos.A4, capturing = Some(Pos.A4)),
      Move(Pos.A3, Pos.B4, capturing = Some(Pos.B4))
    ))
  }

  it should "move to any position next to it" in {
    val actor =
      """
        |
        |
        |
        |
        |pp
        |K p
        |P
        |
      """ actorAt Pos.A3 get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.A3, Pos.B3),
      Move(Pos.A3, Pos.B2)
    ))
  }
}
