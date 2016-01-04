package fafa.api

import fafa.api.Role._

/**
  * Created by mac on 04.01.16.
  */
abstract class Actor(piece: Piece,
                     pos: Pos,
                     board: Board) {

  def possibleMoves: List[Move] = {
    potentialMoves //todo
  }

  def occupiedByFriend(pos: Pos) = occupiedBy(pos, color)

  def occupiedByEnemy(pos: Pos) = occupiedBy(pos, !color)

  def occupiedBy(pos: Pos, color: Color) =
    piecemap.get(pos).exists(_.color == color)


  def potentialMoves: List[Move]

  def resolveMovesShortRange(mobilityVecs: List[MobilityVec]): List[Move] = mobilityVecs flatMap { vec =>
    pos.addVector(vec)
  } filterNot occupiedByFriend map {
    Move(pos, _)
  }

  val piecemap = board.piecemap
  val lastMove = board.lastMove
  val role = piece.role
  val color = piece.color
}

object Actor {
  def apply(piece: Piece, pos: Pos, board: Board): Actor = piece.role match {
    case Pawn => PawnActor(piece, pos, board)
    case Rook | Queen | Bishop => LongRangeActor(piece, pos, board)
    case _ => ShortRangeActor(piece, pos, board)
  }
}