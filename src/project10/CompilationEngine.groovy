package project10
import static project10.JackTokenizer.KEYWORD
import static project10.JackTokenizer.TYPE

/**
 * recursive top-down parser
 *
 * Effects the actual compilation output.
 * 1.Gets its input from a JackTokenizer and emits its parsed structure into an output file/stream.
 * 2.The output is generated by a series of compile_xxx ( ) routines, one for every syntactic element xxx of the Jack grammar.
 * 3.The contract between these routines is that each compile_xxx ( ) routine should read the syntactic construct xxx from the input,
 * advance ( ) the tokenizer exactly beyond xxx, and output the parsing of xxx.
 * Thus, compile_xxx ( ) may only be called if indeed xxx is the next syntactic element of the input.
 *
 * In the first version of the compiler, described in chapter 10, this module emits a structured printout of the code, wrapped in XML tags.
 * In the final version of the compiler, described in chapter 11, this module generates executable VM code.
 * In both cases, the parsing logic and module API are exactly the same.
 */
class CompilationEngine {

    private static PrintWriter printWriter
    private static PrintWriter tokensPrintWriter
    private static JackTokenizer tokenizer
    private static int Indentation = 0
    private static String width= '  '
    static def reverseMap = [
            (TYPE.KEYWORD): 'keyword', (TYPE.SYMBOL): 'symbol', (TYPE.IDENTIFIER): 'identifier',
            (TYPE.INT_CONST): 'integerConstant', (TYPE.STRING_CONST): 'stringConstant',

            (KEYWORD.CLASS):'class', (KEYWORD.CONSTRUCTOR): 'constructor', (KEYWORD.FUNCTION): 'function',
            (KEYWORD.METHOD): 'method', (KEYWORD.FIELD): 'field', (KEYWORD.STATIC): 'static',
            (KEYWORD.VAR): 'var', (KEYWORD.INT): 'int', (KEYWORD.CHAR): 'char', (KEYWORD.BOOLEAN): 'boolean',
            (KEYWORD.VOID): 'void', (KEYWORD.TRUE): 'true', (KEYWORD.FALSE): 'false', (KEYWORD.NULL): 'null',
            (KEYWORD.THIS): 'this', (KEYWORD.LET): 'let', (KEYWORD.DO): 'do', (KEYWORD.IF): 'if',
            (KEYWORD.ELSE): 'else', (KEYWORD.WHILE): 'while', (KEYWORD.RETURN): 'return',
    ]

