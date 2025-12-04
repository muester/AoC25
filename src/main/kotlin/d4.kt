import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun nextNeighbor(x: Int, y: Int): Pair<Int, Int> {
    return (x+y).sign to (y-x).sign
}

fun main() {

    val file = File("src/main/resources/d4.txt")

    val grid = file.readLines().map { line -> line.map { if (it == '.') 0 else 1 }.toMutableList() }.toMutableList()

    val result = mutableListOf<Int>()

    var iteration = 0

    while(iteration == 0 || result.last() != 0) {
        result.add(0)
        for ((rowIndex, row) in grid.withIndex()) {
            for ((columnIndex, column) in row.withIndex()) {

                if (column != 1)
                    continue

                var total: Long = 0

                var x = 1
                var y = 0

                repeat(8) {
                    total += abs(grid.getOrNull(rowIndex + y)?.getOrNull(columnIndex + x) ?: 0)
                    nextNeighbor(x, y).also {
                        x = it.first
                        y = it.second
                    }
                }

                if (total < 4) {
                    grid[rowIndex][columnIndex] = -1
                    ++result[iteration]
                }
            }
        }

        for(row in grid) {
            row.replaceAll() {if (it == -1) 0 else it}
        }
        ++iteration
    }

    println("Part 1: ${result[0]}, Part 2: ${result.sum()}")
}