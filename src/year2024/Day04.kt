package year2024

import readInput
import show

fun main() {
    val directions = listOf(
        1 to 0,
        1 to 1,
        0 to 1,
        -1 to 1,
        -1 to 0,
        -1 to -1,
        0 to -1,
        1 to -1
    )

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEachIndexed { yStart, line ->
            line.forEachIndexed { xStart, _ ->
                directions.forEach { (dx, dy) ->
                    var x = xStart
                    var y = yStart
                    var l = 0
                    while (l < "XMAS".length && y in input.indices && x in input[y].indices && input[y][x] == "XMAS"[l]) {
                        l += 1
                        x += dx
                        y += dy
                    }
                    if (l == "XMAS".length) {
                        count += 1
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        fun letters(x1: Int, y1: Int, x2: Int, y2: Int): String {
            return arrayOf(input[y1][x1], input[y2][x2]).apply { sort() }.joinToString(separator = "")
        }

        var count = 0
        for (y in 1..<(input.size - 1)) {
            for (x in 1..<(input[y].length - 1)) {
                if (input[y][x] == 'A' &&
                    letters(x - 1, y - 1, x + 1, y + 1) == "MS" &&
                    letters(x + 1, y - 1, x - 1, y + 1) == "MS"
                ) {
                    count += 1
                }

            }
        }

        return count
    }

    val input = readInput(2024, 4)
    show { part1(input) }
    show { part2(input) }
}