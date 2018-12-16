import scala.io.Source

object Day01 {
  private def readData(filename: String): List[Int] =
    Source.fromFile(filename).getLines.toList.map(x => x.toInt)

  def part1(filename: String): Int = {
    readData(filename).reduce(_ + _)
  }

  def part2(filename: String): Int = {
    val data = readData(filename)
    var seen : Set[Int] = Set()

    @annotation.tailrec
    def go(index: Int, red: Int): Int = {
      if (seen.contains(red)) red
      else {
        seen = seen + red
        go(if (index < data.size - 1) index + 1 else 0, red + data(index))
      }
    }
    go(0, 0)
  }

  def main(args: Array[String]): Unit =
    println("Part 1: %s".format(part1("./data/day01.txt")))
    println("Part 2: %s".format(part2("./data/day01.txt")))
}
