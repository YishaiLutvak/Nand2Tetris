package presentation.functional.closures.F_functional_programming

//https://groovy-lang.org/closures.html

def factorial
factorial = { int n, def accu = 1G ->
    if (n < 2) return accu
    //println(n * accu)
    factorial.trampoline(n - 1, n * accu)
}
factorial = factorial.trampoline()

assert factorial(1) == 1
//println(factorial(1))

assert factorial(10) == 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1
//println(factorial(3))

assert factorial(60) // == 402387260.. plus another 2560 digits
//println(factorial(1000))
