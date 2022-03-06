package project10

//import java.io.File
//import java.io.FileNotFoundException
//import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * tokenizer
 * 1.Removes all comments and white space from the input stream
 * 2.breaks it into Jack-language tokens, as specified by the Jack grammar.
 */
class JackTokenizer {
    //constant for type
    final static int KEYWORD = 1
    final static int SYMBOL = 2
    final static int IDENTIFIER = 3
    final static int INT_CONST = 4
    final static int STRING_CONST = 5

    //constant for keyword
    final static int CLASS = 10
    final static int METHOD = 11
    final static int FUNCTION = 12
    final static int CONSTRUCTOR = 13
    final static int INT = 14
    final static int BOOLEAN = 15
    final static int CHAR = 16
    final static int VOID = 17
    final static int VAR = 18
    final static int STATIC = 19
    final static int FIELD = 20
    final static int LET = 21
    final static int DO = 22
    final static int IF = 23
    final static int ELSE = 24
    final static int WHILE = 25
    final static int RETURN = 26
    final static int TRUE = 27
    final static int FALSE = 28
    final static int NULL = 29
    final static int THIS = 30

    private Scanner scanner
    private String currentToken
    private int currentTokenType
    private int pointer
    private ArrayList<String> tokens

    private static Pattern tokenPatterns
    private static String keyWordReg
    private static String symbolReg
    private static String intReg
    private static String strReg
    private static String idReg
    private static HashMap<String,Integer> keyWordMap = new HashMap<String, Integer>()
    private static def opSet = ['+','-','*','/','|','<','>','='] as Set

    static {
        keyWordMap.put("class",CLASS)
        keyWordMap.put("constructor",CONSTRUCTOR)
        keyWordMap.put("function",FUNCTION)
        keyWordMap.put("method",METHOD)
        keyWordMap.put("field",FIELD)
        keyWordMap.put("static",STATIC)
        keyWordMap.put("var",VAR)
        keyWordMap.put("int",INT)
        keyWordMap.put("char",CHAR)
        keyWordMap.put("boolean",BOOLEAN)
        keyWordMap.put("void",VOID)
        keyWordMap.put("true",TRUE)
        keyWordMap.put("false",FALSE)
        keyWordMap.put("null",NULL)
        keyWordMap.put("this",THIS)
        keyWordMap.put("let",LET)
        keyWordMap.put("do",DO)
        keyWordMap.put("if",IF)
        keyWordMap.put("else",ELSE)
        keyWordMap.put("while",WHILE)
        keyWordMap.put("return",RETURN)
    }

    /**
     * Opens the input file/stream and gets ready to tokenize it
     * @param inFile
     */
    JackTokenizer(File inFile) {
        try {
            scanner = new Scanner(inFile)
            String preprocessed = ""
            //String line = ""
            while(scanner.hasNext()){
                line = noComments(scanner.nextLine()).trim()
                if (line.length() > 0) {
                    preprocessed += line + "\n"
                }
            }
            preprocessed = noBlockComments(preprocessed).trim()
            //init all regex
            initRegs()
            Matcher m = tokenPatterns.matcher(preprocessed)
            tokens = new ArrayList<String>()
            pointer = 0
            while(m.find()){
                tokens.add(m.group())
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace()
        }
        currentToken = ""
        currentTokenType = -1
    }

    /**
     * init regex we need in tokenizer
     */
    private static void initRegs(){
        keyWordReg = ""
        for(String seg: keyWordMap.keySet()){
            keyWordReg += seg + "|"
        }
        intReg = "[0-9]+"
        //symbolReg = "[\\&\\*\\+\\(\\)\\.\\/\\,\\-\\]\\;\\~\\}\\|\\{\\>\\=\\[\\<]"
        symbolReg = $/[\&\*\+\(\)\.\/\,\-\]\;\~\}\|\{\>\=\[\<]/$
        //strReg = "\"[^\"\n]*\""
        strReg = /"[^"\n]*"/
        //idReg = "[\\w_]+"
        idReg = /[\w_]+/
        tokenPatterns = Pattern.compile(keyWordReg + symbolReg + "|" + intReg + "|" + strReg + "|" + idReg)
    }

