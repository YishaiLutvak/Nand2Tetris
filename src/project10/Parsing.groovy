package project10

class Parsing
{
    static int line_index = 0
    static String xmlT_file = ""
    static String container_txt = ""

    static def getNextToken(){
        if(if_has_more_lines() && xmlT_file.split("\n")[line_index + 1] != "</tokens>\n") {
            line_index ++
            return xmlT_file.split("\n")[line_index] + "\n"
        } else if(if_has_more_lines() && xmlT_file.split("\n")[line_index + 1] == "</tokens>\n"){
            line_index ++
        }
        return ""
    }

    static def if_has_more_lines()
    {
        return line_index < (xmlT_file.split("\n")).length - 1
    }

    static def parser(){
        container_txt += "<class>\n"
        container_txt += getNextToken() //<keyword> class </keyword>
        container_txt += getNextToken() //<identifier> className </identifier>
        container_txt += getNextToken() //<symbol> { </symbol>
        ClassVarDec()
        SubroutineDec()
        container_txt += getNextToken() //<symbol> } </symbol>
        container_txt += "</class>\n"
    }
    static def CheckNextToken(){
        if(if_has_more_lines()
        ){
            return xmlT_file.split("\n")[line_index + 1]
        }
        return ""
    }
    static def ClassVarDec() {
        //while CheckNextToken() contain 'static' or 'field'
        while(CheckNextToken().contains("static") || CheckNextToken().contains("field")){
            container_txt += "<classVarDec>\n"
            container_txt += getNextToken() //<keyword> field || static </keyword>
            container_txt += getNextToken()//<keyword> type </keyword> or <identifier> type </identifier>
            container_txt += getNextToken() //<identifier> var_name </identifier>
            // while CheckNextToken() contain comma (",")
            while(CheckNextToken().contains(",")){
                container_txt += getNextToken() // <symbol> , </symbol>
                container_txt += getNextToken() // <identifier> varName </identifier>
            }
            container_txt += getNextToken() // <symbol> ; </symbol>
            container_txt += "</classVarDec>\n"
        }
    }

    static def SubroutineDec() {
        while (
                CheckNextToken().contains("constructor") ||
                CheckNextToken().contains("function") ||
                CheckNextToken().contains("method")
        ) {
            container_txt += "<subroutineDec>\n"
            container_txt += getNextToken() //<keyword> constructor or function or method </keyword>
            /*if (CheckNextToken().contains("void")) {
                container_txt += getNextToken() //<keyword> void </keyword>
            } else {
                container_txt += getNextToken()//<keyword> type </keyword> || <identifier> type </identifier>
            }*/
            container_txt += getNextToken()
            container_txt += getNextToken() // <identifier> subroutineName </identifier>
            container_txt += getNextToken() // <symbol> ( </symbol>
            parameterList()
            container_txt += getNextToken() // <symbol> ) </symbol>
            subroutineBody()
            container_txt += "</subroutineDec>\n"
        }
    }

    static def subroutineBody(){
        container_txt += "<subroutineBody>\n"
        container_txt += getNextToken() // <symbol> { </symbol>
        varDec()
        statements()
        container_txt += getNextToken() // <symbol> } </symbol>
        container_txt += "</subroutineBody>\n"
    }

    static def varDec(){
        //while CheckNextToken() contain 'var'
        while( CheckNextToken().contains("var"))
        {
            container_txt += "<varDec>\n"
            container_txt += getNextToken() //<keyword> var </keyword>
            container_txt += getNextToken()//<keyword> type </keyword> or <identifier> type </identifier>
            container_txt += getNextToken() // <identifier> varName </identifier>
            // while CheckNextToken() contain comma (",")
            while(CheckNextToken().contains(","))
            {
                container_txt += getNextToken() // <symbol> , </symbol>
                container_txt += getNextToken() // <identifier> varName </identifier>
            }
            container_txt += getNextToken() // <symbol> ; </symbol>
            container_txt += "</varDec>\n"
        }
    }

