package presentation.regex

//********************************************
//https://www.tutorialspoint.com/groovy/groovy_regular_expressions.htm
//********************************************
import java.util.regex.Pattern

println('Groovy' =~ 'Groovy')
println(Pattern.compile('Groovy').matcher('Groovy'))
println()

println('Groovy' =~ 'oo')
println(Pattern.compile('oo').matcher('Groovy'))
println()

println('Groovy' ==~ 'Groovy')
println('Groovy'.matches('Groovy'))
println()

println('Groovy' ==~ 'oo')
println('Groovy'.matches('oo'))
println()

println('Groovy' =~ '∧G')
println(Pattern.compile('∧G').matcher('Groovy'))
println()

println('Groovy' =~ 'G$'      )
println(Pattern.compile('G$').matcher('Groovy'))
println()

println('Groovy' =~ 'Gro*vy'  )
println(Pattern.compile('Gro*vy').matcher('Groovy'))
println()

println('Groovy' =~ 'Gro{2}vy')
println(Pattern.compile('Gro{2}vy').matcher('Groovy'))
println()

//********************************************
//https://e.printstacktrace.blog/groovy-regular-expressions-the-definitive-guide/
//********************************************

def pattern1 = ~"[Gg]roovy"

assert pattern1.class == Pattern

def pattern2 = Pattern.compile("([Gg])roovy")

assert pattern2.class == Pattern

//********************************************

def number = 2

def str1 = "The number is 2"
def str2 = "The number is $number"

assert str1 instanceof String
assert str2 instanceof GString

//********************************************

assert (/Version \d+\.\d+\.\d+/) == 'Version \\d+\\.\\d+\\.\\d+'

assert 'The price is $99' ==~ /The price is \$\d+/

//********************************************

def pattern = ~/\S+er\b/
def matcher = "My code is groovier and better when I use Groovy there" =~ pattern

println(pattern)
println(matcher)
println()

assert pattern instanceof java.util.regex.Pattern
assert matcher instanceof java.util.regex.Matcher

assert matcher.find()
assert matcher.size() == 2
assert matcher[0..-1] == ["groovier", "better"]

def matcher0 = "My code is groovier and better when I use Groovy there" =~ /\S+er\b/

import java.util.regex.Matcher
assert matcher0 instanceof Matcher

assert matcher0.find()
assert matcher0.size() == 2
assert matcher0[0..-1] == ["groovier", "better"]

//********************************************

def myMatcher = "My code is groovier and better when I use Groovy there" =~ /\S+er\b/

if (myMatcher) {
    println "At least one element matches the pattern..."
    println()
}

if ("Lorem ipsum dolor sit amet" =~ /\d+/) {
    println "This line is not executed..."
    println()
}

//********************************************

assert "v3.12.4" ==~ /v\d{1,3}\.\d{1,3}\.\d{1,3}/

assert !("GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}/)

assert "GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}.{1,100}/
