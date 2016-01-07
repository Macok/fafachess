package fafa.actor

import java.io.PrintStream

import akka.actor.{Actor, ActorRef}
import akka.event.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, blocking}
import scala.io.BufferedSource

/**
  * Created by mac on 06.01.16.
  */
class IOActor(protocolHandler: ActorRef) extends Actor {
  val log = Logging(context.system, this)

  val in: BufferedSource = io.Source.stdin
  val out: PrintStream = System.out

  override def preStart(): Unit = {

    log.debug("IOActor preStart")

    Future {
      blocking {
        for (line <- in.getLines()) {
          self ! line
        }
      }
    }
  }

  override def receive: Receive = {
    case s: String if sender() == protocolHandler =>
      out.println(s)
      out.flush()
    case s: String if sender() == self =>
      protocolHandler ! s.trim
  }
}
