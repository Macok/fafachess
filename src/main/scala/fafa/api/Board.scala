package fafa.api

import fafa.api.Color._
import fafa.api.Role._
import Board._

/**
  * Created by mac on 03.01.16.
  */
case class Board(pieces: Map[Pos, Piece], history: List[Move] = List(), turn: Color = White) {

  val actors: Map[Pos, Actor] = pieces.map { case (pos, piece) => (pos, Actor(piece, pos, this)) }

  def actorAt(pos: Pos): Option[Actor] = actors.get(pos)

  def pieceAt(pos: Pos): Option[Piece] = pieces.get(pos)

  def piecesOf(color: Color): Map[Pos, Piece] = pieces filter {
    _._2.color == color
  }

  def actorsOf(color: Color): Iterable[Actor] = actors.values filter {
    _.color == color
  }

  def allPossibleMoves = actorsOf(turn) flatMap {
    _.possibleMoves
  }

  def allPossibleMovesNoKingSafetyFilter = actorsOf(turn) flatMap {
    _.possibleMovesNoKingSafetyFilter()
  }

  def move(move: Move): Board =
    if (move.castling.isDefined) castlingMove(move)
    else standardMove(move)

  private def standardMove(move: Move) = {
    val piece = pieces.get(move.from).get
    val newPiece = if (move.promoteTo.isDefined) piece.copy(role = move.promoteTo.get) else piece
    val newPiecemap: Map[Pos, Piece] = pieces - move.from -- move.capturing.toList + (move.to -> newPiece)

    copy(newPiecemap, move :: history, turn = !turn)
  }

  private def castlingMove(move: Move) = {
    assert(move.capturing.isEmpty)
    assert(move.promoteTo.isEmpty)

    val rookMove = move.castling.get
    val king = pieces.get(move.from).get
    val rook = pieces.get(rookMove.from).get

    val newPiecemap = pieces - move.from - rookMove.from + (move.to -> king) + (rookMove.to -> rook)

    copy(pieces = newPiecemap, move :: history, turn = !turn)
  }

  def isKingSafe(color: Color): Boolean = {
    val kingPosOption = pieces.find(_._2 == Piece(color, King)) map {
      _._1
    }
    if (kingPosOption.isEmpty) return true // for testing convenience

    val kingInCheck = this.actorsOf(!color) flatMap {
      _.possibleMovesNoKingSafetyFilter(allowCastling = false)
    } flatMap {
      _.capturing
    } exists {
      _ == kingPosOption.get
    }

    !kingInCheck
  }

  def withTurn(color: Color) = copy(turn = color)

  val lastMove = history.headOption

  override def toString: String = {
    (for (y <- (0 until BoardSize).reverse) yield {
      (for (x <- 0 until BoardSize) yield {
        pieces.get(Pos(x, y)).fold(' ')(piece => FENNotation.symbol(piece))
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
    val pieces =
      for (
        (line, lineIndex) <- strippedBoardStr.lines.zipWithIndex;
        (c, cIndex) <- line.zipWithIndex;
        role <- FENNotation.charToRole.get(c.toLower)
      ) yield {
        val color = if (c.isLower) Black else White
        val pos = Pos(cIndex, BoardSize - 1 - lineIndex)

        (pos, Piece(color, role))
      }

    Board(pieces.toMap)
  }
}
