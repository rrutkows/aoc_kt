package year2017

import readInput
import show

private fun List<Int>.indexOfMax(): Int {
    var found = -1
    var foundVal = 0
    forEachIndexed { i, v ->
        if (found < 0 || v > foundVal) {
            foundVal = v
            found = i
        }
    }

    return found
}

fun main() {
    fun part1And2(input: String): Pair<Int, Int> {
        var banks = input.split('\t').map { it.toInt() }
        val s = banks.size
        val history = mutableMapOf(banks to 0)
        var cycles = 0
        while (true) {
            cycles += 1
            val iMax = banks.indexOfMax()
            val max = banks[iMax]
            banks = banks.mapIndexed { i, v ->
                val before = if (i == iMax) 0 else v
                val distFromMax = if (i == iMax) s else (s + i - iMax) % s
                before + (max / s) + if (distFromMax <= max % s) 1 else 0
            }

            if (banks in history) {
                return cycles to cycles - history[banks]!!
            }

            history[banks] = cycles
        }
    }

    val input = readInput(2017, 6).first()
    show { part1And2(input) }
}