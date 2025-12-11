import kotlin.time.measureTime

fun main() {
    fun List<String>.createDict() = associate {
        it.substringBefore(":") to it.substringAfter(":").trim().split(" ")
    }

    fun part1(input: List<String>): Int {
        val dict = input.createDict()

        fun dfs(path: String): Set<String> = if (path.endsWith("out")) {
            setOf(path)
        } else {
            dict[path.substringAfterLast("|")]!!.flatMapTo(mutableSetOf()) {
                dfs("$path|$it")
            }
        }

        return dict["you"]!!.flatMap {
            dfs(it)
        }.toSet().size
    }

    fun part2(input: List<String>): Int {
        val dict = input.createDict()

        fun dfs(path: String): Set<String> = if (path.endsWith("out")) {
            setOf(path)
        } else {
            dict[path.substringAfterLast("|")]!!.flatMapTo(mutableSetOf()) {
                dfs("$path|$it")
            }
        }

        return dict["svr"]!!.flatMap {
            dfs(it)
        }.toSet().filter { it.contains("fft") && it.contains("dac") }.size
    }

    val testInput = readInput("Day11_test")
    val testInput2 = readInput("Day11_test2")

    val input = readInput("Day11")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput2)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}