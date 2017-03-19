package my_calculator.translator;

import java.util.ArrayList;

/**
 * Отвечает за разбиение входной строки на "слова"
 */
class Splitter
{
    private enum State {NONE, NUMBER, OPERATION, FUNCTION, OPEN_BRACKET, CLOSE_BRACKET};

    ArrayList<Pair> words = new ArrayList<>();
    StringBuilder   word = new StringBuilder();
    boolean         wasThereRadixPoint = false; // Был ли уже десятичный разделитель
    State           state = State.NONE;

    String          inputExpression;

    Splitter(String expr) { inputExpression = expr; }

    /**
     * Разбивает входную строку на "слова"(операнды и операции выражения)
     * @return списочный массив слов данного выражения
     */
    public ArrayList<Pair> getWords()
    {
        for(int i = 0; i < inputExpression.length(); ++i)
        {
            Character c = inputExpression.charAt(i);

            if( switchState(c) )
            {
                if (c != ' ')
                    word.append(c);
                else if (c == ';')
                    break;
            }
            else
                System.err.println("Проблема при смене состояния в " + getClass().getName());
        }

        if( !addWord() ) // Добавление последнего слова
            System.err.println("Проблема при добавлении последнего слова в " + getClass().getName());

        return words;
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
            if( state != State.FUNCTION)
            {
                isOk = addWord();
                state = State.FUNCTION;
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
            if( OperationsSet.contain(c) )
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
        String value = word.toString();
        if(value.isEmpty()) return true;

        Pair.Type type = Pair.Type.NONE;

        switch (state)
        {
            case NUMBER:
                wasThereRadixPoint = false;
                type = Pair.Type.OPERAND;
                break;
            case OPERATION:
                type = Pair.Type.OPERATION;
                break;
            case FUNCTION:
                if( OperationsSet.contain( value.toLowerCase() ) )
                    type = Pair.Type.OPERATION;
                else
                    return false;
                break;
            case OPEN_BRACKET:
                type = Pair.Type.OPEN_BRACKET;
                break;
            case CLOSE_BRACKET:
                type = Pair.Type.CLOSE_BRACKET;
                break;
            default:
                System.err.println("Ложное срабатываение Splitter.addWord()"); // Throw
                System.exit(1);
        }

        Pair p = new Pair( type, value );
        words.add(p);
        word = new StringBuilder();

        return true;
    }
}
