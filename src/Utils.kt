import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: Int): List<String> {
    val path = String.format("src/year%d/Day%02d.txt", year, day)
    return Path(path)
        .readText()
        .trim()
        .lines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<T>.combinations(n: Int = 2): Sequence<List<T>> {
    val indices = Array(n) { 0 }
    fun yieldCombinations(depth: Int, start: Int): Sequence<List<T>> = sequence {
        for (i in start..<this@combinations.size) {
            indices[depth] = i
            if (depth == n - 1) {
                yield(indices.map { this@combinations[it] })
            } else {
                yieldAll(yieldCombinations(depth + 1, i + 1))
            }
        }
    }

    return yieldCombinations(0, 0)
}
