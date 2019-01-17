package com.ulitjd.kotlin

import java.util.*

/*
* Kotlin Playground
* https://play.kotlinlang.org/koans/Introduction/Named%20arguments/Task.kt
* */

fun Any.println() {
    println(this.toString())
}

/* Hello, world! */
fun start() = "OK"

/* Named arguments */
fun joinOptions(options: Collection<String>) = options.joinToString(prefix = "[", postfix = "]")

/* Default arguments */
fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
    (if (toUpperCase) name.toUpperCase() else name) + number

fun useFoo() = listOf(
    foo("a"),
    foo("b", number = 1),
    foo("c", toUpperCase = true),
    foo(name = "d", number = 2, toUpperCase = true)
)

/* Lambdas */
fun containsEven(collection: Collection<Int>): Boolean = collection.any { it % 2 == 0 }

/* Strings */
val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} ${month} \d{4}"""

/* Data classes */
data class Person(val name: String, val age: Int)

fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}

/* Nullable types */
class PersonalInfo(val email: String?)

class Client(val personalInfo: PersonalInfo?)

interface Mailer {
    fun sendMessage(email: String, message: String)
}

fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    val email = client?.personalInfo?.email
    if (email != null && message != null) {
        mailer.sendMessage(email, message)
    }
}

/* Smart casts */
interface Expr

class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(expr: Expr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("Unknown expression")
    }

/* Extension functions */
data class RationalNumber(val numerator: Int, val denominator: Int)

fun Int.r(): RationalNumber = RationalNumber(this, 1)
fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(this.first, this.second)

fun testExtensionFun() {
    47.r().println()
    Pair(87, 96).r().println()
}

/* Object expressions */
fun getListObjExp(): List<Int> {
    val arrayList = arrayListOf(80, 33, 51, 68, 89)
    Collections.sort(arrayList, object : Comparator<Int> {
        override fun compare(a: Int, b: Int) = b - a
    })
    return arrayList
}

/* SAM (Single Abstract Method) conversions */
fun getListSAM(): List<Int> {
    val arrayList = arrayListOf(79, 97, 13, 96, 41)
    Collections.sort(arrayList, { x, y -> y - x })
    return arrayList
}

/* Extension functions on collections */
fun getListExFun(): List<Int> {
    return arrayListOf(41, 80, 12, 19, 58).sortedDescending()
}

fun main() {
    when (11) {
        1 -> start().println()
        2 -> joinOptions(listOf("1", "A", "#")).println()
        3 -> useFoo().println()
        4 -> {
            containsEven(listOf(1, 3, 5, 7)).println()
            containsEven(listOf(1, 3, 5, 6, 7)).println()
        }
        5 -> {
            val r = Regex(getPattern())
            r.matches("13 JUN 1992").println()
            r.matches("26 TIK 1985").println()
        }
        6 -> getPeople().println()
        7 -> {
            val client = Client(PersonalInfo("ulitjd@gmail.com"))

            class MyMailer : Mailer {
                override fun sendMessage(email: String, message: String) {
                    println("From: $email\nMessage:\n$message")
                }
            }

            val mailer = MyMailer()
            sendMessageToClient(client, "Hello World!", mailer)
            sendMessageToClient(null, "Hello World!", mailer)
            sendMessageToClient(client, null, mailer)
        }
        8 -> {
            eval(Num(15)).println()
            eval(Sum(Sum(Num(34), Num(52)), Sum(Num(28), Num(19))))
                .println()
        }
        9 -> testExtensionFun()
        10 -> getListObjExp().println()
        11 -> getListSAM().println()
        12 -> getListExFun().println()
    }
}
