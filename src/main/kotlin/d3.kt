import java.io.File

fun pickBiggest(list: String, start: Int, end: Int): Pair<Int, Int> {
    var biggest = -1
    var bigIndex = -1

    for(index in start .. end) {
        if(list[index].digitToInt() > biggest) {
            biggest = list[index].digitToInt()
            bigIndex = index
        }
    }
    return Pair(biggest, bigIndex+1)
}

fun solve(list: String, batteries: Int): Long {

    val digits = MutableList(batteries) { _-> 0}
    var currStart = 0

    for(i in 0 until batteries) {
        pickBiggest(list, currStart, list.length-batteries+i).also {
            digits[i] = it.first
            currStart = it.second
        }
    }

    return digits.joinToString("").toLong()
}

fun main() {

    val file = File("src/main/resources/d3.txt")

    var partOne: Long = 0
    var partTwo: Long = 0

    file.forEachLine {
        partOne += solve(it, 2)
        partTwo += solve(it,12)
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}