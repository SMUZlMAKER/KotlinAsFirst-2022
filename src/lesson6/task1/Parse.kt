@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.StringBuilder
import lesson2.task2.daysInMonth

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val match = Regex("""(\d+)\s([а-я]+)\s(\d+)""").matchEntire(str) ?: return ""
    val numbers = mutableListOf(
        match.groupValues[1].toIntOrNull() ?: return "",
        0,
        match.groupValues[3].toIntOrNull() ?: return ""
    )
    numbers[1] = when (match.groupValues[2]) {
        "января" -> 1
        "февраля" -> 2
        "марта" -> 3
        "апреля" -> 4
        "мая" -> 5
        "июня" -> 6
        "июля" -> 7
        "августа" -> 8
        "сентября" -> 9
        "октября" -> 10
        "ноября" -> 11
        "декабря" -> 12
        else -> return ""
    }
    return if (numbers[2] > 0 && numbers[1] in 1..12 &&
        numbers[0] in 1..daysInMonth(numbers[1], numbers[2])
    )
        String.format("%02d.%02d.%d", numbers[0], numbers[1], numbers[2])
    else
        ""
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val match = Regex("""(\d\d).(\d\d).(\d+)""").matchEntire(digital) ?: return ""
    if (match.groupValues[3].toInt() > 0 && match.groupValues[2].toInt() in 1..12 &&
        match.groupValues[1].toInt() in 1..
        daysInMonth(match.groupValues[2].toInt(), match.groupValues[3].toInt())
    )
        return match.groupValues[1].toInt().toString() + " " + when (match.groupValues[2]) {
            "01" -> "января"
            "02" -> "февраля"
            "03" -> "марта"
            "04" -> "апреля"
            "05" -> "мая"
            "06" -> "июня"
            "07" -> "июля"
            "08" -> "августа"
            "09" -> "сентября"
            "10" -> "октября"
            "11" -> "ноября"
            "12" -> "декабря"
            else -> return ""
        } + " " + match.groupValues[3]
    return ""
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String =
    if (phone.matches(Regex("""(\+[ -]*\d+)?([ -]*\([ -]*\d+[\d -]*\))?[ -]*\d+[\d -]*""")))
        phone.filter { it !in "() -" }
    else
        ""

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var max = -1
    if (!jumps.contains(Regex("""[^\d% -]""")) && jumps.isNotEmpty()) {
        val match = Regex("""[% -]*(\d+)""").findAll(jumps)
        match.forEach { if (it.groupValues.last().toInt() >= max) max = it.groupValues.last().toInt() }
    }
    return max
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (jumps.isNotEmpty()) {
        val num = mutableListOf<Int>()
        val str = jumps.split(" ")
        str.forEachIndexed { i, it ->
            if (it.matches(Regex("""\d+""")) &&
                str[i + 1].contains("+") && !str[i + 1].contains(Regex("""[^%+-]"""))
            ) num.add(it.toInt())
        }
        if (num.size > 0)
            return num.max()
    }
    return -1
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * При нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (expression.isNotEmpty()) {
        var sum = 0
        val str = expression.split(Regex("""\s+"""))
        if (str.size % 2 == 1) {
            if (!str[0].contains(Regex("""\D""")))
                sum += str[0].toInt()
            else
                throw IllegalArgumentException()
            for (i in 2..str.lastIndex step 2)
                if (str[i].contains(Regex("""\D""")) && str[i - 1].contains(Regex("""[^+-]""")))
                    throw IllegalArgumentException()
                else
                    sum += (str[i - 1] + str[i]).toInt()
            return sum
        }
    }
    throw IllegalArgumentException()
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    if (str.isNotEmpty()) {
        val match = Regex("""([^ ]+)\s\1""").find(str.lowercase())
        if (match != null)
            return match.range.first
    }
    return -1
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    /*var max = 0.0 to ""
    var i = 0
    while (i < description.length) {
        if (description[i].isDigit()) {
            val tmp = StringBuilder()
            var j = 0
            while (i + j != description.length && description[i + j] != ';') {
                tmp.append(description[i + j])
                j++
            }
            if ((tmp.toString().toDoubleOrNull() ?: return "") >= max.first) {
                val tmp2 = StringBuilder()
                var k = 0
                while (i - 2 - k != -1 && description[i - 2 - k] != ' ') {
                    tmp2.append(description[i - 2 - k])
                    k++
                }
                max = tmp.toString().toDouble() to tmp2.toString().reversed()
            }
            i += j
        }
        i++
    }
    return max.second*/
    var max = 0.0 to ""
    var i = description.lastIndex
    while (i > 0) {
        if (description[i].isDigit()) {
            val tmp = StringBuilder()
            var j = 0
            while (/*i - j != -1 && */description[i - j] != ' ') {
                tmp.append(description[i - j])
                j++
            }
            if ((tmp.toString().reversed().toDoubleOrNull() ?: return "") >= max.first) {
                i-=j+1
                val tmp2 = StringBuilder()
                var k = 0
                while (i - k != -1 && description[i - k] != ' ') {
                    tmp2.append(description[i - k])
                    k++
                }
                max = tmp.toString().reversed().toDouble() to tmp2.toString().reversed()
                i -= k
            }

        }
        i--
    }
    return max.second
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var open = 0
    var close = 0
    commands.forEach {
        when (it) {
            '[' -> open++
            ']' -> {
                close++
                if (close > open) throw IllegalArgumentException("Unpaired closing bracket")
            }

            '>', '<', '+', '-', ' ' -> {}
            else -> throw IllegalArgumentException("Illegal char")
        }
    }
    if (open != close)
        throw IllegalArgumentException("Unpaired opening bracket")
    var i = cells / 2
    val res = MutableList(cells) { 0 }
    var j = 0
    var countcom = 0
    while (j < commands.length && countcom < limit) {
        countcom++
        when (commands[j]) {
            ' ' -> {}
            '>' -> {
                i++
                if (i !in res.indices)
                    throw IllegalStateException("Going beyond borders")
            }

            '<' -> {
                i--
                if (i !in res.indices)
                    throw IllegalStateException("Going beyond borders")
            }

            '+' -> res[i]++
            '-' -> res[i]--
            '[' -> if (res[i] == 0) {
                var brackets = 1
                var k = 1
                while (j + k <= commands.lastIndex && brackets != 0) {
                    when (commands[j + k]) {
                        '[' -> brackets++
                        ']' -> brackets--
                        else -> {}
                    }
                    k++
                }
                j += k - 1
            }

            ']' -> if (res[i] != 0) {
                var brackets = 1
                var k = 1
                while (j - k > 0 && brackets != 0) {
                    when (commands[j - k]) {
                        '[' -> brackets--
                        ']' -> brackets++
                        else -> {}
                    }
                    k++
                }
                j -= k - 1
            }

            else -> {}
        }
        j++
    }
    return res
}
