import util.FileUtils


fun main() {
    val fileUtils = FileUtils("input.txt")

    // Do some processing of the input for grokking later on.
    val cleanLines = cleanInputLines(fileUtils.lines)
    val mapOfBags = bagsToMap(cleanLines)

    //println (mapOfBags)

    println (findTargetBags(mapOfBags, "shiny gold"))

}

fun cleanInputLines(rawData: List<String>): List<List<String>> {
    return rawData
        .map { it.replace(" bags contain", ",") }
        .map { it.replace(Regex(" bags?"), "") }
        .map { it.replace(".", "") }
        //.map { it.replace(Regex(" \\d+ "), "") }
            .map { it.split(',').map { it.trim() } }

}

fun bagsToMap(bagsConfiguration: List<List<String>>): Map<String, List<String>> {
    val mutableMapOfBags = mutableMapOf<String, List<String>>();

    return bagsConfiguration
        .associateByTo(mutableMapOfBags,
                       { it.first() },
                       { it.subList(1, it.lastIndex + 1) })
}

fun findTargetBags(bagsConfig: Map<String, List<String>>, targetBag: String): Int {
    val containerBagsSet = mutableSetOf<String>()
    findTargetBagsHelper(bagsConfig, targetBag, containerBagsSet)

    return containerBagsSet.size
}

fun findTargetBagsHelper(bagsConfig: Map<String, List<String>>, targetBag: String, containerBags: MutableSet<String>) {
    val foundContainers = findContainerBags(bagsConfig, targetBag)
    containerBags.addAll(foundContainers)

    foundContainers.map { findTargetBagsHelper(bagsConfig, it, containerBags) }
}

fun findContainerBags(bagsConfig: Map<String, List<String>>, targetBag: String): Set<String> {
    return bagsConfig
    //.filter { !it.value.map { it.contains(targetBag) }.isEmpty() }
        .filter { it.value.map { it.contains(targetBag) }.contains(true) }
        .keys
}
