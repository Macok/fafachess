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
    val standardMobilityVecs =
      List[MobilityVec]((0, 1)) ++
        (if (isOnInitialPosition) Some(MobilityVec(0, 2)) else None).toList map {
        _ * direction
      }

    val capturingMobilityVecs = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } filter { vec => val posToCapture = pos.addVector(vec)
      posToCapture.isDefined && occupiedByEnemy(posToCapture.get)
    }

    val enPassantMoves = List((-1, 0), (1, 0)) map {
      _ + directionVec
    } flatMap pos.addVector filter {
      nextPos => lastMove.exists { move =>
        val from = nextPos.addVector(directionVec)
        val to = nextPos.addVector(-directionVec)

        from.isDefined && to.isDefined && move.from == from.get && move.to == to.get
      }
    } map { nextPos =>
      Move(pos, nextPos, capturing = nextPos.addVector(-directionVec))
    }

    val standardMoves = standardMobilityVecs flatMap {
      pos.addVector
    } map {
      Move(pos, _)
    }

    val capturingMoves = capturingMobilityVecs flatMap {
      pos.addVector
    } map { to => Move(pos, to, capturing = Some(to)) }

    standardMoves ++ capturingMoves ++ enPassantMoves
  }

  val isOnInitialPosition = (color.isWhite && pos.rowNum == 2) || (!color.isWhite && pos.rowNum == 7)
}
