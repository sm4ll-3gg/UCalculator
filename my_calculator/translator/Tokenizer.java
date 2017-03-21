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
                isOk = switchState(State.NUMBER, c);
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
                isOk = switchState(State.WORD, c);
            }
        }
        else if( c == ' ' || c == ';')
        {
            isOk = switchState(State.NONE, c);
        }
        else if( c == '(' || c == '[')
        {
            isOk = switchState(State.OPEN_BRACKET, c);
        }
        else if( c == ')' || c == ']')
        {
            isOk = switchState(State.CLOSE_BRACKET, c);
        }
        else
        {
            if( OperationsSet.isOperation(c) )
            {
                isOk = switchState(State.OPERATION, c);
            }
            else
                return false;
        }

        return isOk;
    }

    /**
     * Меняет текущее состояние
     * @param newState новое состояние
     * @param character символ, на котором происходит смена состояния
     * @return true, если состояние было успешно изменено.
     * В противном случае возвращает false
     */
    private boolean switchState(State newState, Character character)
    {
        if( isNewStateExprected(newState, character) )
        {
            boolean isOk = addWord();
            state = newState;

            return isOk;
        }
        else return false;
    }


    /**
     * Проверяет особые случаи, когда изменение состояния
     * должно быть предсказуемо
     * @param newState потенциальное новое состояние
     * @param character символ, на котором меняется состояние
     * @return true, если состояние может быть изменено на новое.
     * В противном случае возвращает false
     */
    private boolean isNewStateExprected(State newState, Character character)
    {
        if(state == State.WORD &&
           OperationsSet.isFuncrion( tokenName.toString() ))
        {
            // Операнды функций должны быть в круглых скобках
            return newState == State.OPEN_BRACKET && character.equals('(');
        }
        else return true;
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
