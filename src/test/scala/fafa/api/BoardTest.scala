package fafa.api

import fafa.api.Board._
import fafa.api.Color.{Black, White}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by mac on 03.01.16.
  */
class BoardTest extends FlatSpec with Matchers {

  implicit def stringToBoard(boardStr: String): Board = {
    val strippedBoardStr = boardStr.stripMargin.trim
    val charToRole: Map[Char, Role] = FENNotation.roleChars.map(_.swap)
    val pieces =
      for (
        (line, lineIndex) <- strippedBoardStr.stripMargin.trim.lines.zipWithIndex;
        (c, cIndex) <- line.zipWithIndex;
        role <- charToRole.get(c.toLower)
      ) yield {
        val color = if (c.isLower) Black else White
        val pos = Pos(cIndex, BoardSize - 1 - lineIndex)

        (pos, Piece(color, role))
      }

    Board(pieces.toMap)
  }

  "Board" should "place pieces correctly" in {
    val board: Board =
      """
        |rnbqkbnr
        |pppppppp
        |
        |
        |
        |
        |PPPPPPPP
        |RNBQKBNR
      """
    board should be(Board.initialSet)
  }
}
