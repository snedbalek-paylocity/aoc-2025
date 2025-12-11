import kotlin.time.measureTime

fun main() {
    fun String.parse(): List<List<String>> {
        val diagram = substringAfter("[").substringBefore("]").split("")
        val schema = substringAfter("]").substringBefore("{").trim().split("\\s+".toRegex())
        val joltage = substringAfter("{").substringBefore("}").split(",")
        return listOf(diagram, schema, joltage)
    }
    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (diagram, schema, _) = line.parse()
        val diagramNumber = diagram.filter { it == "." || it == "#" }.mapIndexed { index, char ->
            when (char) {
                "." -> 0
                else -> 1
            } shl index
        }.sum()
        val masks = schema.map { switches ->
            switches.substringAfter("(").substringBefore(")").split(",").sumOf { 1 shl it.toInt() }
        }
        fun findMin(i: Int, mask: Int): Int {
            if (i >= masks.size) {
                return if (mask == diagramNumber) 0 else (Int.MAX_VALUE/2)
            }
            val min = minOf(
                findMin(i + 1, mask),
                findMin(i + 1, mask xor masks[i]) + 1
            )
            return min
        }
        findMin(0,0)
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        val (_, schema, joltage) = line.parse()
        val wiring = schema.map { switches ->
            switches.substringAfter("(").substringBefore(")").split(",").map {it.toInt()}
        }.sortedByDescending { it.size }
        val expected = joltage.map { it.toInt() }

        fun List<Int>.exceeds(another: List<Int>): Boolean =
            indices.any { this[it] > another[it] }

        fun List<Int>.add(indexes: List<Int>): List<Int> =
            mapIndexed { index, v -> v + if (index in indexes) 1 else 0 }

        fun dfs(actual: List<Int>, steps: Int, maxDepth: Int): Int? {
            if (actual.exceeds(expected)) return null
            if (actual == expected) return steps
            if (steps >= maxDepth) return null // Depth limit reached

            return wiring.firstNotNullOfOrNull { wires ->
                dfs(actual.add(wires), steps + 1, maxDepth)
            }
        }

        // Iteratively try increasing depths until solution found
        for (maxDepth in 0..1000) {
            val result = dfs(List(expected.size) { 0 }, 0, maxDepth)
            if (result != null) return@sumOf result
        }

        Int.MAX_VALUE
    }
    val testInput = readInput("Day10_test")

    val input = readInput("Day10")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}