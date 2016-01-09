package fafa.ai

import fafa.api.{Board, Pos}

/**
  * Created by mac on 09.01.16.
  */
object MathUtils {
  implicit def pieceSquareTableFromArray(array: Array[Int]): Array[Array[Int]] = {
    val matrix = array.grouped(8)
    val pieceSquareTable = Array.fill(8, 8)(0)
    val pieces =
      for (
        (row, rowIndex) <- matrix.zipWithIndex;
        (v, vIndex) <- row.zipWithIndex) {
        val pos = Pos(vIndex, Board.BoardSize - 1 - rowIndex)
        pieceSquareTable(pos.x)(pos.y) = v
      }

    pieceSquareTable
  }

  def rotate180(array: Array[Array[Int]]): Array[Array[Int]] = {
    rotate90(rotate90(array))
  }

  private def rotate90(array: Array[Array[Int]]): Array[Array[Int]] = {
    val transposed: Array[Array[Int]] = array.transpose
    transposed map {_.reverse}
  }
}
