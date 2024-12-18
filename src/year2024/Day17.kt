package year2024

import readInput
import show

fun main() {
    fun run(reg: List<Long>, instr: List<Int>): String {
        var (a, b, c) = reg
        fun getCombo(op: Int): Long = when (op) {
            4 -> a
            5 -> b
            6 -> c
            else -> op.toLong()
        }

        var i = 0
        val out = mutableListOf<Long>()
        while (i in instr.indices) {
            val op = instr[i + 1]
            var next = i + 2
            when (instr[i]) {
                0 -> a = a shr getCombo(op).toInt()
                1 -> b = b xor op.toLong()
                2 -> b = getCombo(op) and 7
                3 -> if (a != 0L) next = op
                4 -> b = b xor c
                5 -> out.add(getCombo(op) and 7)
                6 -> b = a shr getCombo(op).toInt()
                7 -> c = a shr getCombo(op).toInt()
            }

            i = next
        }

        return out.joinToString(separator = ",")
    }

    fun part1(input: List<String>): String {
        val reg = input
            .takeWhile { it.isNotBlank() }
            .map { Regex("""\d+""").find(it)!!.value.toLong() }
        val instr = Regex("""\d+""").findAll(input.last()).map { it.value.toInt() }.toList()
        return run(reg, instr)
    }

    fun part2(input: List<String>): Long {
        val instr = Regex("""\d+""").findAll(input.last()).map { it.value.toInt() }.toList()
        // 2,4 b = a % 8
        // 1,1 b = b xor 1
        // 7,5 c = a >> b
        // 1,5 b = b xor 5
        // 4,0 b = b xor c
        // 0,3 a = a >> 3
        // 5,5 out b % 8
        // 3,0 loop

        fun findA(aa: Long, i: Int): Long? {
            for (a in 0L..<8L) {
                var b = a
                b = b xor 1
                val c = (aa + a) shr b.toInt()
                b = b xor 5
                b = b xor c
                if (b and 7 == instr[i].toLong()) {
                    // println(run(listOf(aa + a, 0L, 0L), instr))
                    if (i == 0) return aa + a
                    val aaNext = findA((aa + a) shl 3, i - 1)
                    if (aaNext != null) return aaNext
                }
            }

            return null
        }

        return findA(0, instr.lastIndex) ?: -1
    }

    val input = readInput(2024, 17)

    show { part1(input) }
    show { part2(input) }
}