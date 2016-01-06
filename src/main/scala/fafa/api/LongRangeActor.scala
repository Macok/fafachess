package fafa.api

/**
  * Created by mac on 04.01.16.
  */
class LongRangeActor(piece: Piece,
                     pos: Pos,
                     board: Board) extends Actor(piece, pos, board) {

  override def potentialMoves = resolveMovesLongRange

  def resolveMovesLongRange: List[Move] = {
    val from = pos
    def nextMove(pos: Pos, color: Color, vec: Vec): List[Move] =
      pos.addVector(vec) match {
        // field not in board
        case None => Nil
        // field occupied by friend
        case Some(nextPos) if occupiedByFriend(nextPos) => Nil
        // field occupied by enemy
        case Some(nextPos) if occupiedByEnemy(nextPos) => List(Move(from, nextPos, capturing = Some(nextPos)))
        // field free
        case Some(nextPos) => Move(from, nextPos) :: nextMove(nextPos, color, vec)
      }

    role.mobilityVecs flatMap {
      nextMove(from, color, _)
    }
  }
}
