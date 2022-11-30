/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.parser.tokenizer;

import com.ibm.icu.text.RuleBasedNumberFormat;
import dev.steinmoetzger.mathlang.exceptions.MLException;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Tokenizer {

    enum TokenizerState {
        PARSE_STRING,
        PARSE_NUMBER,
        PARSE_NEG_NUMBER,
        PARSE_ANYTHING,
        PARSE_IDENTIFIER
    }

    private int idx;
    private String string;
    private TokenizerState state;
    private Token current;
    private Token previous;
    private HashMap<Character, TokenType> validCharTokens;
    private HashMap<String, TokenType> validStringTokens;

    public Tokenizer(String string) {
        this.string = string;
        this.idx = 0;
        this.state = TokenizerState.PARSE_ANYTHING;
        this.validCharTokens = new HashMap<>();
        this.validStringTokens = new HashMap<>();
        this.initValidTokens();
    }

    private void initValidTokens() {
        validCharTokens.put('(', TokenType.L_PAR);
        validCharTokens.put(')', TokenType.R_PAR);
        validCharTokens.put(',', TokenType.COMMA);
        validCharTokens.put('+', TokenType.ADD);
        validCharTokens.put('*', TokenType.MULT);
        validCharTokens.put('/', TokenType.DIV);
        validCharTokens.put('^', TokenType.POW);
        validCharTokens.put('-', TokenType.SUB);
        validCharTokens.put('=', TokenType.EQUALS);


        validStringTokens.put("undef", TokenType.KEYWORD_UNDEF);
    }

    public Token current() throws MLException {
        int cIdx = idx;
        Token t = next();
        idx-=(idx - cIdx);
        return t;
    }

    public Token getNext() throws MLException {
        next();
        Token t = next();
        idx--;
        idx--;
        return t;
    }

    public Token next() throws MLException {
        StringBuilder cache = new StringBuilder();
        Token token = new Token();
        state = TokenizerState.PARSE_ANYTHING;
        if(!string.endsWith(";"))
            throw new MLException("Expected semicolon after expression.");



        String[] chars = string.split("");

        if(idx == chars.length)
            return new Token("0", TokenType.NOT_DEFINED);
        do {
            // when state is set to parse strings it should add every next char to cache until reached next
            // quotation mark, then it should pop the token with values {value="<cache_val>", type=STRING_LITERAL}
            if(state == TokenizerState.PARSE_STRING) {
                if(chars[idx].charAt(0) == '"') {
                    token.setValue(cache.toString());
                    token.setType(TokenType.STRING_LITERAL);

                    previous = current;
                    current = token;
                    idx++;
                    state = TokenizerState.PARSE_ANYTHING;
                    return token;
                }
                cache.append(chars[idx]);
            }

            // when state is set to PARSE_NUMBER or PARSE_NEG_NUMBER it checks if a digit or a decimal is present
            // in order to parse negative numbers it multiplies by -1 when state is PARSE_NEG_NUMBER
            if(state == TokenizerState.PARSE_NUMBER || state == TokenizerState.PARSE_NEG_NUMBER) {
                if(!(Character.isDigit(chars[idx].charAt(0)) || chars[idx].charAt(0) == '.')) {
                    token.setType(TokenType.NUMBER);
                    try {
                        token.setValue(String.valueOf(Double.parseDouble(cache.toString()) * (state==TokenizerState.PARSE_NEG_NUMBER?(-1):1)));
                    } catch (NumberFormatException e) {
                        if(e.getMessage().equalsIgnoreCase("multiple points")) {
                            int count = StringUtils.countMatches(cache.toString(), ".");
                            RuleBasedNumberFormat format = new RuleBasedNumberFormat(new Locale("de"), RuleBasedNumberFormat.SPELLOUT);

                            throw new MLException("Cannot have " + format.format(count) + " commas in number.");
                        }
                        throw new MLException("given input cannot be parsed to valid number");
                    }
                    state = TokenizerState.PARSE_ANYTHING;
                    previous = current;
                    current = token;
                    return token;
                }

                cache.append(chars[idx]);
            }


            if(state == TokenizerState.PARSE_ANYTHING) {
                if(chars[idx].charAt(0) == '"') {
                    state = TokenizerState.PARSE_STRING;
                } else if(Character.isDigit(chars[idx].charAt(0))) {
                    state = TokenizerState.PARSE_NUMBER;
                    cache.append(chars[idx]);
                } else if(chars[idx].charAt(0) == '-' && (idx > 0 && !Character.isDigit(chars[idx-1].charAt(0)) || idx == 0)) {
                    // we check if current char is '-' AND if idx is greater than zero to catch an IOB Exception in next step
                    state = TokenizerState.PARSE_NEG_NUMBER;
                } else if(chars[idx].charAt(0) != ' ' && chars[idx].charAt(0) != ';') {

                    TokenType type = this.validCharTokens.get(chars[idx].charAt(0));
                    if(type != null) {
                        token.setValue(String.valueOf(chars[idx].charAt(0)));
                        token.setType(type);
                        previous = current;
                        current = token;
                        idx++;
                        state = TokenizerState.PARSE_ANYTHING;
                        return token;
                    }

                    state = TokenizerState.PARSE_IDENTIFIER;
                }


            }
            if(state == TokenizerState.PARSE_IDENTIFIER) {
                if(!Character.isLetter(chars[idx].charAt(0))) {
                    token.setValue(cache.toString());

                    token.setType(TokenType.IDENTIFIER);
                    if(validStringTokens.containsKey(cache.toString()))
                        token.setType(validStringTokens.get(cache.toString()));
                    previous = current;
                    current = token;
                    state = TokenizerState.PARSE_ANYTHING;

                    return token;
                }
                cache.append(chars[idx]);
            }

            idx++;
        } while (chars[idx-1].charAt(0) != ';');

        return new Token("0", TokenType.NOT_DEFINED);

    }

}
