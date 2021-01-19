import util.FileUtils

val fileUtils = FileUtils("input.txt")

fun main() {
    println ("Day 3")

    val multiplied = listOf(
        countTotalHitTrees(getData(1), 1),
        countTotalHitTrees(getData(1), 3),
        countTotalHitTrees(getData(1), 5),
        countTotalHitTrees(getData(1), 7),
        countTotalHitTrees(getData(2), 1))
        .fold (1) {acc, item ->  acc * item }

    println ("total: $multiplied")
}

fun getData(skipLine: Int): List<String> {
    return fileUtils.lines
        .filterIndexed { index, _ -> index % skipLine == 0 }
}

fun countTotalHitTrees(lines: List<String>, multiplier: Int): Int {
    return lines
        .mapIndexed {index, line -> isTree(index, line, multiplier)}
        .filter { it.equals('#') }
        .count()
}

fun isTree(row: Int, line: String, multiplier: Int): Char {
    if (row == 0) return '9'

    val index = (row * multiplier) % line.length

    return line.get(index)
}
