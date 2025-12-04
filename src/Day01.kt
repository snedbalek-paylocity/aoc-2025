fun main() {
    fun part1(input: List<String>): Int {
        var cnt = 0
        var pos = 50
        for (line in input) {
            val number = line.substring(1).toInt()
            when (line[0]) {
                'R' -> {
                    pos += number
                    pos = pos.mod(100)
                    if (pos == 0) {
                        cnt++
                    }
                }
                'L' -> {
                    pos -= number
                    pos = pos.mod(100)
                    if (pos == 0) {
                        cnt++
                    }
                }
            }
        }
        return cnt
    }

    fun part2(input: List<String>): Int {
        var cnt = 0
        var pos = 50
        for (line in input) {
            val number = line.substring(1).toInt()
            when (line[0]) {
                'R' -> {
                    pos += number
                    cnt += pos / 100
                    pos = pos.mod(100)
                }
                'L' -> {
                    if (pos == 0) cnt--
                    pos -= number
                    cnt += (-pos + 100) / 100
                    pos = pos.mod(100)
                }
            }
        }
        return cnt
    }
    val testInput = readInput("Day01_test")

    val input = readInput("Day01")
    
    println(part1(testInput))
    println(part1(input))
    println(part2(testInput))
    println(part2(input))
}