import java.io.File

fun main() {

    val file = File("src/main/resources/d7.txt")

    var partOne: Long = 0
    var partTwo: Long = 0

    val grid = file.readLines().map { line -> line.toCharArray().map { c: Char ->
            when(c) {
                'S' -> -2L
                '^' -> -1L
                else -> 0L
            }
        }.toMutableList()
    }.toList()

    for(y in 1 until grid.size) {

        for (x in grid[y].indices) {
            // {S}
            if(grid[y-1][x] == -2L) {
                grid[y][x] = 1
            }
            // {^}
            if(grid[y][x] == -1L && grid[y-1][x] > 0) {
                ++partOne
                grid[y][x-1] += grid[y-1][x]
                grid[y][x+1] += grid[y-1][x]
            }
            // {| .}
            if(grid[y][x] >= 0) {
                grid[y][x] += if (grid[y-1][x] > 0) grid[y-1][x] else 0
            }
        }
    }

    partTwo = grid.last().sumOf { it }

    println("Part 1: $partOne, Part 2: $partTwo")
}