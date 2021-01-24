import util.FileUtils

val fileUtils = FileUtils("input.txt")

fun main() {
    val maxValue: Int? = fileUtils.lines
        .map(::convertLine)
        .map(::convertToId)
        .maxByOrNull { it }

    println ("Day-5 problem 1: $maxValue")

    val sequence: Sequence<Int> = generateSequence(0, { it + 1})

    // Find the missing number
    val listOfIds = fileUtils.lines
        .map(::convertLine)
        .map(::convertToId)
        .toList()
    println (sequence.take(maxValue!!+1).toList()
        .filter { number -> !listOfIds.contains(number)}
        .toList())
}

fun convertLine(line: String): String {
    return line.trim()
        .replace("F", "0")
        .replace("B", "1")
        .replace("R", "1")
        .replace("L", "0")
}

fun convertToId(line: String): Int {
    return line.subSequence(0, 7).toString().toInt(2) * 8 + line.subSequence(7, 10).toString().toInt(2);
}
