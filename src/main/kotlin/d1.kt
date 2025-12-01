import java.io.File
import kotlin.math.abs

fun main() {

    val file = File("src/main/resources/d1.txt")

    var partOne = 0
    var partTwo = 0

    var acc = 50

    file.forEachLine {
        val value = it.substring(1).toInt()
        acc = when(it[0]) {
            'R' -> (acc % 100) + value
            else -> acc - value
        }

        // Part 1
        if(acc % 100 == 0) {
            ++partOne
        }

        // Part 2
        if(acc !in 1..99) {
            partTwo += abs(acc / 100) + if (acc <= 0) 1 else 0
        }

        acc = ((acc % 100) + 100) % 100
        if (acc == 0) acc = 100
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}