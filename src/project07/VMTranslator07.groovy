package project07

import project07.Constants07.CommandType
import project08.Constants

//input to test
//1. C:\Nand2Tetris\src\project07\StackArithmetic\SimpleAdd
//2. C:\Nand2Tetris\src\project07\StackArithmetic\StackTest
//3. C:\Nand2Tetris\src\project07\MemoryAccess\BasicTest
//4. C:\Nand2Tetris\src\project07\MemoryAccess\PointerTest
//5. C:\Nand2Tetris\src\project07\MemoryAccess\StaticTest


/**
 * Translator from "vm" intermediate-language-code to hack assembly-language-code.
 */
class VMTranslator07 {

    static CodeWriter07 writer = null
    static Parser07 parser = null

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
        writer = CodeWriter07.getInstance(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     * Handles a folder that has vm files.
     * @param dir - directory.
     */
    static void handleDirectory(File dir) {
        writer = CodeWriter07.getInstance(dir)
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
        parser = Parser07.getInstance(vmFile)
        vmFile.eachLine { line, numberLine ->
            writer.emitComment(line, numberLine)
            // check if line is not comment
            if (!line.startsWith("//")) {
                parser.setCurrentCommand(line)
                def commandType = parser.getCommandType()
                switch (commandType) {
                    case Constants.CommandType.C_ARITHMETIC -> writer.writeArithmetic(/*line*/parser.getCurrentCommand())
                    case CommandType.C_PUSH, CommandType.C_POP ->
                        writer.writePushPop(commandType, parser.arg1(), parser.arg2())
                }
            }
        }
    }
}
