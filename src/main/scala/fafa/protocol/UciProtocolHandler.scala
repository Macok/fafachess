package fafa.protocol

import fafa.api.Role.Pawn
import fafa.api._
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

    val movesTokens = tokens.dropWhile(_ != "moves").drop(1).iterator

    //todo parse startpos
    val initBoard = Board.initialSet
    val board = movesTokens.foldLeft[Board](initBoard) { (board: Board, moveToken: String) =>
      board.move(parseMove(moveToken, board))
    }

    Some(SetPositionMessage(board))
  }

  def parseMove(moveToken: String, board: Board): Move = {
    assert(moveToken.length == 4 || moveToken.length == 5)

    val tokens = moveToken.grouped(2) toArray
    val positions = tokens.take(2) map {
      Pos.fromString
    }

    val from = positions(0)
    val to = positions(1)
    val promoteTo = detectPromotion(tokens)

    // instead of manually parsing the move, find it in actor's possible moves
    val move = board.actorAt(from).get.possibleMoves.find(move => move.from == from && move.to == to)
    if (move.isEmpty)
      throw new Exception("could not parse illegal move: " + moveToken)

    move.get.copy(promoteTo = promoteTo)
  }

  def detectPromotion(tokens: Array[String]): Option[Role] = {
    if (tokens.length == 3) Some(FENNotation.charToRole(tokens(2).head))
    else None
  }

  def parseMessage(inputLine: String): Option[Message] = inputLine match {
    case "isready" => Some(IsReadyMessage())
    case s: String if s.startsWith("go") => parseGoMessage(s)
    case s: String if s.startsWith("position") => parsePositionMessage(s)
    case _ => None
  }
}