    /**
     * Creates a new compilation engine with the given input and output.
     * The next routine called must be compileClass()
     * @param inFile
     * @param outFile
     */
     CompilationEngine(File inFile, File outFile, File outTokensFile) {
        try {
            tokenizer = new JackTokenizer(inFile)
            printWriter = new PrintWriter(outFile)
            tokensPrintWriter = new PrintWriter(outTokensFile)
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    //-------------------compile_xxx ( ) routines - recursive descent-------------------//

    /**
     * Compiles a type
     */
    private static void compileType(){
        tokenizer.advance()
        TYPE type =  tokenizer.tokenType()
        if ((type == TYPE.KEYWORD && tokenizer.keyWord() in [KEYWORD.INT,KEYWORD.CHAR,KEYWORD.BOOLEAN]) ||
                (type == TYPE.IDENTIFIER)){
            writeXMLTag(type)
            return
        }
        error("int|char|boolean|className")
    }

    /**
     * Complies a complete class
     * class: 'class' className '{' classVarDec* subroutineDec* '}'
     */
     void compileClass(){
          //'class'
          tokenizer.advance()
          TYPE type =  tokenizer.tokenType()
          if (type != TYPE.KEYWORD || tokenizer.keyWord() != KEYWORD.CLASS){
               error("class")
          }
          printWriter.print("<class>\n")
          tokensPrintWriter.print("<tokens>\n")
          ++Indentation
          writeXMLTag(type)
          //className
          tokenizer.advance()
          type =  tokenizer.tokenType()
          if (type != TYPE.IDENTIFIER){
              error("className")
          }
          writeXMLTag(type)
          //'{'
          requireSymbol('{')
          //classVarDec* subroutineDec*
          compileClassVarDec()
          compileSubroutine()
          //'}'
          requireSymbol('}')
          if (tokenizer.hasMoreTokens()){
              throw new IllegalStateException("Unexpected tokens")
          }
          --Indentation
          tokensPrintWriter.print("</tokens>\n")
          printWriter.print("</class>\n")
          //save file
          printWriter.close()
          tokensPrintWriter.close()
     }

    /**
     * Compiles a static declaration or a field declaration
     * classVarDec ('static'|'field') type varName (','varName)* ';'
     */
    private void compileClassVarDec(){
        //first determine whether there is a classVarDec, nextToken is } or start subroutineDec
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        //next is a '}' or next is subroutineDec
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            return
        }
        //next is start subroutineDec or classVarDec, both start with keyword
        if (type != TYPE.KEYWORD){
            error("keyword")
        }
        //next is subroutineDec
        KEYWORD keyword = tokenizer.keyWord()
        if (keyword in [KEYWORD.CONSTRUCTOR, KEYWORD.FUNCTION, KEYWORD.METHOD]){
            tokenizer.pointerBack()
            return
        }
        printWriter.print("${width * Indentation++}<classVarDec>\n")
        //classVarDec exists
        if (!(keyword in [KEYWORD.STATIC, KEYWORD.FIELD])){
            error("static or field")
        }
        writeXMLTag(type)
        //type
        compileType()
        //at least one varName
        //boolean varNamesDone = false
        do {
            //varName
            tokenizer.advance()
            type = tokenizer.tokenType()
            if (type != TYPE.IDENTIFIER){
                error("identifier")
            }
            writeXMLTag(type)
            //',' or ';'
            tokenizer.advance()
            type = tokenizer.tokenType()
            String symbol = tokenizer.symbol()
            if (type != TYPE.SYMBOL || !(symbol in [',',';'])){
                error("',' or ';'")
            }
            writeXMLTag(type, symbol)
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        printWriter.print("${width * --Indentation}</classVarDec>\n")
        compileClassVarDec()
    }

    /**
     * Compiles a complete method function or constructor
     */
    private static void compileSubroutine(){
        //determine whether there is a subroutine, next can be a '}'
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        //next is a '}'
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            return
        }
        //start of a subroutine
        if (type != TYPE.KEYWORD || !(tokenizer.keyWord() in [KEYWORD.CONSTRUCTOR, KEYWORD.FUNCTION, KEYWORD.METHOD])){
            error("constructor|function|method")
        }
        printWriter.print("${width * Indentation++}<subroutineDec>\n")
        writeXMLTag(type)
        tokenizer.advance()
        type = tokenizer.tokenType()
        if (type == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.VOID){
            writeXMLTag(type,'void')
        } else {
            tokenizer.pointerBack()
            compileType()
        }
        //subroutineName which is a identifier
        tokenizer.advance()
        type = tokenizer.tokenType()
        if (type != TYPE.IDENTIFIER){
            error("subroutineName")
        }
        writeXMLTag(type)
        //'('
        requireSymbol('(')
        //parameterList
        printWriter.print("${width * Indentation++}<parameterList>\n")
        compileParameterList()
        printWriter.print("${width * --Indentation}</parameterList>\n")
        //')'
        requireSymbol(')')
        //subroutineBody
        compileSubroutineBody()
        printWriter.print("${width * --Indentation}</subroutineDec>\n")
        compileSubroutine()
    }

    /**
     * Compiles the body of a subroutine
     * '{'  varDec* statements '}'
     */
    private static void compileSubroutineBody(){
        printWriter.print("${width * Indentation++}<subroutineBody>\n")
        //'{'
        requireSymbol('{')
        //varDec*
        compileVarDec()
        //statements
        printWriter.print("${width * Indentation++}<statements>\n")
        compileStatement()
        printWriter.print("${width * --Indentation}</statements>\n")
        //'}'
        requireSymbol('}')
        printWriter.print("${width * --Indentation}</subroutineBody>\n")
    }

    /**
     * Compiles a single statement
     */
    private static void compileStatement(){
        //determine whether there is a statement_next can be a '}'
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        //next is a '}'
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            return
        }
        //next is 'let'|'if'|'while'|'do'|'return'
        if (type != TYPE.KEYWORD){
            error("keyword")
        } else {
            switch (tokenizer.keyWord()){
                case KEYWORD.LET -> compileLet()
                case KEYWORD.IF -> compileIf()
                case KEYWORD.WHILE -> compilesWhile()
                case KEYWORD.DO -> compileDo()
                case KEYWORD.RETURN -> compileReturn()
                default -> error("'let'|'if'|'while'|'do'|'return'")
            }
        }
        compileStatement()
    }

