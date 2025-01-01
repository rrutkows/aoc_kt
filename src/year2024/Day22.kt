package year2024

import readInput
import show

fun main() {
    fun next(n: Long): Long {
        fun Long.step(f: (Long) -> Long) = (f(this) xor this) and 0xFFFFFF

        return n
            .run { step { it shl 6 } }
            .run { step { it shr 5 } }
            .run { step { it shl 11 } }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            (0..<2000).fold(line.toLong()) { acc, _ -> next(acc) }
        }
    }

    fun part2(input: List<String>): Int {
        // A sequence will be converted from a list if four -9 to 9 numbers
        // to a list of four 0 to 19 numbers
        // and packed into a 20 bits integer.
        // Prices got from each possible sequence will be stored in the following vector instead of a hashmap.
        val allSequences = MutableList(1 shl 20) { 0 }
        val sequenceMask = (1 shl 20) - 1
        input.forEach { line ->
            var prev = line.toLong()
            var sequence = 0

            // Store sequences seen by a single seller in a bitmap instead of a hashmap.
            // Round the size of the bitmap up to the nearest multiple of 64.
            val seen = MutableList(((1 shl 20) + ULong.SIZE_BITS - 1) / ULong.SIZE_BITS) { 0UL }
            for (i in 0..<2000) {
                val number = next(prev)
                val price = (number % 10).toInt()
                val priceDiff = 9 + price - (prev % 10).toInt()
                sequence = ((sequence shl 5) + priceDiff) and sequenceMask

                // We don't get a full sequence until seeing at least 4 new numbers.
                if (i >= 3) {
                    val wordIndex = sequence / ULong.SIZE_BITS
                    val bitIndex = sequence % ULong.SIZE_BITS
                    if (seen[wordIndex] and (1UL shl bitIndex) == 0UL) {
                        allSequences[sequence] += price
                        seen[wordIndex] = seen[wordIndex] or (1UL shl bitIndex)
                    }
                }
                prev = number
            }
        }

        return allSequences.max()
    }

    val input = readInput(2024, 22)

    show { part1(input) }
    show { part2(input) }
}