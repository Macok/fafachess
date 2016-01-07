package fafa.protocol

import fafa.messages._

/**
  * Created by mac on 07.01.16.
  */
trait Protocol {
  def serializeMessage(message: Message): String

  def parseMessage(inputLine: String): Option[Message]
}
