package project07

import project07.Constants07.COMMAND_TYPE

/*input to test
C:\Nand2Tetris\src\project07\StackArithmetic\SimpleAdd
C:\Nand2Tetris\src\project07\StackArithmetic\StackTest
C:\Nand2Tetris\src\project07\MemoryAccess\BasicTest
C:\Nand2Tetris\src\project07\MemoryAccess\PointerTest
C:\Nand2Tetris\src\project07\MemoryAccess\StaticTest
*/

/**
 * Translator from "vm" intermediate-language-code to hack assembly-language-code.
 */
class VMTranslator07 {

    static CodeWriter07 writer
    static Parser07 parser

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
        writer = new CodeWriter07(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     * Handles a folder that has vm files.
     * @param dir - directory.
     */
    static void handleDirectory(File dir) {
        writer = new CodeWriter07(dir)
        dir.eachFileMatch(~/.*\.vm/) {File vmFile ->
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
        parser = new Parser07(vmFile)
        vmFile.eachLine { String line, int numberLine ->
            writer.emitComment(line, numberLine)
            // check if line is not comment
            if (!(line ==~ '^( *//).*')) {
            // If you do not want to use regex you can replace this line with the next line: "if (!line.startsWith('//')) {"
                parser.setCurrentCommand(line)
                COMMAND_TYPE commandType = parser.getCommandType()
                switch (commandType) {
                    case COMMAND_TYPE.ARITHMETIC -> writer.writeArithmetic(parser.getCurrentCommand()/*line*/)
                    case COMMAND_TYPE.PUSH, COMMAND_TYPE.POP ->
                        writer.writePushPop(commandType, parser.arg1(), parser.arg2())
                }
            } // else { println("(line ==~ \'^( *//).*\')" + " " + line) }
        }
    }
}
