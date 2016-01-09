package fafa.ai

import fafa.api.{Pos, Piece, Color, Board}
import PieceSquareTables._

/**
  * Created by mac on 09.01.16.
  */
object Evaluator {
  def evaluate(board: Board): Int = {
    evaluateColorPosition(board, board.turn) - evaluateColorPosition(board, !board.turn)
  }

  def evaluateColorPosition(board: Board, color: Color): Int = {
    board.piecesOf(color) map {
      case (pos: Pos, piece: Piece) =>
        val pieceSquareTable = pieceToPieceSquareTable.get(piece).get
        pieceSquareTable(pos.x)(pos.y)
    } sum
  }
}
