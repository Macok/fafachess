package fafa.api

import fafa.api.Color._
import fafa.api.Role._
import Board._

/**
  * Created by mac on 03.01.16.
  */
case class Board(piecemap: Map[Pos, Piece]) {

  def resolveMovesShortRange(piece: Piece): List[Move] = piece.role.mobilityVecs flatMap { vec =>
    piece.pos.addVector(vec)
  } filter { to =>
    piecemap.get(to).forall(_.color == !piece.color)
  } map {
    Move(piece.pos, _)
  }

  def resolveMovesLongRange(piece: Piece): List[Move] = {
    val from = piece.pos
    def nextMove(pos: Pos, color: Color, vec: MobilityVec): List[Move] =
      pos.addVector(vec) match {
        // field not in board or occupied by friend
        case None | Some(nextPos) if occupiedBy(nextPos, color) => Nil
        // field occupied by enemy
        case Some(nextPos) if occupiedBy(nextPos, !color) => List(Move(from, nextPos))
        // field free
        case Some(nextPos) => Move(from, nextPos) :: nextMove(nextPos, color, vec)
      }

    piece.role.mobilityVecs flatMap {
      nextMove(from, piece.color, _)
    }
  }

  def occupiedBy(pos: Pos, color: Color) =
    piecemap.get(pos).exists(_.color == color)

  def resolvePawnMoves(piece: Piece): List[Move] = ???

  def possibleMoves(piece: Piece): List[Move] = {
    piece.role match {
      case Pawn => resolvePawnMoves(piece)
      case role: Role if role.longRange => resolveMovesLongRange(piece)
      case role: Role => resolveMovesShortRange(piece)
    }
  }

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
        Piece(Pos(x, 0), White, initSeq(x)),
        Piece(Pos(x, 1), White, Pawn),

        //black in this row
        Piece(Pos(x, BoardSize - 1), Black, initSeq(x)),
        Piece(Pos(x, BoardSize - 2), Black, Pawn)
      ) map (piece => (piece.pos, piece))
    }

    Board(pieces.toMap)
  }
}
