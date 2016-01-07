package fafa.api

import fafa.api.Board._
import fafa.api.Role.{Pawn, Queen}

/**
  * Created by mac on 04.01.16.
  */
class PawnTest extends ChessApiTest {

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

    actor.possibleMoves.toSet should be(Set(
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

    actor.possibleMoves.toSet should be(Set(
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

  it should "capture other pawns en passant" in {
    val actor =
      """
        |  p
        |
        |pP
        |
        |
        |
        |
        |
      """ move Move(Pos.C8, Pos.C6) actorAt Pos.B6 get

    actor.possibleMoves.toSet should be(Set(
      Move(Pos.B6, Pos.B7),
      Move(Pos.B6, Pos.C7, capturing = Some(Pos.C6))
    ))
  }

  it should "get promotion at last rank" in {
    val actor =
      """
        |  p
        | P
        |
        |
        |
        |
        |
        |
      """ actorAt Pos.B7 get

    actor.possibleMoves.toSet should be(Set(
      Move(Pos.B7, Pos.B8, promoteTo = Some(Queen)),
      Move(Pos.B7, Pos.C8, capturing = Some(Pos.C8), promoteTo = Some(Queen))
    ))
  }

  it should "not skip over other piece" in {
    val actor =
      """
        |
        |
        |
        |
        |
        | p
        | P
        |
      """ actorAt Pos.B2 get

    actor.possibleMoves shouldBe List()
  }

  "Black pawn" should "threat pieces one square forward and to the left/right" in {
    val board =
      """
        |
        |
        |
        |PPP
        |ppP
        |K P
        |P
        |
      """

    capturingMoves(board actorAt Pos.A4 get) shouldBe Set()
    capturingMoves(board actorAt Pos.B4 get) shouldBe Set(
      Move(Pos.B4, Pos.A3, capturing = Some(Pos.A3)),
      Move(Pos.B4, Pos.C3, capturing = Some(Pos.C3))
    )
  }
}
