package fafa.protocol

import fafa.BaseTest
import fafa.api.Role.{Knight, Queen}
import fafa.api.{Board, Pos, Move}
import fafa.messages._
import fafa.api.Board._

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

  it should "parse position message without moves" in {
    parseMessage("position startpos") match {
      case Some(SetPositionMessage(board: Board)) =>
        board.history shouldBe List()
      case _ => fail()
    }
  }

  it should "parse promoting moves" in {
    val board =
      """
        |
        |       P
        |
        |
        |
        |
        |PPPPPPP
        |RNBQKBNR
      """

    uciHandler.parseMove("h7h8n", board) shouldBe Move(Pos.H7, Pos.H8, promoteTo = Some(Knight))
  }

  it should "parse en passant moves" in {
    val boardAfterMove =
      """
        |rnbqkbnr
        |pppppppp
        |
        |P
        |
        |
        | PPPPPPP
        |RNBQKBNR
      """ move Move(Pos.B7, Pos.B5)

    uciHandler.parseMove("a5b6", boardAfterMove) shouldBe Move(Pos.A5, Pos.B6, capturing = Some(Pos.B5))
  }

  it should "serialize readyok message" in {
    serializeMessage(ReadyOkMessage()) shouldBe "readyok"
  }

  it should "serialize bestmove message" in {
    serializeMessage(BestMoveMessage(Move(Pos.E2, Pos.E4))) shouldBe "bestmove e2e4"
  }
}
