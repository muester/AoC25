import java.io.File
import java.util.PriorityQueue

class UF(n: Int) {

    fun find(x: Int): Int {
        if (ancestor[x] != x) ancestor[x] = find(ancestor[x])
        return ancestor[x]
    }

    fun union(left: Int, right: Int) {
        val leftRoot = find(left)
        val rightRoot = find(right)

        if (leftRoot == rightRoot) return

        if (length[leftRoot] < length[rightRoot]) {
            ancestor[leftRoot] = rightRoot
            length[rightRoot] += length[leftRoot]
        } else {
            ancestor[rightRoot] = leftRoot
            length[leftRoot] += length[rightRoot]
        }

        --components
    }

    fun connected(a: Int, b: Int) = find(a) == find(b)
    fun isAllConnected() = components == 1
    fun topThreeProduct() = length.sortedDescending().take(3).reduce { a, b -> a * b }.toLong()

    private val ancestor = IntArray(n) { i -> i }
    private val length = IntArray(n) { 1 }
    private var components = n
}

fun distance(a: Triple<Long,Long,Long>, b: Triple<Long,Long,Long>): Long {
    val dx = a.first - b.first
    val dy = a.second - b.second
    val dz = a.third - b.third
    return dx*dx + dy*dy + dz*dz
}

fun main() {

    val file = File("src/main/resources/d8.txt").readLines()

    var partOne: Long = 0
    var partTwo: Long = 0

    val partOneIterations = 1000
    val entries = file.size

    val boxes = Array(entries) { i ->
        file[i].split(",").map { it.toLong() }.let {
        Triple(it[0], it[1], it[2]) }
    }

    val components = UF(entries)

    val distances = PriorityQueue<Pair<Pair<Int,Int>, Long>>(compareBy { it.second })

    for (i in 0 until entries) {
        for (j in i + 1 until entries) {
            distances.add((i to j) to distance(boxes[i], boxes[j]))
        }
    }

    var currentIteration = 1

    while (distances.isNotEmpty()) {
        val (left, right) = distances.poll().first

        if (!components.connected(left, right)) {
            components.union(left, right)
        }

        if (currentIteration == partOneIterations) {
            partOne = components.topThreeProduct()
        }
        currentIteration++

        if (components.isAllConnected()) {
            partTwo = boxes[left].first * boxes[right].first
            break
        }
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}