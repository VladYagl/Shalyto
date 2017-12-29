import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.concurrent.Executors
import kotlin.system.exitProcess

//TODO: Parallel, stop reading every cycle

val pool = Executors.newFixedThreadPool(15)!!

fun gen(n: Int, max: Int, last: String, test: (String) -> Unit) {
    if (n == 0) {
        test(last)
    } else {
        (1..max).forEach { nofood ->
            (1..max).forEach { food ->
                listOf('M', 'L', 'R').forEach { nofoodMove ->
                    listOf('M', 'L', 'R').forEach { foodMove ->
                        if (n != max) {
                            gen(n - 1, max, "$last$nofood $food $nofoodMove $foodMove\n", test)
                        } else {
                            pool.submit {
                                gen(n - 1, max, "$last$nofood $food $nofoodMove $foodMove\n", test)
                            }
                        }
                    }
                }
            }
        }
    }
}

operator fun Date.minus(startDate: Date): Long {
    return this.time - startDate.time
}

fun main(args: Array<String>) {
    val testFile = File("res/tests/test_${args[0]}.txt")
    BufferedReader(FileReader(testFile)).use { test ->
        val testLines = test.readLines()
        val (_, n, steps) = testLines.first().split(' ').map(String::toInt)
        val startDate = Date()

        var count: Long = 0
        val aprox = Math.pow((n * n * 3 * 3).toDouble(), n.toDouble())
        gen(n, n, "") { antString ->
            val time = (Date() - startDate).toDouble() / 1000
            val progress = count / aprox
            if (count++ % 1000000 == 0L) println("count = $count aprox = ${aprox.toLong()} progress = ${progress * 100}%  total time: $time aproxTime = ${(time / progress / 60).toInt()}min")

            antString.reader().use { antFile ->
                val ant = Ant(antFile)
                val world = World(testLines)
                (1..steps).forEach {
                    world.move(ant.move(world.next()))
                }

                if (world.empty()) {
                    println(antString)
                    println()
                    exitProcess(0)
                }
            }
        }
    }
}