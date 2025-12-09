import java.io.File

import kotlin.math.abs

fun area(a: Pair<Int,Int>, b: Pair<Int,Int>): Long {
    val width = abs(a.first-b.first)+1
    val height = abs(a.second-b.second)+1
    return (width.toLong()*height.toLong())
}


fun main() {

    val file = File("src/main/resources/d9.txt")

    val points = mutableListOf<Pair<Int,Int>>()

    file.forEachLine {
        val (a,b) = it.split(",").map { it.toInt() }
        points += a to b
    }

    var topArea: Long = 0

    for(i in points.indices){
        for(j in points.indices){
            if(i == j) continue
            val newArea = area(points[i], points[j])
            topArea = if(topArea > newArea) topArea else newArea
        }
    }

    println("Part 1: $topArea")
}