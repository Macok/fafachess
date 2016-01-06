package fafa.actor

import akka.actor.Actor

/** {
  * Created by mac on 06.01.16.
  */
class ProtocolHandlerActor extends Actor {
  override def receive: Receive = {
    case s: String => println("PROTOCOLHANDLER: " + s)
  }
}