    /**
     * Compiles a (possibly empty) parameter list
     * not including the enclosing "()"
     * ((type varName)(',' type varName)*)?
     */
    private static void compileParameterList(){
        //check if there is parameterList, if next token is ')' than go back
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        if (type == TYPE.SYMBOL && tokenizer.symbol() == ')'){
            tokenizer.pointerBack()
            return
        }
        //there is parameter, at least one varName
        tokenizer.pointerBack()
        do {
            //type
            compileType()
            //varName
            tokenizer.advance()
            type = tokenizer.tokenType()
            if (type != TYPE.IDENTIFIER){
                error("identifier")
            }
            writeXMLTag(type)
            //',' or ')'
            tokenizer.advance()
            type = tokenizer.tokenType()
            String symbol = tokenizer.symbol()
            if (type != TYPE.SYMBOL || (!(symbol in [',',')']))){
                error("',' or ')'")
            }
            if (symbol == ','){
                writeXMLTag(type,',')
            } else {
                tokenizer.pointerBack()
                break
            }
        } while(true)
    }

    /**
     * Compiles a var declaration
     * 'var' type varName (',' varName)*
     */
    private static void compileVarDec(){
        //determine if there is a varDec
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        //no 'var' go back
        if (type != TYPE.KEYWORD || tokenizer.keyWord() != KEYWORD.VAR){
            tokenizer.pointerBack()
            return
        }
        printWriter.print("${width * Indentation++}<varDec>\n")
        writeXMLTag(type,'var')
        //type
        compileType()
        //at least one varName
        //boolean varNamesDone = false
        do {
            //varName
            tokenizer.advance()
            type = tokenizer.tokenType()
            if (type != TYPE.IDENTIFIER){
                error("identifier")
            }
            writeXMLTag(type)
            //',' or ';'
            tokenizer.advance()
            type = tokenizer.tokenType()
            String symbol = tokenizer.symbol()
            if (type != TYPE.SYMBOL || (!(symbol in [',',';']))){
                error("',' or ';'")
            }
            writeXMLTag(type, symbol)
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        printWriter.print("${width * --Indentation}</varDec>\n")
        compileVarDec()
    }

    /**
     * Compiles a do statement
     * 'do' subroutineCall ';'
     */
    private static void compileDo(){
        printWriter.print("${width * Indentation++}<doStatement>\n")
        writeXMLTag(TYPE.KEYWORD,'do')
        //subroutineCall
        compileSubroutineCall()
        //';'
        requireSymbol(';')
        printWriter.print("${width * --Indentation}</doStatement>\n")
    }

    /**
     * Compiles a let statement
     * 'let' varName ('[' ']')? '=' expression ';'
     */
    private static void compileLet(){
        printWriter.print("${width * Indentation++}<letStatement>\n")
        writeXMLTag(TYPE.KEYWORD,'let')
        //varName
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        if (type != TYPE.IDENTIFIER){
            error("varName")
        }
        writeXMLTag(type)
        //'[' or '='
        tokenizer.advance()
        type = tokenizer.tokenType()
        if (type != TYPE.SYMBOL || !(tokenizer.symbol() in ['[','='])){
            error("'['|'='")
        }
        //'[' expression ']'
        if (tokenizer.symbol() == '['){
            writeXMLTag(type,'[')
            compileExpression()
            //']'
            tokenizer.advance()
            type = tokenizer.tokenType()
            if (type == TYPE.SYMBOL && tokenizer.symbol() == ']'){
                writeXMLTag(type,']')
            } else {
                error("']'")
            }
            tokenizer.advance()
        }
        //'='
        writeXMLTag(TYPE.SYMBOL,'=')
        //expression
        compileExpression()
        //';'
        requireSymbol(';')
        printWriter.print("${width * --Indentation}</letStatement>\n")
    }

    /**
     * Compiles a while statement
     * 'while' '(' expression ')' '{' statements '}'
     */
    private static void compilesWhile(){
        printWriter.print("${width * Indentation++}<whileStatement>\n")
        writeXMLTag(TYPE.KEYWORD,'while')
        //'('
        requireSymbol('(')
        //expression
        compileExpression()
        //')'
        requireSymbol(')')
        //'{'
        requireSymbol('{')
        //statements
        printWriter.print("${width * Indentation++}<statements>\n")
        compileStatement()
        printWriter.print("${width * --Indentation}</statements>\n")
        //'}'
        requireSymbol('}')
        printWriter.print("${width * --Indentation}</whileStatement>\n")
    }

    /**
     * Compiles a return statement
     * ‘return’ expression? ';'
     */
    private static void compileReturn(){
        printWriter.print("${width * Indentation++}<returnStatement>\n")
        writeXMLTag(TYPE.KEYWORD,'return')
        //check if there is any expression
        tokenizer.advance()
        //no expression
        TYPE type = tokenizer.tokenType()
        if (type == TYPE.SYMBOL && tokenizer.symbol() == ';'){
            writeXMLTag(type,';')
            printWriter.print("${width * --Indentation}</returnStatement>\n")
            return
        }
        tokenizer.pointerBack()
        //expression
        compileExpression()
        //';'
        requireSymbol(';')
        printWriter.print("${width * --Indentation}</returnStatement>\n")
    }

    /**
     * Compiles an if statement
     * possibly with a trailing else clause
     * 'if' '(' expression ')' '{' statements '}' ('else' '{' statements '}')?
     */
    private static void compileIf(){
        printWriter.print("${width * Indentation++}<ifStatement>\n")
        writeXMLTag(TYPE.KEYWORD,'if')
        //'('
        requireSymbol('(')
        //expression
        compileExpression()
        //')'
        requireSymbol(')')
        //'{'
        requireSymbol('{')
        //statements
        printWriter.print("${width * Indentation++}<statements>\n")
        compileStatement()
        printWriter.print("${width * --Indentation}</statements>\n")
        //'}'
        requireSymbol('}')
        //check if there is 'else'
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        if (type == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.ELSE){
            writeXMLTag(type,'else')
            //'{'
            requireSymbol('{')
            //statements
            printWriter.print("${width * Indentation++}<statements>\n")
            compileStatement()
            printWriter.print("${width * --Indentation}</statements>\n")
            //'}'
            requireSymbol('}')
        } else { tokenizer.pointerBack() }
        printWriter.print("${width * --Indentation}</ifStatement>\n")
    }

    /**
     * Compiles a term.
     * This routine is faced with a slight difficulty when trying to decide between some of the alternative parsing rules.
     * Specifically, if the current token is an identifier
     * the routine must distinguish between a variable, an array entry and a subroutine call
     * A single look-ahead token, which may be one of "[" "(" "." suffices to distinguish between the three possibilities
     * Any other token is not part of this term and should not be advanced over
     *
     * integerConstant|stringConstant|keywordConstant|varName|varName '[' expression ']'|subroutineCall|
     * '(' expression ')'|unaryOp term
     */
    private static void compileTerm(){
        printWriter.print("${width * Indentation++}<term>\n")
        tokenizer.advance()
        //check if it is an identifier
        TYPE type1 = tokenizer.tokenType()
        if (type1 == TYPE.IDENTIFIER){
            //varName|varName '[' expression ']'|subroutineCall
            String token1 = tokenizer.token()
            tokenizer.advance()
            TYPE type2 = tokenizer.tokenType()
            String token2 = tokenizer.token()
            if (type2 == TYPE.SYMBOL && (token2 in ['(','.'])){
                //this is a subroutineCall
                tokenizer.pointerBack()
                tokenizer.pointerBack()
                compileSubroutineCall()
            } else {
                writeXMLTag(type1,token1)
                if (type2 == TYPE.SYMBOL && token2 == '['){
                    //this is an array entry
                    writeXMLTag(type2,'[')
                    //expression
                    compileExpression()
                    //']'
                    requireSymbol(']')
                } else  { tokenizer.pointerBack() } // this is varName
            }
        } else {
            //integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term
            if (type1 == TYPE.INT_CONST){
                writeXMLTag(type1,tokenizer.intVal() as String)
            } else if (type1 == TYPE.STRING_CONST){
                writeXMLTag(type1,tokenizer.stringVal())
            } else if(type1 == TYPE.KEYWORD && (tokenizer.keyWord() in [KEYWORD.TRUE, KEYWORD.FALSE, KEYWORD.NULL, KEYWORD.THIS])){
                writeXMLTag(type1)
            } else if (type1 == TYPE.SYMBOL && tokenizer.symbol() == '('){
                writeXMLTag(type1,'(')
                //expression
                compileExpression()
                //')'
                requireSymbol(')')
            } else if (type1 == TYPE.SYMBOL && (tokenizer.symbol() in ['-','~'])){
                writeXMLTag(type1)
                //term
                compileTerm()
            } else {
                error("integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term")
            }
        }
        printWriter.print("${width * --Indentation}</term>\n")
    }

    /**
     * Compiles a subroutine call
     * subroutineName '(' expressionList ')' | (className|varName) '.' subroutineName '(' expressionList ')'
     */
    private static void compileSubroutineCall(){
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        if (type != TYPE.IDENTIFIER){
            error("identifier")
        }
        writeXMLTag(type)
        tokenizer.advance()
        type = tokenizer.tokenType()
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '('){
            //'(' expressionList ')'
            writeXMLTag(type,'(')
            //expressionList
            compileExpressionList()
            //')'
            requireSymbol(')')
        } else if (type == TYPE.SYMBOL && tokenizer.symbol() == '.'){
            //(className|varName) '.' subroutineName '(' expressionList ')'
            writeXMLTag(type,'.')
            //subroutineName
            tokenizer.advance()
            type = tokenizer.tokenType()
            if (type != TYPE.IDENTIFIER){
                error("identifier")
            }
            writeXMLTag(type)
            //'('
            requireSymbol('(')
            //expressionList
            compileExpressionList()
            //')'
            requireSymbol(')')
        } else {
            error("'('|'.'")
        }
    }