    static def statement(){
        //while CheckNextToken() contain 'var'
        while (CheckNextToken().contains("while") || CheckNextToken().contains("if") || CheckNextToken().contains("let") ||
                CheckNextToken().contains("return") || CheckNextToken().contains("do")){
            if (CheckNextToken().contains("while")){
                whileStatement()
            }
            if (CheckNextToken().contains("if")){
                ifStatement()
            }
            if (CheckNextToken().contains( "let")){
                letStatement()
            }
            if (CheckNextToken().contains("return")){
                returnStatement()
            }
            if (CheckNextToken().contains("do")){
                doStatement()
            }
        }
    }
    static def ifStatement(){
        container_txt += "<ifStatement>\n"
        container_txt += getNextToken() //<keyword> if </keyword>
        container_txt += getNextToken() // <symbol> ( </symbol>
        expression()
        container_txt += getNextToken() // <symbol> ) </symbol>
        container_txt += getNextToken() // <symbol> { </symbol>
        statements()
        container_txt += getNextToken() // <symbol> } </symbol>
        if (CheckNextToken().contains("else")){
            container_txt += getNextToken() //<keyword> else </keyword>
            container_txt += getNextToken() // <symbol> { </symbol>
            statements()
            container_txt += getNextToken() // <symbol> } </symbol>
        }
        container_txt += "</ifStatement>\n"
    }
    static def letStatement(){
        container_txt += "<letStatement>\n"
        container_txt += getNextToken() //<keyword> let </keyword>
        container_txt += getNextToken() // <identifier> varName </identifier>
        if (CheckNextToken().contains("[")){
            container_txt += getNextToken() // <symbol> [ </symbol>
            expression()
            container_txt += getNextToken() // <symbol> ] </symbol>
        }
        container_txt += getNextToken() // <symbol> = </symbol>
        expression()
        container_txt += getNextToken() // <symbol> ; </symbol>
        container_txt += "</letStatement>\n"
    }

    static def returnStatement(){
        container_txt += "<returnStatement>\n"
        container_txt += getNextToken() //<keyword> return </keyword>
        // if CheckNextToken() is start of expression
        if (CheckNextToken().contains("integerConstant")|| CheckNextToken().contains( "stringConstant")|| CheckNextToken().contains("true")||
                CheckNextToken().contains( "false")|| CheckNextToken().contains( "null")|| CheckNextToken().contains( "this")||
                CheckNextToken().contains("identifier")|| CheckNextToken().contains( "(")|| CheckNextToken().contains( "-")|| CheckNextToken().contains( "~") )
        {
            expression()
        }
        container_txt += getNextToken() // <symbol> ; </symbol>
        container_txt += "</returnStatement>\n"
    }

    static def doStatement(){
        container_txt += "<doStatement>\n"
        container_txt += getNextToken() //<keyword> do </keyword>
        subroutineCall()
        container_txt += getNextToken() // <symbol> ; </symbol>
        container_txt += "</doStatement>\n"
    }
    static def whileStatement(){
        container_txt += "<whileStatement>\n"
        container_txt += getNextToken() //<keyword> while </keyword>
        container_txt += getNextToken() // <symbol> ( </symbol>
        expression()
        container_txt += getNextToken() // <symbol> ) </symbol>
        container_txt += getNextToken() // <symbol> { </symbol>
        statements()
        container_txt += getNextToken() // <symbol> } </symbol>
        container_txt += "</whileStatement>\n"

    }

    static def statements(){
        container_txt += "<statements>\n"
        statement()
        container_txt += "</statements>\n"
    }

    static def expression(){
        container_txt += "<expression>\n"
        term()
        while( CheckNextToken().contains("+") || CheckNextToken().contains( "-") || //'+' | '-' | '*' | '/' | '&' | '|' | '>' | '<' | '='
                CheckNextToken().contains( "*") || CheckNextToken().contains("<symbol> /") || CheckNextToken().contains( "&amp;") ||
                CheckNextToken().contains( "|") || CheckNextToken().contains( "&gt;") || CheckNextToken().contains( "&lt;") || CheckNextToken().contains( "="))
        {
            container_txt += getNextToken() // <symbol> operator </symbol>
            term()
        }
        container_txt += "</expression>\n"
    }

