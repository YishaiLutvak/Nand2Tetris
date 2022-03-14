package project10

//C:\Nand2Tetris\src\project10\ArrayTest

/**
 * top-level driver that sets up and invokes the other modules
 *
 * The analyzer program operates on a given source, where source is either a file name of the form Xxx.jack or a directory name containing one or more such files.
 * For each source Xxx.jack file, the analyzer goes through the following logic:
 * 1. Create a JackTokenizer from the Xxx.jack input file.
 * 2. Create an output file called Xxx.xml and prepare it for writing.
 * 3. Use the CompilationEngine to compile the input JackTokenizer into the output file.”
 */
class JackAnalyzer{

    static void main(args) {
        if(args.length == 0){
            println('Missing argument!!!')
            return
        }

        File inFile = new File(args[0])
        def jackFiles = inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)

        jackFiles.each {
            String fileName = it.name.split(/\.jack/)[0]
            String fileOutPath = "My_${fileName}.xml"
            String tokenFileOutPath = "My_${fileName}T.xml"
            String parent = it.getParent()

            File fileOut = new File(parent,fileOutPath)
            File tokenFileOut = new File(parent,tokenFileOutPath)

            CompilationEngine compilationEngine = new CompilationEngine(it,fileOut,tokenFileOut)
            compilationEngine.compileClass()

            println("File created : $fileOutPath")
            println("File created : $tokenFileOutPath")
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
        [file]
    }

    /**
     * Handles a folder that has jack files.
     * @param dir - path of directory.
     */
    static ArrayList<File> handleDirectory(File dir) {
        def jackFiles = dir.listFiles().findAll{it.name.endsWith('.jack')}
        if (jackFiles.size() == 0){
            throw new IllegalArgumentException('No jack file in this directory')
        }
        jackFiles
    }
}