package fafa.api

/**
  * Created by mac on 03.01.16.
  */
sealed trait Role {

}

object Role {

  case object Pawn extends Role

  case object Rook extends Role

  case object Knight extends Role

  case object Bishop extends Role

  case object Queen extends Role

  case object King extends Role

  val all = Seq(Pawn, Rook, Knight, Bishop, Queen, King)
}
