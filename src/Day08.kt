import kotlin.time.measureTime

data class Dist(val i: Int, val j: Int, val d: Long)

fun main() {
    fun part1(input: List<String>, connections: Int): Int {
        val boxes: List<List<Long>> = input.map {
            it.split(",").map { it.toLong() }
        }
        val distances = mutableListOf<Dist>()
        for (i in boxes.indices) for (j in i + 1..boxes.lastIndex) {
            val dist = ((boxes[i][0] - boxes[j][0]) * (boxes[i][0] - boxes[j][0])) +
                    ((boxes[i][1] - boxes[j][1]) * (boxes[i][1] - boxes[j][1])) +
                    ((boxes[i][2] - boxes[j][2]) * (boxes[i][2] - boxes[j][2]))
            distances += Dist(i, j, dist)
        }

        val toConnect = distances.sortedBy { it.d }.take(connections)
        val connections = mutableListOf<MutableSet<Int>>()
        toConnect.forEach { dist ->
            val overlapping: List<MutableSet<Int>> = connections.filter { dist.i in it || dist.j in it }
            if (overlapping.isEmpty()) {
                connections.add(mutableSetOf(dist.i, dist.j))
            } else {
                val merged = overlapping.fold(mutableSetOf(dist.i, dist.j)) { acc, set -> acc.addAll(set); acc }
                connections.removeAll(overlapping)
                connections.add(merged)
            }
        }

        return connections.sortedByDescending { it.size }.take(3).fold(1) { acc, value -> acc * value.size }
    }

    fun part2(input: List<String>): Long {
        val boxes: List<List<Long>> = input.map {
            it.split(",").map { it.toLong() }
        }
        val distances = mutableListOf<Dist>()
        for (i in boxes.indices) for (j in i + 1..boxes.lastIndex) {
            val dist = ((boxes[i][0] - boxes[j][0]) * (boxes[i][0] - boxes[j][0])) +
                    ((boxes[i][1] - boxes[j][1]) * (boxes[i][1] - boxes[j][1])) +
                    ((boxes[i][2] - boxes[j][2]) * (boxes[i][2] - boxes[j][2]))
            distances += Dist(i, j, dist)
        }

        var index = 0
        val toConnect = distances.sortedBy { it.d }
        val connections = mutableListOf<MutableSet<Int>>()
        while (index <= toConnect.lastIndex) {
            val dist = toConnect[index++]
            val overlapping: List<MutableSet<Int>> = connections.filter { dist.i in it || dist.j in it }
            if (overlapping.isEmpty()) {
                connections.add(mutableSetOf(dist.i, dist.j))
            } else {
                val merged = overlapping.fold(mutableSetOf(dist.i, dist.j)) { acc, set -> acc.addAll(set); acc }
                connections.removeAll(overlapping)
                connections.add(merged)
            }
            if (connections.size == 1 && connections.first().size == boxes.size) {
                return boxes[dist.i].first() * boxes[dist.j].first()
            }
        }
        return -1
    }

    val testInput = readInput("Day08_test")

    val input = readInput("Day08")

    val times = listOf(
        measureTime { println(part1(testInput, 10)) },
        measureTime { println(part1(input, 1000)) },
        measureTime { println(part2(testInput)) },
        measureTime { println(part2(input)) },
    )
    println(times)
}