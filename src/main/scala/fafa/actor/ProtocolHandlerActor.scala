package fafa.actor

import akka.actor.Actor
import fafa.messages.Message
import fafa.protocol.UciProtocol

/**
  * Created by mac on 06.01.16.
  */
class ProtocolHandlerActor extends Actor {
  val protocol = UciProtocol() //todo allow protocol switch

  override def receive: Receive = {
    case inputLine: String => protocol.parseMessage(inputLine)
    case m: Message => protocol.serializeMessage(m)
  }
}
