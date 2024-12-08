package year2024

import combinations
import readInput
import show

fun main() {
    fun solve(input: List<String>, genAntinodes: (List<Coords>, Int, Int) -> Iterable<Coords>): Int {
        val h = input.size
        val w = input[0].length
        val antennas = input
            .flatMapIndexed { y, line ->
                Regex("""[^.]""").findAll(line).map { it.value[0] to (it.range.first to y) }
            }
            .groupBy({ it.first }, { it.second })
        val antinodes = antennas.values
            .flatMap { sameAntennas ->
                sameAntennas
                    .combinations()
                    .flatMap { genAntinodes(it, w, h) }
            }
            .toSet()

        return antinodes
            .count { (x, y) -> x in 0..<w && y in 0..<h }
    }

    fun part1(input: List<String>): Int = solve(input) { a, _, _ ->
        val (x1, y1) = a[0]
        val (x2, y2) = a[1]
        listOf(
            x1 - (x2 - x1) to y1 - (y2 - y1),
            x2 + (x2 - x1) to y2 + (y2 - y1)
        )
    }

    fun part2(input: List<String>): Int = solve(input) { a, w, h ->
        var (x1, y1) = a[0]
        var (x2, y2) = a[1]
        val dx = x2 - x1
        val dy = y2 - y1
        sequence {
            while (x1 in 0..<w && y1 in 0..<h || x2 in 0..<w && y2 in 0..<h) {
                yield(x1 to y1)
                yield(x2 to y2)
                x1 -= dx
                y1 -= dy
                x2 += dx
                y2 += dy
            }
        }.asIterable()
    }

    val input = readInput(2024, 8)
    show { part1(input) }
    show { part2(input) }
}