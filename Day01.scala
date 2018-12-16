import scala.io.Source

object Day01 {
  private def readData(filename: String): List[Int] =
    Source.fromFile(filename).getLines.toList.map(x => x.toInt)

  def part1(filename: String): Int =
    readData(filename).reduce(_ + _)

  def part2(filename: String): Int = {
    @annotation.tailrec
    def loop(index: Int, red: Int, seen: Set[Int], data: List[Int]): Int = {
      if (seen.contains(red)) red
      else {
        loop(if (index < data.size - 1) index + 1 else 0,
          red + data(index),
          seen + red,
          data)
      }
    }
    loop(0, 0, Set(), readData(filename))
  }

  def main(args: Array[String]): Unit = {
    println("Part 1: %s".format(part1("./data/day01.txt")))
    println("Part 2: %s".format(part2("./data/day01.txt")))
  }
}
