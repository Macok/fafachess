package fafa.actor

import akka.actor.Props
import akka.testkit.TestProbe
import fafa.messages.{IsReadyMessage, ReadyOkMessage}

import scala.concurrent.duration._

/**
  * Created by mac on 07.01.16.
  */
class ProtocolHandlerActorTest extends BaseActorTest {

  val engineProbe = TestProbe()
  val ioActor = testActor
  val protocolHandlerRef = system.actorOf(Props(classOf[ProtocolHandlerActor], engineProbe.ref))

  override def beforeAll = {
    protocolHandlerRef ! ioActor // set IO actor
  }

  "ProtocolHandlerActor" should "forward serialized messages to io actor" in {
    within(500 millis) {
      protocolHandlerRef ! ReadyOkMessage()
      expectMsg("readyok")
    }
  }

  it should "forward deserialized messages to the engine" in {
    within(500 millis) {
      protocolHandlerRef ! "isready"
      engineProbe.expectMsg(IsReadyMessage())
    }
  }
}
