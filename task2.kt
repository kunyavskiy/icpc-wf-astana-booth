// https://open.kattis.com/problems/jetlag
fun main() {
   val n = readln().toInt()
   val times = buildList {
       add(0)
       repeat(n) {
           val ts = readln().split(" ").map { it.toLong() }
           if (last() != ts[0]) {
               addAll(ts)
           } else {
               this[lastIndex] = ts.last()
           }
       }
   }
   val ans = mutableListOf<Pair<Long, Long>>()
   fun add(x: Long, y: Long) = ans.add(Pair(x, y))
   var lastE = times.last()
   for ((prevE, curS) in times.dropLast(1).reversed().chunked(2)) {
       val t = curS - (lastE - curS + 1) / 2
       if (prevE > t) continue
       // fixing rounding issues on small segments, no bugs here
       val lFix = if (prevE == t - 1) -1 else 0
       val rFix = if (lastE == prevE + 3 && curS == prevE + 2) -1 else 0
       add(t + lFix, curS + rFix)
       add(prevE, (prevE + t) / 2)
       lastE = prevE
   }
   if (lastE != 0L) return println("impossible")
   ans.removeIf { (s, t) -> s >= t }
   println(ans.size)
   println(ans.reversed().joinToString("\n") { (a, b) -> "$a $b" })
}
