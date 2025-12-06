import java.io.File

fun applyOperation(prev: Long?, new: Long, oper: (Long, Long) -> Long): Long {
    return if (prev is Long) oper(prev, new) else new
}

fun main() {

    val file = File("src/main/resources/d6.txt")

    var partOne: Long = 0
    var partTwo: Long = 0

    val data = mutableListOf<MutableList<Long>>()
    val dataTwo = mutableListOf<String>()
    var signs = mutableListOf<String>()

    file.forEachLine { line ->
        if(line[0] != '+' && line[0] != '*') {
            data += line.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }.toMutableList()
            dataTwo += line
        } else {
            signs = line.split(" ").filter { it.isNotEmpty() }.toMutableList()
        }
    }

    dataTwo.maxOf { it.length }.also {dataTwo.replaceAll {line -> line.padEnd(it+1,' ')}}

    // Part 1
    for (i in signs.indices) {
        var total: Long? = null
        for (j in data.indices) {
            total = when(signs[i]){
                "+" ->  applyOperation(total, data[j][i]) { x, y -> x + y }
                else -> applyOperation(total, data[j][i]) { x, y -> x * y }
            }
        }
        partOne += total!!
    }

    // Part 2
    var currentSign = 0
    var total: Long? = null

    for(i in 0 until dataTwo.last().length) {
        val num = dataTwo.map { it[i] }.joinToString("")

        if(num.isNotBlank()) {
            val value = num.trim().toLong()
            total = when(signs[currentSign]) {
                "+" ->  applyOperation(total, value) { x, y -> x + y }
                else -> applyOperation(total, value) { x, y -> x * y }
            }
        } else {
            partTwo += total!!
            total = null
            ++currentSign
        }
    }

    println("Part 1: $partOne, Part 2: $partTwo")
}