import java.io.File

fun solvePartTwo(ranges: MutableList<Pair<Long,Long>>): Long {
    var res: Long = 0
    var actualRange = 0L to 0L

    for (pair in ranges) {
        if(pair.first > actualRange.second) {
            res += 1 + actualRange.second - actualRange.first
            actualRange = pair
        } else {
            actualRange = actualRange.first to if (actualRange.second > pair.second) actualRange.second else pair.second
        }
    }

    return res + actualRange.second - actualRange.first
}

fun main() {

    val file = File("src/main/resources/d5.txt").bufferedReader()

    var partOne: Long = 0
    var partTwo: Long = 0

    val ranges = mutableListOf<Pair<Long,Long>>()

    while(true) {
        val line = file.readLine()

        if(line.isBlank()) break

        val (start, end) = line.split("-").map { it.toLong() }
        ranges += start to end
    }

    ranges.sortBy { it.first }

    partTwo = solvePartTwo(ranges)

    while(true) {

        val value = file.readLine()?.toLong() ?: break

        for ((lower,higher) in ranges) {
            if (value in lower..higher) {
                ++partOne
                break
            }
        }
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}