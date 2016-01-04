package fafa.api

import fafa.api.Color._
import fafa.api.Role._
import Board._

/**
  * Created by mac on 03.01.16.
  */
case class Board(piecemap: Map[Pos, Piece], lastMove: Option[Move] = None) {

  implicit def mapEntryToActor: ((Pos, Piece)) => Actor = {
    case (pos, piece) => Actor(piece, pos, this)
  }

  def allPossibleMoves: List[Move] = piecemap.flatMap {
    _.possibleMoves
  } toList

  override def toString: String = {
    (for (y <- (0 until BoardSize).reverse) yield {
      (for (x <- 0 until BoardSize) yield {
        piecemap.get(Pos(x, y)).fold(' ')(piece => FENNotation.symbol(piece))
      }).mkString
    }).mkString("\n")
  }
}

object Board {
  val BoardSize = 8

  def initialSet: Board = {
    val initSeq = Seq(Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook)

    val pieces = (0 until BoardSize) flatMap { x: Int =>
      Seq(
        //white in this row
        (Pos(x, 0), Piece(White, initSeq(x))),
        (Pos(x, 1), Piece(White, Pawn)),

        //black in this row
        (Pos(x, BoardSize - 1), Piece(Black, initSeq(x))),
        (Pos(x, BoardSize - 2), Piece(Black, Pawn))
      )
    }

    Board(pieces.toMap)
  }
}
