package fafa.actor

import akka.actor.{ActorRef, Actor}
import fafa.messages.Message
import fafa.protocol.{ProtocolHandler, UciProtocolHandler}

/**
  * Created by mac on 06.01.16.
  */
class ProtocolHandlerActor(engineActor: ActorRef) extends Actor {
  var ioActor: Option[ActorRef] = None
  val protocol: ProtocolHandler = new UciProtocolHandler //todo allow protocol switch

  override def receive: Receive = {
    case ioActor: ActorRef => this.ioActor = Some(ioActor)

    case inputLine: String => protocol.parseMessage(inputLine) foreach {
      engineActor ! _
    }

    case m: Message => ioActor.get ! protocol.serializeMessage(m)
  }
}
