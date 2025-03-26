package vm

import java.io.File

class VMParser(file: File) {

    private val fileLines = file.readLines()

    private var curLineNumber = -1
    private var curCommandTokens = listOf<String>()

    /**
     * Returns true if there are more lines to advance to.
     */
    fun hasMoreLines() = curLineNumber < fileLines.lastIndex

    /**
     * Advances to the next non-comment and non-blank line.
     */
    fun advance() {
        while (hasMoreLines()) {
            curLineNumber++
            val curLine = fileLines[curLineNumber].trim()

            when {
                // Skip blank lines.
                curLine.isBlank() -> continue
                // Skip lines with comment.
                curLine.startsWith("//") -> continue
            }

            curCommandTokens = curLine.split(" ")
            break
        }
    }

    fun getCurCommandType(): CommandType {
        return when (val command = curCommandTokens[0]) {
            "add",
            "sub",
            "neg",
            "eq",
            "gt",
            "lt",
            "and",
            "or",
            "not" -> CommandType.ARITHMETIC_LOGICAL
            "push" -> CommandType.PUSH
            "pop" -> CommandType.POP
            "label" -> CommandType.LABEL
            "goto" -> CommandType.GOTO
            "if" -> CommandType.IF
            "function" -> CommandType.FUNCTION
            "return" -> CommandType.RETURN
            "call" -> CommandType.CALL
            else -> throw UnsupportedOperationException("'$command' is not a supported command type.")
        }
    }

    fun getCurArg1(): String {
        return when (getCurCommandType()) {
            CommandType.ARITHMETIC_LOGICAL -> curCommandTokens[0]
            CommandType.PUSH,
            CommandType.POP,
            CommandType.LABEL,
            CommandType.GOTO,
            CommandType.IF,
            CommandType.FUNCTION,
            CommandType.CALL -> curCommandTokens[1]
            CommandType.RETURN -> throw UnsupportedOperationException("getCurrentArg1 is not supported for a 'return' command type.")
        }
    }

    fun getCurArg2(): Int {
        return when (getCurCommandType()) {
            CommandType.PUSH,
            CommandType.POP,
            CommandType.FUNCTION,
            CommandType.CALL -> curCommandTokens[2].toInt()
            CommandType.ARITHMETIC_LOGICAL -> throw UnsupportedOperationException("getCurrentArg2 is not supported for a 'arithmetic_logical' command.")
            CommandType.IF -> throw UnsupportedOperationException("getCurrentArg2 is not supported for a 'if' command.")
            CommandType.GOTO -> throw UnsupportedOperationException("getCurrentArg2 is not supported for a 'goto' command.")
            CommandType.LABEL -> throw UnsupportedOperationException("getCurrentArg2 is not supported for a 'label' command.")
            CommandType.RETURN -> throw UnsupportedOperationException("getCurrentArg2 is not supported for a 'return' command.")
        }
    }
}