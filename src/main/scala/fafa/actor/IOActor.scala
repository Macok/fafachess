package fafa.actor

import java.io.PrintStream

import akka.actor.{Actor, ActorRef}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, blocking}
import scala.io.BufferedSource

/**
  * Created by mac on 06.01.16.
  */
class IOActor(protocolHandler: ActorRef) extends Actor {
  val in: BufferedSource = io.Source.stdin
  val out: PrintStream = System.out

  override def preStart(): Unit = {
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
    case s: String if sender() == self =>
      protocolHandler ! s.trim
  }
}
