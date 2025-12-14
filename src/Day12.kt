import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val inputParts = input.parts { it }

        data class Shape(val area: Int)

        val shapes = inputParts.dropLast(1).mapIndexed { index, lines ->
            assert(lines[0] == "$index:")
            Shape(lines.drop(1).joinToString("").count { it == '#' })
        }
        return inputParts.last().sumOf { grid ->
            val (m, n) = grid.substringBefore(':').split('x').map { it.toInt() }
            val presents = grid.substringAfter(": ").split(' ').map { it.toInt() }

            val area = m * n
            if (presents.sumOf { c -> 9 * c } <= area) {
                // fit without overlap
                return@sumOf 1
            }
            if (presents.withIndex().sumOf { (i, c) -> shapes[i].area * c } > area) {
                // more presents area than available area
                return@sumOf 0
            }
            0
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day12_test")

    val input = readInput("Day12")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}