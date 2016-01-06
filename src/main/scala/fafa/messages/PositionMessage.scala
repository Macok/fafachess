package fafa.messages

import fafa.api.Move

/**
  * Created by mac on 06.01.16.
  */
case class PositionMessage(position: String,
                           moves: List[Move]) extends Message
