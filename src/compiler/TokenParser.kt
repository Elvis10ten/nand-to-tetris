package compiler

/**
 * Responsible for parsing tokens from the source file text.
 * Each TokenParser can parse only a token of the specified type [T].
 */
interface TokenParser<T: Token> {

    /**
     * Parses the token at the head of [remainingText] (if possible) into a single Token of type [T].
     * Otherwise, return null.
     */
    fun parse(remainingText: String): T?
}