package year2024

import readInput
import show
import kotlin.math.min

private typealias Keyboard = Map<Char, Map<Char, Char>>

fun main() {
    /*
    +---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+
     */
    val num = mapOf(
        'A' to mapOf('<' to '0', '^' to '3'),
        '0' to mapOf('>' to 'A', '^' to '2'),
        '1' to mapOf('>' to '2', '^' to '4'),
        '2' to mapOf('<' to '1', 'v' to '0', '>' to '3', '^' to '5'),
        '3' to mapOf('<' to '2', 'v' to 'A', '^' to '6'),
        '4' to mapOf('v' to '1', '>' to '5', '^' to '7'),
        '5' to mapOf('<' to '4', 'v' to '2', '>' to '6', '^' to '8'),
        '6' to mapOf('<' to '5', 'v' to '3', '^' to '9'),
        '7' to mapOf('v' to '4', '>' to '8'),
        '8' to mapOf('<' to '7', 'v' to '5', '>' to '9'),
        '9' to mapOf('<' to '8', 'v' to '6')
    )

    /*
   +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
     */
    val dir = mapOf(
        'A' to mapOf('<' to '^', 'v' to '>'),
        '^' to mapOf('v' to 'v', '>' to 'A'),
        '<' to mapOf('>' to 'v'),
        'v' to mapOf('<' to '<', '>' to '>', '^' to '^'),
        '>' to mapOf('<' to 'v', '^' to 'A')
    )

    val distCache = mutableMapOf<Pair<Char, Char>, Int>()
    fun getDist(keyboard: Keyboard, from: Char, to: Char): Int {
        return distCache.getOrPut(from to to) {
            val q = ArrayDeque<Pair<Char, Int>>()
            q.add(from to 0)
            val visited = mutableSetOf(from)
            while (q.isNotEmpty()) {
                val (key, d) = q.removeFirst()
                if (key == to) {
                    return d
                }

                for (next in keyboard[key]!!.values) {
                    if (next !in visited) {
                        q.add(next to d + 1)
                        visited.add(next)
                    }
                }
            }
            error("route not found")
        }
    }

    fun getRoutes(keyboard: Keyboard, from: Char, to: Char) = sequence {
        val q = ArrayDeque<Pair<Char, List<Char>>>()
        q.add(from to listOf())
        while (q.isNotEmpty()) {
            val (key, route) = q.removeFirst()
            if (key == to) {
                yield(route)
            }

            for ((k, v) in keyboard[key]!!) {
                if (getDist(keyboard, v, to) < getDist(keyboard, key, to)) {
                    q.add(v to route + k)
                }
            }
        }
    }

    @Suppress("unused")
    fun List<Char>.print() {
        println(joinToString(separator = ""))
    }


    val cache = mutableMapOf<Pair<List<Char>, Int>, Long>()
    fun solve(keyboard: Keyboard, sequence: List<Char>, robotsLeft: Int): Long {
        return cache.getOrPut(sequence to robotsLeft) {
            if (robotsLeft == 0) {
                sequence.size.toLong()
            } else {
                var sum = 0L
                var prev = 'A'
                for (key in sequence) {
                    var m = Long.MAX_VALUE
                    for (r1 in getRoutes(keyboard, prev, key)) {
                        val r = r1 + 'A'
                        m = min(m, solve(dir, r, robotsLeft - 1))
                    }
                    sum += m
                    prev = key
                }

                sum
            }
        }
    }

    fun solve(input: List<String>, robotCount: Int): Long {
        return input.sumOf { code ->
            val sum = solve(num, code.toList(), robotCount)
            sum * code.trimEnd('A').toInt()
        }
    }

    fun part1(input: List<String>) = solve(input, 3)
    fun part2(input: List<String>) = solve(input, 26)

    val input = readInput(2024, 21)

    show { part1(input) }
    show { part2(input) }
}