/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.parser.tokenizer;

import dev.steinmoetzger.mathlang.exceptions.MLException;

public class Tokenizer {

    enum TokenizerState {
        PARSE_STRING,
        PARSE_NUMBER,
        PARSE_NEG_NUMBER,
        PARSE_ANYTHING
    }

    private int idx;
    private String string;
    private TokenizerState state;
    private Token current;
    private Token previous;

    public Tokenizer(String string) {
        this.string = string;
        this.idx = 0;
        this.state = TokenizerState.PARSE_ANYTHING;
    }

    public Token next() throws MLException {
        StringBuilder cache = new StringBuilder();
        Token token = new Token();
        state = TokenizerState.PARSE_ANYTHING;
        if(!string.endsWith(";"))
            throw new MLException("Expected semicolon after expression.");


        String[] chars = string.split("");

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
                    return token;
                }
                cache.append(chars[idx]);
            }

            if(state == TokenizerState.PARSE_NUMBER || state == TokenizerState.PARSE_NEG_NUMBER) {
                if(!(Character.isDigit(chars[idx].charAt(0)) || chars[idx].charAt(0) == '.')) {
                    token.setType(TokenType.NUMBER);
                    try {
                        token.setValue(String.valueOf(Double.parseDouble(cache.toString()) * (state==TokenizerState.PARSE_NEG_NUMBER?(-1):1)));
                    } catch (NumberFormatException e) {
                        if(e.getMessage().equalsIgnoreCase("multiple points")) {
                            throw new MLException("cannot have more than one decimal points in number");
                        }
                    }

                    previous = current;
                    current = token;
                    return token;
                }

                cache.append(chars[idx]);
            }


            if(state == TokenizerState.PARSE_ANYTHING) {
                if(chars[idx].charAt(0) == '"') {
                    state = TokenizerState.PARSE_STRING;
                }
                if(Character.isDigit(chars[idx].charAt(0))) {
                    state = TokenizerState.PARSE_NUMBER;
                    cache.append(chars[idx]);
                }
                if(chars[idx].charAt(0) == '-') {
                    state = TokenizerState.PARSE_NEG_NUMBER;
                }
                if(chars[idx].charAt(0) == '(') {
                    token.setValue("(");
                    token.setType(TokenType.R_PAR);
                    previous = current;
                    current = token;
                    return token;
                }
                if(chars[idx].charAt(0) == ')') {
                    token.setValue(")");
                    token.setType(TokenType.L_PAR);
                    previous = current;
                    current = token;
                    return token;
                }


            }

            idx++;
        } while (chars[idx-1].charAt(0) != ';');

        return null;

    }

}
