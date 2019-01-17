package com.ulitjd.kotlin

import java.time.LocalDate
import com.ulitjd.kotlin.TimeInterval.*

enum class TimeInterval { DAY, WEEK, YEAR }

/*-------------------------- Comparison --------------------------*/

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2

/*-------------------------- In Range --------------------------*/

class DateRangeOld(private val start: MyDate, private val endInclusive: MyDate) {
    operator fun contains(d: MyDate) = start <= d && d <= endInclusive
}

fun checkInRangeOld(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRangeOld(first, last)
}

/*-------------------------- Range To --------------------------*/

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

/*-------------------------- For Loop --------------------------*/

fun MyDate.nextDay(): MyDate {
    val d = LocalDate.of(year, month, dayOfMonth).plusDays(1)
    return MyDate(d.year, d.monthValue, d.dayOfMonth)
}

class DateIterator(private val dateRange: DateRange) : Iterator<MyDate> {
    private var current: MyDate = dateRange.start
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

/*-------------------------- Operators Overloading --------------------------*/

fun MyDate.addTimeIntervals(timeInterval: TimeInterval, number: Int): MyDate {
    var d = LocalDate.of(year, month, dayOfMonth)
    d = when (timeInterval) {
        DAY -> d.plusDays(number.toLong())
        WEEK -> d.plusWeeks(number.toLong())
        YEAR -> d.plusYears(number.toLong())
    }
    return MyDate(d.year, d.monthValue, d.dayOfMonth)
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) =
    addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}

/*-------------------------- Destructuring Declarations --------------------------*/

fun isLeapDay(date: MyDate): Boolean {

    val (year, month, dayOfMonth) = date

    // 29 February of a leap year
    return year % 4 == 0 && month == 2 && dayOfMonth == 29
}

/*-------------------------- Invoke --------------------------*/

class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        ++numberOfInvocations
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()

operator fun Int.invoke() { println(this) }

fun main() {
    when (7) {
        1 -> {
            println(MyDate(2019, 1, 13) > MyDate(2019, 1, 10))
            println(MyDate(2019, 1, 13) < MyDate(2019, 1, 10))
        }
        2 -> {
            println(checkInRangeOld(MyDate(2019, 7, 6), MyDate(2019, 2, 10), MyDate(2020, 1, 1)))
            println(checkInRangeOld(MyDate(2019, 1, 1), MyDate(2019, 2, 10), MyDate(2020, 1, 1)))
        }
        3 -> {
            println(checkInRange(MyDate(2017, 1, 1), MyDate(2017, 2, 10), MyDate(2018, 1, 1)))
            println(checkInRange(MyDate(2017, 7, 6), MyDate(2017, 2, 10), MyDate(2018, 1, 1)))
        }
        4 -> {
            iterateOverDateRange(
                MyDate(2017, 1, 27), MyDate(2017, 2, 2)
                , { println(it) })
        }
        5 -> {
            val date = MyDate(2017, 1, 27)
            date.println()
            task1(date).println()
            task2(date).println()
        }
        6 -> {
            isLeapDay(MyDate(2018, 2, 29)).println()
            isLeapDay(MyDate(2000, 2, 29)).println()
        }
        7->{
            val inv = Invokable()
            invokeTwice(inv)
            inv.numberOfInvocations.println()
            inv()()
            inv.numberOfInvocations.println()
            6051210()
        }
    }
}
