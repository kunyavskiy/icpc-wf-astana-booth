// https://open.kattis.com/problems/turningred
private fun readInts() = readln().split(" ").map { it.toInt() }
private fun no() = println("impossible")
fun main() {
   val (l, b) = readInts()
   val vs = readln().map { (3 - "RGB".indexOf(it)) % 3 }
   val lamps = List(l) { mutableListOf<Int>() }
   repeat(b) { id -> readInts().drop(1).forEach { lamps[it - 1].add(id) } }
   val g = List(b) { mutableListOf<Pair<Int, Int>>() }
   for ((conn, col) in lamps.zip(vs).filter { it.first.size == 2 }) {
       g[conn[0]].add(Pair(conn[1], col))
       g[conn[1]].add(Pair(conn[0], col))
   }
   val color = IntArray(b) { -1 }
   fun runDfs(i: Int, value: Int, final: Boolean): Int? {
       val component = mutableListOf<Int>()
       fun dfs(v: Int, value: Int): Boolean {
           if (color[v] != -1) return color[v] == value
           color[v] = value
           component.add(v)
           return g[v].all { (u, d) -> dfs(u, (3 - value + d) % 3) }
       }
       val ans = if (!dfs(i, value)) null else component.sumOf { color[it] }
       if (!final) component.forEach { color[it] = -1 }
       return ans
   }
   var ans = 0
   for ((conn, col) in lamps.zip(vs)) {
       if (conn.isEmpty() && col != 0) return no()
       if (conn.size == 1) ans += runDfs(conn[0], col, true) ?: return no()
   }
   for (i in color.indices) {
       if (color[i] != -1) continue
       val best = (0 until 3).minOf { runDfs(i, it, false) ?: Int.MAX_VALUE }
       ans += runDfs(i, best, true) ?: return no()
   }
   println(ans)
}
