package fafa.api

/**
  * Created by mac on 06.01.16.
  */
case class KingActor(piece: Piece,
                     pos: Pos,
                     board: Board) extends Actor(piece, pos, board) {
  override protected def potentialMoves: List[Move] =
    resolveMovesShortRange(role.mobilityVecs) ++ castlingMoves

  private def castlingMoves = if(color.isWhite) List((Move()))
}
