package year2017

import println
import readInput

fun main() {
    fun part1And2(input: List<String>): Pair<Int, Int> {
        val registers = mutableMapOf<String, Int>()
        var maxHeld = 0
        input.asSequence()
            .map { it.split(" ") }
            .forEach { parts ->
                val otherVal = registers[parts[4]] ?: 0
                val conditionArg = parts[6].toInt()
                val condition = when (parts[5]) {
                    "<" -> otherVal < conditionArg
                    "<=" -> otherVal <= conditionArg
                    ">" -> otherVal > conditionArg
                    ">=" -> otherVal >= conditionArg
                    "==" -> otherVal == conditionArg
                    "!=" -> otherVal != conditionArg
                    else -> false
                }

                if (condition) {
                    val current = registers[parts[0]] ?: 0
                    val arg = parts[2].toInt()
                    val new = when (parts[1]) {
                        "inc" -> current + arg
                        "dec" -> current - arg
                        else -> current
                    }
                    registers[parts[0]] = new
                    if (new > maxHeld) {
                        maxHeld = new
                    }
                }
            }

        return registers.values.max() to maxHeld
    }

    val input = readInput(2017, 8)
    part1And2(input).println()
}