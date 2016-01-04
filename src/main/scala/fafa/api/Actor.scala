package fafa.api

import fafa.api.Color._
import fafa.api.Role.Pawn
import MobilityVec._

/**
  * Created by mac on 04.01.16.
  */
case class Actor(piece: Piece,
                 pos: Pos,
                 board: Board) {

  def possibleMoves: List[Move] = {
    potentialMoves //todo
  }

  def occupiedByFriend(pos: Pos) = occupiedBy(pos, color)

  def occupiedByEnemy(pos: Pos) = occupiedBy(pos, !color)

  def occupiedBy(pos: Pos, color: Color) =
    piecemap.get(pos).exists(_.color == color)

  def resolvePawnMoves: List[Move] = {
    val direction = if (color.isWhite) 1 else -1
    val directionVec = (0, 1) * direction
    val standardMobilityVecs = List((0, 1), (0, 2)) map {
      _ * direction
    }

    val captureMobilityVecs = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } filter { vec => val posToCapture = pos.addVector(vec)
      posToCapture.isEmpty && occupiedByEnemy(posToCapture.get)
    }

    val enPassantMoves = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } flatMap pos.addVector filter {
      posToCapture => lastMove.exists { move =>
        val from = posToCapture.addVector(directionVec)
        val to = posToCapture.addVector(-directionVec)

        from.isDefined && to.isDefined && move.from == from.get && move.to == to.get
      }
    } map {Move(pos, _)}
  }

  def potentialMoves: List[Move] = {
    role match {
      case Pawn => resolvePawnMoves
      case role: Role if role.longRange => resolveMovesLongRange(role.mobilityVecs)
      case role: Role => resolveMovesShortRange(role.mobilityVecs)
    }
  }

  def resolveMovesShortRange(mobilityVecs: List[MobilityVec]): List[Move] = mobilityVecs flatMap { vec =>
    pos.addVector(vec)
  } filterNot occupiedByFriend map {
    Move(pos, _)
  }

  def resolveMovesLongRange(mobilityVecs: List[MobilityVec]): List[Move] = {
    val from = pos
    def nextMove(pos: Pos, color: Color, vec: MobilityVec): List[Move] =
      pos.addVector(vec) match {
        // field not in board or occupied by friend
        case None | Some(nextPos) if occupiedByFriend(nextPos) => Nil
        // field occupied by enemy
        case Some(nextPos) if occupiedBy(nextPos, !color) => List(Move(from, nextPos))
        // field free
        case Some(nextPos) => Move(from, nextPos) :: nextMove(nextPos, color, vec)
      }

    mobilityVecs flatMap {
      nextMove(from, color, _)
    }
  }

  val piecemap = board.piecemap
  val lastMove = board.lastMove
  val role = piece.role
  val color = piece.color
}
