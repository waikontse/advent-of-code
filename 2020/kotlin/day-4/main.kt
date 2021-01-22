import util.FileUtils

val fileUtils = FileUtils("input.txt")

fun main() {
    val allPassports: List<String> = combineLines(fileUtils.lines)

    println ("number of passport lines: "  + allPassports.size)

    val validPassportCount = allPassports.map(::splitPassportFields)
        .filter(::isValidPassport)
        .count()

    val verifiedPassportCount = allPassports.map(::splitPassportFields)
        .filter(::isValidPassport)
        .filter(::isVerifiedPassport)
        .count()

    println ("Day-4 challenge 1: $validPassportCount")
    println ("Day-4 challenge 2: $verifiedPassportCount")
}

fun combineLines(lines: List<String>): List<String> {
    val combinedLines: MutableList<String> = ArrayList()

    val passportLine: MutableList<String> = ArrayList()
    lines.forEach{ line ->
                       if (line.isBlank()) {
                           val combinedPassportLine = passportLine.joinToString(" ")
                           combinedLines.add(combinedPassportLine);
                           passportLine.clear()
                       } else {
                           passportLine.add(line);
                       }
    }

    val combinedPassportLine = passportLine.joinToString(" ")
    combinedLines.add(combinedPassportLine);
    passportLine.clear()

    return combinedLines;
}

fun splitPassportFields(passport: String): Map<String, String> {
    return passport.split(" ")
        .associate {
            it.split(":").let { (key, value) -> key to value }
        }
}

fun isValidPassport(mappedPassportFields: Map<String, String>): Boolean {
    if (mappedPassportFields.size == 8) {
        return true;
    }

    return isFromNorthPole(mappedPassportFields);
}

fun isFromNorthPole(mappedPassportFields: Map<String, String>): Boolean {
    if (mappedPassportFields.size == 7 && !mappedPassportFields.containsKey("cid")) {
        return true;
    }

    return false
}

fun isVerifiedPassport(mappedPassportFields: Map<String, String>): Boolean {
    return verifyBirthYear(mappedPassportFields.get("byr")) &&
    verifyIssueYear(mappedPassportFields.get("iyr")) &&
    verifyExpirationYear(mappedPassportFields.get("eyr")) &&
    verifyHeight(mappedPassportFields.get("hgt")) &&
    verifyHairColor(mappedPassportFields.get("hcl")) &&
    verifyEyeColor(mappedPassportFields.get("ecl")) &&
    verifyPassportId(mappedPassportFields.get("pid"))
}

fun verifyValue(value: Int, validRegion: Pair<Int, Int>): Boolean {
    return value >= validRegion.first && value <= validRegion.second
}

fun String.isNullOrBlank(value: String?): Boolean {
    return value == null || value.isBlank()
}

fun verifyBirthYear(byr: String?): Boolean {
    if (byr.isNullOrBlank()) {
        return false
    }

    return verifyValue(byr.toInt(), Pair(1920, 2002));
}

fun verifyIssueYear(iyr: String?): Boolean {
    if (iyr.isNullOrBlank()) {
        return false
    }

    return verifyValue(iyr.toInt(), Pair(2010, 2020));
}

fun verifyExpirationYear(eyr: String?): Boolean {
    if (eyr.isNullOrBlank()) {
        return false;
    }

    return verifyValue(eyr.toInt(), Pair(2020, 2030))
}

fun verifyHeight(hgt: String?): Boolean {
    if (hgt.isNullOrBlank()) {
        return false
    }

    if (hgt.endsWith("cm")) {
        return verifyValue(hgt.dropLast(2).toInt(), Pair(150, 193))
    }

    if (hgt.endsWith("in")) {
        return verifyValue(hgt.dropLast(2).toInt(), Pair(59, 76))
    }

    return false
}

fun verifyHairColor(hcl: String?): Boolean {
    if (hcl.isNullOrBlank()) {
        return false
    }

    val regexp = """#[0-9a-f]{6,6}""".toRegex()

    return regexp.matches(hcl)
}

fun verifyEyeColor(ecl: String?): Boolean {
    if (ecl.isNullOrBlank()) {
        return false
    }

    return ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") || ecl.equals("gry")
        || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth")
}

fun verifyPassportId(pid: String?): Boolean {
    if (pid.isNullOrBlank()) {
        return false
    }

    return pid.length == 9 && pid.toIntOrNull() != null
}
