package fafa.protocol

import fafa.messages._

/**
  * Created by mac on 06.01.16.
  */
class UciProtocol extends Protocol {

  def serializeMessage(message: Message): String = message match {
    case m: ReadyOkMessage => "readyok"
    case m: BestMoveMessage => "bestmove " + (m.move.from.toString + m.move.to.toString).toLowerCase
  }

  def parseMessage(inputLine: String): Option[Message] = inputLine match {
    case "isready" => Some(IsReadyMessage())
    case s: String if s.startsWith("go") => Some(StartCalculatingMessage())
    case _ => None
  }
}
