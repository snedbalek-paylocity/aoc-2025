import kotlin.time.measureTime

fun main() {
    fun Long.isDuplicate(): Boolean {
        val str = toString()
        val half = str.length / 2
        return Regex("(" + str.take(half) + ")" + "{2}").matches(str)
    }

    fun LongProgression.getWrongCodes(): List<Long> = this.filter { it.isDuplicate() }
    fun Long.isDuplicate2(): Boolean {
        val str = toString()
        val maxLen = str.length / 2
        for (len in 1..maxLen) {
            val count = str.length / len
            if (Regex("(" + str.take(len) + ")" + "{$count}").matches(str)) {
                return true
            }
        }
        return false
    }

    fun LongProgression.getWrongCodes2(): List<Long> = this.filter { it.isDuplicate2() }

    fun part1(input: List<String>): Long {
        return input.first()
            .splitToSequence(",")
            .map {
                val range = it.split("-")
                LongProgression.fromClosedRange(range[0].toLong(), range[1].toLong(), step = 1)
            }
            .flatMap { it.getWrongCodes() }
            .sum()
    }

    fun part2(input: List<String>): Long {
        return input.first()
            .splitToSequence(",")
            .map {
                val range = it.split("-")
                LongProgression.fromClosedRange(range[0].toLong(), range[1].toLong(), step = 1)
            }
            .flatMap { it.getWrongCodes2() }
            .sum()
    }

    val testInput = readInput("Day02_test")

    val input = readInput("Day02")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}