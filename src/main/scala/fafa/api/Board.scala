package fafa.api

import fafa.api.Color._
import fafa.api.Role._
import Board._

/**
  * Created by mac on 03.01.16.
  */
case class Board(piecemap: Map[Pos, Piece], lastMove: Option[Move] = None) {

  val actors = piecemap.map { case (pos, piece) => (pos, Actor(piece, pos, this)) }

  def actorAt(pos: Pos) = actors.get(pos)

  def move(move: Move) = {
    val piece = piecemap.get(move.from).get
    val newPiecemap: Map[Pos, Piece] = piecemap - move.from -- move.capturing.toList + (move.to -> piece)

    copy(newPiecemap, Some(move))
  }

  override def toString: String = {
    (for (y <- (0 until BoardSize).reverse) yield {
      (for (x <- 0 until BoardSize) yield {
        piecemap.get(Pos(x, y)).fold(' ')(piece => FENNotation.symbol(piece))
      }).mkString
    }).mkString("\n")
  }
}

object Board {
  val BoardSize = 8

  val initialSet: Board =
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

  implicit def fromString(boardStr: String): Board = {
    val strippedBoardStr = boardStr.trim.stripMargin
    val charToRole: Map[Char, Role] = FENNotation.roleChars.map(_.swap)
    val pieces =
      for (
        (line, lineIndex) <- strippedBoardStr.lines.zipWithIndex;
        (c, cIndex) <- line.zipWithIndex;
        role <- charToRole.get(c.toLower)
      ) yield {
        val color = if (c.isLower) Black else White
        val pos = Pos(cIndex, BoardSize - 1 - lineIndex)

        (pos, Piece(color, role))
      }

    Board(pieces.toMap)
  }
}
