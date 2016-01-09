package fafa.actor

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import fafa.Config
import fafa.ai.Evaluator
import fafa.api.Color.White
import fafa.api.{Color, Move, Board}
import fafa.messages._

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by mac on 06.01.16.
  */
class EngineActor extends Actor {

  var protocolHandlerActor: Option[ActorRef] = None

  var board: Option[Board] = None

  var minMaxBestMove: Option[Move] = None

  override def receive: Receive = {
    case actor: ActorRef => protocolHandlerActor = Some(actor)
    case IsReadyMessage() => protocolHandlerActor.get ! ReadyOkMessage()
    case m: StartCalculationMessage =>
      minimax(board.get, board.get.turn, 0)
      protocolHandlerActor.get ! BestMoveMessage(minMaxBestMove.get)
    case SetPositionMessage(newBoard: Board) =>
      this.board = Some(newBoard)
  }

  def minimax(board: Board, myColor: Color, depth: Int): Int = {
    if (board.kingPos(myColor).isEmpty) {
      //we lost todo przeniesc do evaluatora
      return -30000 + depth
    }

    if (board.kingPos(!myColor).isEmpty) {
      //we won
      return 30000 - depth
    }

    if (depth == Config.minimaxDepth) {
      return Evaluator.evaluate(board, myColor)
    }

    val allPossibleMoves =
      if (Config.filterMovesLeavingKingInCheck) board.allPossibleMoves
      else board.allPossibleMovesNoKingSafetyFilter

    if (allPossibleMoves.isEmpty) {
      val mate = !board.isKingSafe(board.turn)
      if (!mate)
        return 0 //remis
      else return if (board.turn == myColor) -30000 + depth //przegrana
      else 30000 - depth //wygrana
    }

    val movesWithScores = allPossibleMoves map { move =>
      move -> minimax(board.move(move), myColor, depth + 1)
    }

    val bestMoveWithScore = if (myColor == board.turn) {
      movesWithScores.maxBy {
        _._2
      }
    } else {
      movesWithScores.minBy {
        _._2
      }
    }

    minMaxBestMove = Some(bestMoveWithScore._1)

    bestMoveWithScore._2
  }

  /*
    def bestMove(moves: Iterable[Move]): Move = {
      moves.minBy { move => Evaluator.evaluate(board.get.move(move)) }
    }
    */
}
