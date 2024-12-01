package year2017

import combinations
import readInput
import show

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(' ') }
            .count { it.combinations().all { x -> x[0] != x[1] } }
    }

    fun part2(input: List<String>): Int {
        fun isAnagram(s1: String, s2: String): Boolean {
            if (s1.length == s2.length) {
                val c1 = s1.toMutableList()
                s2.forEach { c1.remove(it) }
                return c1.isEmpty()
            }

            return false
        }

        return input
            .map { it.split(' ') }
            .count { it.combinations().none() { x -> isAnagram(x[0], x[1]) } }
    }

    val input = readInput(2017, 4)
    show { part1(input) }
    show { part2(input) }
}