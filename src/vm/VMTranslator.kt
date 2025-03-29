package vm

import java.io.File

object VMTranslator {

    /**
     * Translates a VM file (pathY/FileX.vm) into a Hack assembly file (pathY/FileX.asm).
     *
     * The output of the VM translator is a single assembly file, named "[input].asm".
     * If [input] is a folder name, the single .asm file contains the translation of all
     * the functions in all the .vm files in the folder, one after the other.
     */
    fun translate(input: File) {
        val files = if (input.isDirectory) {
            input.listFiles { _, name -> name.endsWith(".vm") }
        } else {
            arrayOf(input)
        }

        val writer = CodeWriter(outputFile = File(input.parent, "${input.nameWithoutExtension}.asm"))

        files.forEach { file ->
            val parser = VMParser(file)
            writer.setFileName(file)

            while (parser.hasMoreLines()) {
                parser.advance()

                writer.addTranslationLogs(
                    type = parser.getCurCommandType(),
                    arg1 = runCatching { parser.getCurArg1() }.getOrDefault(""),
                    arg2 = runCatching { parser.getCurArg2().toString() }.getOrDefault("")
                )

                when (parser.getCurCommandType()) {
                    CommandType.ARITHMETIC_LOGICAL -> {
                        writer.writeArithmeticLogical(parser.getCurArg1())
                    }

                    CommandType.PUSH -> {
                        writer.writePushPop(
                            isPush = true,
                            segmentName = parser.getCurArg1(),
                            index = parser.getCurArg2()
                        )
                    }

                    CommandType.POP -> {
                        writer.writePushPop(
                            isPush = false,
                            segmentName = parser.getCurArg1(),
                            index = parser.getCurArg2()
                        )
                    }

                    CommandType.LABEL -> writer.writeLabel(parser.getCurArg1())
                    CommandType.GOTO -> writer.writeGoto(parser.getCurArg1())
                    CommandType.IF -> writer.writeIf(parser.getCurArg1())
                    CommandType.FUNCTION -> writer.writeFunction(parser.getCurArg1(), parser.getCurArg2())
                    CommandType.CALL -> writer.writeCall(parser.getCurArg1(), parser.getCurArg2())
                    CommandType.RETURN -> writer.writeReturn()
                }
            }
        }

        writer.close()
    }
}

