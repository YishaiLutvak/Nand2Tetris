package regex

import java.util.regex.Matcher


//********************************************
//https://www.tutorialspoint.com/groovy/groovy_regular_expressions.htm
//********************************************

println('Groovy' =~ 'Groovy'  )
println('Groovy'.matches('Groovy'))
println()
println('Groovy' =~ 'oo')
println('Groovy'.matches('oo'))
println()
println('Groovy' ==~ 'Groovy' )
println('Groovy'.matches('Groovy'))
println()
println('Groovy' ==~ 'oo'     )
println('Groovy'.matches('oo'))
println()
println('Groovy' =~ '∧G'      )
println('Groovy'.matches('∧G'))
println()
println('Groovy' =~ 'G$'      )
println('Groovy'.matches('G$'))
println()
println('Groovy' =~ 'Gro*vy'  )
println('Groovy'.matches('Gro*vy'))
println()
println('Groovy' =~ 'Gro{2}vy')
println('Groovy'.matches('Gro{2}vy'))

//********************************************
//https://e.printstacktrace.blog/groovy-regular-expressions-the-definitive-guide/
//********************************************

import java.util.regex.Pattern

def pattern1 = ~"([Gg])roovy"

assert pattern1.class == Pattern

def pattern2 = Pattern.compile("([Gg])roovy")

assert pattern2.class == Pattern

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

//assert "v3.12.4" ==~ /v\d{1,3}\.\d{1,3}\.\d{1,3}/
//
//assert !("GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}/)
//
//assert "GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}.{1,100}/

//********************************************
// https://stackoverflow.com/questions/10954870/groovy-remove-multiline-comment
//********************************************

//String a = "/*dd*/kafsa;l/*kld*/dklja/*kdjl/*ooiuuyy*/oooooo"
//
//println(a.replaceAll('(?s)/\\*.*?\\*/',''))

//********************************************

//enum KeyWords {
//    KW_CLASS,
//    KW_METHOD,
//    KW_FUNCTION,
//    KW_CONSTRUCTOR,
//    KW_INT,
//    KW_BOOLEAN,
//    KW_CHAR,
//    KW_VOID,
//    KW_VAR,
//    KW_STATIC,
//    KW_FIELD,
//    KW_LET,
//    KW_DO,
//    KW_IF,
//    KW_ELSE,
//    KW_WHILE,
//    KW_RETURN,
//    KW_TRUE,
//    KW_FALSE,
//    KW_NULL,
//    KW_THIS,
//    KW_NOTHING,
//}
//
//myMap = [
//        class      : KeyWords.KW_CLASS  ,
//        constructor:KeyWords.KW_CONSTRUCTOR,
//        function   :KeyWords.KW_FUNCTION,
//        method     :KeyWords.KW_METHOD  ,
//        field      :KeyWords.KW_FIELD   ,
//        static     :KeyWords.KW_STATIC  ,
//        var        :KeyWords.KW_VAR     ,
//        int        :KeyWords.KW_INT     ,
//        char       :KeyWords.KW_CHAR    ,
//        boolean    :KeyWords.KW_BOOLEAN ,
//        void       :KeyWords.KW_VOID    ,
//        true       :KeyWords.KW_TRUE    ,
//        false      :KeyWords.KW_FALSE   ,
//        null       :KeyWords.KW_NULL    ,
//        this       :KeyWords.KW_THIS    ,
//        let        :KeyWords.KW_LET     ,
//        do         :KeyWords.KW_DO      ,
//        if         :KeyWords.KW_IF      ,
//        else       :KeyWords.KW_ELSE    ,
//        while      :KeyWords.KW_WHILE   ,
//        return     :KeyWords.KW_RETURN  ,
//]
//
///**
// * init regex we need in tokenizer
// */
//String initKeyWordReg(){
//    def keyWordReg = ""
//    for(String seg: myMap.keySet()){
//        keyWordReg += seg + "|"
//    }
//    println(keyWordReg)
//}
//
//initKeyWordReg()