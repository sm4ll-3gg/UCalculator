package my_calculator.translator;

import my_calculator.translator.expressions.*;
import my_calculator.translator.expressions.Expression;

import java.util.ArrayList;

/**
 * Отвечает за разбиение входной строки на токены
 */
class Tokenizer
{
    private enum State {NONE, UNDEFINED, NUMBER, WORD, BRACKET}

    private ArrayList<Expression>   tokens = new ArrayList<>();
    private StringBuilder           tokenName = new StringBuilder();

    private boolean                 wasThereRadixPoint = false; // Был ли уже десятичный разделитель
    private boolean                 isParametr = false; // Является ли данное выражение параметром функции
    private State                   state = State.NONE;

    private String                  inputExpression;

    Tokenizer(String expr) { inputExpression = expr; }

    /**
     * Разбивает входную строку на токены(операнды, операции, функции, открывающиеся и закрывающиеся скобки)
     * @return списочный массив токенов данного выражения
     */
    ArrayList<Expression> getTokens()
    {
        for(int i = 0; i < inputExpression.length(); ++i)
        {
            Character c = inputExpression.charAt(i);

            if( switchState(c) )
                tokenName.append(c);
        }

        if( !addWord() ) // Добавление последнего слова
            System.err.println("Проблема при добавлении последнего слова в " + getClass().getName());

        return tokens;
    }

    /**
     * Меняет состояние в зависимости от переданного символа
     * @param c символ входной строки
     * @return true, если данный символ нужно добавить к
     * токену. false, если нет.
     */
    private boolean switchState(Character c)
    {
        if( Character.isDigit(c) )
        {
            processDigit();
            return true;
        }
        else if( c.equals('.') )
        {
            if( state == State.NUMBER && !wasThereRadixPoint )
            {
                wasThereRadixPoint = true;
                return true;
            }
            else
            {
                System.err.println("Лишняя точка");
                return false;
            }
        }
        else if( Character.isLetter(c) )
        {
            if( state != State.WORD)
            {
                addWord();
                state = State.WORD;
            }
            return true;
        }
        else if( c.equals(',') )
        {
            if( isParametr )
            {
                addWord();
            }
            else System.err.println("Лишняя запятая");

            return false;
        }
        else if( c.equals('(') || c.equals(')') )
        {
            processBracket(c);
            return true;
        }
        else if( c.equals(' ') )
        {
            addWord();
            state = State.NONE;
            return false;
        }
        else
        {
            if(state != State.UNDEFINED)
            {
                addWord();
                state = State.UNDEFINED;
            }
            return true;

        }
    }

    private void processDigit()
    {
        if( state != State.NUMBER )
        {
            addWord();
            state = State.NUMBER;
        }
    }

    private void processBracket(Character c)
    {
         Bracket.BracketType type = ExpressionsSet.getBracketTypeByLiteral(c);

         if(type == Bracket.BracketType.OPEN && state == State.WORD)
         {
             String token = tokenName.toString();
             if( ExpressionsSet.getExpressionType(token) == ExpressionsSet.ExpressionType.FUNCTION )
             {
                 addWord();
                 isParametr = true;
             }
             else
             {
                 System.err.println("Скобка после константы");
             }
         }
         else
         {
             if( isParametr && type == Bracket.BracketType.CLOSE)
                 isParametr = false;
         }

         state = State.BRACKET;
    }

    private boolean addWord()
    {
        if( tokenName.toString().isEmpty() )
            return false;

        String value = tokenName.toString();
        tokenName = new StringBuilder();
        ExpressionsSet.ExpressionType type = ExpressionsSet.getExpressionType(value);

        switch (state)
        {
            case NUMBER:
                tokens.add( new Operand(value) );
                break;
            case WORD:
                if(type == ExpressionsSet.ExpressionType.CONSTANT)
                    tokens.add( new Constant(value) );
                else if(type == ExpressionsSet.ExpressionType.FUNCTION)
                    tokens.add( new Function(value) );
                else System.err.println("Неизвестное слово");
                break;
            case BRACKET:
                tokens.add( new Bracket(value) );
                break;
            case UNDEFINED:
                if(type == ExpressionsSet.ExpressionType.OPERATION)
                    tokens.add( new Operation(value) );
                else
                    System.err.println("Неизвестное выражение");
                break;
        }

        return true;
    }
}
