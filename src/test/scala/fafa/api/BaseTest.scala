package fafa.api

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by mac on 04.01.16.
  */
class BaseTest extends FlatSpec with Matchers {
  def capturingMoves(actor: Actor) = actor.possibleMoves filter {
    _.capturing.isDefined
  } toSet

  def nonCapturingMoves(actor: Actor) = actor.possibleMoves filter {
    _.capturing.isEmpty
  } toSet
}
