import util.FileUtils

data class Accumulator(val acc: Int)
data class ProgramCounter(val pc: Int)
data class CPU(val acc: Accumulator, val pc: ProgramCounter)


fun main() {
    val fileUtils: FileUtils = FileUtils("input.txt")
    fileUtils.printLines()

    // Challenge #1
    val cpu: CPU = runProgram1(fileUtils.lines)
    println ("Challenge #1 CPU values: $cpu")

    // Challenge #2
    val cpu2: CPU = runProgram2(fileUtils.lines)
    println ("Challenge #2 CPU values: $cpu2")
}

fun runProgram1(program: List<String>): CPU {
    return runProgram1Helper(program, CPU(Accumulator(0), ProgramCounter(0)), mutableSetOf<Int>())
}

// Code for solving 2nd challenge
fun runProgram2(program: List<String>): CPU {
    // Create a zipped list, appending an index to the instruction
    // modify the operand and run the program
    val endStates: List<CPU> =
        (0..program.size).toList().zip(program)
            .filter { (_, item) -> item.startsWith("nop") || item.startsWith("jmp") }
            .map { (index, item) -> Pair(index, flipOperator(item)) }
            .map { patch -> patchProgram(program, patch) }
            .map { patchedProgram -> runProgram1Helper(patchedProgram, CPU(Accumulator(0), ProgramCounter(0)), mutableSetOf<Int>()) }

    // Filter the correct ended program
    return endStates.first { cpu -> cpu.pc.pc == program.size }
}

// Code for solving 1st challenge
fun runProgram1Helper(program: List<String>, cpu: CPU, seen: MutableSet<Int>): CPU {
    if (seen.contains(cpu.pc.pc) || cpu.pc.pc >= program.size || cpu.pc.pc < 0) {
        return cpu
    }

    // Execute current line
    val newCPU: CPU = executeLine(program, cpu, seen);
    return runProgram1Helper(program, newCPU, seen);
}

fun executeLine(program: List<String>, cpu: CPU, seen: MutableSet<Int>): CPU {
    val op: Pair<String, Int> = tokenize(program[cpu.pc.pc])

    val newCPU: CPU = when(op.first) {
        "nop" -> nop(cpu, seen)
        "acc" -> acc(cpu, seen, op.second)
        "jmp" -> jmp(cpu, seen, op.second)
        else -> cpu
    }

    return newCPU
}

fun tokenize(line: String): Pair<String, Int> {
    val tokenized: List<String> = line.split(" ")

    return Pair(tokenized[0], Integer.parseInt(tokenized[1]))
}

fun nop(cpu: CPU, seen: MutableSet<Int>): CPU {
    seen.add(cpu.pc.pc)

    return cpu.copy(pc = ProgramCounter(cpu.pc.pc + 1))
}


fun acc(cpu: CPU, seen: MutableSet<Int>, operand: Int): CPU {
    seen.add(cpu.pc.pc)

    return cpu.copy(acc = Accumulator(cpu.acc.acc + operand), ProgramCounter(cpu.pc.pc + 1))
}

fun jmp(cpu: CPU, seen: MutableSet<Int>, operand: Int): CPU {
    seen.add(cpu.pc.pc)

    return cpu.copy(pc = ProgramCounter(cpu.pc.pc + operand))
}


// Code needed for solving the 2nd challenge
fun patchProgram(program: List<String>, patch: Pair<Int, String>): List<String> {
    val mutableProgram = program.toMutableList()
    mutableProgram.set(patch.first, patch.second);

    return mutableProgram.toList();
}

fun flipOperator(op: String): String {
    if (op.startsWith("nop")) {
        return op.replace("nop", "jmp")
    } else {
        return op.replace("jmp", "nop")
    }
}