    /**
     * Compiles an expression
     * term (op term)*
     */
    private static void compileExpression(){
        printWriter.print("${width * Indentation++}<expression>\n")
        //term
        compileTerm()
        //(op term)*
        do {
            tokenizer.advance()
            //op
            TYPE type = tokenizer.tokenType()
            if (type == TYPE.SYMBOL && tokenizer.isOp()){
                switch (tokenizer.symbol()){
                    case '>' -> writeXMLTag(type,'&gt;')
                    case '<' -> writeXMLTag(type,'&lt;')
                    case '&' -> writeXMLTag(type,'&amp;')
                    default -> writeXMLTag(type)
                }
                //term
                compileTerm()
            } else {
                tokenizer.pointerBack()
                break
            }
        } while (true)
        printWriter.print("${width * --Indentation}</expression>\n")
    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions
     * (expression(','expression)*)?
     */
    private static void compileExpressionList(){
        printWriter.print("${width * Indentation++}<expressionList>\n")
        tokenizer.advance()
        TYPE type = tokenizer.tokenType()
        //determine if there is any expression, if next is ')' then no
        tokenizer.pointerBack()
        if (!(type == TYPE.SYMBOL && tokenizer.symbol() == ')')){
            //expression
            compileExpression()
            //(','expression)*
            do {
                tokenizer.advance()
                type = tokenizer.tokenType()
                if (type == TYPE.SYMBOL && tokenizer.symbol() == ','){
                    writeXMLTag(type,',')
                    //expression
                    compileExpression()
                } else {
                    tokenizer.pointerBack()
                    break
                }
            } while(true)
        }
        printWriter.print("${width * --Indentation}</expressionList>\n")
    }

    //---------------------------------Auxiliary functions---------------------------------//

    /**
     * Writes the appropriate tag in a Tokens file and a Tree file
     */
    private static void writeXMLTag(TYPE type, String bodyTag = tokenizer.token()){
        String nameTag = reverseMap[type]
        printWriter.print("${width * Indentation}<$nameTag> $bodyTag </$nameTag>\n")
        tokensPrintWriter.print("$width<$nameTag> $bodyTag </$nameTag>\n")
        //println("<$nameTag> $infoTag </$nameTag>\n")
    }

    /**
     * require symbol when we know there must be such symbol
     * @param symbol
     */
    private static void requireSymbol(String symbol){
        tokenizer.advance()
        TYPE type =  tokenizer.tokenType()
        if (type == TYPE.SYMBOL && tokenizer.symbol() == symbol){
            writeXMLTag(type,symbol)
        } else error("'$symbol'")
    }

    /**
     * throw an exception to report errors
     * @param val is expected token missing
     */
    private static void error(String val){
        throw new IllegalStateException("Expected token missing: $val. Current token: ${tokenizer.token()}")
    }
}