@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.digitNumber
import kotlin.math.sqrt
import kotlin.math.pow

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { prev, el -> prev + sqr(el) })

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    list.fold(0.0) { prev, el -> prev + el } / if (list.isEmpty()) 1 else list.size

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val tmp = mean(list)
    for (i in 0 until list.size)
        list[i] -= tmp
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var sum = 0
    for (i in a.indices)
        sum += a[i] * b[i]
    return sum
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var sum = 0
    for (i in p.indices)
        sum += p[i] * x.toDouble().pow(i).toInt()
    return sum
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size)
        list[i] += list[i - 1]
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n1 = n
    val result = mutableListOf<Int>()
    while (n1 > 1) {
        var i = 2
        while (n1 % i != 0)
            i++
        n1 /= i
        result.add(i)
    }
    return result
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var tmp = n
    val list = mutableListOf<Int>()
    do {
        list.add(tmp % base)
        tmp /= base
    } while (tmp >= 1)
    return list.reversed()
}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String =
    convert(n, base).fold(StringBuilder()) { prev, el ->
        prev.append(
            if (el > 9)
                'a' + el - 10
            else
                (el).toString()
        )
    }.toString()

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    val degree = digits.size - 1
    var sum = 0
    for (i in digits.indices)
        sum += digits[i] * (base.toDouble().pow(degree - i).toInt())
    return sum
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var sum = 0
    val degree = str.length
    for (i in 0 until degree) {
        sum += base.toDouble().pow(degree - 1 - i).toInt() * if (str[i] < 'a' || str[i] > 'z')
            str[i] - '0'
        else
            (str[i] - 'a') + 10
    }
    return sum
}

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun step(max: String, mid: String, min: String, n: Int): String {
    var n1 = n
    val str = StringBuilder()
    val del = (10.0).pow(digitNumber(n) - 1).toInt()
    if (n1 / del == 9)
        str.append("$min$max")
    else {
        if (n1 / del >= 5) {
            str.append(mid)
            n1 -= 5 * del
            for (i in 0 until n1 / del)
                str.append(min)
        } else {
            if (n1 / del == 4) {
                str.append("$min$mid")
                n1 -= 4 * del
            }
            for (i in 0 until n1 / del)
                str.append(min)
        }
    }
    return str.toString()
}

fun roman(n: Int): String {
    val str = StringBuilder()
    var n1 = n
    for (i in 0 until n1 / 1000)
        str.append("M")
    n1 = n % 1000
    if (digitNumber(n1) == 3) {
        str.append(step("M", "D", "C", n1))
        n1 %= 100
    }
    if (digitNumber(n1) == 2) {
        str.append(step("C", "L", "X", n1))
        n1 %= 10
    }
    str.append(step("X", "V", "I", n1))
    return str.toString()
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var n1 = n / 1000
    val str = StringBuilder()
    for (i in 0..1) {
        if (n1 > 0) {
            str.append(
                when (n1 / 100) {
                    1 -> "сто "
                    2 -> "двести "
                    3 -> "триста "
                    4 -> "четыреста "
                    5 -> "пятьсот "
                    6 -> "шестьсот "
                    7 -> "семьсот "
                    8 -> "восемьсот "
                    9 -> "девятьсот "
                    else -> ""
                }
            )
            str.append(
                when (n1 / 10 % 10) {
                    1 -> when (n1 % 10) {
                        1 -> "одиннадцать "
                        2 -> "двенадцать "
                        3 -> "тринадцать "
                        4 -> "четырнадцать "
                        5 -> "пятнадцать "
                        6 -> "шестнадцать "
                        7 -> "семнадцать "
                        8 -> "восемнадцать "
                        9 -> "девятнадцать "
                        else -> "десять "
                    }

                    2 -> "двадцать "
                    3 -> "тридцать "
                    4 -> "сорок "
                    5 -> "пятьдесят "
                    6 -> "шестьдесят "
                    7 -> "семьдесят "
                    8 -> "восемьдесят "
                    9 -> "девяносто "
                    else -> ""
                }
            )
            if (n1 / 10 % 10 != 1)
                str.append(
                    when (n1 % 10) {
                        1 -> if (n / 1000 == n1) "одна " else "один "
                        2 -> if (n / 1000 == n1) "две " else "два"
                        3 -> "три "
                        4 -> "четыре "
                        5 -> "пять "
                        6 -> "шесть "
                        7 -> "семь "
                        8 -> "восемь "
                        9 -> "девять "
                        else -> ""
                    }
                )
            if (n / 1000 == n1)
                str.append(
                    if (n1 % 100 in 10..19)
                        "тысяч "
                    else
                        when (n1 % 10) {
                            1 -> "тысяча "
                            in 2..4 -> "тысячи "
                            else -> "тысяч "
                        }
                )
        }
        n1 = n % 1000
    }
    return str.toString().trim()
}

