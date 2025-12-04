import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }
    val testInput = readInput("Day00_test")

    val input = readInput("Day00")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}