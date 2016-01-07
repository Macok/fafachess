package fafa.actor

import akka.actor.ActorSystem
import akka.testkit.{TestActors, ImplicitSender, TestKit}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

/**
  * Created by mac on 07.01.16.
  */
class BaseActorTest() extends TestKit(ActorSystem()) with ImplicitSender
with FlatSpecLike with Matchers with BeforeAndAfterAll with MockFactory {

}
