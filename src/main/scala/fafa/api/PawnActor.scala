package fafa.api

import MobilityVec._

/**
  * Created by mac on 04.01.16.
  */
case class PawnActor(piece: Piece,
                     pos: Pos,
                     board: Board) extends Actor(piece, pos, board) {

  override def potentialMoves: List[Move] = resolvePawnMoves

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
    } map {
      Move(pos, _)
    }

    resolveMovesShortRange(standardMobilityVecs ++ captureMobilityVecs) ++ enPassantMoves
  }
}
