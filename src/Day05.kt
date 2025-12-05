import kotlin.math.max
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val splitIndex = input.indexOf("")
        val freshRange = input
            .take(splitIndex)
            .map { it.split("-") }
            .map { it[0].toLong()..it[1].toLong() }
        val freshIdsCount = input
            .takeLast(input.lastIndex - splitIndex)
            .count { id -> freshRange.any { range -> id.toLong() in range } }
        return freshIdsCount
    }

    fun part2(input: List<String>): Long {
        val splitIndex = input.indexOf("")
        val freshRange = input
            .take(splitIndex)
            .map { it.split("-") }
            .map { it[0].toLong()..it[1].toLong() }
            .sortedBy { it.first }

        val result = mutableListOf(freshRange.first())
        for (i in 1..freshRange.lastIndex) {
            val range = freshRange[i]
            val last = result.last()
            if (range.first <= last.last + 1) {
                result[result.lastIndex] = last.first..max(range.last, last.last)
            } else {
                result += range
            }
        }
        return result.sumOf { it.last - it.first + 1 }
    }

    val testInput = readInput("Day05_test")

    val input = readInput("Day05")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}