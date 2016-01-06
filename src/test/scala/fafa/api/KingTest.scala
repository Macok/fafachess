package fafa.api

import fafa.api.Board._
import fafa.api.Move
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
        |rp
        |K  p
        |P
        |
      """ actorAt Pos.A3 get

    nonCapturingMoves(actor) should be(Set(
      Move(Pos.A3, Pos.B3),
      Move(Pos.A3, Pos.B2)
    ))
  }

  "White King" should "be able to perform short castling" in {
    val king =
      """
        |
        |
        |
        |
        |
        |
        |
        |R Q K  R
      """ actorAt Pos.E1 get

    king.possibleMoves.toSet filter {
      _.castling.isDefined
    } shouldBe Set(
      Move(Pos.E1, Pos.G1, castling = Some(Move(Pos.H1, Pos.F1)))
    )
  }

  it should "be able to perform long castling" in {
    val king =
      """
        |
        |
        |
        |
        |
        |
        |
        |R   KB R
      """ actorAt Pos.E1 get

    king.possibleMoves.toSet filter {
      _.castling.isDefined
    } shouldBe Set(
      Move(Pos.E1, Pos.C1, castling = Some(Move(Pos.A1, Pos.D1)))
    )
  }

  "Black King" should "be able to perform castling" in {
    val king =
      """
        |r   k  r
        |
        |
        |
        |   p
        |
        |p
        |   K
      """ actorAt Pos.E8 get

    king.possibleMoves.toSet filter {
      _.castling.isDefined
    } shouldBe Set(
      Move(Pos.E8, Pos.C8, castling = Some(Move(Pos.A8, Pos.D8))),
      Move(Pos.E8, Pos.G8, castling = Some(Move(Pos.H8, Pos.F8)))
    )
  }

  "King" should "be unable to perform castling after move" in {
    val king =
      """
        |    kb r
        |r
        |
        |
        |   p
        |
        |p
        |   K
      """ move Move(Pos.A7, Pos.A8) actorAt Pos.E8 get

    king.possibleMoves.toSet filter {
      _.castling.isDefined
    } shouldBe Set()
  }
}
