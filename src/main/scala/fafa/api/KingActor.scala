package fafa.api

import fafa.api.Role.Rook

/**
  * Created by mac on 06.01.16.
  */
class KingActor(piece: Piece,
                pos: Pos,
                board: Board) extends Actor(piece, pos, board) {

  val onInitialPosition: Boolean = if (color.isWhite) pos == Pos.E1 else pos == Pos.E8

  override def possibleMovesNoKingSafetyFilter(allowCastling: Boolean): List[Move] =
    resolveMovesShortRange(role.mobilityVecs) ++
      (if (allowCastling && onInitialPosition && board.isKingSafe(color)) castlingMoves else Nil)

  private def castlingMoves = (if (color.isWhite) List(
    Move(Pos.E1, Pos.G1, castling = Some(Move(Pos.H1, Pos.F1))), // short castling for white
    Move(Pos.E1, Pos.C1, castling = Some(Move(Pos.A1, Pos.D1))) // long castling for white
  )
  else List(
    Move(Pos.E8, Pos.G8, castling = Some(Move(Pos.H8, Pos.F8))), // short castling for black
    Move(Pos.E8, Pos.C8, castling = Some(Move(Pos.A8, Pos.D8))) // long castling for black
  )) filter isCastlingMoveLegal

  def isCastlingMoveLegal(move: Move): Boolean = {
    val rookMove = move.castling.get
    if (piecemap.get(rookMove.from) contains Piece(color, Rook)) {

      // squares between king and rook must be empty
      val squaresBetween =
        (if (rookMove.from.x < move.from.x) rookMove.from to move.from
        else move.from to rookMove.from).drop(1).dropRight(1)

      val squaresBetweenEmpty = squaresBetween forall {
        piecemap.get(_).isEmpty
      }

      // neither piece can have moved
      val hasRookOrKingMoved = history map {
        _.to
      } exists Set(rookMove.from, move.from)

      squaresBetweenEmpty && !hasRookOrKingMoved
    } else false
  }
}
