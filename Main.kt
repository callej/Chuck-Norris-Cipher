package chucknorris

fun String.chuck(r: Boolean = false): String {
    val bin = if (r) this else this.toList().joinToString("") { Integer.toBinaryString(it.code).padStart(7, '0') } + " "
    if (bin.first() == '0') return "00 " + Regex("(0+)").find(bin)!!.value + " " + bin.dropWhile { it == '0' }.chuck(true).trim()
    if (bin.first() == '1') return "0 " + "0".repeat(Regex("(1+)").find(bin)!!.value.length) + " " + bin.dropWhile { it == '1' }.chuck(true).trim()
    return ""
}

fun String.unChuck(): String {
    return this.split(" ").mapIndexed { i, s ->
        if (i % 2 == 1) if (this.split(" ")[i - 1] == "0") "1".repeat(s.length) else s else ""
    }.joinToString("").chunked(7).map { if (it.length != 7) throw Exception() else it.toInt(2).toChar() }.joinToString("")
}

fun encode() {
    println("Input string:")
    println(readln().also { println("Encoded string:") }.chuck())
}

fun decode() {
    println("Input encoded string:")
    val encoded = readln()
    if (!encoded.all { it in listOf('0', ' ') } ||
        !encoded.split(" ").filterIndexed { i, _ -> i % 2 == 0 }.all { it in listOf("0", "00") } ||
        encoded.split(" ").size % 2 != 0 ) {
        println("Encoded string is not valid.")
    } else {
        try {
            println("Decoded string:\n${encoded.unChuck()}")
        } catch (e : Exception) {
            println("Encoded string is not valid.")
        }
    }
}

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val input = readln()) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> { println("Bye!"); break }
            else -> println("There is no '$input' operation")
        }
        println()
    }
}