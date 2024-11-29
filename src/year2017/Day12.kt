package year2017

import println
import readInput

fun main() {
    fun part1And2(input: List<String>): Pair<Int, Int> {
        val programs = input.associate { line ->
            val ids = Regex("""\w+""").findAll(line).map { it.value.toInt() }
            ids.first() to ids.drop(1)
        }
        val visited = mutableSetOf<Int>()
        var groups = 0
        var group0Size = 0
        for (start in programs.keys) {
            if (start !in visited) {
                visited.add(start)
                val q = ArrayDeque(listOf(start))
                while (q.isNotEmpty()) {
                    val id = q.removeFirst()
                    programs[id]!!.forEach {
                        if (it !in visited) {
                            visited.add(it)
                            q.addLast(it)
                        }
                    }
                }

                groups += 1
                if (group0Size == 0) {
                    group0Size = visited.size // first group is guaranteed to have 0
                }
            }
        }

        return group0Size to groups
    }

    val input = readInput(2017, 12)
    part1And2(input).println()
}