package project10

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
 * top-level driver that sets up and invokes the other modules
 *
 * The analyzer program operates on a given source,
 * where source is either a file name of the form xxx.jack or a directory name containing one or more such files.
 * For each source xxx.jack file, the analyzer goes through the following logic:
 * 1. Create a JackTokenizer from the xxx.jack input file.
 * 2. Create an output file called My_xxx.xml and prepare it for writing.
 * 3. Use the CompilationEngine to compile the input JackTokenizer into the output file.”
 */
class JackAnalyzer{

    static void main(String[] args) {
        if(args.length == 0){
            println('Missing argument!!!')
            return
        }

        File inFile = new File(args[0])
        ArrayList<File> jackFiles = inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)

        jackFiles.each { currentJackFile ->
            String fileName = currentJackFile.name.split(/\.jack/)[0]
            String fileOutPath = "My_${fileName}.xml"
            String tokensFileOutPath = "My_${fileName}T.xml"
            String parent = currentJackFile.getParent()

            File fileOut = new File(parent,fileOutPath)
            File tokensFileOut = new File(parent,tokensFileOutPath)

            CompilationEngine compilationEngine = new CompilationEngine(currentJackFile,fileOut,tokensFileOut)
            compilationEngine.compileClass()

            println("File created : $fileOutPath")
            println("File created : $tokensFileOutPath")
        }
    }

    /**
     * Handles a single jack file.
     * @param file - path of jack file.
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
        List<File> jackFiles = dir.listFiles().findAll{file -> file.name.endsWith('.jack')}
        if (jackFiles.size() == 0){
            throw new IllegalArgumentException('No jack file in this directory')
        }
        return jackFiles
    }
}