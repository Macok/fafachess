package fafa.api

/**
  * Created by mac on 04.01.16.
  */
class KnightActor(piece: Piece,
                  pos: Pos,
                  board: Board) extends Actor(piece, pos, board) {

  override def possibleMovesNoKingSafetyFilter(allowCastling: Boolean) = resolveMovesShortRange(role.mobilityVecs)
}
