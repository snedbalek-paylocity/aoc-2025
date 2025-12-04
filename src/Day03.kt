import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it.toCharArray().map { it.digitToInt() }
        }.sumOf { batteries ->
            val left = batteries.subList(0, batteries.lastIndex).max()
            val index = batteries.indexOfFirst { it == left }
            val right = batteries.subList(index + 1, batteries.lastIndex + 1).max()
            //println("$left $right")
            (left * 10) + right
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { inputNumber ->
            var remove = inputNumber.length - 12
            val stack = ArrayDeque<Char>()
            for (digit in inputNumber) {
                while (remove > 0 && stack.isNotEmpty() && stack.last() < digit) {
                    stack.removeLast()
                    remove--
                }
                stack.addLast(digit)
            }

            repeat(remove) {
                stack.removeLast()
            }

            stack.joinToString("").take(12).toLong()
        }
    }

    val testInput = readInput("Day03_test")

    val input = readInput("Day03")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}