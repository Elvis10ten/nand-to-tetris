package vm

import java.io.File

object VMTranslator {

    /**
     * Translates a VM file (pathY/FileX.vm) into a Hack assembly file (pathY/FileX.asm).
     * If [input] represents a directory, all the ".vm" files in it are translated.
     */
    fun translate(input: File) {
        val files = if (input.isDirectory) {
            input.listFiles { _, name -> name.endsWith(".vm") }
        } else {
            arrayOf(input)
        }

        files.forEach { file ->
            val parser = VMParser(file)
            val writer = CodeWriter(outputFile = File(file.parent, "${file.nameWithoutExtension}.asm"))

            while (parser.hasMoreLines()) {
                parser.advance()

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

                    CommandType.LABEL -> TODO()
                    CommandType.GOTO -> TODO()
                    CommandType.IF -> TODO()
                    CommandType.FUNCTION -> TODO()
                    CommandType.RETURN -> TODO()
                    CommandType.CALL -> TODO()
                }
            }

            writer.close()
        }
    }
}

