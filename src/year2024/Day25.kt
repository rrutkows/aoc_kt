package year2024

import readInput
import show

fun main() {
    fun part1(input: List<String>): Int {
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()
        input
            .filter { it.isNotEmpty() }
            .chunked(7)
            .forEach { lines ->
                val x = MutableList(5) { 0 }
                for (i in 1..<lines.size - 1) {
                    lines[i].forEachIndexed { j, c ->
                        if (c == '#') {
                            x[j] += 1
                        }
                    }
                }
                if (lines.first().startsWith("#")) locks.add(x) else keys.add(x)
            }

        var sum = 0
        for (l in locks) {
            for (k in keys) {
                if (l.zip(k).all { it.first + it.second <= 5 }) {
                    sum += 1
                }
            }
        }

        return sum
    }

    val input = readInput(2024, 25)

    show { part1(input) }
}