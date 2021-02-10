import util.FileUtils


fun main() {
    val fileUtils = FileUtils("input.txt")

    // Do some processing of the input for grokking later on.
    val cleanLines = cleanInputLines(fileUtils.lines)
    val mapOfBags = bagsToMap(cleanLines)

    println (mapOfBags)

    println (findTargetBags(mapOfBags, "shiny gold"))
    println (findTargetBags2(mapOfBags, "shiny gold"))
    println (findTargetBags2Helper(mapOfBags, "shiny gold"))
    println (findTargetBags2(mapOfBags, "shiny gold").map { getMultiplier(it)})
    println (findTargetBags2(mapOfBags, "shiny gold").map { getBag(it)})

}

fun cleanInputLines(rawData: List<String>): List<List<String>> {
    return rawData
        .map { it.replace(" bags contain", ",") }
        .map { it.replace(Regex(" bags?"), "") }
        .map { it.replace(".", "") }
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
        .filter { it.value.map { it.contains(targetBag) }.contains(true) }
        .keys
}


fun findTargetBags2(bagsConfig: Map<String, List<String>>, targetBag: String): List<String> {
    return bagsConfig
        .filter { it.key.contains(targetBag) }
        .values
        .flatten()
}

fun findTargetBags2Helper(bagsConfig: Map<String, List<String>>, targetBag: String): Int {
    if (bagsConfig.get(targetBag)!!.contains("no other")) {
        return 1
    }

    val bagCount = findTargetBags2(bagsConfig, targetBag)
        .map { getMultiplier(it) * findTargetBags2Helper(bagsConfig, getBag(it)) + getMultiplier(it) * isFinalBag(bagsConfig, getBag(it)) }
        .sum()

    return bagCount
}

fun getMultiplier(bag: String): Int {
    return bag.split(' ')[0].toInt()
}

fun isFinalBag(bagsConfig: Map<String, List<String>>, targetBag: String): Int {
    if (bagsConfig.get(targetBag)!!.contains("no other")) {
        return 0
    } else {
        return 1
    }
}

fun getBag(bag: String): String {
    return bag.replace(Regex("\\d+ "), "")
}
