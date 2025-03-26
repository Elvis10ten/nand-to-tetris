package compiler

import java.io.File

/**
 * Responsible for reading Jack source files as a series of tokens.
 */
class JackTokenizer(private val file: File) {

    private val sourceText = file.readText()
    private var currentIndex = 0

    private fun hasMoreText() = currentIndex < sourceText.length

    /**
     * Returns the current token and advance past it if [advance] is true.
     * Note: This function has the side effect of moving the [currentIndex] to the head of a token via [advancePastCommentsAndWhitespaces].
     * This operation is idempotent: It will only move once, and subsequent calls will do nothing.
     */
    fun getCurrentToken(advance: Boolean): Token? {
        advancePastCommentsAndWhitespaces()
        return if (hasMoreText()) {
            val remainingText = getRemainingText()
            val token = Token.Keyword.parse(remainingText)
                ?: Token.Symbol.parse(remainingText)
                ?: Token.IntegerConstant.parse(remainingText)
                ?: Token.StringConstant.parse(remainingText)
                ?: Token.Identifier.parse(remainingText)
                ?: throw UnsupportedOperationException("Token couldn't be identified in '${file.path} at start of '$remainingText'")

            if (advance) {
                // Advance past the returned token.
                currentIndex += token.length
            }

            token
        } else {
            null
        }
    }

    fun getRemainingText() = sourceText.substring(currentIndex)

    /**
     * Advances the [currentIndex] until it has gone past any comments or whitespaces.
     * This function is idempotent.
     */
    private fun advancePastCommentsAndWhitespaces() {
        while (hasMoreText()) {
            val lengthToAdvancePastWhiteSpaces = lengthToAdvancePastWhitespace(getRemainingText())
            val lengthToAdvancePastComments = lengthToAdvancePastComments(getRemainingText())

            if (lengthToAdvancePastWhiteSpaces > 0 && lengthToAdvancePastComments > 0) {
                throw AssertionError("Both toAdvance lengths can't be positive at the same time.")
            }

            currentIndex += lengthToAdvancePastWhiteSpaces
            currentIndex += lengthToAdvancePastComments

            if (lengthToAdvancePastWhiteSpaces == 0 && lengthToAdvancePastComments == 0) {
                // Break from loop as we the current index no longer points to a comment or whitespace.
                break
            }
        }
    }

    /**
     * Returns a positive value if [remainingText] starts with a comment.
     * The positive value returned is the length of the comment, allowing the tokenizer to easily jump over the comment.
     * Otherwise, returns zero if [remainingText] does not start with a comment.
     */
    private fun lengthToAdvancePastComments(remainingText: String): Int {
        return when {
            // Single line comment.
            remainingText.startsWith("//") -> {
                remainingText.takeWhile { it != '\n' }.length
            }
            // Multi-line comment.
            remainingText.startsWith("/*") -> {
                val endOfMultilineComment = remainingText.indexOf("*/").takeIf { it != -1 } ?: throw IllegalArgumentException("Multiline comment must be closed.")
                endOfMultilineComment + 2 // +2 to include "*/".
            }
            else -> {
                0 // remainingText doesn't start with a comment.
            }
        }
    }

    /**
     * Returns a positive value if [remainingText] starts with whitespace(s).
     * The positive value returned is the length of the whitespace(s), allowing the tokenizer to easily jump over the whitespaces.
     * Otherwise, returns zero if [remainingText] does not start with whitespace(s).
     */
    private fun lengthToAdvancePastWhitespace(remainingText: String): Int {
        val whiteSpaces = remainingText.takeWhile {
            it == ' ' || it == '\n' || it == '\t' || it == '\r'
        }

        return whiteSpaces.length
    }
}