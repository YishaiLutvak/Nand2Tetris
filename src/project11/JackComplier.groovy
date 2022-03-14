package project11

//import java.io.File;
//import java.util.ArrayList;

/**
 * The compiler operates on a given source, where source is either a file name of the form Xxx.jack or a directory name containing one or more such files.
 * For each Xxx . jack input file, the compiler creates a JackTokenizer and an output Xxx.vm file.
 * Next, the compiler uses the CompilationEngine, SymbolTable, and VMWriter modules to write the output file.
 */
class JackComplier {
//    /**
//     * Return all the .jack files in a directory
//     * @param dir
//     * @return
//     */
//    static ArrayList<File> getJackFiles(File dir){
//        File[] files = dir.listFiles()
//        ArrayList<File> result = new ArrayList<File>()
//        if (files == null) return result
//        for (File f:files){
//            if (f.getName().endsWith(".jack")){
//                result.add(f)
//            }
//        }
//        return result
//    }

    static void main(args) {
        if (args.length == 0){
            println("Usage:java JackCompiler [filename|directory]")
            return
        }

        File inFile = new File(args[0])
        def jackFiles = inFile.isDirectory() ? handleDirectory(inFile) : handleSingleFile(inFile)

        jackFiles.each {
            String outFileName = it.name.split(/\.jack/)[0]
            File outFile = new File(it.getParent(),"${outFileName}.vm")
            CompilationEngine compilationEngine = new CompilationEngine(it, outFile)
            compilationEngine.compileClass()
            println("File created : " + outFileName)

        }

//        String fileInName = args[0]
//        File fileIn = new File(fileInName)
//        String fileOutPath
//        File fileOut
//        ArrayList<File> jackFiles = new ArrayList<File>()
//        if (fileIn.isFile()) {
//            //if it is a single file, see whether it is a vm file
//            String path = fileIn.getAbsolutePath()
//            if (!path.endsWith(".jack")) {
//                throw new IllegalArgumentException(".jack file is required!")
//            }
//            jackFiles.add(fileIn)
//        } else if (fileIn.isDirectory()) {
//            //if it is a directory get all jack files under this directory
//            jackFiles = getJackFiles(fileIn)
//            //if no vn file in this directory
//            if (jackFiles.size() == 0) {
//                throw new IllegalArgumentException("No jack file in this directory")
//            }
//        }
//        for (File f: jackFiles) {
//            fileOutPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + ".vm"
//            fileOut = new File(fileOutPath)
//            CompilationEngine compilationEngine = new CompilationEngine(f,fileOut)
//            compilationEngine.compileClass()
//            System.out.println("File created : " + fileOutPath)
//        }
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