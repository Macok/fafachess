package fafa.api

import Role._

/**
  * Created by mac on 03.01.16.
  */
sealed trait Role {
  def mobilityVectors: List[MobilityVec]

  def longRange: Boolean
}

object Role {

  case class MobilityVec(dx: Int, dy: Int)

  implicit def tupleToMobilityVec(t: Tuple2[Int, Int]): MobilityVec = MobilityVec(t._1, t._2)

  case object Pawn extends Role {
    override def mobilityVectors: List[MobilityVec] = throw new Exception("Method not supported for Pawns")

    override def longRange: Boolean = throw new Exception("Method not supported for Pawns")
  }

  case object Rook extends Role {
    override def mobilityVectors: List[MobilityVec] = List(
      (1, 0),
      (0, 1),
      (-1, 0),
      (0, -1))

    override def longRange: Boolean = true
  }

  case object Knight extends Role {
    override def mobilityVectors: List[MobilityVec] = List(
      (2, 1),
      (2, -1),
      (-2, 1),
      (-2, -1),
      (1, 2),
      (1, -2),
      (-1, 2),
      (-1, -2))

    override def longRange: Boolean = false
  }

  case object Bishop extends Role {
    override def mobilityVectors: List[MobilityVec] = List(
      (1, 1),
      (1, -1),
      (-1, 1),
      (-1, -1))

    override def longRange: Boolean = true
  }

  case object Queen extends Role {
    override def mobilityVectors: List[MobilityVec] = Bishop.mobilityVectors ++ Rook.mobilityVectors

    override def longRange: Boolean = true
  }

  case object King extends Role {
    override def mobilityVectors: List[MobilityVec] = Queen.mobilityVectors

    override def longRange: Boolean = false
  }

  val all = Seq(Pawn, Rook, Knight, Bishop, Queen, King)
}
