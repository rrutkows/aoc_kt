package year2017

import println
import readInput

fun main() {
    fun sparseHash(lengths: List<Int>, rounds: Int): List<Int> {
        var pos = 0
        var skip = 0
        var data = List(256) { it }
        for (round in 0..<rounds) {
            lengths.forEach { l ->
                data = List(data.size) { i ->
                    val distFromPos = (data.size + i - pos) % data.size
                    if (distFromPos < l) {
                        data[(data.size + pos + l - 1 - distFromPos) % data.size]
                    } else {
                        data[i]
                    }
                }

                pos = (pos + l + skip) % data.size
                skip += 1
            }
        }

        return data
    }

    fun part1(input: String): Int {
        val lengths = input.split(',').map { it.toInt() }
        val hash = sparseHash(lengths, 1)
        return hash[0] * hash[1]
    }

    fun part2(input: String): String {
        val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
        return sparseHash(lengths, 64)
            .chunked(16)
            .map { it.reduce { acc, i -> acc xor i } }
            .joinToString(separator = "") { String.format("%02x", it) }
    }

    val input = readInput(2017, 10).single()
    part1(input).println()
    part2(input).println()
}