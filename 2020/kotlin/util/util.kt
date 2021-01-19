package util

import java.io.File

class FileUtils {
    val lines: List<String>
    val filename: String

    constructor(filename: String) {
        this.filename = filename
        this.lines = readLines(filename)
    }

    fun readLines(filename: String): List<String> {
        return File(filename).readLines();
    }

    fun printLines() {
        lines.forEach { line -> println(line)}
    }
}
