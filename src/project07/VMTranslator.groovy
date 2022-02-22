package project07

import project07.Constants.CommandType

//input to test
//1. C:\Nand2Tetris\src\project07\StackArithmetic\SimpleAdd
//2. C:\Nand2Tetris\src\project07\StackArithmetic\StackTest
//3. C:\Nand2Tetris\src\project07\MemoryAccess\BasicTest
//4. C:\Nand2Tetris\src\project07\MemoryAccess\PointerTest
//5. C:\Nand2Tetris\src\project07\MemoryAccess\StaticTest


/**
 *
 */
class VMTranslator {

    static CodeWriter writer = null
    static Parser parser = null

    /**
     *
     * @param args
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
     *
     * @param vmFile
     */
    static void handleSingleFile(File vmFile) {
        writer = CodeWriter.getInstance(vmFile)
        writer.setCurrentFileName(vmFile.name)
        translateVmFile(vmFile)
        writer.close()
    }

    /**
     *
     * @param dir
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
     *
     * @param vmFile
     */
    static void translateVmFile(File vmFile) {
        parser = Parser.getInstance(vmFile)
        vmFile.eachLine() { line, numberLine ->
            writer.emitComment(line, numberLine)
            parser.setCurrentCommand(line)
            def commandType =parser.getCommandType()
            switch (commandType) {
                case CommandType.C_ARITHMETIC -> writer.writeArithmetic(line)
                case CommandType.C_PUSH,CommandType.C_POP ->
                    writer.writePushPop(commandType, parser.arg1(), parser.arg2())
            }
        }
    }
}
