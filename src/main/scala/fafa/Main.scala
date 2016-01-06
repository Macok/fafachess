package fafa

import akka.actor.{ActorSystem, Props}
import fafa.actor.{IOActor, ProtocolHandlerActor}

/**
  * Created by mac on 02.01.16.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()

    val protocolHandlerActor = system.actorOf(Props[ProtocolHandlerActor])
    val ioActor = system.actorOf(Props(classOf[IOActor], protocolHandlerActor))
  }
}
