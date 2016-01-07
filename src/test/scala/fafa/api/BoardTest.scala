package fafa.api

import fafa.api.Board._
import fafa.api.Color.{Black, White}
import fafa.api.Role._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by mac on 03.01.16.
  */
class BoardTest extends ChessApiTest {

  implicit def tupleToPiece(t: (Color, Role)): Piece = Piece(t._1, t._2)

  val at = Board.initialSet.pieces.get _

  def rank(rowNum: Int) = Pos.all.slice((rowNum - 1) * BoardSize, rowNum * BoardSize) flatMap {
    at(_)
  }

  "Board" should "place white pieces according to the rules" in {
    rank(1) should be(
      Seq[Piece](
        (White, Rook), (White, Knight), (White, Bishop), (White, Queen), (White, King), (White, Bishop), (White, Knight), (White, Rook)
      ))

    rank(2) should be(Seq.fill[Piece](8)((White, Pawn)))

    rank(8) should be(
      Seq[Piece](
        (Black, Rook), (Black, Knight), (Black, Bishop), (Black, Queen), (Black, King), (Black, Bishop), (Black, Knight), (Black, Rook)
      ))

    rank(7) should be(Seq.fill[Piece](8)((Black, Pawn)))
  }

  it should "reposition pawn after move" in {
    val boardAfterMove =
      """
        |
        |
        |
        |
        |
        |
        |   P
        |
      """ move Move(Pos.D2, Pos.D4)

    boardAfterMove.pieces should be (Map[Pos, Piece](Pos.D4 -> Piece(White, Pawn)))
    boardAfterMove.lastMove should be (Some(Move(Pos.D2, Pos.D4)))
  }

  it should "perform castling moves" in {
    val boardAfterMove =
      """
        |
        |
        |
        |
        |
        |
        |
        |R   KB R
      """ move Move(Pos.E1, Pos.C1, castling = Some(Move(Pos.A1, Pos.D1)))

    boardAfterMove pieceAt Pos.C1 shouldBe Some(Piece(White, King))
    boardAfterMove pieceAt Pos.D1 shouldBe Some(Piece(White, Rook))
  }
}