    /**
     * Do we have more tokens in the input?
     * @return
     */
    boolean hasMoreTokens() {
        return pointer < tokens.size()
    }

    /**
     * Gets the next token from the input and makes it the current token
     * This method should only be called if hasMoreTokens() is true
     * Initially there is no current token
     */
    void advance(){
        if(hasMoreTokens()) {
            currentToken = tokens.get(pointer)
            pointer++
        } else {
            throw new IllegalStateException("No more tokens")
        }
        //System.out.println(currentToken)
        if(currentToken.matches(keyWordReg)){
            currentTokenType = KEYWORD
        } else if(currentToken.matches(symbolReg)){
            currentTokenType = SYMBOL
        } else if(currentToken.matches(intReg)){
            currentTokenType = INT_CONST
        } else if(currentToken.matches(strReg)){
            currentTokenType = STRING_CONST
        } else if(currentToken.matches(idReg)){
            currentTokenType = IDENTIFIER
        } else {
            throw new IllegalArgumentException("Unknown token:" + currentToken)
        }
    }

    String getCurrentToken() {
        return currentToken
    }

    /**
     * Returns the type of the current token
     * @return
     */
    int tokenType(){
        return currentTokenType
    }

    /**
     * Returns the keyword which is the current token
     * Should be called only when tokenType() is KEYWORD
     * @return
     */
    int keyWord(){
        if(currentTokenType == KEYWORD){
            return keyWordMap.get(currentToken)
        } else {
            throw new IllegalStateException("Current token is not a keyword!")
        }
    }

    /**
     * Returns the character which is the current token
     * should be called only when tokenType() is SYMBOL
     * @return if current token is not a symbol return \0
     */
    String symbol(){
        if(currentTokenType == SYMBOL){
            // return currentToken.charAt(0)
            return currentToken[0]
        } else {
            throw new IllegalStateException("Current token is not a symbol!")
        }
    }

    /**
     * Return the identifier which is the current token
     * should be called only when tokenType() is IDENTIFIER
     * @return
     */
    String identifier(){
        if(currentTokenType == IDENTIFIER){
            return currentToken
        } else {
            throw new IllegalStateException("Current token is not an identifier!")
        }
    }

    /**
     * Returns the integer value of the current token
     * should be called only when tokenType() is INT_CONST
     * @return
     */
    int intVal(){
        if(currentTokenType == INT_CONST){
            return Integer.parseInt(currentToken)
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
    String stringVal(){
        if(currentTokenType == STRING_CONST){
            return currentToken.substring(1, currentToken.length() - 1)
        } else {
            throw new IllegalStateException("Current token is not a string constant!")
        }
    }

    /**
     * move pointer back
     */
    void pointerBack(){
        if(pointer > 0) {
            pointer--
        }
    }

    /**
     * return if current symbol is a op
     * @return
     */
    boolean isOp(){
        return opSet.contains(symbol())
    }

    /**
     * Delete comments(String after "//") from a String
     * @param strIn
     * @return
     */
    static String noComments(String strIn){
        int position = strIn.indexOf("//")
        if(position != -1){
            strIn = strIn.substring(0, position)
        }
        return strIn
    }

    /**
     * Delete spaces from a String
     * @param strIn
     * @return
     */
    static String noSpaces(String strIn){
        String result = ""
        if(strIn.length() != 0){
            String[] segs = strIn.split(" ")
            for(String s: segs){
                result += s
            }
        }
        return result
    }

    /**
     * delete block comment
     * @param strIn
     * @return
     */
    static String noBlockComments(String strIn){
        int startIndex = strIn.indexOf("/*")
        if(startIndex == -1) return strIn
        String result = strIn
        int endIndex = strIn.indexOf("*/")
        while(startIndex != -1){
            if(endIndex == -1){
                return strIn.substring(0,startIndex - 1)
            }
            result = result.substring(0,startIndex) + result.substring(endIndex + 2)
            startIndex = result.indexOf("/*")
            endIndex = result.indexOf("*/")
        }
        return result
    }
}