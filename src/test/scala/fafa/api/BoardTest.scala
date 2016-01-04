package fafa.api

import fafa.api.Board._
import fafa.api.Color.{Black, White}
import fafa.api.Role._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by mac on 03.01.16.
  */
class BoardTest extends FlatSpec with Matchers {

  implicit def tupleToPiece(t: (Color, Role)): Piece = Piece(t._1, t._2)

  val at = Board.initialSet.piecemap.get _


  def row(rowNum: Int) = Pos.all.slice((rowNum - 1) * BoardSize, rowNum * BoardSize) flatMap {
    at(_)
  }

  "Board" should "place white pieces correctly" in {

    row(1) should be(
      Seq[Piece](
        (White, Rook), (White, Knight), (White, Bishop), (White, Queen), (White, King), (White, Bishop), (White, Knight), (White, Rook)
      ))

    row(2) should be(Seq.fill[Piece](8)((White, Pawn)))

    row(8) should be(
      Seq[Piece](
        (Black, Rook), (Black, Knight), (Black, Bishop), (Black, Queen), (Black, King), (Black, Bishop), (Black, Knight), (Black, Rook)
      ))

    row(7) should be(Seq.fill[Piece](8)((Black, Pawn)))
  }

}
