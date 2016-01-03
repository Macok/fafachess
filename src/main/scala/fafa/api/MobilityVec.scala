package fafa.api

/**
  * Created by mac on 03.01.16.
  */
case class MobilityVec(dx: Int, dy: Int)

object MobilityVec {
  implicit def tupleToMobilityVec(t: Tuple2[Int, Int]): MobilityVec = MobilityVec(t._1, t._2)
}
