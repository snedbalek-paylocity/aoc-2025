import java.util.Stack
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val maze = input.toCharArray2()
        val (m, n) = maze.size2()
        var count = 0
        maze.forEachIndexed2 { i, j, c ->
            if (c == '@') {
                var nearest = 0
                for (di in -1..1) for (dj in -1..1) {
                    val id = i + di
                    val jd = j + dj
                    if (id in 0..<m && jd in 0..<n && maze[id][jd] == '@') nearest++
                }
                if (nearest <= 4) count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val maze = input.toCharArray2()
        val (m, n) = maze.size2()
        var count = 0
        var changed: Boolean
        do {
            changed = false
            maze.forEachIndexed2 { i, j, c ->
                if (c == '@') {
                    var nearest = 0
                    for (di in -1..1) for (dj in -1..1) {
                        val id = i + di
                        val jd = j + dj
                        if (id in 0..<m && jd in 0..<n && maze[id][jd] == '@') nearest++
                    }
                    if (nearest <= 4) {
                        count++
                        changed = true
                        maze[i][j] = '.'
                    }
                }
            }
        } while (changed)

        return count
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}