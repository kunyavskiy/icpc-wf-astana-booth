// https://open.kattis.com/problems/riddleofthesphinx
class Query(val a: Int, val b: Int, val c: Int) {
    val d = run {
        println("$a $b $c")
        System.out.flush()
        readln().toInt()
    }
    fun check(x: Int, y:Int, z:Int) = a * x + b * y + c * z == d
}
fun main() {
    val qs = listOf(
        Query(1, 0, 0),
        Query(0, 1, 0),
        Query(0, 0, 1),
        Query(1, 1, 1),
        Query(1, 2, 3),
    )
    val (a, b, c, d, _) = qs.map { it.d }
    fun test(x: Int, y: Int, z: Int): String? {
        return "$x $y $z".takeUnless { qs.count { it.check(x, y, z) } >= 4 }
    }
    println(
        test(a, b, c) ?: test(d - b - c, b, c) ?:
        test(a, d - a - c, c) ?: test(a, b, d - a - b)
    )
}
