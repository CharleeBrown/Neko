package eu.kanade.tachiyomi.util.lang

import kotlin.math.floor

/**
 * Replaces the given string to have at most [count] characters using [replacement] at its end.
 * If [replacement] is longer than [count] an exception will be thrown when `length > count`.
 */
fun String.chop(count: Int, replacement: String = "..."): String {
    return if (length > count)
        take(count - replacement.length) + replacement
    else
        this
}

fun String.removeArticles(): String {
    return when {
        startsWith("a ", true) -> substring(2)
        startsWith("an ", true) -> substring(3)
        startsWith("the ", true) -> substring(4)
        else -> this
    }
}

val String.sqLite: String
    get() = replace("'", "''")

fun String.trimOrNull(): String? {
    val trimmed = trim()
    return if (trimmed.isBlank()) null else trimmed
}

/**
 * Replaces the given string to have at most [count] characters using [replacement] near the center.
 * If [replacement] is longer than [count] an exception will be thrown when `length > count`.
 */
fun String.truncateCenter(count: Int, replacement: String = "..."): String {
    if (length <= count)
        return this

    val pieceLength: Int = floor((count - replacement.length).div(2.0)).toInt()

    return "${take(pieceLength)}$replacement${takeLast(pieceLength)}"
}

fun String.capitalizeWords(): String {
    val firstReplace = split(" ").joinToString(" ") { it.capitalize() }
    return firstReplace.split("-").joinToString("-") { it.capitalize() }
}

/**
 * Case-insensitive natural comparator for strings.
 */
fun String.compareToCaseInsensitiveNaturalOrder(other: String): Int {
    return String.CASE_INSENSITIVE_ORDER.then(naturalOrder()).compare(this, other)
}

private val uuidFormatLines = arrayOf(8, 13, 18, 23)
private val uuidFormatDigits = arrayOf((0..7), (9..12), (14..17), (19..22), (24..35))

/**
 * Check if a string is in UUID format.
 */
fun String.isUUID() =
    this.length == 36
        && uuidFormatLines.all { idx -> this[idx] == '-' }
        && uuidFormatDigits.all { range ->
        range.all { idx ->
            this[idx].let { char -> char in '0'..'9' || char in 'a'..'f' || char in 'A'..'F' }
        }
    }
