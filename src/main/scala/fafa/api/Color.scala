package fafa.api

/**
  * Created by mac on 03.01.16.
  */
sealed trait Color {
  def isWhite: Boolean
}

object Color {
  case object White extends Color {
    override def isWhite: Boolean = true
  }

  case object Black extends Color {
    override def isWhite: Boolean = false
  }
}
