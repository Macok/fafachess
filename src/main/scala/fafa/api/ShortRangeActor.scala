package fafa.api

/**
  * Created by mac on 04.01.16.
  */
case class ShortRangeActor(piece: Piece,
                           pos: Pos,
                           board: Board) extends Actor(piece, pos, board) {

  override def potentialMoves = resolveMovesShortRange(role.mobilityVecs)

  protected def resolveMovesShortRange(mobilityVecs: List[MobilityVec]): List[Move] = mobilityVecs flatMap { vec =>
    pos.addVector(vec)
  } filterNot occupiedByFriend map { to =>
    val capturing = if (occupiedByEnemy(to)) Some(to) else None
    Move(pos, to, capturing)
  }
}
