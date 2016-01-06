package fafa.api

import Vec._
import fafa.api.Role.Queen

/**
  * Created by mac on 04.01.16.
  */
case class PawnActor(piece: Piece,
                     pos: Pos,
                     board: Board) extends Actor(piece, pos, board) {

  override def potentialMoves: List[Move] = resolvePawnMoves

  val direction = if (color.isWhite) 1 else -1
  val directionVec = (0, 1) * direction

  def resolvePawnMoves: List[Move] = {
    val oneSquareForward = pos.addVector(directionVec)
    val twoSquaresForward =
      if (oneSquareForward.exists(isEmpty) && isOnInitialPosition) pos.addVector(directionVec * 2)
      else None

    val standardMoves = oneSquareForward.toList ++ twoSquaresForward filter isEmpty map {
      Move(pos, _)
    }

    val standardCapturingMoves = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } filter { vec => val posToCapture = pos.addVector(vec)
      posToCapture.isDefined && occupiedByEnemy(posToCapture.get)
    } flatMap {
      pos.addVector
    } map { to => Move(pos, to, capturing = Some(to)) }

    val enPassantMoves = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } flatMap pos.addVector filter {
      nextPos => board.lastMove.exists { move =>
        val from = nextPos.addVector(directionVec)
        val to = nextPos.addVector(-directionVec)

        from.isDefined && to.isDefined && move.from == from.get && move.to == to.get
      }
    } map { nextPos =>
      Move(pos, nextPos, capturing = nextPos.addVector(-directionVec))
    }

    // include promotions
    // todo allow weak promotions
    (standardMoves ++ standardCapturingMoves ++ enPassantMoves) map {
      case move if isLastRank(move.to) => move.copy(promoteTo = Some(Queen))
      case move => move
    }
  }

  private def isLastRank(pos: Pos) = (color.isWhite && pos.rowNum == Board.BoardSize) ||
    (!color.isWhite && pos.rowNum == 1)

  private val isOnInitialPosition = (color.isWhite && pos.rowNum == 2) ||
    (!color.isWhite && pos.rowNum == Board.BoardSize - 1)

  private def isEmpty(pos: Pos) = piecemap.get(pos).isEmpty
}
