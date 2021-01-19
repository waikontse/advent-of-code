import java.io.File
import util.FileUtils


fun main() {
    val fileUtils: FileUtils = FileUtils("input.txt")
    val intSet: MutableSet<Int> = HashSet()

    fileUtils.lines.flatMapTo(intSet, {x -> setOf(x.toInt())})

    // Get  the target value and print it
    println(findTargetInSet(intSet, 2020));
    println(findTargetInSet2(intSet, 2020));
}

fun readFile(filename: String): Set<Int> {
    val file = File(filename)
    val intSet = HashSet<Int>()

    file.readLines()
        .flatMapTo(intSet, {x -> setOf(x.toInt())})

    return intSet
}

fun findTargetInSet(intSet: Set<Int>, target: Int): Pair<Int, Int> {
    val firstInt: Int? = intSet.find { x -> intSet.contains(target-x) }

    return Pair(firstInt!!, target-firstInt)
}

fun findTargetInSet2(intSet: Set<Int>, target: Int): Pair<Int, Int> {
    for (int1 in intSet) {
        for (int2 in intSet) {
            if (intSet.contains(target - int1 - int2)) {
                return Pair(int1, int2)
            }
        }
    }

    return Pair(0,0)
}
