package project10

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * tokenizer
 * 1.Removes all comments and white space from the input stream
 * 2.breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
class JackTokenizer {
    static enum TYPE {
        KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST, NONE,
    }
    static enum KEYWORD {
        CLASS, METHOD, FUNCTION, CONSTRUCTOR, INT, BOOLEAN, CHAR, VOID, VAR, STATIC,
        FIELD, LET, DO, IF, ELSE, WHILE, RETURN, TRUE, FALSE, NULL, THIS, NONE,
    }

    private static String currentToken = ''
    private static TYPE currentTokenType = TYPE.NONE
    private static int pointer = 0
    private static def tokens = []

    private static String keywordReg =
            'class|constructor|function|method|field|static|' +
                    'var |int |char |boolean |' +
                    'void|true|false|null|this|' +
                    'let |do |if|else|while|return'
    private static String symbolReg = $/[\&\*\+\(\)\.\/\,\-\]\;\~\}\|\{\>\=\[\<]/$
    private static String intReg = '[0-9]+'
    private static String strReg = '"[^"\n]*"'
    private static String idReg = /[\w_]+/
    private static Pattern tokenPatterns = ~"$keywordReg|$symbolReg|$intReg|$strReg|$idReg"
    private static def opSet = ['+','-','*','/','|','<','>','=','&'] as Set
    static def keywordMap = [
            class : KEYWORD.CLASS, constructor: KEYWORD.CONSTRUCTOR, function: KEYWORD.FUNCTION,
            method: KEYWORD.METHOD, field: KEYWORD.FIELD, static: KEYWORD.STATIC,
            'var ': KEYWORD.VAR, 'int ': KEYWORD.INT, 'char ': KEYWORD.CHAR, 'boolean ': KEYWORD.BOOLEAN,
            void  : KEYWORD.VOID, true: KEYWORD.TRUE, false: KEYWORD.FALSE, null: KEYWORD.NULL,
            this  : KEYWORD.THIS, 'let ': KEYWORD.LET, 'do ': KEYWORD.DO, if: KEYWORD.IF,
            else  : KEYWORD.ELSE, while: KEYWORD.WHILE, return: KEYWORD.RETURN,
    ]

    /**
     * Opens the input file/stream and gets ready to tokenize it
     * @param inFile
     */
    JackTokenizer(File inFile) {
        try {
            String preProcessed = ''
            inFile.eachLine {line ->
                String lineWithoutComments = noComments(line).trim()
                preProcessed += (line.length() > 0) ? "$lineWithoutComments\n" : ''
            }
            preProcessed = noBlockComments(preProcessed).trim()
            Matcher matcher = preProcessed =~ tokenPatterns
            while(matcher.find()){
                tokens += matcher.group()
                println(tokens)
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    /**
     * Do we have more tokens in the input?
     * @return
     */
    static boolean hasMoreTokens() {
        pointer < tokens.size()
    }

    /**
     * Gets the next token from the input and makes it the current token
     * This method should only be called if hasMoreTokens() is true
     * Initially there is no current token
     */
    static void advance(){
        if(hasMoreTokens()){
            currentToken = tokens[pointer++]
        } else {
            throw new IllegalStateException("No more tokens")
        }
        //println(currentToken)
        if(currentToken ==~ keywordReg) {
            currentTokenType = TYPE.KEYWORD
        } else if(currentToken ==~ symbolReg) {
            currentTokenType = TYPE.SYMBOL
        } else if(currentToken ==~ intReg) {
            currentTokenType = TYPE.INT_CONST
        } else if(currentToken ==~ strReg) {
            currentTokenType = TYPE.STRING_CONST
        } else if(currentToken ==~ idReg) {
            currentTokenType = TYPE.IDENTIFIER
        } else {
            throw new IllegalArgumentException("Unknown token: $currentToken")
        }
    }

    static String token() {
        currentToken
    }

    /**
     * Returns the type of the current token
     * @return
     */
    static TYPE tokenType(){
        currentTokenType
    }

    /**
     * Returns the keyword which is the current token
     * Should be called only when tokenType() is KEYWORD
     * @return
     */
    static KEYWORD keyWord(){
        if(currentTokenType == TYPE.KEYWORD){
            keywordMap[currentToken]
        } else {
            throw new IllegalStateException("Current token is not a keyword!")
        }
    }

    /**
     * Returns the character which is the current token
     * should be called only when tokenType() is SYMBOL
     * @return if current token is not a symbol return \0
     */
    static String symbol(){
        if(currentTokenType == TYPE.SYMBOL){
            currentToken
        } else {
            throw new IllegalStateException("Current token is not a symbol!")
        }
    }

    /**
     * Return the identifier which is the current token
     * should be called only when tokenType() is IDENTIFIER
     * @return
     */
    static String identifier(){
        if(currentTokenType == TYPE.IDENTIFIER){
            currentToken
        } else {
            throw new IllegalStateException("Current token is not an identifier!")
        }
    }

    /**
     * Returns the integer value of the current token
     * should be called only when tokenType() is INT_CONST
     * @return
     */
    static int intVal(){
        if(currentTokenType == TYPE.INT_CONST){
            currentToken as int
        } else {
            throw new IllegalStateException("Current token is not an integer constant!")
        }
    }

    /**
     * Returns the string value of the current token
     * without the double quotes
     * should be called only when tokenType() is STRING_CONST
     * @return
     */
    static String stringVal(){
        if(currentTokenType == TYPE.STRING_CONST){
            currentToken[1..-2]
        } else {
            throw new IllegalStateException("Current token is not a string constant!")
        }
    }

    /**
     * move pointer back
     */
    static void pointerBack(){
        pointer = pointer > 0 ? --pointer: 0
    }

    /**
     * return if current symbol is a op
     * @return
     */
    static boolean isOp(){
        symbol() in opSet
    }

    /**
     * Delete comments(String after "//") from a String
     * @param strIn
     * @return
     */
    static String noComments(String strIn){
        strIn.split('//')[0]
    }

    /**
     * delete block comment
     * @param strIn
     * @return
     */
    static String noBlockComments(String strIn){
        strIn.replaceAll('(?s)/\\*.*?\\*/','')
    }
}