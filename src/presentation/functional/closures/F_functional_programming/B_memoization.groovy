package presentation.functional.closures.F_functional_programming

//https://groovy-lang.org/closures.html

def fib
fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }
assert fib(15) == 610 // slow!

fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoize()
assert fib(25) == 75025 // fast!

fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoizeAtLeast(10)
assert fib(25) == 75025

fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoizeAtMost(100)
assert fib(25) == 75025

fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoizeBetween(10,100)
assert fib(25) == 75025

