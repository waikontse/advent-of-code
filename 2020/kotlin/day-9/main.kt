import util.FileUtils;

fun main(args: Array<String>) {
    val fileUtils: FileUtils = FileUtils("input.txt")

    val preambleListLimit = 25

    // Create the preamble list
    val preamble: MutableList<Int> = fileUtils.lines.take(preambleListLimit).map {it.toInt()}.toMutableList()


    // Try to combine the rest of the numbers in the list
    fileUtils.lines
        .drop(preambleListLimit)
        .forEach {
            if (!canCombineFromPreamble(preamble, it.toInt())) {
                println ("The number cannot be combined into the preamble ${it}")
                throw Exception("")
            }

            updatePreambleList(preamble, it.toInt())
        }
}

fun canCombineFromPreamble(preamble: List<Int>, target: Int): Boolean {
    return preamble
        .filter { canCombineFromPreambleSingle(preamble, target, it.toInt())}
        .any()
}

fun canCombineFromPreambleSingle(preamble: List<Int>, target: Int, addition: Int): Boolean {
    return preamble.contains (target - addition)
}

fun updatePreambleList(preamble: MutableList<Int>, newEntry: Int) {
    preamble.removeAt(0)
    preamble.add(newEntry)
}
