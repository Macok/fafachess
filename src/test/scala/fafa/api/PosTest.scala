package fafa.api

/**
  * Created by mac on 06.01.16.
  */
class PosTest extends BaseTest {

  "Pos" should "print as field's name" in {
    Pos.G6.toString shouldBe "G6"
  }

  it should "detect when out of board" in {
    Pos.H8.addVector((1, 0)) shouldBe None
    Pos.H8.addVector((0, 1)) shouldBe None
  }

  it should "calculate field ranges" in {
    Pos.A1 to Pos.H8 shouldBe Pos.all
  }
}
