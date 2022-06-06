package project11
import static project11.JackTokenizer.KEYWORD
import static project11.JackTokenizer.TYPE
import static project11.Symbol.KIND

/**
 * This class does the compilation itself.
 * It reads its input from a JackTokenizer and writes its output into a VMWriter.
 * It is organized as a series of compile_xxx ( ) routines, where xxx is a syntactic element of the Jack language.
 * The contract between these routines is that each compile_xxx ( ) routine should read the syntactic construct xxx from the input,
 * advance ( ) the tokenizer exactly beyond xxx, and emit to the output VM code effecting the semantics of xxx.
 * Thus compile_xxx ( ) may only be called if indeed xxx is the next syntactic element of the input.
 * If xxx is a part of an expression and thus has a value, the emitted code should compute this value and leave it at the top of the VM stack
 */
class CompilationEngine {

    private static JackTokenizer tokenizer
    private static VMWriter vmWriter
    private static SymbolTable symbolTable
    private static int labelIndex = 0

    private static String currentClass
    private static String currentSubroutine

    public static int Indentation = 0
    public static String width= '    '


    /**
     * Creates a new compilation engine with the given input and output.
     * The next routine called must be compileClass()
     * @param inFile
     * @param outFile
     */
    CompilationEngine(File inFile, File outFile) {
        try {
            tokenizer = new JackTokenizer(inFile)
            vmWriter = new VMWriter(outFile)
            symbolTable = new SymbolTable()
            labelIndex = 0
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    //-------------------compile_xxx ( ) routines - recursive descent-------------------//

    /**
     * Compiles a type
     * @return type
     */
    private static String compileType(){
        printOpenTagFunction('compileType')
        tokenizer.advance()
        TYPE type =  tokenizer.getTokenType()
        if ((type == TYPE.KEYWORD && (tokenizer.keyWord() in [KEYWORD.INT, KEYWORD.CHAR, KEYWORD.BOOLEAN])) ||
                (type == TYPE.IDENTIFIER)){
            printCloseTagFunction('compileType')
            return tokenizer.getCurrentToken()
        }
        error('int|char|boolean|className')
        printCloseTagFunction('compileType')
        return ""
    }

    /**
     * Complies a complete class
     * class: 'class' className '{' classVarDec* subroutineDec* '}'
     */
    static void compileClass(){
        printOpenTagFunction('compileClass')
        //'class'
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.KEYWORD || tokenizer.keyWord() != KEYWORD.CLASS){
            error('class')
        }
        //className
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
            error('className')
        }
        //classname does not need to be put in symbol table
        currentClass = tokenizer.identifier()
        //'{'
        requireSymbol('{')
        //classVarDec* subroutineDec*
        compileClassVarDec()
        compileSubroutine()
        //'}'
        requireSymbol('}')
        if (tokenizer.hasMoreTokens()){
            throw new IllegalStateException('Unexpected tokens')
        }
        printCloseTagFunction('compileClass')
        //save file
        vmWriter.close()
    }

