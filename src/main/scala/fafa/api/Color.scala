package fafa.api

/**
  * Created by mac on 03.01.16.
  */
sealed trait Color {
  def isWhite: Boolean
}

object Color {

  case object White extends Color {
    lazy val unary_! = Black
    lazy val isWhite = true
  }

  case object Black extends Color {
    lazy val unary_! = White
    lazy val isWhite = false
  }
}
