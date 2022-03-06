package regex

import java.util.regex.Matcher


//********************************************
//https://www.tutorialspoint.com/groovy/groovy_regular_expressions.htm
//********************************************

//println('Groovy' =~ 'Groovy'  )
//println('Groovy' =~ 'oo'      )
//println('Groovy' ==~ 'Groovy' )
//println('Groovy' ==~ 'oo'     )
//println('Groovy' =~ '∧G'      )
//println('Groovy' =~ 'G$'      )
//println('Groovy' =~ 'Gro*vy'  )
//println('Groovy' =~ 'Gro{2}vy')

//********************************************
//https://e.printstacktrace.blog/groovy-regular-expressions-the-definitive-guide/
//********************************************

//import java.util.regex.Pattern
//
//def pattern1 = ~"([Gg])roovy"
//
//assert pattern1.class == Pattern
//
//def pattern2 = Pattern.compile("([Gg])roovy")
//
//assert pattern2.class == Pattern

//********************************************

//def number = 2
//
//def str1 = /The number is 2/
//def str2 = /The number is $number/
//
//assert str1 instanceof String
//assert str2 instanceof GString

//********************************************

//assert (/Version \d+\.\d+\.\d+/) == 'Version \\d+\\.\\d+\\.\\d+'
//
//assert 'The price is $99' ==~ /The price is \$\d+/

//********************************************

//def pattern = ~/\S+er\b/
//def matcher = "My code is groovier and better when I use Groovy there" =~ pattern
//
//assert pattern instanceof java.util.regex.Pattern
//assert matcher instanceof java.util.regex.Matcher
//
//assert matcher.find()
//assert matcher.size() == 2
//assert matcher[0..-1] == ["groovier", "better"]

//def matcher = "My code is groovier and better when I use Groovy there" =~ /\S+er\b/
//
//assert matcher instanceof Matcher
//
//assert matcher.find()
//assert matcher.size() == 2
//assert matcher[0..-1] == ["groovier", "better"]

//********************************************

//if ("My code is groovier and better when I use Groovy there" =~ /\S+er\b/) {
//    println "At least one element matches the pattern..."
//}
//
//if ("Lorem ipsum dolor sit amet" =~ /\d+/) {
//    println "This line is not executed..."
//}

//********************************************

assert "v3.12.4" ==~ /v\d{1,3}\.\d{1,3}\.\d{1,3}/

assert !("GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}/)

assert "GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}.{1,100}/