    static def term(){
        // term: integerConstant | stringConstant | keywordConstant
        if (CheckNextToken().contains( "integerConstant")|| CheckNextToken().contains("stringConstant")|| CheckNextToken().contains( "true")||
                CheckNextToken().contains( "false")|| CheckNextToken().contains("null")|| CheckNextToken().contains("this"))
        {
            container_txt += "<term>\n"
            container_txt += getNextToken() // <integerConstant> </integerConstant> or <stringConstant> </stringConstant> or <keyword> keywordConstant </keyword>
            container_txt += "</term>\n"

        }
        else if(CheckNextToken().contains( "(")) // term: '(' expression ')'
        {
            container_txt += "<term>\n"
            container_txt += getNextToken() // <symbol> ( </symbol>
            expression()
            container_txt += getNextToken() // <symbol> ) </symbol>
            container_txt += "</term>\n"

        } else if(CheckNextToken().contains("identifier"))
        { // term: varName | varName '[' expression ']' | subroutineCall
            container_txt += "<term>\n"
            // term: subroutineCall
            if (xmlT_file.split( "\n")[line_index + 2].contains("(") || xmlT_file.split( "\n")[line_index + 2].contains( "."))
            {
                subroutineCall()

            }
            else// term: varName | varName '[' expression ']'
            {
                container_txt += getNextToken() // <identifier> varName </identifier>
                // term: varName '[' expression ']'
                if(CheckNextToken().contains("["))
                {
                    container_txt += getNextToken() // <symbol> [ </symbol>
                    expression()
                    container_txt += getNextToken() // <symbol> ] </symbol>
                }
            }
            container_txt += "</term>\n"
            // term: unaryOp term
        }
        else if( CheckNextToken().contains( "-")|| CheckNextToken().contains( "~") )
        {
            container_txt += "<term>\n"
            container_txt += getNextToken() // <symbol> unaryOp </symbol>
            term()
            container_txt += "</term>\n"
        }
    }

    static def subroutineCall(){
        container_txt += getNextToken() // <identifier> subroutineName | className | varName </identifier>
        //subroutineCall: subroutineName '(' expressionList ')'
        if (CheckNextToken().contains("("))
        {
            container_txt += getNextToken() // <symbol> ( </symbol>
            expressionList()
            container_txt += getNextToken() // <symbol> ) </symbol>
        }
        else //subroutineCall: ( className | varName) '.' subroutineName '(' expressionList ')'
        {
            container_txt += getNextToken() // <symbol> . </symbol>
            container_txt += getNextToken() // <identifier> subroutineName </identifier>
            container_txt += getNextToken() // <symbol> ( </symbol>
            expressionList()
            container_txt += getNextToken() // <symbol> ) </symbol>
        }
    }
    static def expressionList(){
        container_txt += "<expressionList>\n"
        if (CheckNextToken().contains("integerConstant")|| CheckNextToken().contains("stringConstant")|| CheckNextToken().contains( "true")||
                CheckNextToken().contains( "false")|| CheckNextToken().contains("null")|| CheckNextToken().contains( "this")||
                CheckNextToken().contains( "identifier")|| CheckNextToken().contains("(")|| CheckNextToken().contains( "-")||
                CheckNextToken().contains("~"))
        {
            expression()
            while (CheckNextToken().contains(","))// <symbol> , </symbol>
            {
                container_txt += getNextToken()
                expression()
            }
        }
        container_txt += "</expressionList>\n"
    }

    static def parameterList(){
        container_txt += "<parameterList>\n"
        if (CheckNextToken().contains("int") || CheckNextToken().contains("char") || CheckNextToken().contains( "boolean"))
        {
            container_txt += getNextToken()//<keyword> type </keyword> or <identifier> type </identifier>
            container_txt += getNextToken() // <identifier> varName </identifier>
            while( CheckNextToken().contains(",")) // while CheckNextToken() contain comma (",")
            {
                container_txt += getNextToken() // <symbol> , </symbol>
                container_txt += getNextToken()//<keyword> type </keyword> or <identifier> type </identifier>
                container_txt += getNextToken() // <identifier> varName </identifier>
            }
        }
        container_txt += "</parameterList>\n"
    }


    static def create_xml(Txml_file){
        line_index = 0
        xmlT_file = ""
        container_txt = ""
        File xml_file = new File(Txml_file.path[0..-6]+".xml")
        xmlT_file = Txml_file.text
        parser()
        xml_file.write(container_txt)
    }

    static def over_the_files(File folder){
        folder.eachFile {
            if(it.path[-5..-1]=="T.xml"){
                create_xml(it)
            }
        }// read vm file from the folder
    }
}
