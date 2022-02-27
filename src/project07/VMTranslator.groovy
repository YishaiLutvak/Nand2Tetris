package project07

import project07.Constants.CommandType

//input to test
//1. C:\Nand2Tetris\src\project07\StackArithmetic\SimpleAdd
//2. C:\Nand2Tetris\src\project07\StackArithmetic\StackTest
//3. C:\Nand2Tetris\src\project07\MemoryAccess\BasicTest
//4. C:\Nand2Tetris\src\project07\MemoryAccess\PointerTest
//5. C:\Nand2Tetris\src\project07\MemoryAccess\StaticTest


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
        def path = new File(args[0])
        path.isDirectory() ? handleDirectory(path) : handleSingleFile(path)
    }

    /**
     * Handles a single vm file.
     * @param vmFile - path of vm file.
     */
    static void handleSingleFile(File vmFile) {
        writer = CodeWriter.getInstance(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     * Handles a folder that has vm files.
     * @param dir - path of directory.
     */
    static void handleDirectory(File dir) {
        writer = CodeWriter.getInstance(dir)
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
            parser.setCurrentCommand(line)
            def commandType = parser.getCommandType()
            switch (commandType) {
                case CommandType.C_ARITHMETIC -> writer.writeArithmetic(line)
                case CommandType.C_PUSH,CommandType.C_POP ->
                    writer.writePushPop(commandType, parser.arg1(), parser.arg2())
            }
        }
    }
}
