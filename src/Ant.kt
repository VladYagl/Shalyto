import java.io.Reader

class Ant(reader: Reader) {
    class Node(val food: Int, val nofood: Int, val foodMove: String, val nofoodMove: String)

    val machine = ArrayList(reader.readLines().map {
        val (nofood, food, nofoodMove, foodMove) = it.split(' ')
        Node(food.toInt() - 1, nofood.toInt() - 1, foodMove, nofoodMove)
    })

    var current: Node = machine.first()

    fun move(hasFood: Boolean): String {
        val ans: String
        if (hasFood) {
            ans = current.foodMove
            current = machine[current.food]
        } else {
            ans = current.nofoodMove
            current = machine[current.nofood]
        }

//        println(ans + ' ' + (machine.indexOf(current) + 1))
        return ans
    }
}