    /**
     * Compiles a static declaration or a field declaration
     * classVarDec ('static'|'field') type varName (','varNAme)* ';'
     */
    private static void compileClassVarDec(){
        printOpenTagFunction('compileClassVarDec')
        //first determine whether there is a classVarDec, nextToken is } or start subroutineDec
        tokenizer.advance()
        //next is a '}' or next is subroutineDec
        TYPE type = tokenizer.getTokenType()
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            printCloseTagFunction('compileClassVarDec')
            return
        }
        //next is start subroutineDec or classVarDec, both start with keyword
        if (type != TYPE.KEYWORD){
            error('keyword')
        }
        //next is subroutineDec
        KEYWORD keyword = tokenizer.keyWord()
        if (keyword in [KEYWORD.CONSTRUCTOR, KEYWORD.FUNCTION, KEYWORD.METHOD]){
            tokenizer.pointerBack()
            printCloseTagFunction('compileClassVarDec')
            return
        }
        //classVarDec exists
        if (!(keyword in [KEYWORD.STATIC, KEYWORD.FIELD])){
            error('static or field')
        }
        KIND kind = KIND.NONE
        switch (keyword){
            case KEYWORD.STATIC -> kind = KIND.STATIC
            case KEYWORD.FIELD -> kind = KIND.FIELD
        }
        //type
        String stringType = compileType()
        //at least one varName
        String name
        do {
            //varName
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
                error('identifier')
            }
            name = tokenizer.getCurrentToken()
            symbolTable.define(name,stringType,kind)
            //',' or ';'
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.SYMBOL || !(tokenizer.symbol() in [',',';'])){
                error("',' or ';'")
            }
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        compileClassVarDec() //Recursive call
        printCloseTagFunction('compileClassVarDec')
    }

    /**
     * Compiles a complete method function or constructor
     */
    private static void compileSubroutine(){
        printOpenTagFunction('compileSubroutine')
        //determine whether there is a subroutine, next can be a '}'
        tokenizer.advance()
        TYPE type = tokenizer.getTokenType()
        //next is a '}'
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            printCloseTagFunction('compileSubroutine')
            return
        }
        //start of a subroutine
        if (type != TYPE.KEYWORD || !(tokenizer.keyWord() in [KEYWORD.CONSTRUCTOR, KEYWORD.FUNCTION, KEYWORD.METHOD])){
            error('constructor|function|method')
        }
        KEYWORD keyword = tokenizer.keyWord()
        //reset symbol table of subroutine
        symbolTable.startSubroutine()
        //for method this is the first argument
        if (keyword == KEYWORD.METHOD){
            symbolTable.define('this', currentClass, KIND.ARG)
        }
        tokenizer.advance()
        //'void' or type
        if (!(tokenizer.getTokenType() == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.VOID)){
            tokenizer.pointerBack()
            compileType()
        }
        //subroutineName which is a identifier
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
            error('subroutineName')
        }
        currentSubroutine = tokenizer.getCurrentToken()
        //'('
        requireSymbol('(')
        //parameterList
        compileParameterList()
        //')'
        requireSymbol(')')
        //subroutineBody
        compileSubroutineBody(keyword)
        compileSubroutine() //Recursive call
        printCloseTagFunction('compileSubroutine')
    }

    /**
     * Compiles the body of a subroutine
     * '{'  varDec* statements '}'
     */
    private static void compileSubroutineBody(KEYWORD keyword){
        printOpenTagFunction("compileSubroutineBody")
        //'{'
        requireSymbol('{')
        //varDec*
        compileVarDec()
        //write VM function declaration
        writeFunctionDec(keyword)
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        printCloseTagFunction("compileSubroutineBody")
    }

    /**
     * Compiles a single statement
     * letStatement|ifStatement|whileStatement|doStatement|returnStatement
     */
    private static void compileStatement(){
        printOpenTagFunction("compileStatement")
        //determine whether there is a statement next can be a '}'
        tokenizer.advance()
        TYPE type = tokenizer.getTokenType()
        //next is a '}'
        if (type == TYPE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            printCloseTagFunction("compileStatement")
            return
        }
        //next is 'let'|'if'|'while'|'do'|'return'
        if (type != TYPE.KEYWORD){
            error("keyword")
        } else {
            switch (tokenizer.keyWord()){
                case KEYWORD.LET   -> compileLet()
                case KEYWORD.IF    -> compileIf()
                case KEYWORD.WHILE -> compilesWhile()
                case KEYWORD.DO    -> compileDo()
                case KEYWORD.RETURN-> compileReturn()
                default            -> error("'let'|'if'|'while'|'do'|'return'")
            }
        }
        compileStatement() //Recursive call
        printCloseTagFunction("compileStatement")
    }

    /**
     * Compiles a (possibly empty) parameter list
     * not including the enclosing "()"
     * ((type varName)(',' type varName)*)?
     */
    private static void compileParameterList(){
        printOpenTagFunction("compileParameterList")
        //check if there is parameterList, if next token is ')' than go back
        tokenizer.advance()
        if (tokenizer.getTokenType() == TYPE.SYMBOL && tokenizer.symbol() == ')'){
            tokenizer.pointerBack()
            printCloseTagFunction("compileParameterList")
            return
        }
        String type
        //there is parameter, at least one varName
        tokenizer.pointerBack()
        do {
            //type
            type = compileType()
            //varName
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
                error("identifier")
            }
            symbolTable.define(tokenizer.identifier(), type, KIND.ARG)
            //',' or ')'
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.SYMBOL || !(tokenizer.symbol() in [',',')'])){
                error("',' or ')'")
            }
            if (tokenizer.symbol() == ')'){
                tokenizer.pointerBack()
                break
            }
        } while(true)
        printCloseTagFunction("compileParameterList")
    }

    /**
     * Compiles a var declaration
     * 'var' type varName (',' varName)*;
     */
    private static void compileVarDec(){
        printOpenTagFunction("compileVarDec")
        //determine if there is a varDec
        tokenizer.advance()
        //no 'var' go back
        if (tokenizer.getTokenType() != TYPE.KEYWORD || tokenizer.keyWord() != KEYWORD.VAR){
            tokenizer.pointerBack()
            printCloseTagFunction("compileVarDec")
            return
        }
        //type
        String type = compileType()
        //at least one varName
        //# boolean varNamesDone = false
        do {
            //varName
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
                error("identifier")
            }
            symbolTable.define(tokenizer.identifier(), type, KIND.VAR)
            //',' or ';'
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.SYMBOL || !(tokenizer.symbol() in [',',';'])){
                error("',' or ';'")
            }
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        compileVarDec() //Recursive call
        printCloseTagFunction("compileVarDec")
    }

    /**
     * Compiles a do statement
     * 'do' subroutineCall ';'
     */
    private static void compileDo(){
        printOpenTagFunction("compileDo")
        //subroutineCall
        compileSubroutineCall()
        //';'
        requireSymbol(';')
        //pop return value
        vmWriter.writePop(VMWriter.SEGMENT.TEMP,0)
        printCloseTagFunction("compileDo")
    }

    /**
     * Compiles a let statement
     * 'let' varName ('[' ']')? '=' expression ';'
     */
    private static void compileLet(){
        printOpenTagFunction("compileLet")
        //varName
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
            error("varName")
        }
        String varName = tokenizer.getCurrentToken()
        //'[' or '='
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.SYMBOL || !(tokenizer.symbol() in ['[','='])){
            error("'['|'='")
        }
        boolean arrayExist = false
        //'[' expression ']' ,need to deal with array [base+offset]
        if (tokenizer.symbol() == '['){
            arrayExist = true
            //push array variable, base address of array into stack
            vmWriter.writePush(getSeg(symbolTable.kindOf(varName)),symbolTable.indexOf(varName))
            //calc offset
            compileExpression()
            //']'
            requireSymbol(']')
            //base+offset
            vmWriter.writeArithmetic(VMWriter.COMMAND.ADD)
            //=
            requireSymbol('=')
        }
        //expression
        compileExpression()
        //';'
        requireSymbol(';')
        if (arrayExist){
            //*(base+offset) = expression
            //pop expression value to temp
            vmWriter.writePop(VMWriter.SEGMENT.TEMP,0)
            //pop base+index into 'that'
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,1)
            //pop expression value into *(base+index)
            vmWriter.writePush(VMWriter.SEGMENT.TEMP,0)
            vmWriter.writePop(VMWriter.SEGMENT.THAT,0)
        } else {
            //pop expression value directly
            vmWriter.writePop(getSeg(symbolTable.kindOf(varName)), symbolTable.indexOf(varName))
        }
        printCloseTagFunction("compileLet")
    }

    /**
     * Compiles a while statement
     * 'while' '(' expression ')' '{' statements '}'
     */
    private static void compilesWhile(){
        printOpenTagFunction("compilesWhile")
        String continueLabel = newLabel()
        String topLabel = newLabel()
        //top label for while loop
        vmWriter.writeLabel(topLabel)
        //'('
        requireSymbol('(')
        //expression while condition: true or false
        compileExpression()
        //')'
        requireSymbol(')')
        //if ~(condition) go to continue label
        vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
        vmWriter.writeIf(continueLabel)
        //'{'
        requireSymbol('{')
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        //if (condition) go to top label
        vmWriter.writeGoto(topLabel)
        //or continue
        vmWriter.writeLabel(continueLabel)
        printCloseTagFunction("compilesWhile")
    }

    /**
     * Compiles a return statement
     * ‘return’ expression? ';'
     */
    private static void compileReturn(){
        printOpenTagFunction("compileReturn")
        //check if there is any expression
        tokenizer.advance()
        if (tokenizer.getTokenType() == TYPE.SYMBOL && tokenizer.symbol() == ';'){
            //no expression, push 0 to stack
            vmWriter.writePush(VMWriter.SEGMENT.CONST,0)
        } else {
            //expression exist
            tokenizer.pointerBack()
            //expression
            compileExpression()
            //';'
            requireSymbol(';')
        }
        vmWriter.writeReturn()
        printCloseTagFunction("compileReturn")
    }

    /**
     * Compiles an if statement
     * possibly with a trailing else clause
     * 'if' '(' expression ')' '{' statements '}' ('else' '{' statements '}')?
     */
    static private void compileIf(){
        printOpenTagFunction("compileIf")
        String elseLabel = newLabel()
        String endLabel = newLabel()
        //'('
        requireSymbol('(')
        //expression
        compileExpression()
        //')'
        requireSymbol(')')
        //if ~(condition) go to else label
        vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
        vmWriter.writeIf(elseLabel)
        //'{'
        requireSymbol('{')
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        //if condition after statement finishing, go to end label
        vmWriter.writeGoto(endLabel)
        vmWriter.writeLabel(elseLabel)
        //check if there is 'else'
        tokenizer.advance()
        if (tokenizer.getTokenType() == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.ELSE){
            //'{'
            requireSymbol('{')
            //statements
            compileStatement()
            //'}'
            requireSymbol('}')
        } else {
            tokenizer.pointerBack()
        }
        vmWriter.writeLabel(endLabel)
        printCloseTagFunction("compileIf")
    }

    /**
     * Compiles a term.
     * This routine is faced with a slight difficulty when trying to decide between some of the alternative parsing rules.
     * Specifically, if the current token is an identifier
     * the routine must distinguish between a variable, an array entry and a subroutine call
     * A single look-ahead token, which may be one of "[" "(" "." suffices to distinguish between the three possibilities
     * Any other token is not part of this term and should not be advanced over
     *
     * varName|varName '[' expression ']'|subroutineCall|
     * integerConstant|stringConstant|keywordConstant|
     * '(' expression ')'|unaryOp term
     */
    static private void compileTerm(){
        printOpenTagFunction("compileTerm")
        tokenizer.advance()
        TYPE type1 = tokenizer.getTokenType()
        //check if it is an identifier
        if (type1 == TYPE.IDENTIFIER){
            //varName|varName '[' expression ']'|subroutineCall
            String token1 = tokenizer.getCurrentToken()
            tokenizer.advance()
            TYPE type2 = tokenizer.getTokenType()
            String token2 = tokenizer.getCurrentToken()
            if (type2 == TYPE.SYMBOL && (token2 in ['(', '.'])){
                //this is a subroutineCall
                tokenizer.pointerBack()
                tokenizer.pointerBack()
                compileSubroutineCall()
            } else {
                //this is an array entry or varName <=> varName|varName '[' expression ']'
                //if this is an array entry push array variable, base address into stack
                //if this is varName push variable directly onto stack
                vmWriter.writePush(getSeg(symbolTable.kindOf(token1)), symbolTable.indexOf(token1))
                if (type2 == TYPE.SYMBOL && token2 == '['){
                    //this is an array entry <=> varName '[' expression ']'
                    //expression
                    compileExpression()
                    //']'
                    requireSymbol(']')
                    //base+offset
                    vmWriter.writeArithmetic(VMWriter.COMMAND.ADD)
                    //pop into 'that' pointer
                    vmWriter.writePop(VMWriter.SEGMENT.POINTER,1)
                    //push *(base+index) onto stack
                    vmWriter.writePush(VMWriter.SEGMENT.THAT,0)
                } else { tokenizer.pointerBack() } //this is varName
            }
        } else {
            //integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term
            if (type1 == TYPE.INT_CONST){
                //integerConstant just push its value onto stack
                vmWriter.writePush(VMWriter.SEGMENT.CONST, tokenizer.intVal())
            } else if (type1 == TYPE.STRING_CONST){
                //stringConstant new a string and append every char to the new stack
                String str = tokenizer.stringVal()
                vmWriter.writePush(VMWriter.SEGMENT.CONST, str.length())
                vmWriter.writeCall("String.new",1)
                for (String ch in str){
                    vmWriter.writePush(VMWriter.SEGMENT.CONST, ch as char as int)
                    vmWriter.writeCall("String.appendChar",2)
                }
            } else if (type1 == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.TRUE){
                //~0 is true
                vmWriter.writePush(VMWriter.SEGMENT.CONST, 0)
                vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
            } else if (type1 == TYPE.KEYWORD && tokenizer.keyWord() == KEYWORD.THIS){
                //push this pointer onto stack
                vmWriter.writePush(VMWriter.SEGMENT.POINTER, 0)
            } else if (type1 == TYPE.KEYWORD && (tokenizer.keyWord() in [KEYWORD.FALSE, KEYWORD.NULL])){
                //0 for false and null
                vmWriter.writePush(VMWriter.SEGMENT.CONST,0)
            } else if (type1 == TYPE.SYMBOL && tokenizer.symbol() == '('){
                //expression
                compileExpression()
                //')'
                requireSymbol(')')
            } else if (type1 == TYPE.SYMBOL && (tokenizer.symbol() in ['-','~'])){
                String s = tokenizer.symbol()
                //term
                compileTerm()
                if (s == '-'){
                    vmWriter.writeArithmetic(VMWriter.COMMAND.NEG)
                } else {
                    vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
                }
            } else {
                error("integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term")
            }
        }
        printCloseTagFunction("compileTerm")
    }

    // I am here!!!

    /**
     * Compiles a subroutine call
     * subroutineName '(' expressionList ')' | (className|varName) '.' subroutineName '(' expressionList ')'
     */
    static private void compileSubroutineCall(){
        printOpenTagFunction("compileSubroutineCall")
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
            error("identifier")
        }
        String name = tokenizer.identifier()
        int nArgs = 0
        tokenizer.advance()
        TYPE tokenType = tokenizer.getTokenType()
        if (tokenType == TYPE.SYMBOL && tokenizer.symbol() == '('){
            //push this pointer
            vmWriter.writePush(VMWriter.SEGMENT.POINTER,0)
            //'(' expressionList ')'
            //expressionList
            nArgs = compileExpressionList() + 1
            //')'
            requireSymbol(')')
            //call subroutine
            vmWriter.writeCall("$currentClass.$name", nArgs)
        } else if (tokenType == TYPE.SYMBOL && tokenizer.symbol() == '.'){
            //(className|varName) '.' subroutineName '(' expressionList ')'
            String objName = name
            //subroutineName
            tokenizer.advance()
            if (tokenizer.getTokenType() != TYPE.IDENTIFIER){
                error("identifier")
            }
            name = tokenizer.identifier()
            //check for if it is built-in type
            String symbolType = symbolTable.typeOf(objName)
            if (symbolType in ["int","boolean","char","void"]){
                error("no built-in type")
            } else if (symbolType==""){
                name = "$objName.$name"
            } else {
                //push variable directly onto stack
                vmWriter.writePush(getSeg(symbolTable.kindOf(objName)), symbolTable.indexOf(objName))
                nArgs = 1
                name = "$symbolType.$name"
            }
            //'('
            requireSymbol('(')
            //expressionList
            nArgs += compileExpressionList()
            //')'
            requireSymbol(')')
            //call subroutine
            vmWriter.writeCall(name, nArgs)
        } else {
            error("'('|'.'")
        }
        printCloseTagFunction("compileSubroutineCall")
    }

    /**
     * Compiles an expression
     * term (op term)*
     */
    static private void compileExpression(){
        printOpenTagFunction("compileExpression")
        //term
        compileTerm()
        //(op term)*
        do {
            tokenizer.advance()
            //op
            if (tokenizer.getTokenType() == TYPE.SYMBOL && tokenizer.isOp()){
                String opCmd = ""
                switch (tokenizer.symbol()){
                    case '+': opCmd = "add"; break
                    case '-': opCmd = "sub"; break
                    case '*': opCmd = "call Math.multiply 2"; break
                    case '/': opCmd = "call Math.divide 2"; break
                    case '<': opCmd = "lt"; break
                    case '>': opCmd = "gt"; break
                    case '=': opCmd = "eq"; break
                    case '&': opCmd = "and"; break
                    case '|': opCmd = "or"; break
                    default:  error("Unknown op!")
                }
                //term
                compileTerm()
                vmWriter.writeCommand(opCmd)
            } else {
                tokenizer.pointerBack()
                break
            }
        } while (true)
        printCloseTagFunction("compileExpression")
    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions
     * (expression(','expression)*)?
     * @return nArgs
     */
    static private int compileExpressionList(){
        printOpenTagFunction("compileExpressionList")
        int nArgs = 0
        tokenizer.advance()
        //determine if there is any expression, if next is ')' then no
        if (tokenizer.getTokenType() == TYPE.SYMBOL && tokenizer.symbol() == ')'){
            tokenizer.pointerBack()
        } else {
            nArgs = 1
            tokenizer.pointerBack()
            //expression
            compileExpression()
            //(','expression)*
            do {
                tokenizer.advance()
                if (tokenizer.getTokenType() == TYPE.SYMBOL && tokenizer.symbol() == ','){
                    //expression
                    compileExpression()
                    nArgs++
                } else {
                    tokenizer.pointerBack()
                    break
                }
            } while (true)
        }
        printCloseTagFunction("compileExpressionList")
        return nArgs
    }

    //---------------------------------Auxiliary functions---------------------------------//

    /**
     * write function declaration, load pointer when keyword is METHOD or CONSTRUCTOR
     */
    private static void writeFunctionDec(KEYWORD keyword){
        printOpenTagFunction("writeFunctionDec")
        vmWriter.writeFunction(currentFunction(), symbolTable.varCount(KIND.VAR))
        //METHOD and CONSTRUCTOR need to load this pointer
        if (keyword == KEYWORD.METHOD){
            //A Jack method with k arguments is compiled into a VM function that operates on k + 1 arguments.
            //The first argument (argument number 0) always refers to the this object.
            vmWriter.writePush(VMWriter.SEGMENT.ARG, 0)
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,0)
        } else if (keyword == KEYWORD.CONSTRUCTOR){
            //A Jack constructor with k arguments is compiled into a VM function that operates on k arguments.
            vmWriter.writePush(VMWriter.SEGMENT.CONST, symbolTable.varCount(KIND.FIELD))
            vmWriter.writeCall("Memory.alloc", 1)
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,0)
        }
        printCloseTagFunction("writeFunctionDec")
    }

    /**
     * return corresponding seg for input kind
     * @param kind
     * @return
     */
    private static VMWriter.SEGMENT getSeg(KIND kind){
        printOpenTagFunction("getSeg")
        VMWriter.SEGMENT mySegment
        switch (kind){
            case KIND.FIELD  -> mySegment = VMWriter.SEGMENT.THIS
            case KIND.STATIC -> mySegment = VMWriter.SEGMENT.STATIC
            case KIND.VAR    -> mySegment = VMWriter.SEGMENT.LOCAL
            case KIND.ARG    -> mySegment = VMWriter.SEGMENT.ARG
            default          -> mySegment = VMWriter.SEGMENT.NONE
        }
        printCloseTagFunction("getSeg")
        return mySegment
    }

    private static String newLabel(){
        return "LABEL_" + (labelIndex++)
    }

    /**
     * require symbol when we know there must be such symbol
     * @param symbol
     */
    private static void requireSymbol(String symbol){
        tokenizer.advance()
        if (tokenizer.getTokenType() != TYPE.SYMBOL || tokenizer.symbol() != symbol){
            error("'$symbol'")
        }
    }

    /**
     * throw an exception to report errors
     * @param val
     */
    private static void error(String val){
        throw new IllegalStateException("Expected token missing : $val. Current token : ${tokenizer.getCurrentToken()}")
    }

    /**
     * return current function name, className.subroutineName
     * @return
     */
    static String currentFunction(){
        if (currentClass.length() != 0 && currentSubroutine.length() != 0){
            return "${currentClass}.${currentSubroutine}"
        }
        return ""
    }

    /**
     *
     */
    private static void printOpenTagFunction(String nameFunction){
        println("${width * Indentation++}<$nameFunction>")
    }

    /**
     *
     */
    private static void printCloseTagFunction(String nameFunction){
        println("${width * --Indentation}<$nameFunction/>")
    }
}