package year2024

import readInput
import show

fun main() {
    fun checksumAddend(fileId: Int, fileSize: Int, blockId: Int): Long =
        (blockId..<(blockId + fileSize)).sumOf { it.toLong() * fileId.toLong() }

    fun part1(input: String): Long {
        var sum: Long = 0

        var i = 0 // fragment (file or free space) index
        var iBlock = 0 // disk block
        var j = (input.length - 1) / 2 * 2 // last file index; must be even
        var jBlock = 0 // moved block from the j file
        var jSize = input[j].digitToInt()
        while (i <= j) {
            val iSize = input[i].digitToInt()
            if (i % 2 == 0) {
                if (i == j) {
                    sum += checksumAddend(i / 2, iSize - jBlock, iBlock)
                } else {
                    sum += checksumAddend(i / 2, iSize, iBlock)
                }
            } else {
                var moved = 0
                while (moved < iSize && j > i) {
                    sum += (iBlock.toLong() + moved) * (j.toLong() / 2) // j / 2 is the file ID
                    moved += 1
                    jBlock += 1
                    if (jBlock == jSize) {
                        // move to the next j file, while still moving blocks to the same i free space
                        j -= 2
                        jBlock = 0
                        jSize = input[j].digitToInt()
                    }
                }
            }

            iBlock += iSize
            i += 1
        }

        return sum
    }

    fun part2(input: String): Long {
        var sum: Long = 0
        var iBlock = 0

        // pair of
        // - starting block index and size for files,
        // - first free block index and free size left for free spaces
        val fragments = input.map {
            val iSize = it.digitToInt()
            (iBlock to iSize).also { iBlock += iSize }
        }.toMutableList()

        var j = (input.length - 1) / 2 * 2 // last file index; must be even
        while (j >= 0) {
            val jSize = input[j].digitToInt()
            var moved = false
            for (i in 1..<j step 2) {
                if (fragments[i].second >= jSize) {
                    sum += checksumAddend(j / 2, jSize, fragments[i].first)
                    fragments[i] = fragments[i].first + jSize to fragments[i].second - jSize
                    moved = true
                    break
                }
            }
            if (!moved) {
                sum += checksumAddend(j / 2, jSize, fragments[j].first)
            }

            j -= 2
        }

        return sum
    }

    val input = readInput(2024, 9).single()

    show { part1(input) }
    show { part2(input) }
}