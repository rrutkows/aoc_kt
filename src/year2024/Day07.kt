package year2024

import readInput
import show

private fun interface Operator {
    fun calculate(a: Long, b: Long): Long
}

fun main() {
    val allOperators = arrayOf(
        Operator(Long::plus),
        Operator(Long::times),
        Operator { a, b -> "$a$b".toLong() }
    )

    fun canCalculate(value: Long, args: List<Long>, operators: List<Operator>): Boolean {
        fun test(result: Long, i: Int): Boolean = when {
            i == args.size -> result == value
            result > value -> false
            else -> operators.any { test(it.calculate(result, args[i]), i + 1) }
        }

        return test(args[0], 1)
    }

    @Suppress("unused")
    fun canCalculateOrig(value: Long, args: List<Long>, operators: List<Operator>): Boolean {
        fun values(result: Long, i: Int): Sequence<Long> = sequence {
            if (i == args.size) {
                yield(result)
            } else if (result <= value) {
                for (op in operators) {
                    yieldAll(values(op.calculate(result, args[i]), i + 1))
                }
            }
        }

        return values(args[0], 1).any { value == it }
    }

    fun solve(input: List<String>, operators: List<Operator>): Long {
        return input
            .map { it.split(": ") }
            .map { parts -> parts[0].toLong() to parts[1].split(' ').map { it.toLong() } }
            .filter { (value, args) -> canCalculate(value, args, operators) }
            .sumOf { it.first }
    }

    fun part1(input: List<String>) = solve(input, allOperators.take(2))
    fun part2(input: List<String>) = solve(input, allOperators.toList())

    val input = readInput(2024, 7)

    show { part1(input) }
    show { part2(input) }
}