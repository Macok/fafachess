package fafa

import akka.actor.{ActorSystem, Props}
import fafa.actor.{EngineActor, IOActor, ProtocolHandlerActor}

/**
  * Created by mac on 02.01.16.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()

    val engineActor = system.actorOf(Props[EngineActor])
    val protocolHandlerActor = system.actorOf(Props(classOf[ProtocolHandlerActor], engineActor))
    val ioActor = system.actorOf(Props(classOf[IOActor], protocolHandlerActor))

    protocolHandlerActor ! ioActor
    engineActor ! protocolHandlerActor
  }
}
