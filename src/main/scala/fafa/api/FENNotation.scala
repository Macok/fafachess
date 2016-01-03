package fafa.api

import fafa.api.Color.White
import fafa.api.Role._

/**
  * Created by mac on 03.01.16.
  */
object FENNotation {
  val roleChars: Map[Role, Char] =
    Map(Rook -> 'r',
      Knight -> 'n',
      Bishop -> 'b',
      Queen -> 'q',
      King -> 'k',
      Pawn -> 'p')

  def symbol(piece: Piece): Char = {
    val c = roleChars.get(piece.role).get

    if (piece.color.isWhite) c.toUpper
    else c
  }
}
