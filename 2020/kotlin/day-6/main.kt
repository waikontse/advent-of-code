import util.FileUtils

val fileUtils = FileUtils("input.txt")

fun main() {
    val sumAnswers = combineLines(fileUtils.lines)
        .flatMap { listOf(it.joinToString("")) }
        .map { it.toHashSet() }
        .map { it.size }
        .reduce { acc, e ->  acc + e }

    println ("Answer day-6 problem 1: $sumAnswers")

    val sumAnswers2 = combineLines(fileUtils.lines)
        .map { it.map { it.toHashSet() } }
        .map { it.reduce { acc, e -> acc.intersect(e).toHashSet() } }
        .map { it.size }
        .reduce { acc, e -> acc + e }

    println ("Answer day-6 problem 2: $sumAnswers2")
}

fun combineLines(lines: List<String>): List<List<String>> {
    val combinedLines: MutableList<List<String>> = ArrayList()

    var groupedLines: MutableList<String> = ArrayList()
    lines.forEach { line ->
                        if (line.isBlank()) {
                           combinedLines.add(groupedLines.toList())
                           groupedLines = ArrayList()
                       } else {
                           groupedLines.add(line)
                       }
    }

    combinedLines.add(groupedLines)

    return combinedLines
}
