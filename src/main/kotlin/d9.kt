import java.io.File

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Rect(val xLow: Int, val xHigh: Int, val yLow: Int, val yHigh: Int, val area: Long)

fun sortedRectangle(a: Pair<Int,Int>, b: Pair<Int,Int>): Rect {
    val xLow = min(a.first, b.first)
    val xHigh = max(a.first, b.first)
    val yLow = min(a.second, b.second)
    val yHigh = max(a.second, b.second)
    return Rect(xLow,xHigh,yLow,yHigh,(abs(xHigh-xLow)+1).toLong() * (abs(yHigh-yLow)+1).toLong())
}

fun main() {

    val file = File("src/main/resources/d9.txt")

    val points = mutableListOf<Pair<Int,Int>>()
    val rectangles = mutableListOf<Rect>()

    var partOne: Long = 0
    var partTwo: Long = 0

    file.forEachLine { line ->
        points += line.split(",").map { it.toInt() }.let{ Pair(it[0], it[1]) }
    }

    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            rectangles += sortedRectangle(points[i], points[j])
        }
    }

    partOne = rectangles.maxByOrNull { it.area }?.area ?: 0

    var largest: Long = 0

    for (r in rectangles) {

        var clipped = false

        for(i in points.indices) {
            val borderLine = sortedRectangle(points[i], points.getOrElse(i+1) {points[0]})
            // Check for intersects with any edge
            if(borderLine.xLow + 1 > r.xHigh || borderLine.xHigh - 1 < r.xLow||
                borderLine.yLow + 1 > r.yHigh || borderLine.yHigh - 1 < r.yLow) {
                continue
            }
            clipped = true
            break
        }

        if (!clipped) {
            if (r.area > largest) {
                largest = r.area
            }
        }
    }

    partTwo = largest

    println("Part 1: $partOne, Part 2: $partTwo")
}