package fafa.protocol

import fafa.BaseTest
import fafa.api.{Pos, Move}
import fafa.messages.{BestMoveMessage, ReadyOkMessage, StartCalculationMessage, IsReadyMessage}

/**
  * Created by mac on 07.01.16.
  */
class UciProtocolHandlerTest extends BaseTest {

  val uciHandler = new UciProtocolHandler

  "UciProtocolHandler" should "parse isready message" in {
    uciHandler.parseMessage("isready") shouldBe Some(IsReadyMessage())
  }

  it should "parse simple go message" in {
    uciHandler.parseMessage("go") shouldBe Some(StartCalculationMessage())
  }

  it should "parse unknown message as None" in {
    uciHandler.parseMessage("foo") shouldBe None
  }

  it should "serialize readyok message" in {
    uciHandler.serializeMessage(ReadyOkMessage()) shouldBe "readyok"
  }

  it should "serialize bestmove message" in {
    uciHandler.serializeMessage(BestMoveMessage(Move(Pos.E2, Pos.E4))) shouldBe "bestmove e2e4"
  }
}
