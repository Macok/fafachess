package fafa.api

/**
  * Created by mac on 04.01.16.
  */
case class ShortRangeActor(piece: Piece,
                           pos: Pos,
                           board: Board) extends Actor(piece, pos, board) {

  override def potentialMoves = resolveMovesShortRange(role.mobilityVecs)
}
