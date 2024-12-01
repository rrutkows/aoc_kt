import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.time.DurationUnit
import kotlin.time.measureTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: Int): List<String> {
    val path = String.format("src/year%d/Day%02d.txt", year, day)
    if (Path(path).notExists()) {
        val session = Path("session").readText().trim()
        ProcessBuilder(
            "curl",
            "\"https://adventofcode.com/$year/day/$day/input\"",
            "--cookie", "\"session=$session\"",
            "--output", "\"$path\""
        )
            .start()
            .waitFor()
    }
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

fun show(solve: () -> Any) {
    val result: Any
    val time = measureTime { result = solve() }
    val unit = sequenceOf(
        DurationUnit.SECONDS to 1.0,
        DurationUnit.MILLISECONDS to 1e-3,
        DurationUnit.MICROSECONDS to 1e-6,
        DurationUnit.NANOSECONDS to 1e-9
    )
        .first { time.toDouble(DurationUnit.SECONDS) > it.second }.first
    println("$result ${time.toString(unit, 2)}")
}

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
