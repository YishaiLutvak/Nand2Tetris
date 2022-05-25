package project11

/*
(project 10)
C:\Nand2Tetris\src\project10\ArrayTest
C:\Nand2Tetris\src\project10\ExpressionLessSquare
C:\Nand2Tetris\src\project10\Square

(project 11)
C:\Nand2Tetris\src\project11\Average
C:\Nand2Tetris\src\project11\ComplexArrays
C:\Nand2Tetris\src\project11\ConvertToBin
C:\Nand2Tetris\src\project11\Pong
C:\Nand2Tetris\src\project11\Seven
C:\Nand2Tetris\src\project11\Square
*/

/**
 * The compiler operates on a given source, where source is either a file name of the form xxx.jack or a directory name containing one or more such files.
 * For each xxx.jack input file, the compiler creates a JackTokenizer and an output xxx.vm file.
 * Next, the compiler uses the CompilationEngine, SymbolTable, and VMWriter modules to write the output file.
 */
class JackCompiler {

    static void main(String[] args) {
        if (args.length == 0){
            println("Usage:java JackCompiler [filename|directory]")
            return
        }

        File inFile = new File(args[0])
        ArrayList<File> jackFiles = inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)

        jackFiles.each { File currentJackFile ->
            String outFileName = currentJackFile.name.split(/\.jack/)[0]
            File outFile = new File(currentJackFile.getParent(),"My_${outFileName}.vm")
            CompilationEngine compilationEngine = new CompilationEngine(currentJackFile, outFile)
            compilationEngine.compileClass()
            println("File created : " + outFileName)
        }
    }

    /**
     * Handles a single jack file.
     * @param vmFile - path of jack file.
     */
    static ArrayList<File> handleSingleFile(File file) {
        if (!file.name.endsWith('.jack')){
            throw new IllegalArgumentException('.jack file is required!')
        }
        return [file]
    }

    /**
     * Handles a folder that has jack files.
     * @param dir - path of directory.
     */
    static ArrayList<File> handleDirectory(File dir) {
        List<File> jackFiles = dir.listFiles().findAll{ File file -> file.name.endsWith('.jack')}
        if (jackFiles.size() == 0){
            throw new IllegalArgumentException('No jack file in this directory')
        }
        return jackFiles
    }
}