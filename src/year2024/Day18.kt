package year2024

import readInput
import show

fun main() {
    data class QEl(val c: Coords, val distance: Int)

    val w = 71
    val h = 71
    val dx = listOf(-1, 0, 1, 0)
    val dy = listOf(0, -1, 0, 1)

    fun path(bytes: Set<Coords>): Int {
        val q = ArrayDeque<QEl>()
        q.addLast(QEl(0 to 0, 0))
        val visited = mutableSetOf(0 to 0)
        while (q.isNotEmpty()) {
            val (c, d) = q.removeFirst()
            val (x, y) = c
            if (x == w - 1 && y == h - 1) {
                return d
            }

            for (i in dx.indices) {
                val nextX = x + dx[i]
                val nextY = y + dy[i]
                if (nextX in 0..<w
                    && nextY in 0..<h
                    && nextX to nextY !in bytes
                    && nextX to nextY !in visited
                ) {
                    q.addLast(QEl(nextX to nextY, d + 1))
                    visited.add(nextX to nextY)
                }
            }
        }

        return -1
    }

    fun part1(input: List<String>): Int {
        val bytes = input.take(1024).map { line ->
            line.split(',').let { it[0].toInt() to it[1].toInt() }
        }.toSet()

        return path(bytes)
    }

    fun part2(input: List<String>): Coords {
        val allBytes = input.map { line ->
            line.split(',').let { it[0].toInt() to it[1].toInt() }
        }

        val bytes = allBytes.take(1024).toMutableSet()
        return allBytes
            .drop(1024)
            .first {
                bytes.add(it)
                path(bytes) < 0
            }
    }

    val input = readInput(2024, 18)

    show { part1(input) }
    show { part2(input) }
}