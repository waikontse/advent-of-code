import util.FileUtils

fun main() {
    println ("This is day #2")

    val fileUtils = FileUtils("input.txt")

    val validPasswordCount = fileUtils.lines
        .map {fullLine -> fullLine.split(' ')}
        .filter(::isPasswordValid2)
        .count()

    println ("The total number of correct passwords: $validPasswordCount")
}

fun isPasswordValid(line: List<String>): Boolean {
    return isCharWithinCount(getCharCounts(line[2]), getTargetChar(line[1]), getModifiers(line[0]))
}

fun isPasswordValid2(line: List<String>): Boolean {
    return isCharWithinPassword(line[2], getTargetChar(line[1]), getModifiers(line[0]))
}

fun isCharWithinCount(charCounts: Map<Char, Int>, char: Char, count: Pair<Int, Int>): Boolean {
    val charCount: Int = charCounts.get(char)?: 0

    return charCount >= count.first && charCount <= count.second;
}

fun isCharWithinPassword(line: String, char: Char, count: Pair<Int, Int>): Boolean {
    val isCharOnPosition1: Boolean = line.get(count.first-1).equals(char);
    val isCharOnPosition2: Boolean = line.get(count.second-1).equals(char);

    return isCharOnPosition1.xor(isCharOnPosition2)
}

fun getCharCounts(line: String): MutableMap<Char, Int> {
    val counts = HashMap<Char, Int>()
    line.groupingBy { it }.eachCountTo(counts)

    return counts
}

fun getModifiers(line: String): Pair<Int, Int> {
    val lowAndHigh = line.split('-')

    return Pair(lowAndHigh[0].toInt(), lowAndHigh[1].toInt())
}

fun getTargetChar(line: String): Char {
    return line.get(0)
}
