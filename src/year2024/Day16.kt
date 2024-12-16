package year2024

import readInput
import show
import java.util.Comparator
import java.util.PriorityQueue

fun main() {
    data class Data(val walls: Set<Coords>, val start: Coords, val end: Coords)
    data class Node(val c: Coords, val d: Int)
    data class QEl(val n: Node, val v: Long, val path: List<Coords>)

    val dx = listOf(1, 0, -1, 0)
    val dy = listOf(0, 1, 0, -1)

    fun parse(input: List<String>): Data {
        val walls = mutableSetOf<Coords>()
        var start: Coords? = null
        var end: Coords? = null
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                when (c) {
                    '#' -> walls.add(x to y)
                    'S' -> start = x to y
                    'E' -> end = x to y
                }
            }
        }

        return Data(walls, start!!, end!!)
    }

    fun Node.neighbors(walls: Set<Coords>) = sequence {
        val (x, y) = c
        if (x + dx[d] to y + dy[d] !in walls) {
            yield(Node(x + dx[d] to y + dy[d], d) to 1)
        }
        for (i in listOf(-1, 1)) {
            val nextD = (dx.size + d + i) % dx.size
            if (x + dx[nextD] to y + dy[nextD] !in walls) {
                yield(Node(c, nextD) to 1000)
            }
        }
    }

    fun part1And2(input: List<String>): Pair<Long, Int> {
        val (walls, start, end) = parse(input)

        val cmprtr = Comparator<QEl> { e1: QEl?, e2: QEl? -> e1!!.v.compareTo(e2!!.v) }
        val q = PriorityQueue(cmprtr)
        q.add(QEl(Node(start, 0), 0, listOf(start)))
        val visited = mutableMapOf(Node(start, 0) to 0L)

        var cost: Long = Long.MAX_VALUE
        val bestTiles = mutableSetOf<Coords>()

        while (q.isNotEmpty()) {
            val (n, v, path) = q.poll()!!
            val (x, y) = n.c
            if (x to y == end) {
                if (v < cost) {
                    cost = v
                    bestTiles.clear()
                }
                if (v <= cost) {
                    bestTiles.addAll(path)
                }
            } else {
                for ((next, stepCost) in n.neighbors(walls)) {
                    if (next !in visited || v + stepCost <= visited[next]!!) {
                        q.add(QEl(next, v + stepCost, path + next.c))
                        visited[next] = v + stepCost
                    }
                }
            }
        }

        return cost to bestTiles.size
    }

    val input = readInput(2024, 16)
    show { part1And2(input) }
    show { part1And2(input) }
}