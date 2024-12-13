package year2024

import readInput
import show

fun main() {
    val p1 = 0L
    val p2 = 10000000000000L

    fun parse(input: List<String>): List<List<List<Int>>> {
        return input.chunked(4)
            .map { chunk ->
                chunk.take(3).map { line ->
                    Regex("""\d+""").findAll(line).map { it.value.toInt() }.toList()
                }
            }
    }

    fun solve(input: List<String>, mod: Long): Long {
        val machines = parse(input)
        return machines.sumOf { m ->
            val (ax, ay) = m[0]
            val (bx, by) = m[1]
            val (px, py) = m[2].map { it + mod }

            // a*ax+b*bx=px
            // a*ay+b*by=py

            // a*ax*ay + b*bx*ay=px*ay
            // a*ay*ax + b*by*ax=py*ax

            // b(bx*ay-by*ax) = px*ay-py*ax
            val b = (px * ay - py * ax) / (bx * ay - by * ax)
            val a = (px - b * bx) / ax

            if (px == a * ax + b * bx && py == a * ay + b * by) a * 3 + b else 0
        }
    }

    fun part1(input: List<String>) = solve(input, p1)
    fun part2(input: List<String>) = solve(input, p2)

    val input = readInput(2024, 13)

    show { part1(input) }
    show { part2(input) }
}