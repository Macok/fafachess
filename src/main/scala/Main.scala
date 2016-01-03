import java.io.File

/**
  * Created by mac on 02.01.16.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val file = new File("chesslog.txt")
    val p = new java.io.PrintWriter(file)

    for (ln <- io.Source.stdin.getLines) {
      p.println("LINIA: " + ln)
      p.flush()
    }
  }
}
