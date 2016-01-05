package fafa.api

import fafa.api.Board._
import fafa.api.Role.{Queen, Knight}

/**
  * Created by mac on 04.01.16.
  */
class QueenTest extends BaseTest {

  "Queen" should "threat pieces at the same rank, file or diagonal" in {
    val actor =
      """
        |
        |
        |
        |p
        |p p
        | p
        |Q  pp
        |P
      """ actorAt Pos.A2 get

    actor.role should be(Queen)

    capturingMoves(actor) should be(Set(
      Move(Pos.A2, Pos.A4, capturing = Some(Pos.A4)),
      Move(Pos.A2, Pos.B3, capturing = Some(Pos.B3)),
      Move(Pos.A2, Pos.D2, capturing = Some(Pos.D2))
    ))
  }

  it should "move to any position at the same rank, file or diagonal" in {
    val actor =
      """
        |
        |
        |
        |p
        |p p
        | p
        |Q  pp
        |P
      """ actorAt Pos.A2 get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.A2, Pos.A3),
      Move(Pos.A2, Pos.B2),
      Move(Pos.A2, Pos.C2),
      Move(Pos.A2, Pos.B1)
    ))
  }
}
