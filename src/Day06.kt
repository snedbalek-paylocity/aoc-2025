import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        val data = input.dropLast(1).map { line ->
            line.trim().split("\\s+".toRegex()).map { it.toLong() }
        }
        val ops = input.takeLast(1).flatMap { line ->
            line.trim().split("\\s+".toRegex())
        }
        var acc = 0L
        ops.forEachIndexed { col, op ->
            when (op) {
                "*" -> {
                    var aux = 1L
                    for (row in data.indices) {
                        aux *= data[row][col]
                    }
                    acc += aux
                }

                "+" -> {
                    var aux = 0L
                    for (row in data.indices) {
                        aux += data[row][col]
                    }
                    acc += aux
                }
            }
        }
        return acc
    }

    fun part2(input: List<String>): Long {
        fun readCol(index: Int) = buildString {
            for (i in input.indices) {
                append(input[i].getOrElse(index) { ' ' })
            }
        }.trim()

        var index = input.first().lastIndex

        var acc = 0L
        val operands = mutableListOf<Long>()
        while (index >= 0) {
            val col = readCol(index)
            if (col.endsWith("*")) {
                operands += col.removeSuffix("*").trim().toLong()
                acc += operands.reduce { acc, value -> acc * value }
                operands.clear()
            } else if (col.endsWith("+")) {
                operands += col.removeSuffix("+").trim().toLong()
                acc += operands.sum()
                operands.clear()
            } else if (col.isNotEmpty()) {
                operands += col.toLong()
            }
            index--
        }

        return acc
    }

    val testInput = readInput("Day06_test")

    val input = readInput("Day06")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}