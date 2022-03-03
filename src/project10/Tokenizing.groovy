package project10

import java.util.regex.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import org.w3c.dom.Element

class Tokenizing {
    static Element tokens
    static List keywords = [
            "class",
            "constructor",
            "function",
            "method",
            "field",
            "static",
            "var",
            "int",
            "char",
            "boolean",
            "void",
            "true",
            "false",
            "null",
            "this",
            "let",
            "do",
            "if",
            "else",
            "while",
            "return"
    ]
    static String isSymbol = "{}()[].,;+-*/&<>=~"

    /**
     *
     * @param token
     * @return
     */
    static def GetNodeName(token){

        if(token as String in keywords){
            return "keyword"
        }
        else if(isSymbol.contains(token)){
            return "symbol"
        }else if(token.toString().isInteger()){
            return "integerConstant"
        }else if( Pattern.matches("^\"[^\\\"]*\"\$" ,token)){
            return "stringConstant"
        }else if( Pattern.matches("^[^\\d][\\d\\w\\_]*" ,token)){
            return "identifier"
        }
        return ""
    }

    /**
     *
     * @param content
     * @return
     */
    static def GetContent( String content)
    {
        return content.replace("\"","")
    }

    /**
     *
     * @param xml_file
     * @param line
     * @param document
     * @param tokens
     */
    static convert_to_xml(xml_file,String line,document,tokens){
        var regular = ~"(?:\\/\\/.*|\\/\\*|\\*\\/|\\<|\\>|\\.|#|&|\\,|:|\\*|\\(|\\)|=|\\{|\\}|\\(|\\)|\\[|\\]|\\.|\\;|\\+|\\-|\\*|\\/|\\&|\\|\\|\\=|\\~|\\\"[^\\\"]*\\\"|\\d+\\.{0,1}\\d*|\\s|\\n|\\w+)?"
        if(!line.replaceAll("\\s","").startsWith("/*") && !line.replaceAll("\\s","").startsWith("*") && !line.replaceAll("\\s","").startsWith("//")  )
        {
            if(line.contains("//")){
                line = line.split("//")[0]
            }
            var items = regular.matcher(line)
            for(item in items){
                var curitem = item
                if(item.replaceAll("\\s","").length() == 0)
                    curitem = item.replaceAll("\\s","")
                if(curitem != "")
                {
                    Element elem = document.createElement(GetNodeName(curitem))
                    elem.appendChild(document.createTextNode(" " + GetContent(curitem) + " "))
                    tokens.appendChild(elem)
                }
            }
        }
    }

    /**
     *
     * @param xml_file
     * @param jack_file
     * @return
     */
    static def read_lines(xml_file,jack_file){
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        tokens = document.createElement("tokens");
        document.appendChild(tokens);
        jack_file.each{line->{convert_to_xml(xml_file,line,document,tokens)}}
        xml_file.append(tokens)
    }

    /**
     *
     * @param jack_file
     * @return
     */
    static def create_xxxT_xml(jack_file){
        File xml_file = new File(jack_file.path[0..-6]+"T.xml")
        read_lines(xml_file,jack_file)
    }

    /**
     *
     * @param folder
     * @return
     */
    static def over_the_files(folder){
        folder.eachFile {jack_file->{if(jack_file.name[-4..-1]=="jack"){create_xxxT_xml(jack_file)}}}// read vm file from the folder
    }
}
