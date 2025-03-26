package compiler

/**
 * The Jack language includes five types of terminal elements (tokens).
 */
sealed interface Token {

    /**
     * The length enables the Tokenizer to know how many characters to jump to move past this token in the source text.
     */
    val length: Int

    /**
     * The value of this token as a string.
     */
    val valueString: String

    /**
     * The type of this token as a string.
     */
    val typeName: String

    enum class Keyword(
        val value: String
    ): Token {
        CLASS("class"),
        CONSTRUCTOR("constructor"),
        FUNCTION("function"),
        METHOD("method"),
        FIELD("field"),
        STATIC("static"),
        VAR("var"),
        INT("int"),
        CHAR("char"),
        BOOLEAN("boolean"),
        VOID("void"),
        TRUE("true"),
        FALSE("false"),
        NULL("null"),
        THIS("this"),
        LET("let"),
        DO("do "),
        IF("if"),
        ELSE("else"),
        WHILE("while"),
        RETURN("return");

        override val length = value.length

        override val typeName = "keyword"

        override val valueString = value

        companion object: TokenParser<Keyword> {

            override fun parse(remainingText: String): Keyword? {
                return entries.find { remainingText.startsWith(it.value) }
            }
        }
    }

    enum class Symbol(
        val value: String
    ): Token {
        OPENING_CURLY_BRACE("{"),
        CLOSING_CURLY_BRACE("}"),
        OPENING_PARENTHESIS("("),
        CLOSING_PARENTHESIS(")"),
        OPENING_BRACKET("["),
        CLOSING_BRACKET("]"),
        PERIOD("."),
        COMMA(","),
        SEMI_COLON(";"),
        PLUS("+"),
        DASH("-"),
        ASTERISK("*"),
        SLASH("/"),
        AMPERSAND("&"),
        PIPE("|"),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        EQUAL("="),
        TILDE("~");

        override val length = value.length

        override val typeName = "symbol"

        override val valueString = value

        companion object: TokenParser<Symbol> {

            override fun parse(remainingText: String): Symbol? {
                return entries.find { remainingText.startsWith(it.value) }
            }
        }
    }

    /**
     * A decimal integer in the range 0 to 32767.
     */
    data class IntegerConstant(
        val value: Int
    ): Token {

        override val length = value.toString().length

        override val typeName = "integerConstant"

        override val valueString = value.toString()

        companion object: TokenParser<IntegerConstant> {

            override fun parse(remainingText: String): IntegerConstant? {
                val number = remainingText.takeWhile { it.isDigit() }.toIntOrNull() ?: return null

                return if (number in 0..32767) {
                    IntegerConstant(number)
                } else {
                    null
                }
            }
        }
    }

    /**
     * A sequence of characters enclosed in double quotes. Not including double quotes or newline characters.
     */
    data class StringConstant(
        val quotedValue: String
    ): Token {

        val unquotedValue = quotedValue.removePrefix("\"").removeSuffix("\"")

        // Keeping the quotes, allows us easily compute the true length of the token.
        override val length = quotedValue.length

        override val typeName = "stringConstant"

        override val valueString = unquotedValue

        companion object: TokenParser<StringConstant> {

            override fun parse(remainingText: String): StringConstant? {
                if (!remainingText.startsWith("\"")) {
                    return null
                }

                // Get the string without quotes.
                val str = remainingText.removePrefix("\"").takeWhile {
                    it != '"'
                }

                if (str.contains("\n")) {
                    throw UnsupportedOperationException("New lines are unsupported in string literals.")
                }

                return StringConstant("\"$str\"") // Add the quotes back in.
            }
        }
    }

    /**
     * A sequence of letters, digits, and underscores that does not begin with a digit. Used to name classes, variables, and subroutines.
     */
    data class Identifier(
        val value: String
    ): Token {

        override val length = value.length

        override val typeName = "identifier"

        override val valueString = value

        companion object: TokenParser<Identifier> {

            override fun parse(remainingText: String): Identifier? {
                if (remainingText[0].isDigit()) {
                    return null
                }

                val str = remainingText.takeWhile {
                    it.isLetterOrDigit() || it == '_'
                }

                if (str.isEmpty()) {
                    return null
                }

                return Identifier(str)
            }
        }
    }
}