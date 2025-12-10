import kotlin.math.abs
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        val c = input.map { it.split(",").map { it.trim().toInt() } }
        var max = 0L
        for (i in c.indices) for (j in i..c.lastIndex) {
            val first = abs(c[i][0] - c[j][0]) + 1L
            val last = abs(c[i][1] - c[j][1]) + 1L
            max = maxOf(max, first * last)
        }
        return max
    }

    fun part2(input: List<String>): Long {
        val c = input.map { it.split(",").map { it.trim().toInt() } }
        val cs = c.size
        val xs = c.map { it[0] }.distinct().sorted()
        val ys = c.map { it[1] }.distinct().sorted()
        val xm = xs.withIndex().associate { (i, x) -> x to i }
        val ym = ys.withIndex().associate { (i, y) -> y to i }
        val a = Array(xs.size) { CharArray(ys.size) { ' ' } }
        for (i in 0..<cs) {
            val xi = xm[c[i][0]]!!
            val yi = ym[c[i][1]]!!
            val j1 = (i + 1) % cs
            val j2 = (i + cs - 1) % cs
            val j = if (c[j1][0] == c[i][0]) j1 else j2
            a[xi][yi] = if (c[j][1] > c[i][1]) 'v' else '^'
        }
        for (i in 0..<cs) {
            val j = (i + 1) % cs
            when {
                c[i][0] == c[j][0] -> {
                    val x = xm[c[i][0]]!!
                    val yi = ym[c[i][1]]!!
                    val yj = ym[c[j][1]]!!
                    val y1 = minOf(yi, yj)
                    val y2 = maxOf(yi, yj)
                    for (y in y1 + 1..<y2) {
                        check(a[x][y] == ' ')
                        a[x][y] = '|'
                    }
                }

                c[i][1] == c[j][1] -> {
                    val y = ym[c[i][1]]!!
                    val xi = xm[c[i][0]]!!
                    val xj = xm[c[j][0]]!!
                    val x1 = minOf(xi, xj)
                    val x2 = maxOf(xi, xj)
                    for (x in x1 + 1..<x2) {
                        check(a[x][y] == ' ')
                        a[x][y] = '-'
                    }
                }

                else -> error("$i -> $j")
            }
        }
        for (y in ys.indices) {
            var c = ' '
            var inside = false
            for (x in xs.indices) {
                val wasX = inside || c != ' '
                when (a[x][y]) {
                    '|' -> inside = !inside
                    'v' -> when (c) {
                        ' ' -> c = 'v'
                        'v' -> c = ' '
                        '^' -> {
                            c = ' '; inside = !inside
                        }
                    }

                    '^' -> when (c) {
                        ' ' -> c = '^'
                        '^' -> c = ' '
                        'v' -> {
                            c = ' '; inside = !inside
                        }
                    }
                }
                if ((wasX || inside || c != ' ') && a[x][y] == ' ') a[x][y] = 'X'
            }
            assert(!inside)
        }
        var max = 0L
        main@ for (i in 0..<cs) for (j in i + 1..<cs) {
            val xi = xm[c[i][0]]!!
            val xj = xm[c[j][0]]!!
            val x1 = minOf(xi, xj)
            val x2 = maxOf(xi, xj)
            val yi = ym[c[i][1]]!!
            val yj = ym[c[j][1]]!!
            val y1 = minOf(yi, yj)
            val y2 = maxOf(yi, yj)
            for (x in x1..x2) for (y in y1..y2) if (a[x][y] == ' ') {
                continue@main
            }
            val a = (abs(c[i][0] - c[j][0]) + 1L) * (abs(c[i][1] - c[j][1]) + 1L)
            max = maxOf(max, a)
        }
        return max
    }

    val testInput = readInput("Day09_test")

    val input = readInput("Day09")

    val times = listOf(
        measureTime { println(part1(testInput)) },
        measureTime { println(part1(input)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}