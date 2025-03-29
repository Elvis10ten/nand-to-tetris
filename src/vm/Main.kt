package vm

import java.io.File

fun main() {
    val project7RootDir = File("src/vm/project_7/io")
    val project8RootDir = File("src/vm/project_8/io")

    VMTranslator.translate(File(project8RootDir, "FunctionCalls${File.separator}FibonacciElement"))
    VMTranslator.translate(File(project8RootDir, "FunctionCalls${File.separator}NestedCall"))
    VMTranslator.translate(File(project8RootDir, "FunctionCalls${File.separator}SimpleFunction"))
    VMTranslator.translate(File(project8RootDir, "FunctionCalls${File.separator}StaticsTest"))
}
