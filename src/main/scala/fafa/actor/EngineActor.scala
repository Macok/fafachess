package fafa.actor

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import fafa.api.Board
import fafa.messages.{BestMoveMessage, StartCalculatingMessage, ReadyOkMessage, IsReadyMessage}

/**
  * Created by mac on 06.01.16.
  */
class EngineActor extends Actor {

  var protocolHandlerActor: Option[ActorRef] = None

  var board: Board = Board.initialSet

  override def receive: Receive = {
    case actor: ActorRef => protocolHandlerActor = Some(actor)
    case m: IsReadyMessage => protocolHandlerActor.get ! ReadyOkMessage()
    case m: StartCalculatingMessage =>
      val bestMove = board.allPossibleMoves.head
      protocolHandlerActor.get ! BestMoveMessage(bestMove)
  }
}