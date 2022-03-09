package project08

import project08.Constants.CommandType

// input to tests
// C:\Nand2Tetris\src\project08\FunctionCalls\FibonacciElement
// C:\Nand2Tetris\src\project08\FunctionCalls\NestedCall
// C:\Nand2Tetris\src\project08\FunctionCalls\SimpleFunction
// C:\Nand2Tetris\src\project08\FunctionCalls\StaticsTest
// C:\Nand2Tetris\src\project08\ProgramFlow\BasicLoop
// C:\Nand2Tetris\src\project08\ProgramFlow\FibonacciSeries

/*(input to test 07
1. C:\Nand2Tetris\src\project07\StackArithmetic\SimpleAdd
2. C:\Nand2Tetris\src\project07\StackArithmetic\StackTest
3. C:\Nand2Tetris\src\project07\MemoryAccess\BasicTest
4. C:\Nand2Tetris\src\project07\MemoryAccess\PointerTest
5. C:\Nand2Tetris\src\project07\MemoryAccess\StaticTest)*/

/**
 * Translator from "vm" intermediate-language-code to hack assembly-language-code.
 */
class VMTranslator {

    static CodeWriter writer = null
    static Parser parser = null

    /**
     * Manages the translation of vm files into a hack file.
     * @param args - args from the command line.
     */
    static void main(args) {
        if(args.length == 0){
            println("Missing argument!!!")
            return
        }
        def inFile = new File(args[0])
        inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)
    }

    /**
     * Handles a single vm file.
     * @param vmFile - vm file.
     */
    static void handleSingleFile(File vmFile) {
        writer = CodeWriter.getInstance(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     * Handles a folder that has vm files.
     * @param dir - directory.
     */
    static void handleDirectory(File dir) {
        writer = CodeWriter.getInstance(dir)
        if ((dir.listFiles().findAll{it.name.endsWith(".vm")}.size()) > 1){
            writer.writeInit()
        }
        dir.eachFileMatch(~/.*\.vm/) {
            writer.setCurrentFileName(it.name)
            translateVmFile(it)
        }
        writer.close()
    }

    /**
     * The heart of the program.
     * Analyzes the vm file using the "Parser" instance,
     * And then translates and writes the commands using a "Writer" instance to an asm file.
     * @param vmFile - vm file to translate.
     */
    static void translateVmFile(File vmFile) {
        parser = Parser.getInstance(vmFile)
        vmFile.eachLine() { line, numberLine ->
            writer.emitComment(line, numberLine)
            // check if line is not comment
            if (!(line==~"^( *//).*")) {
                parser.setCurrentCommand(line)
                def commandType = parser.getCommandType()
                switch (commandType) {
                    case CommandType.C_ARITHMETIC -> writer.writeArithmetic(/*line*/parser.getCurrentCommand())
                    case CommandType.C_PUSH,CommandType.C_POP ->
                        writer.writePushPop(commandType, parser.arg1(), parser.arg2())
                    case CommandType.C_LABEL -> writer.writeLabel(parser.arg1())
                    case CommandType.C_GOTO -> writer.writeGoto(parser.arg1())
                    case CommandType.C_IF -> writer.writeIf(parser.arg1())
                    case CommandType.C_CALL -> writer.writeCall(parser.arg1(), parser.arg2())
                    case CommandType.C_RETURN -> writer.writeReturn()
                    case CommandType.C_FUNCTION -> writer.writeFunction(parser.arg1(), parser.arg2())
                }
            } //else {println("(line==~\"^( *//).*\")" + " " + line)}
        }
    }
}

