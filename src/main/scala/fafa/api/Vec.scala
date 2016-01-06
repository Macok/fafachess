package fafa.api

/**
  * Created by mac on 03.01.16.
  */
case class Vec(dx: Int, dy: Int) {
  def *(factor: Int): Vec = Vec(dx * factor, dy * factor)

  def +(other: Vec): Vec = Vec(dx + other.dx, dy + other.dy)

  lazy val unary_- = Vec(-dx, -dy)
}

object Vec {
  implicit def tupleToVec(t: Tuple2[Int, Int]): Vec = Vec(t._1, t._2)
}
