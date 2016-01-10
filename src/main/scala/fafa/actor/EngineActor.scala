package fafa.actor

import akka.actor.{Actor, ActorRef}
import fafa.ai.Search
import fafa.api.{Board, Move}
import fafa.messages._
import akka.pattern.pipe
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

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
      Future {
        new Search(board.get).search()
      } pipeTo self
    case bestMove: Move =>
      protocolHandlerActor.get ! BestMoveMessage(bestMove)
    case SetPositionMessage(newBoard: Board) =>
      this.board = Some(newBoard)
  }
}
