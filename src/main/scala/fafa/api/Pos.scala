package fafa.api

import Pos._

/**
  * Created by mac on 03.01.16.
  */
case class Pos(x: Int, y: Int) {
  def addVector(vec: Vec): Option[Pos] =
    posOption(x + vec.dx, y + vec.dy)

  val rowNum = y + 1

  def to(other: Pos): Seq[Pos] =
    for (y <- this.y to other.y;
         x <- this.x to other.x) yield Pos(x, y)

  override def toString = {
    "" + ('A' + x).asInstanceOf[Char] + (y + 1).toString
  }
}

object Pos {
  def posOption(x: Int, y: Int): Option[Pos] = {
    if (posInsideBoard(Pos(x, y))) Some(Pos(x, y))
    else None
  }

  def fromString(str: String): Pos = {
    assert(str.length == 2)
    val chars = str.toUpperCase.toCharArray
    val pos = Pos(chars(0) - 'A', chars(1) - '1')
    assert(posInsideBoard(pos))

    pos
  }

  def posInsideBoard(pos: Pos): Boolean = {
    pos.x >= 0 && pos.x < Board.BoardSize &&
      pos.y >= 0 && pos.y < Board.BoardSize
  }

  val A1 = Pos(0, 0)
  val B1 = Pos(1, 0)
  val C1 = Pos(2, 0)
  val D1 = Pos(3, 0)
  val E1 = Pos(4, 0)
  val F1 = Pos(5, 0)
  val G1 = Pos(6, 0)
  val H1 = Pos(7, 0)
  val A2 = Pos(0, 1)
  val B2 = Pos(1, 1)
  val C2 = Pos(2, 1)
  val D2 = Pos(3, 1)
  val E2 = Pos(4, 1)
  val F2 = Pos(5, 1)
  val G2 = Pos(6, 1)
  val H2 = Pos(7, 1)
  val A3 = Pos(0, 2)
  val B3 = Pos(1, 2)
  val C3 = Pos(2, 2)
  val D3 = Pos(3, 2)
  val E3 = Pos(4, 2)
  val F3 = Pos(5, 2)
  val G3 = Pos(6, 2)
  val H3 = Pos(7, 2)
  val A4 = Pos(0, 3)
  val B4 = Pos(1, 3)
  val C4 = Pos(2, 3)
  val D4 = Pos(3, 3)
  val E4 = Pos(4, 3)
  val F4 = Pos(5, 3)
  val G4 = Pos(6, 3)
  val H4 = Pos(7, 3)
  val A5 = Pos(0, 4)
  val B5 = Pos(1, 4)
  val C5 = Pos(2, 4)
  val D5 = Pos(3, 4)
  val E5 = Pos(4, 4)
  val F5 = Pos(5, 4)
  val G5 = Pos(6, 4)
  val H5 = Pos(7, 4)
  val A6 = Pos(0, 5)
  val B6 = Pos(1, 5)
  val C6 = Pos(2, 5)
  val D6 = Pos(3, 5)
  val E6 = Pos(4, 5)
  val F6 = Pos(5, 5)
  val G6 = Pos(6, 5)
  val H6 = Pos(7, 5)
  val A7 = Pos(0, 6)
  val B7 = Pos(1, 6)
  val C7 = Pos(2, 6)
  val D7 = Pos(3, 6)
  val E7 = Pos(4, 6)
  val F7 = Pos(5, 6)
  val G7 = Pos(6, 6)
  val H7 = Pos(7, 6)
  val A8 = Pos(0, 7)
  val B8 = Pos(1, 7)
  val C8 = Pos(2, 7)
  val D8 = Pos(3, 7)
  val E8 = Pos(4, 7)
  val F8 = Pos(5, 7)
  val G8 = Pos(6, 7)
  val H8 = Pos(7, 7)

  val all = List(A1, B1, C1, D1, E1, F1, G1, H1, A2, B2, C2, D2, E2, F2, G2, H2, A3, B3, C3, D3, E3, F3, G3, H3, A4, B4, C4, D4, E4, F4, G4, H4, A5, B5, C5, D5, E5, F5, G5, H5, A6, B6, C6, D6, E6, F6, G6, H6, A7, B7, C7, D7, E7, F7, G7, H7, A8, B8, C8, D8, E8, F8, G8, H8)
}