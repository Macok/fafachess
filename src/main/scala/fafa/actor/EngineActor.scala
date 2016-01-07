package fafa.actor

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import fafa.api.Board
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
      val bestMove = Random.shuffle(board.get.allPossibleMoves).head
      protocolHandlerActor.get ! BestMoveMessage(bestMove)
    case SetPositionMessage(newBoard: Board) =>
      this.board = Some(newBoard)
  }
}
