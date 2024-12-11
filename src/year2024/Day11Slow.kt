package year2024

import readInput
import show
import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun solve(input: String, maxDepth: Int): Long {
        val cache = mutableMapOf<Long, List<Long>>()
        fun blink(stone: Long, depth: Int): Long {
            val stones = cache.getOrPut(stone) {
                val stones = mutableListOf(stone)
                for (i in 0..<25) {
                    for (j in stones.indices) {
                        val s = stones[j]
                        if (s == 0L) {
                            stones[j] = 1L
                        } else {
                            val digits = log10(s.toDouble()).toInt() + 1
                            if (digits % 2 == 0) {
                                val t = 10.0.pow(digits / 2).toLong()
                                stones[j] = s / t
                                stones.add(s - s / t * t)
                            } else {
                                stones[j] = stones[j] * 2024
                            }
                        }
                    }
                }
                stones
            }

            if (depth == maxDepth) {
                return stones.size.toLong()
            }

            return stones.sumOf { blink(it, depth + 1) }
        }

        return input.split(" ").sumOf { blink(it.toLong(), 1) }
    }

    fun part1(input: String) = solve(input, 1)
    fun part2(input: String) = solve(input, 3)
    val input = readInput(2024, 11).single()
    show { part1(input) }
    show { part2(input) }
}