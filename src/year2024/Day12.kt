package year2024

import readInput
import show

fun main() {
    val dx = listOf(-1, 0, 1, 0)
    val dy = listOf(0, -1, 0, 1)
    val fencePoleDx = listOf(0, 0, 1, 1)
    val fencePoleDy = listOf(1, 0, 0, 1)

    fun part1AndTwo(input: List<String>): Pair<Int, Int> {
        val w = input[0].length
        val h = input.size
        val visited = mutableSetOf<Coords>()
        var sum = 0
        var sumSides = 0
        for (xStart in 0..<w) {
            for (yStart in 0..<h) {
                if (xStart to yStart !in visited) {
                    visited.add(xStart to yStart)
                    val q = ArrayDeque(listOf(xStart to yStart))
                    var area = 0
                    val fences = mutableSetOf<Pair<Coords, Coords>>()
                    while (q.isNotEmpty()) {
                        val (x, y) = q.removeFirst()
                        area += 1
                        for (i in dx.indices) {
                            val xNext = x + dx[i]
                            val yNext = y + dy[i]
                            val putFence: Boolean
                            if (xNext to yNext !in visited) {
                                if (xNext in 0..<w
                                    && yNext in 0..<h
                                    && input[yNext][xNext] == input[y][x]
                                ) {
                                    visited.add(xNext to yNext)
                                    q.addLast(xNext to yNext)
                                    putFence = false
                                } else {
                                    putFence = true
                                }
                            } else {
                                putFence = input[yNext][xNext] != input[y][x]
                            }
                            if (putFence) {
                                fences.add(
                                    (x + fencePoleDx[i] to y + fencePoleDy[i])
                                            to (x + fencePoleDx[(i + 1) % 4] to y + fencePoleDy[(i + 1) % 4])
                                )
                            }
                        }
                    }
                    sum += area * fences.size
                    val sides = fences.count { (c1, c2) ->
                        val (x1, y1) = c1
                        val (x2, y2) = c2
                        // If there isn't an adjacent fence in the same direction,
                        // the current fence is an end of a side.
                        ((x2 to y2) to (x2 - (x1 - x2) to y2 - (y1 - y2))) !in fences
                    }
                    sumSides += area * sides
                }
            }
        }
        return sum to sumSides
    }

    val input = readInput(2024, 12)

    show { part1AndTwo(input) }
}