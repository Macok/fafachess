package fafa.api

import fafa.api.Role._

/**
  * Created by mac on 04.01.16.
  */
abstract class Actor(piece: Piece,
                     val pos: Pos,
                     board: Board) {

  lazy val possibleMoves: List[Move] = {
    possibleMovesNoKingSafetyFilter() filter {
      board.move(_).isKingSafe(color)
    }
  }

  // possible moves without checking for king's safety
  def possibleMovesNoKingSafetyFilter(allowCastling: Boolean = true): List[Move]

  protected def occupiedByFriend(pos: Pos) = occupiedBy(pos, color)

  protected def occupiedByEnemy(pos: Pos) = occupiedBy(pos, !color)

  private def occupiedBy(pos: Pos, color: Color) =
    piecemap.get(pos).exists(_.color == color)

  protected def resolveMovesShortRange(mobilityVecs: List[Vec]): List[Move] = mobilityVecs flatMap { vec =>
    pos.addVector(vec)
  } filterNot occupiedByFriend map { to =>
    val capturing = if (occupiedByEnemy(to)) Some(to) else None
    Move(pos, to, capturing)
  }

  val piecemap = board.pieces
  val history = board.history
  val role = piece.role
  val color = piece.color
}

object Actor {
  def apply(piece: Piece, pos: Pos, board: Board): Actor = piece.role match {
    case Pawn => new PawnActor(piece, pos, board)
    case King => new KingActor(piece, pos, board)
    case Knight => new KnightActor(piece, pos, board)
    case _ => new LongRangeActor(piece, pos, board)
  }
}