import java.io.File

fun solve(start: String, end: String, inp: String): Long {

    val precursors = mutableMapOf<String, MutableList<String>>()
    val noOfWays = mutableMapOf<String, Long>()

    inp.lines().forEach {

        val tokens = it.split(" ")
        val parent = tokens[0].dropLast(1)

        precursors.putIfAbsent(parent, mutableListOf())
        noOfWays.putIfAbsent(parent, 0)

        for (child in 1 until tokens.size) {
            precursors.getOrPut(tokens[child]) {mutableListOf()}.add(parent)
        }
    }

    precursors[start] = mutableListOf()
    noOfWays[start] = 1

    var reachedEnd = false

    while(!reachedEnd) {
        for(precKey in precursors.keys) {
            if (precursors[precKey]!!.isEmpty()) {
                if(precKey == end) {
                    reachedEnd = true
                } else {
                    for (key in precursors.keys) {
                        if (precKey in precursors[key]!!) {
                            precursors[key]!!.remove(precKey)
                            noOfWays[key] = noOfWays.getOrDefault(key, 0) + noOfWays[precKey]!!
                        }
                    }
                    precursors.remove(precKey)
                }
                break
            }
        }
    }
    return noOfWays[end]!!
}


fun main() {

    val input = File("src/main/resources/d11.txt").readText()

    val partOne: Long = solve("you", "out", input)
    val partTwo: Long = solve("svr", "dac", input) *
                        solve("dac", "fft", input) *
                        solve("fft", "out", input) +
                        solve("svr", "fft", input) *
                        solve("fft", "dac", input) *
                        solve("dac", "out", input)


    println("Part 1: $partOne, Part 2: $partTwo")
}