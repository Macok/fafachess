package fafa.api

import fafa.api.Board._
import fafa.api.Role.{Pawn, King}

/**
  * Created by mac on 04.01.16.
  */
class PawnTest extends BaseTest {

  "Pawn" should "threat pieces one square forward and to the left/right" in {
    val actor =
      """
        |
        |
        |
        |
        |
        | ppp
        | pP
        |   p
      """ actorAt Pos.C2 get

    actor.role should be(Pawn)

    capturingMoves(actor) should be(Set(
      Move(Pos.C2, Pos.B3, capturing = Some(Pos.B3)),
      Move(Pos.C2, Pos.D3, capturing = Some(Pos.D3))
    ))
  }

  it should "move one or two squares forward if first move" in {
    val actor =
      """
        |
        |
        |
        |
        |
        |
        |  P
        |
      """ actorAt Pos.C2 get

    actor.possibleMoves should be(Set(
      Move(Pos.C2, Pos.C3),
      Move(Pos.C2, Pos.C4)
    ))
  }

  it should "only move one square forward if not first move" in {
    val actor =
      """
        |
        |
        |
        |
        |
        |
        |  P
        |
      """ move Move(Pos.C2, Pos.C3) actorAt Pos.C3 get

    actor.possibleMoves.toSet should be(Set(
      Move(Pos.C3, Pos.C4)
    ))
  }
}
