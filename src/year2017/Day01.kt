package year2017

import println
import readInput

fun main() {
    fun solve(input: String, distance: Int): Int {
        return input
            .filterIndexed { i, c -> c == input[(i + distance) % input.length] }
            .sumOf { it.digitToInt() }
    }

    fun part1(input: String) = solve(input, 1)

    fun part2(input: String) = solve(input, input.length / 2)

    // Read the input from the `src/year2017/Day01.txt` file.
    val input = readInput(2017, 1).first()
    part1(input).println()
    part2(input).println()
}
