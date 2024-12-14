package year2024

import readInput
import show

fun main() {
    val w = 101
    val h = 103

    fun part1(input: List<String>, time: Int): Long {
        val q = mutableListOf(0L, 0L, 0L, 0L)

        input.forEach { line ->
            val (x0, y0, dx, dy) = Regex("""-?\d+""").findAll(line).map { it.value.toInt() }.toList()
            val x = (w * time + x0 + time * dx) % w
            val y = (h * time + y0 + time * dy) % h
            val i = when {
                x < w / 2 && y < h / 2 -> 0
                x > w / 2 && y < h / 2 -> 1
                x < w / 2 && y > h / 2 -> 2
                x > w / 2 && y > h / 2 -> 3
                else -> -1
            }
            if (i >= 0) {
                q[i] = q[i] + 1
            }
        }

        return q.reduce { acc, i -> acc * i }
    }

    @Suppress("unused")
    fun part2(input: List<String>) {
        var r = input.map { line ->
            val r = Regex("""-?\d+""").findAll(line).map { it.value.toInt() }.toList()
            (r[0] to r[1]) to (r[2] to r[3])
        }
        var rSet = r.map { it.first }.toSet()

        var t = 0
        while (true) {
            val longLine = (0..<h).any { j ->
                val line = (0..<w).map { it to j in rSet }
                line.count { it } > w / 4
            }

            if (longLine) {
                for (j in 0..<h) {
                    println((0..<w).map { if (it to j in rSet) '#' else '.' }.joinToString(separator = ""))
                }
                println(t)
                readln()
            }

            r = r.map { (k, v) ->
                val (x, y) = k
                val (dx, dy) = v
                ((w + x + dx) % w to (h + y + dy) % h) to v
            }
            rSet = r.map { it.first }.toSet()
            t += 1
        }
    }

    val input = readInput(2024, 14)
    @Suppress("UNUSED_VARIABLE") val xinput = """p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3""".lines()

    show { part1(input, 100) }
    // part2(input)
}