package my_calculator.translator;

import java.util.ArrayList;

/**
 * Отвечает за разбиение входной строки на токены
 */
class Tokenizer
{
    private enum State {NONE, NUMBER, OPERATION, WORD, OPEN_BRACKET, CLOSE_BRACKET}

    private ArrayList<Token>    tokens = new ArrayList<>();
    private StringBuilder       tokenName = new StringBuilder();
    private boolean             wasThereRadixPoint = false; // Был ли уже десятичный разделитель
    private State               state = State.NONE;

    private String              inputExpression;

    Tokenizer(String expr) { inputExpression = expr; }

    /**
     * Разбивает входную строку на токены(операнды, операции, функции, открывающиеся и закрывающиеся скобки)
     * @return списочный массив токенов данного выражения
     */
    ArrayList<Token> getTokens()
    {
        for(int i = 0; i < inputExpression.length(); ++i)
        {
            Character c = inputExpression.charAt(i);

            if( switchState(c) )
            {
                if ( !c.equals(' ') )
                    tokenName.append(c);
                else if ( c.equals(';') )
                    break;
            }
            else
                System.err.println("Проблема при смене состояния в " + getClass().getName());
        }

        if( !addWord() ) // Добавление последнего слова
            System.err.println("Проблема при добавлении последнего слова в " + getClass().getName());

        return tokens;
    }

    private boolean switchState(Character c)
    {
        boolean isOk = true;

        if( Character.isDigit(c) )
        {
            if( state != State.NUMBER)
            {
                isOk = addWord();
                state = State.NUMBER;
            }
        }
        else if(c == '.')
        {
            if(state == State.NUMBER && !wasThereRadixPoint)
                wasThereRadixPoint = true;
            else
            {
                System.err.println("Входное выражение некорректно"); // Throw
                return false;
            }
        }
        else if( Character.isLetter(c) )
        {
            if( state != State.WORD)
            {
                isOk = addWord();
                state = State.WORD;
            }
        }
        else if( c == ' ' || c == ';')
        {
            isOk = addWord();
            state = State.NONE;
        }
        else if( c == '(' || c == '[')
        {
            isOk = addWord();
            state = State.OPEN_BRACKET;
        }
        else if( c == ')' || c == ']')
        {
            isOk = addWord();
            state = State.CLOSE_BRACKET;
        }
        else
        {
            if( OperationsSet.isOperation(c) )
            {
                isOk = addWord();
                state = State.OPERATION;
            }
            else
                return false;
        }

        return isOk;
    }

    private boolean addWord()
    {
        String value = tokenName.toString();
        if(value.isEmpty()) return true;

        Token.Type type = Token.Type.NONE;

        switch (state)
        {
            case NUMBER:
                wasThereRadixPoint = false;
                type = Token.Type.OPERAND;
                break;
            case OPERATION:
                type = Token.Type.OPERATION;
                break;
            case WORD:
                String word = value.toLowerCase();
                if( OperationsSet.isFuncrion( word ) )
                    type = Token.Type.OPERATION;
                else if( OperationsSet.isConstant( word) )
                    type = Token.Type.CONST;
                else return false;
                break;
            case OPEN_BRACKET:
                type = Token.Type.OPEN_BRACKET;
                break;
            case CLOSE_BRACKET:
                type = Token.Type.CLOSE_BRACKET;
                break;
            default:
                System.err.println("Ложное срабатываение Tokenizer.addWord()"); // Throw
                System.exit(1);
        }

        Token p = new Token( type, value );
        tokens.add(p);
        tokenName = new StringBuilder();

        return true;
    }
}
