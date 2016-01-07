package fafa.protocol

import fafa.BaseTest
import fafa.api.{Board, Pos, Move}
import fafa.messages._

/**
  * Created by mac on 07.01.16.
  */
class UciProtocolHandlerTest extends BaseTest {

  val uciHandler = new UciProtocolHandler

  val parseMessage = uciHandler.parseMessage _
  val serializeMessage = uciHandler.serializeMessage _

  "UciProtocolHandler" should "parse isready message" in {
    parseMessage("isready") shouldBe Some(IsReadyMessage())
  }

  it should "parse simple go message" in {
    parseMessage("go") shouldBe Some(StartCalculationMessage())
  }

  it should "parse unknown message as None" in {
    parseMessage("foo") shouldBe None
  }

  it should "parse position message" in {
    parseMessage("position startpos moves e2e4 d7d5") match {
      case Some(SetPositionMessage(board: Board)) =>
        board.history shouldBe List(Move(Pos.D7, Pos.D5), Move(Pos.E2, Pos.E4))
      case _ => fail()
    }

  }


  it should "serialize readyok message" in {
    serializeMessage(ReadyOkMessage()) shouldBe "readyok"
  }

  it should "serialize bestmove message" in {
    serializeMessage(BestMoveMessage(Move(Pos.E2, Pos.E4))) shouldBe "bestmove e2e4"
  }
}
