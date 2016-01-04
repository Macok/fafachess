package fafa.api

/**
  * Created by mac on 03.01.16.
  */
case class Move(from: Pos,
                to: Pos,
                capturing: Option[Pos] = None,
                promoteTo: Option[Role] = None) {

}
