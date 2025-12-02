import java.io.File

fun sumRange(start: Long, end: Long): Pair<Long, Long> {

    var first: Long = 0
    var second: Long = 0

    for(i in start .. end) {
        val p = tryPatterns(i.toString())
        first += p.first
        second += p.second
    }

    return Pair(first, second)
}

fun tryPatterns(text: String): Pair<Long, Long> {

    var first: Long = 0
    var second: Long = 0

    for(c in (text.length/2) downTo 1) {
        if (text.length % c != 0) {
            continue
        }

        if(doesRepeat(text, c)) {
            if(text.length % 2 == 0 && c == text.length/2) {
                first += text.toLong()
            }
            second += text.toLong()
            break
        }
    }
    return Pair(first, second)
}

fun doesRepeat(text: String, length: Int) = (0 until text.length).all { text[it] == text[it % length] }

fun main() {
    val file = File("src/main/resources/d2.txt")
    val values = file.readLines()[0].split(',')

    var partOne: Long = 0
    var partTwo: Long = 0

    for (value in values) {
        val (first, second) = value.split("-").map { x -> x.toLong() }
        val p = sumRange(first, second)
        partOne += p.first
        partTwo += p.second
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}