package fafa.ai

import fafa.api.{Pos, Piece, Color, Board}
import PieceSquareTables._

/**
  * Created by mac on 09.01.16.
  */
object Evaluator {
  def evaluate(board: Board, color: Color): Int = {
    evaluateColorPosition(board, color) - evaluateColorPosition(board, !color)
  }

  def evaluateColorPosition(board: Board, color: Color): Int = {
    board.piecesOf(color) map {
      case (pos: Pos, piece: Piece) =>
        val pieceSquareTable = pieceToPieceSquareTable.get(piece).get
        rolesValues(piece.role) + pieceSquareTable(pos.x)(pos.y)
    } sum
  }
}
