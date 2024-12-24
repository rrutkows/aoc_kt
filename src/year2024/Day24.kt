package year2024

import readInput
import show
import kotlin.io.path.Path
import kotlin.io.path.writeText

fun main() {
    fun Boolean.toNum(sh: Int) = (if (this) 1L else 0L) shl sh

    fun part1(input: List<String>): Long {
        val values = mutableMapOf<String, Boolean>()
        val gates = mutableMapOf<String, List<String>>()
        val operations = mapOf<String, (Boolean, Boolean) -> Boolean>(
            "AND" to Boolean::and,
            "OR" to Boolean::or,
            "XOR" to Boolean::xor
        )

        fun getValue(wire: String): Boolean {
            return values.getOrPut(wire) {
                val (w1, op, w2) = gates[wire]!!
                operations[op]!!(getValue(w1), getValue(w2))
            }
        }

        input.forEach { line ->
            when {
                ':' in line -> line.split(": ").run {
                    values[this[0]] = this[1] != "0"
                }

                line.isNotBlank() -> line.split(" -> ").run {
                    gates[this[1]] = this[0].split(' ')
                }
            }
        }

        return gates.keys
            .filter { it.matches(Regex("""z\d+""")) }
            .sorted()
            .mapIndexed { i, wire -> getValue(wire).toNum(i) }
            .sum()
    }

    fun part2(input: List<String>): String {
        val g = buildString {
            val xyz = mutableSetOf<String>()
            appendLine("strict digraph {")
            input
                .filter { it.contains("->") }
                .forEachIndexed { i, line ->
                    val s = Regex("""\w+""").findAll(line).map { it.value }.toList()
                    xyz.addAll(s.filter { it.matches(Regex("""[xyz]\d+""")) })
                    appendLine("g$i [label=${s[1]} shape=box]")
                    appendLine("${s[0]} -> g$i")
                    appendLine("${s[2]} -> g$i")
                    appendLine("g$i -> ${s[3]}")
                }
            xyz.forEach {
                appendLine("$it [style=filled fillcolor=${if (it.startsWith("z")) "green" else "lightgrey"}]")
            }
            appendLine("}")
        }
        Path("src/year2024/Day24.dot").writeText(g)

        // dot src/year2024/Day24.dot -Tsvg -O

        return listOf(
            "z24",
            "fpq",
            "fgt",
            "pcp",
            "z07",
            "nqk",
            "z32",
            "srn"
        ).sorted().joinToString(separator = ",")
    }

    val input = readInput(2024, 24)

    show { part1(input) }
    show { part2(input) }
}