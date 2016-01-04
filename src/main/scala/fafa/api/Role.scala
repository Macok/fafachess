package fafa.api

import MobilityVec._

/**
  * Created by mac on 03.01.16.
  */
sealed trait Role {
  def mobilityVecs: List[MobilityVec]
}

object Role {

  case object Pawn extends Role {
    override def mobilityVecs: List[MobilityVec] = throw new Exception("Method not supported for Pawns")
  }

  case object Rook extends Role {
    override def mobilityVecs: List[MobilityVec] = List(
      (1, 0),
      (0, 1),
      (-1, 0),
      (0, -1))
  }

  case object Knight extends Role {
    override def mobilityVecs: List[MobilityVec] = List(
      (2, 1),
      (2, -1),
      (-2, 1),
      (-2, -1),
      (1, 2),
      (1, -2),
      (-1, 2),
      (-1, -2))
  }

  case object Bishop extends Role {
    override def mobilityVecs: List[MobilityVec] = List(
      (1, 1),
      (1, -1),
      (-1, 1),
      (-1, -1))
  }

  case object Queen extends Role {
    override def mobilityVecs: List[MobilityVec] = Bishop.mobilityVecs ++ Rook.mobilityVecs
  }

  case object King extends Role {
    override def mobilityVecs: List[MobilityVec] = Queen.mobilityVecs
  }

  val all = Seq(Pawn, Rook, Knight, Bishop, Queen, King)
}
