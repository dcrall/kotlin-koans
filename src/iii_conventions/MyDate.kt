package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    operator override fun compareTo(other: MyDate): Int {
        val yearComparison = year.compareTo(other.year)
        val monthComparison = month.compareTo(other.month)
        val dayComparison = dayOfMonth.compareTo(other.dayOfMonth)
        val comparison = listOf(yearComparison, monthComparison, dayComparison)
        val result = comparison.firstOrNull() { it != 0 }
        return result?: 0
    }
}


operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    operator override fun iterator() : Iterator<MyDate> {
        return DateRangeIterator(start, endInclusive);
    }

    private class DateRangeIterator(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {

        var current : MyDate = start

        override fun next() : MyDate {
            if(hasNext()) {
                var next = current;
                current = current.nextDay();
                return next;
            } else {
                throw IllegalStateException()
            }
        }

        override fun hasNext() : Boolean {
            return current <= endInclusive;
        }
    }
}

