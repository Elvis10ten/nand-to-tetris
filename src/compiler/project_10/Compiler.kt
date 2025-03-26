package compiler.project_10

import java.io.File

object JackAnalyzer {

    /**
     * Analyzes a Jack source file (pathY/FileX.jck) into an XML parse tree.
     * If [input] represents a directory, all the ".jack" files in it are analyzed.
     */
    fun analyze(input: File) {
        val files = if (input.isDirectory) {
            input.listFiles { _, name -> name.endsWith(".jack") }
        } else {
            arrayOf(input)
        }

        files.forEach { file ->
            val tokenizer = JackTokenizer(file)
            val compilationEngine = CompilationEngine(
                file = File(file.parent, "${file.nameWithoutExtension}.xml"),
                tokenizer = tokenizer
            )

            compilationEngine.compileClass()
            compilationEngine.close()
        }
    }
}

class JackTokenizer(
    private val file: File
) {

    private val sourceText = file.readText()
    private var currentIndex = 0

    private fun hasMoreText() = currentIndex < sourceText.length

    /**
     * Returns the token at the head and advance past it if [advance] is true.
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

    private fun getRemainingText() = sourceText.substring(currentIndex)

    /**
     * Advances the [currentIndex] until it has gone past any comments or whitespaces.
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

class CompilationEngine(
    private val file: File,
    private val tokenizer: JackTokenizer
) {

    private val fileWriter = file.bufferedWriter()
    private val spacing = "  "

    init {
        // Clear the file.
        fileWriter.write("")
    }

    fun compileClass() {
        appendLine("<class>")
        val leadingSpace = spacing
        process(leadingSpace, Token.Keyword.CLASS)

        compileIdentifier(leadingSpace) // className.
        process(leadingSpace, Token.Symbol.OPENING_CURLY_BRACE)

        // Handles "classVarDec*".
        while (tokenizer.getCurrentToken(advance = false) in listOf(Token.Keyword.STATIC, Token.Keyword.FIELD)) {
            compileClassVarDec(leadingSpace)
        }

        // Handles "subroutineDec*".
        while (tokenizer.getCurrentToken(advance = false) in listOf(
                Token.Keyword.CONSTRUCTOR,
                Token.Keyword.FUNCTION,
                Token.Keyword.METHOD
            )) {
            compileSubroutineDec(leadingSpace)
        }

        process(leadingSpace, Token.Symbol.CLOSING_CURLY_BRACE)
        appendLine("</class>")
    }

    fun compileClassVarDec(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<classVarDec>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.STATIC, Token.Keyword.FIELD)
        compileType(leadingSpace)
        compileIdentifier(leadingSpace) // varName.

        // Handles "(',', varName)*".
        while(tokenizer.getCurrentToken(advance = false) == Token.Symbol.COMMA) {
            process(leadingSpace, Token.Symbol.COMMA)
            compileIdentifier(leadingSpace) // varName.
        }

        process(leadingSpace, Token.Symbol.SEMI_COLON)
        appendLine("$inheritedLeadingSpace</classVarDec>")
    }

    internal fun compileType(inheritedLeadingSpace: String) {
        val token = tokenizer.getCurrentToken(advance = false)!!
        process(inheritedLeadingSpace, *getTypeTokens(token))
    }

    private fun getTypeTokens(token: Token): Array<Token> {
        return arrayOf(
            Token.Keyword.CHAR,
            Token.Keyword.INT,
            Token.Keyword.BOOLEAN,
            Token.Identifier(token.valueString)
        )
    }

    internal fun compileIdentifier(inheritedLeadingSpace: String) {
        val identifierToken = tokenizer.getCurrentToken(advance = false)!!
        process(inheritedLeadingSpace, Token.Identifier(identifierToken.valueString))
    }

    fun compileSubroutineDec(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<subroutineDec>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"
        process(leadingSpace, Token.Keyword.CONSTRUCTOR, Token.Keyword.FUNCTION, Token.Keyword.METHOD)

        // Handles "('void' | type)".
        val token = tokenizer.getCurrentToken(advance = false)!!
        process(leadingSpace, Token.Keyword.VOID, *getTypeTokens(token))

        compileIdentifier(leadingSpace) // subroutineName.

        process(leadingSpace, Token.Symbol.OPENING_PARENTHESIS)
        compileParameterList(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_PARENTHESIS)

        compileSubroutineBody(leadingSpace)

        appendLine("$inheritedLeadingSpace</subroutineDec>")
    }

    fun compileParameterList(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<parameterList>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        val firstToken = tokenizer.getCurrentToken(advance = false)!!
        if (firstToken in getTypeTokens(firstToken)) {
            compileType(leadingSpace)
            compileIdentifier(leadingSpace) // varName.

            // Handles "(',', type varName)*".
            while (tokenizer.getCurrentToken(advance = false) == Token.Symbol.COMMA) {
                process(leadingSpace, Token.Symbol.COMMA)
                compileType(leadingSpace)
                compileIdentifier(leadingSpace) // varName.
            }
        }

        appendLine("$inheritedLeadingSpace</parameterList>")
    }

    private fun compileSubroutineBody(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<subroutineBody>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"
        process(leadingSpace, Token.Symbol.OPENING_CURLY_BRACE)

        // Handles "varDec*"
        while (tokenizer.getCurrentToken(advance = false) == Token.Keyword.VAR) {
            compileVarDec(leadingSpace)
        }

        // Handles "statement*"
        compileStatements(leadingSpace)

        process(leadingSpace, Token.Symbol.CLOSING_CURLY_BRACE)
        appendLine("$inheritedLeadingSpace</subroutineBody>")
    }

    private fun compileVarDec(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<varDec>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.VAR)
        compileType(leadingSpace)
        compileIdentifier(leadingSpace) // varName.

        // Handles "(',', varName)*".
        while(tokenizer.getCurrentToken(advance = false) == Token.Symbol.COMMA) {
            process(leadingSpace, Token.Symbol.COMMA)
            compileIdentifier(leadingSpace) // varName.
        }

        process(leadingSpace, Token.Symbol.SEMI_COLON)

        appendLine("$inheritedLeadingSpace</varDec>")
    }

    private fun compileStatements(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<statements>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        while(true) {
            when (val token = tokenizer.getCurrentToken(advance = false)) {
                Token.Keyword.IF -> compileIf(leadingSpace)
                Token.Keyword.WHILE -> compileWhile(leadingSpace)
                Token.Keyword.DO -> compileDo(leadingSpace)
                Token.Keyword.LET -> compileLet(leadingSpace)
                Token.Keyword.RETURN -> compileReturn(leadingSpace)
                else -> break
            }
        }

        appendLine("$inheritedLeadingSpace</statements>")
    }

    private fun compileLet(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<letStatement>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.LET)
        compileIdentifier(leadingSpace) // varName.

        if (tokenizer.getCurrentToken(advance = false) == Token.Symbol.OPENING_BRACKET) {
            process(leadingSpace, Token.Symbol.OPENING_BRACKET)
            compileExpression(leadingSpace)
            process(leadingSpace, Token.Symbol.CLOSING_BRACKET)
        }

        process(leadingSpace, Token.Symbol.EQUAL)
        compileExpression(leadingSpace)

        process(leadingSpace, Token.Symbol.SEMI_COLON)
        appendLine("$inheritedLeadingSpace</letStatement>")
    }

    private fun compileIf(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<ifStatement>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.IF)
        process(leadingSpace, Token.Symbol.OPENING_PARENTHESIS)
        compileExpression(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_PARENTHESIS)

        process(leadingSpace, Token.Symbol.OPENING_CURLY_BRACE)
        compileStatements(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_CURLY_BRACE)

        if (tokenizer.getCurrentToken(advance = false) == Token.Keyword.ELSE) {
            process(leadingSpace, Token.Keyword.ELSE)
            process(leadingSpace, Token.Symbol.OPENING_CURLY_BRACE)
            compileStatements(leadingSpace)
            process(leadingSpace, Token.Symbol.CLOSING_CURLY_BRACE)
        }

        appendLine("$inheritedLeadingSpace</ifStatement>")
    }

    private fun compileWhile(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<whileStatement>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.WHILE)
        process(leadingSpace, Token.Symbol.OPENING_PARENTHESIS)
        compileExpression(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_PARENTHESIS)

        process(leadingSpace, Token.Symbol.OPENING_CURLY_BRACE)
        compileStatements(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_CURLY_BRACE)

        appendLine("$inheritedLeadingSpace</whileStatement>")
    }

    private fun compileDo(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<doStatement>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        process(leadingSpace, Token.Keyword.DO)
        compileIdentifier(leadingSpace) // varName or subroutineName or className.
        compileTermSubroutineCall(leadingSpace)
        process(leadingSpace, Token.Symbol.SEMI_COLON)

        appendLine("$inheritedLeadingSpace</doStatement>")
    }

    private fun compileReturn(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<returnStatement>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"
        process(leadingSpace, Token.Keyword.RETURN)

        if (tokenizer.getCurrentToken(advance = false) != Token.Symbol.SEMI_COLON) {
           compileExpression(leadingSpace)
        }

        process(leadingSpace, Token.Symbol.SEMI_COLON)
        appendLine("$inheritedLeadingSpace</returnStatement>")
    }

    private fun compileExpression(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<expression>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        compileTerm(leadingSpace)

        val binaryOps = arrayOf(
            Token.Symbol.PLUS,
            Token.Symbol.DASH,
            Token.Symbol.ASTERISK,
            Token.Symbol.SLASH,
            Token.Symbol.AMPERSAND,
            Token.Symbol.PIPE,
            Token.Symbol.LESS_THAN,
            Token.Symbol.GREATER_THAN,
            Token.Symbol.EQUAL
        )

        while (tokenizer.getCurrentToken(advance = false) in binaryOps) {
            process(leadingSpace, *binaryOps)
            compileTerm(leadingSpace)
        }

        appendLine("$inheritedLeadingSpace</expression>")
    }

    private fun compileTerm(inheritedLeadingSpace: String) {
        appendLine("$inheritedLeadingSpace<term>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"

        val currentToken = tokenizer.getCurrentToken(advance = false)!!
        val unaryOps = arrayOf(Token.Symbol.TILDE, Token.Symbol.DASH)
        val keywordConstant = arrayOf(Token.Keyword.TRUE, Token.Keyword.FALSE, Token.Keyword.NULL, Token.Keyword.THIS)
        println(tokenizer.getCurrentToken(false))

        when (currentToken) {
            in unaryOps -> {
                process(leadingSpace, *unaryOps)
                compileTerm(leadingSpace)
            }
            Token.Symbol.OPENING_PARENTHESIS -> {
                process(leadingSpace, Token.Symbol.OPENING_PARENTHESIS)
                compileExpression(leadingSpace)
                process(leadingSpace, Token.Symbol.CLOSING_PARENTHESIS)
            }
            is Token.IntegerConstant -> {
                process(leadingSpace, Token.IntegerConstant(currentToken.valueString.toInt()))
            }

            is Token.StringConstant -> {
                process(leadingSpace, Token.StringConstant(currentToken.valueString))
            }

            in keywordConstant -> {
                process(leadingSpace, *keywordConstant)
            }
            is Token.Identifier -> {
                compileIdentifier(leadingSpace) // varName or subroutineName or className.

                // Look ahead again.
                val nextToken = tokenizer.getCurrentToken(advance = false)

                when (nextToken) {
                    Token.Symbol.OPENING_BRACKET -> {
                        compileTermExpression(leadingSpace)
                    }
                    Token.Symbol.PERIOD, Token.Symbol.OPENING_PARENTHESIS -> {
                        compileTermSubroutineCall(leadingSpace)
                    }
                    else -> {
                        // Just a varName. Nothing else to do.
                    }
                }
            } else -> {
                throw UnsupportedOperationException("Syntax error: '$currentToken' term type is not supported.")
            }
        }

        appendLine("$inheritedLeadingSpace</term>")
    }

    private fun compileTermExpression(leadingSpace: String) {
        process(leadingSpace, Token.Symbol.OPENING_BRACKET)
        compileExpression(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_BRACKET)
    }

    private fun compileTermSubroutineCall(leadingSpace: String) {
        when (val token = tokenizer.getCurrentToken(advance = false)) {
            Token.Symbol.PERIOD -> {
                // We have a static method call. Or a member method call with a receiver.
                process(leadingSpace, Token.Symbol.PERIOD)
                compileIdentifier(leadingSpace) // subroutineName.
            }
            Token.Symbol.OPENING_PARENTHESIS -> {
                // We have a method call on "this".
            }
            else -> {
                throw UnsupportedOperationException("Syntax error: '$token' not understood in subroutine call context.")
            }
        }

        process(leadingSpace, Token.Symbol.OPENING_PARENTHESIS)
        val numExpressions = compileExpressionList(leadingSpace)
        process(leadingSpace, Token.Symbol.CLOSING_PARENTHESIS)
    }

    private fun compileExpressionList(inheritedLeadingSpace: String): Int {
        appendLine("$inheritedLeadingSpace<expressionList>")
        val leadingSpace = "$inheritedLeadingSpace$spacing"
        var numExpressions = 0

        if (tokenizer.getCurrentToken(advance = false) != Token.Symbol.CLOSING_PARENTHESIS) {
            compileExpression(leadingSpace)
            numExpressions++

            while(tokenizer.getCurrentToken(advance = false) == Token.Symbol.COMMA) {
                process(leadingSpace, Token.Symbol.COMMA)
                compileExpression(leadingSpace)
                numExpressions++
            }
        }

        appendLine("$inheritedLeadingSpace</expressionList>")
        return numExpressions
    }

    private fun process(leadingSpace: String, vararg expectedTokens: Token) {
        val currentToken = tokenizer.getCurrentToken(advance = false)!!
        val matchedToken = expectedTokens.find { it.valueString == currentToken.valueString }

        if (matchedToken != null) {
            appendLine("$leadingSpace<${currentToken.typeName}>${encodeXMLString(currentToken.valueString)}</${currentToken.typeName}>")
            tokenizer.getCurrentToken(advance = true)
        } else {
            throw IllegalArgumentException("Syntax error: Expecting '${expectedTokens.toList()}' but got '${currentToken.valueString}' for '${file.path}")
        }
    }

    private fun encodeXMLString(str: String): String {
        return str
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
    }

    private fun appendLine(line: String) {
        fileWriter.appendLine(line)
    }

    fun close() {
        fileWriter.flush()
        fileWriter.close()
    }
}

interface TokenParser {

    fun parse(remainingText: String): Token?
}

/**
 * The Jack language includes five types of terminal elements (tokens).
 */
sealed interface Token {

    /**
     * The length enables the Tokenizer to know how many characters to jump to move past this token in the source text.
     */
    val length: Int

    val valueString: String

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
        DO("do"),
        IF("if"),
        ELSE("else"),
        WHILE("while"),
        RETURN("return");

        override val length = value.length

        override val typeName = "keyword"

        override val valueString = value

        companion object: TokenParser {

            override fun parse(remainingText: String): Token? {
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

        companion object: TokenParser {

            override fun parse(remainingText: String): Token? {
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

        companion object: TokenParser {

            override fun parse(remainingText: String): Token? {
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

        companion object: TokenParser {

            override fun parse(remainingText: String): Token? {
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

        companion object: TokenParser {

            override fun parse(remainingText: String): Token? {
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