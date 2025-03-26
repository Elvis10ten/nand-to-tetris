package compiler.project_11

import compiler.JackTokenizer
import java.io.File

object JackCompiler {

    /**
     * This function drives the compilation process:
     * Translating Jack source file (pathY/FileX.jack) into VM files (pathY/FileX.vm) for the Hack VM.
     * If [input] represents a directory, all the ".jack" files in it are compiled.
     */
    fun compile(input: File) {
        val files = if (input.isDirectory) {
            input.listFiles { _, name -> name.endsWith(".jack") }
        } else {
            arrayOf(input)
        }

        files.forEach { file ->
            val tokenizer = JackTokenizer(file)
            val vmWriter = VMWriter(File(file.parent, "${file.nameWithoutExtension}.vm"))
            val classLevelSymbolTable = SymbolTable()

            val compilationEngine = CompilationEngine(tokenizer, vmWriter, classLevelSymbolTable)

            try {
                compilationEngine.compileClass()
            } catch (t: Throwable) {
                println("Remaining text when error occurred: ${tokenizer.getRemainingText()}")
                throw t
            }
            compilationEngine.close()
        }
    }
}

