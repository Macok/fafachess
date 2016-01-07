package fafa.api

/**
  * Created by mac on 04.01.16.
  */
class ShortRangeActor(piece: Piece,
                      pos: Pos,
                      board: Board) extends Actor(piece, pos, board) {

  override lazy val possibleMovesNoKingSafetyFilter = resolveMovesShortRange(role.mobilityVecs)
}
