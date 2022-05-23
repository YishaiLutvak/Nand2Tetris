package presentation.functional.closures.A_syntax

//https://groovy-lang.org/closures.html

{ item++ }

{ -> item++ }

{ println it }

{ it -> println it }

{ name -> println name }

{ String x, int y ->
    println "hey ${x} the value is ${y}"
}

{ reader ->
    def line = reader.readLine()
    line.trim()
}

