package presentation.functional.closures.A_syntax

//https://groovy-lang.org/closures.html

def listener = { e -> println "Clicked on $e.source" }
assert listener instanceof Closure
Closure callback = { println 'Done!' }
Closure<Boolean> isTextFile = {
    File it -> it.name.endsWith('.txt')
}

