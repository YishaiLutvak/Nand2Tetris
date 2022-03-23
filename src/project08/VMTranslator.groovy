package project08

import static project08.Constants.COMMAND_TYPE

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

    static CodeWriter writer
    static Parser parser

    /**
     * Manages the translation of vm files into a hack file.
     * @param args - args from the command line.
     */
    static void main(String[] args) {
        if(args.length == 0){
            println('Missing argument!!!')
            return
        }
        File inFile = new File(args[0])
        inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)
    }

    /**
     * Handles a single vm file.
     * @param vmFile - vm file.
     */
    static void handleSingleFile(File vmFile) {
        writer = new CodeWriter(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     * Handles a folder that has vm files.
     * @param dir - directory.
     */
    static void handleDirectory(File dir) {
        writer = new CodeWriter(dir)
        if ((dir.listFiles().findAll{File file -> file.name.endsWith('.vm')}.size()) > 1){
            writer.writeInit()
        }
        dir.eachFileMatch(~/.*\.vm/) { File vmFile ->
            writer.setCurrentFileName(vmFile.name)
            translateVmFile(vmFile)
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
        parser = new Parser(vmFile)
        vmFile.eachLine { String line, int numberLine ->
            writer.emitComment(line, numberLine)
            // check if line is not comment
            if (!(line==~'^( *//).*')) {
                parser.setCurrentCommand(line)
                COMMAND_TYPE myCommandType = parser.getCommandType()
                switch (myCommandType) {
                    case COMMAND_TYPE.ARITHMETIC -> writer.writeArithmetic(parser.getCurrentCommand()/*line*/)
                    case COMMAND_TYPE.PUSH,COMMAND_TYPE.POP ->
                        writer.writePushPop(myCommandType, parser.arg1(), parser.arg2())
                    case COMMAND_TYPE.LABEL -> writer.writeLabel(parser.arg1())
                    case COMMAND_TYPE.GOTO -> writer.writeGoto(parser.arg1())
                    case COMMAND_TYPE.IF -> writer.writeIf(parser.arg1())
                    case COMMAND_TYPE.CALL -> writer.writeCall(parser.arg1(), parser.arg2())
                    case COMMAND_TYPE.RETURN -> writer.writeReturn()
                    case COMMAND_TYPE.FUNCTION -> writer.writeFunction(parser.arg1(), parser.arg2())
                }
            } // else {println("(line==~\'^( *//).*\')" + " " + line)}
        }
    }
}

