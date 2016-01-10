package fafa.ai

import fafa.Config
import fafa.api.{Move, Board}

/**
  * Created by mac on 10.01.16.
  */
class Search(board: Board) {

  var bestFoundMove: Option[Move] = None

  /**
    * Implementation of negamax algorithm to find best move
    */
  def negamax(board: Board, depth: Int): Int = {
    if (board.kingPos(board.turn).isEmpty) {
      //we lost todo przeniesc do evaluatora
      return -30000 + depth
    }

    if (board.kingPos(!board.turn).isEmpty) {
      //we won
      return 30000 - depth
    }

    if (depth == Config.minimaxDepth) {
      return Evaluator.evaluate(board, board.turn)
    }

    val allPossibleMoves =
      if (Config.filterMovesLeavingKingInCheck) board.allPossibleMoves
      else board.allPossibleMovesNoKingSafetyFilter

    if (allPossibleMoves.isEmpty) {
      val mate = !board.isKingSafe(board.turn)
      return if (!mate) 0 else -30000 + depth
    }

    val movesWithScores = allPossibleMoves map { move =>
      move -> negamax(board.move(move), depth + 1)
    }

    val bestMoveWithScore = movesWithScores.maxBy {
      -_._2
    }

    bestFoundMove = Some(bestMoveWithScore._1)

    -bestMoveWithScore._2
  }

  def search(): Move = {
    negamax(board, 0)
    bestFoundMove.get
  }
}
