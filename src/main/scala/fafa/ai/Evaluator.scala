package fafa.ai

import fafa.api.{Pos, Piece, Color, Board}
import PieceSquareTables._

/**
  * Created by mac on 09.01.16.
  */
case class Evaluator(board: Board) {
  lazy val evaluate: Int = {
    evaluateColorPosition(board.turn) - evaluateColorPosition(!board.turn)
  }

  def evaluateColorPosition(color: Color): Int = {
    board.piecesOf(color) map {
      case (pos: Pos, piece: Piece) =>
        val pieceSquareTable = pieceToPieceSquareTable.get(piece).get
        rolesValues(piece.role) + pieceSquareTable(pos.x)(pos.y)
    } sum
  }
}
