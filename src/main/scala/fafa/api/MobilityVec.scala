package fafa.api

/**
  * Created by mac on 03.01.16.
  */
case class MobilityVec(dx: Int, dy: Int) {
  def *(factor: Int): MobilityVec = MobilityVec(dx * factor, dy * factor)

  def +(other: MobilityVec): MobilityVec = MobilityVec(dx + other.dx, dy + other.dy)

  val unary_- = MobilityVec(-dx, -dy)
}

object MobilityVec {
  implicit def tupleToMobilityVec(t: Tuple2[Int, Int]): MobilityVec = MobilityVec(t._1, t._2)
}
