package project10

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * tokenizer
 * 1.Removes all comments and white space from the input stream
 * 2.breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
class JackTokenizer {
    static enum LexicalElements {
        LE_KEYWORD, LE_SYMBOL, LE_IDENTIFIER, LE_INT_CONST, LE_STRING_CONST, LE_NOTHING,
    }
    static enum KeyWords{
        KW_CLASS, KW_METHOD, KW_FUNCTION, KW_CONSTRUCTOR, KW_INT, KW_BOOLEAN, KW_CHAR, KW_VOID, KW_VAR, KW_STATIC,
        KW_FIELD, KW_LET, KW_DO, KW_IF, KW_ELSE, KW_WHILE, KW_RETURN, KW_TRUE, KW_FALSE, KW_NULL, KW_THIS, KW_NOTHING,
    }

    private static String currentToken = ''
    private static LexicalElements currentTokenType = LexicalElements.LE_NOTHING
    private static int pointer = 0
    private static def tokens = []

    private static String keyWordReg =
            'class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return|'
    private static String symbolReg = $/[\&\*\+\(\)\.\/\,\-\]\;\~\}\|\{\>\=\[\<]/$
    private static String intReg = '[0-9]+'
    private static String strReg = '"[^"\n]*"'
    private static String idReg = /[\w_]+/
    private static Pattern tokenPatterns = ~(keyWordReg + symbolReg + "|" + intReg + "|" + strReg + "|" + idReg)
    private static def opSet = ['+','-','*','/','|','<','>','='] as Set
    static def myMap = [
            class: KeyWords.KW_CLASS, constructor: KeyWords.KW_CONSTRUCTOR, function: KeyWords.KW_FUNCTION,
            method: KeyWords.KW_METHOD, field : KeyWords.KW_FIELD, static: KeyWords.KW_STATIC,
            var: KeyWords.KW_VAR, int: KeyWords.KW_INT, char: KeyWords.KW_CHAR, boolean: KeyWords.KW_BOOLEAN,
            void: KeyWords.KW_VOID, true: KeyWords.KW_TRUE, false: KeyWords.KW_FALSE, null: KeyWords.KW_NULL,
            this: KeyWords.KW_THIS, let: KeyWords.KW_LET, do: KeyWords.KW_DO, if: KeyWords.KW_IF,
            else: KeyWords.KW_ELSE, while: KeyWords.KW_WHILE, return: KeyWords.KW_RETURN,
    ]

    /**
     * Opens the input file/stream and gets ready to tokenize it
     * @param inFile
     */
    JackTokenizer(File inFile) {
        try {
            def preProcessed = ""
            inFile.eachLine {
                def line = noComments(it).trim()
                preProcessed += (line.length() > 0) ? "$line\n" : ""
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
        if(currentToken ==~ keyWordReg) {
            currentTokenType = LexicalElements.LE_KEYWORD
        } else if(currentToken ==~ symbolReg){
            currentTokenType = LexicalElements.LE_SYMBOL
        } else if(currentToken ==~ intReg) {
            currentTokenType = LexicalElements.LE_INT_CONST
        } else if(currentToken ==~ strReg) {
            currentTokenType = LexicalElements.LE_STRING_CONST
        } else if(currentToken ==~ idReg) {
            currentTokenType = LexicalElements.LE_IDENTIFIER
        } else {
            throw new IllegalArgumentException("Unknown token: $currentToken")
        }
    }

    static String getCurrentToken() {
        currentToken
    }

    /**
     * Returns the type of the current token
     * @return
     */
    static LexicalElements getTokenType(){
        currentTokenType
    }

    /**
     * Returns the keyword which is the current token
     * Should be called only when tokenType() is KEYWORD
     * @return
     */
    static KeyWords keyWord(){
        if(currentTokenType == LexicalElements.LE_KEYWORD){
            myMap[currentToken]
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
        if(currentTokenType == LexicalElements.LE_SYMBOL){
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
        if(currentTokenType == LexicalElements.LE_IDENTIFIER){
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
        if(currentTokenType == LexicalElements.LE_INT_CONST){
            currentToken as int
        } else {
            throw new IllegalStateException("Current token is not an integer constant!")
        }
    }

    /**
     * Returns the string value of the current token
     * should be called only when tokenType() is STRING_CONST
     * @return
     */
    static String stringVal(){
        if(currentTokenType == LexicalElements.LE_STRING_CONST){
            currentToken//[1,-1]
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