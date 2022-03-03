package project10
//import java.util.regex.Pattern


class Main {
    static void main(args) {
        println("Enter a folder path: ")
        def folder_path= System.in.newReader().readLine()
        File folder = new File(folder_path)
        Tokenizing.over_the_files(folder)
        Parsing.over_the_files(folder)
    }
}