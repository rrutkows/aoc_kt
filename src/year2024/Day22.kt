package year2024

import readInput
import show

fun main() {
    fun next(n: Long): Long {
        fun Long.step(f: (Long) -> Long) = (f(this) xor this) and 0xFFFFFF

        return n
            .run { step { it shl 6 } }
            .run { step { it shr 5 } }
            .run { step { it shl 11 } }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            (0..<2000).fold(line.toLong()) { acc, _ -> next(acc) }
        }
    }

    fun part2(input: List<String>): Int {
        val allSequences = mutableMapOf<List<Int>, Int>().withDefault { 0 }
        input.forEach { line ->
            var prev = line.toLong()
            val seen = mutableSetOf<List<Int>>()
            (0..<2000)
                .asSequence()
                .map { next(prev).also { prev = it } }
                .map { (it % 10).toInt() }
                .windowed(5)
                .forEach { prices ->
                    val sequence = prices
                        .zipWithNext()
                        .map { it.second - it.first }
                    if (sequence !in seen) {
                        allSequences[sequence] = allSequences.getValue(sequence) + prices.last()
                        seen.add(sequence)
                    }
                }
        }

        return allSequences.values.max()
    }

    val input = readInput(2024, 22)

    show { part1(input) }
    show { part2(input) }
}