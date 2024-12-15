package year2024

import readInput
import show

fun main() {
    val d = mapOf(
        '<' to (-1 to 0),
        '^' to (0 to -1),
        '>' to (1 to 0),
        'v' to (0 to 1)
    )

    data class Data(val grid: MutableList<MutableList<Char>>, val start: Coords, val moves: String)

    fun <T> List<List<T>>.v(x: Int, y: Int, dx: Int, dy: Int, steps: Int): T =
        this[y + dy * steps][x + dx * steps]

    @Suppress("unused")
    fun List<List<Char>>.show(x: Int, y: Int) {
        this.forEachIndexed { j, row ->
            println(
                row
                    .mapIndexed { i, c ->
                        if (i == x && j == y) {
                            '@'
                        } else {
                            c
                        }
                    }
                    .joinToString(separator = ""))
        }
        println()
    }

    fun parse(input: List<String>): Data {
        var start = 0 to 0
        val grid = input
            .takeWhile { it.isNotBlank() }
            .mapIndexed { y, line ->
                if ('@' in line) {
                    start = line.indexOf('@') to y
                    line.replace('@', '.').toMutableList()
                } else {
                    line.toMutableList()
                }
            }
            .toMutableList()
        val moves = input.takeLastWhile { it.isNotBlank() }
            .joinToString(separator = "")
        return Data(grid, start, moves)
    }

    fun part1(input: List<String>): Int {
        val (grid, start, moves) = parse(input)
        val w = grid[0].size
        val h = grid.size

        var (x, y) = start
        moves
            .map { d[it]!! }
            .forEach { (dx, dy) ->
                var i = 1
                while (x + dx * i in 0..<w && y + dy * i in 0..<h && grid.v(x, y, dx, dy, i) != '#') {
                    if (grid.v(x, y, dx, dy, i) == '.') {
                        while (i > 0) {
                            grid[y + dy * i][x + dx * i] = grid.v(x, y, dx, dy, i - 1)
                            i -= 1
                        }
                        //grid[y][x] = '.'
                        x += dx
                        y += dy
                        break
                    }
                    i += 1
                }
            }

        return grid
            .flatMapIndexed { yy, row ->
                row.indices.filter { row[it] == 'O' }.map { it to yy }
            }
            .sumOf { (x, y) -> x + 100 * y }
    }

    val input = readInput(2024, 15)

    show { part1(input) }
}