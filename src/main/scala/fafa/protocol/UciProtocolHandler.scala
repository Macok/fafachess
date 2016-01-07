package fafa.protocol

import fafa.api.{Board, Pos, Move}
import fafa.messages._

/**
  * Created by mac on 06.01.16.
  */
class UciProtocolHandler extends ProtocolHandler {

  def serializeMessage(message: Message): String = message match {
    case m: ReadyOkMessage => "readyok"
    case m: BestMoveMessage => "bestmove " + (m.move.from.toString + m.move.to.toString).toLowerCase
  }

  def parseGoMessage(s: String): Option[Message] = {
    // todo handle additional parameters
    Some(StartCalculationMessage())
  }

  def parsePositionMessage(s: String): Option[Message] = {
    val tokens = s.split(" ")

    val moves = tokens.dropWhile(_ != "moves").drop(1) map parseMove
    val board = moves.foldLeft[Board](Board.initialSet)(_.move(_))

    //todo parse startpos
    Some(SetPositionMessage(board))
  }

  def parseMove(moveStr: String): Move = {
    val tokens = moveStr.grouped(2)
    val positions = tokens map {
      Pos.fromString
    } toArray

    // todo promotions

    Move(positions(0), positions(1))
  }

  def parseMessage(inputLine: String): Option[Message] = inputLine match {
    case "isready" => Some(IsReadyMessage())
    case s: String if s.startsWith("go") => parseGoMessage(s)
    case s: String if s.startsWith("position") => parsePositionMessage(s)
    case _ => None
  }
}
