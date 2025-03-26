package compiler

import compiler.project_10.JackAnalyzer
import compiler.project_11.JackCompiler
import java.io.File

fun main() {
    val project10RootDir = File("src/compiler/project_10/io/")
    val project11RootDir = File("src/compiler/project_11/io/")

    JackAnalyzer.analyze(File(project10RootDir, "ArrayTest"))
    JackAnalyzer.analyze(File(project10RootDir, "ExpressionLessSquare"))
    JackAnalyzer.analyze(File(project10RootDir, "Square"))

    JackCompiler.compile(File(project11RootDir, "Average"))
    JackCompiler.compile(File(project11RootDir, "ComplexArrays"))
    JackCompiler.compile(File(project11RootDir, "ConvertToBin"))
    JackCompiler.compile(File(project11RootDir, "Pong"))
    JackCompiler.compile(File(project11RootDir, "Seven"))
    JackCompiler.compile(File(project11RootDir, "Square"))
}
