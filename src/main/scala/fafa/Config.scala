package fafa

import com.typesafe.config.ConfigFactory

/**
  * Created by mac on 06.01.16.
  */
object Config {
  private val conf = ConfigFactory.load()
  val filterMovesLeavingKingInCheck = conf.getBoolean("fafa.filterMovesLeavingKingInCheck")
}
