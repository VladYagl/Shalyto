class World(lines: List<String>) {
    val moves = arrayOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    val map = ArrayList(lines.drop(1).map {
        ArrayList(it.map { symbol ->
            when (symbol) {
                '*' -> true
                '.' -> false
                else -> throw Exception("Wrong world input")
            }
        })
    })
    val size = map.size

    var pos = Pair(0, 0)
    var direction = 0

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
            Pair((first + other.first + size) % size, (second + other.second + size) % size)

    private operator fun <E> List<ArrayList<E>>.get(pos: Pair<Int, Int>): E = get(pos.first)[pos.second]
    private operator fun <E> List<ArrayList<E>>.set(pos: Pair<Int, Int>, value: E) { get(pos.first)[pos.second] = value }

    fun move(command: String) = when (command) {
        "R" -> direction = (direction + 1) % 4

        "L" -> direction = (direction + 3) % 4

        "M" -> {
            pos += moves[direction]
            map[pos] = false
        }

        else -> throw Exception("Wrong command")
    }

    fun next(): Boolean = map[pos + moves[direction]]

    fun empty(): Boolean = !map.map { it.contains(true) }.contains(true)

    fun print() {
        val image = map.map { ArrayList(it.map { if (it) '*' else '.' }) }
        image[pos] = when (direction) {
            0 -> '>'
            1 -> 'v'
            2 -> '<'
            3 -> '^'
            else -> '?'
        }

        image.forEach { println(it.joinToString(separator = "")) }
        print(next().toString() + ' ')
    }
}

