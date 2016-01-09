package fafa.actor

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import fafa.Config
import fafa.ai.Evaluator
import fafa.api.{Move, Board}
import fafa.messages._

import scala.util.Random

/**
  * Created by mac on 06.01.16.
  */
class EngineActor extends Actor {

  var protocolHandlerActor: Option[ActorRef] = None

  var board: Option[Board] = None

  override def receive: Receive = {
    case actor: ActorRef => protocolHandlerActor = Some(actor)
    case IsReadyMessage() => protocolHandlerActor.get ! ReadyOkMessage()
    case m: StartCalculationMessage =>
      val allPossibleMoves =
        if (Config.filterMovesLeavingKingInCheck) board.get.allPossibleMoves
        else board.get.allPossibleMovesNoKingSafetyFilter

      protocolHandlerActor.get ! BestMoveMessage(bestMove(allPossibleMoves))
    case SetPositionMessage(newBoard: Board) =>
      this.board = Some(newBoard)
  }

  def bestMove(moves: Iterable[Move]): Move = {
    // after move it's opponents turn, so we want min value
    moves.minBy { move => Evaluator.evaluate(board.get.move(move)) }
  }
}
