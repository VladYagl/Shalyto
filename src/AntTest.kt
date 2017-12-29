import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    BufferedReader(FileReader(File("res/ants/ant_${args[0]}.txt"))).use { antFile ->
        BufferedReader(FileReader(File("res/tests/test_${args[0]}.txt"))).use { testFile ->
            val ant = Ant(antFile)
            val world = World(testFile.readLines())
            world.print()

            var count = 0
            while (!world.empty()) {
                count++
                world.move(ant.move(world.next()))
                println()
                world.print()
            }
            println("steps = $count")
        }
    }
}