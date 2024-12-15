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

    data class Data(
        val walls: Set<Coords>,
        val boxes: MutableSet<Coords>,
        val start: Coords,
        val w: Int,
        val h: Int,
        val moves: String
    )

    fun parse(input: List<String>): Data {
        val w = mutableSetOf<Coords>()
        val b = mutableSetOf<Coords>()
        var start: Coords? = null
        input
            .takeWhile { it.isNotBlank() }
            .forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    when (c) {
                        '#' -> {
                            w.add(2 * x to y)
                            w.add(2 * x + 1 to y)
                        }

                        'O' -> {
                            b.add(2 * x to y)
                        }

                        '@' -> {
                            start = 2 * x to y
                        }
                    }
                }
            }
        val moves = input.takeLastWhile { it.isNotBlank() }
            .joinToString(separator = "")

        return Data(
            w,
            b,
            start!!,
            2 * input[0].length,
            input.size,
            moves
        )
    }

    fun part2(input: List<String>): Int {
        val (walls, boxes, start, w, h, moves) = parse(input)
        var (x, y) = start
        moves.forEach { m ->
            val bToMove = mutableListOf<Coords>()
            val (dx, dy) = d[m]!!
            val q = ArrayDeque(listOf(x + dx to y + dy))
            while (q.isNotEmpty()) {
                val (xx, yy) = q.removeFirst()
                if (xx !in 0..<w || yy !in 0..<h || xx to yy in walls) {
                    return@forEach
                }

                when (m) {
                    '<' -> {
                        if (xx - 1 to yy in boxes) {
                            bToMove.add(xx - 1 to yy)
                            q.addLast(xx - 2 to yy)
                        }
                    }

                    '^' -> {
                        if (xx to yy in boxes) {
                            bToMove.add(xx to yy)
                            q.addLast(xx to yy - 1)
                            q.addLast(xx + 1 to yy - 1)
                        }
                        if (xx - 1 to yy in boxes) {
                            bToMove.add(xx - 1 to yy)
                            q.addLast(xx - 1 to yy - 1)
                            q.addLast(xx to yy - 1)
                        }
                    }

                    '>' -> {
                        if (xx to yy in boxes) {
                            bToMove.add(xx to yy)
                            q.addLast(xx + 2 to yy)
                        }
                    }

                    'v' -> {
                        if (xx to yy in boxes) {
                            bToMove.add(xx to yy)
                            q.addLast(xx to yy + 1)
                            q.addLast(xx + 1 to yy + 1)
                        }
                        if (xx - 1 to yy in boxes) {
                            bToMove.add(xx - 1 to yy)
                            q.addLast(xx - 1 to yy + 1)
                            q.addLast(xx to yy + 1)
                        }
                    }
                }
            }

            for (b in bToMove.reversed()) {
                val (bx, by) = b
                boxes.remove(b)
                boxes.add(bx + dx to by + dy)
            }

            x += dx
            y += dy
        }
        return boxes.sumOf { (x, y) -> x + 100 * y }
    }

    val input = readInput(2024, 15)
    show { part2(input) }
}