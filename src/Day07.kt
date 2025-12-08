import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val beamIndices = mutableSetOf(input.first().indexOf("S"))
        var beamCount = 0
        for (row in 1..input.lastIndex) {
            val newIndices = mutableSetOf<Int>()
            for (column in beamIndices) {
                if (input[row][column] == '^') {
                    newIndices.add(column + 1)
                    newIndices.add(column - 1)
                    beamCount++
                } else {
                    newIndices.add(column)
                }
            }
            beamIndices.clear()
            beamIndices.addAll(newIndices)
        }

        return beamCount
    }

    fun part2(input: List<String>): Long {
        val beamIndices = mutableMapOf(input.first().indexOf("S") to 1L)
        for (row in 1..input.lastIndex) {
            val newIndices = mutableMapOf<Int, Long>()
            for ((column, value) in beamIndices) {
                if (input[row][column] == '^') {
                    newIndices[column + 1] = newIndices.getOrDefault(column + 1, 0) + value
                    newIndices[column - 1] = newIndices.getOrDefault(column - 1, 0) + value
                } else {
                    newIndices[column] = newIndices.getOrDefault(column, 0) + value
                }
            }
            beamIndices.clear()
            beamIndices.putAll(newIndices)
        }

        return beamIndices.values.sum()
    }

    val testInput = readInput("Day07_test")

    val input = readInput("Day07")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}