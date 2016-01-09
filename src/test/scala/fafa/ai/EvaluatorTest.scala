package fafa.ai

import fafa.BaseTest
import Evaluator._
import fafa.api.Board

/**
  * Created by mac on 09.01.16.
  */
class EvaluatorTest extends BaseTest {
  "Evaluator" should "evaluate initial position as 0" in {
    Evaluator.evaluate(Board.initialSet) shouldBe 0
  }

  it should "favor position knight's position at center" in {
    val board =
      """
        |rnbqkbnr
        |pppppppp
        |
        |
        |
        |  N
        |PPPPPPPP
        |R BQKBNR
      """

    Evaluator.evaluate(board) should be > 0
  }

  it should "favor pawn position at front" in {
    val board =
      """
        |rnbqkbnr
        |ppp pppp
        |
        |   p
        |
        |
        |PPPPPPPP
        |RNBQKBNR
      """

    Evaluator.evaluate(board) should be < 0
  }
}
