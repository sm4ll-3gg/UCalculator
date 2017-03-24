package my_calculator.translator;

import my_calculator.translator.expressions.*;
import my_calculator.translator.expressions.Expression;

import java.util.ArrayList;

/**
 * Отвечает за разбиение входной строки на токены
 */
class Tokenizer
{
    private enum State {NONE, UNDEFINED, NUMBER, OPERATION, WORD, PARAMETRES}

    private ArrayList<Expression>   tokens = new ArrayList<>();
    private StringBuilder           tokenName = new StringBuilder();
    private ArrayList<Double>       parametres = null; //

    private boolean                 wasThereRadixPoint = false; // Был ли уже десятичный разделитель
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

            //}
//            else
//                System.err.println("Проблема при смене состояния в " + getClass().getName());
        }

//        if( !addWord() ) // Добавление последнего слова
//            System.err.println("Проблема при добавлении последнего слова в " + getClass().getName());

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
            if( ( state == State.NUMBER || state == State.PARAMETRES ) && !wasThereRadixPoint )
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
            if( state == State.PARAMETRES)
            {
                if(parametres == null)
                    parametres = new ArrayList<>();

                Double parametr = Double.parseDouble( tokenName.toString() );
                parametres.add(parametr);
                tokenName = new StringBuilder();
            }
            return false;
        }
        else if( c.equals('(') || c.equals(')') )
        {
            addWord();
            processBracket(c);
            return false;
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
        if( state != State.NUMBER && state != State.PARAMETRES )
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
                 state = State.PARAMETRES;
             }
             else
             {
                 System.err.println("Скобка после константы");
             }
         }
         else
         {
             if( state == State.PARAMETRES)
                 state = State.NONE;
             else
                tokens.add(new Bracket(c));
         }
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
            case UNDEFINED:
                if(type == ExpressionsSet.ExpressionType.OPERATION)
                    tokens.add( new Operation(value) );
                else
                    System.err.println("Неизвестное выражение");
                break;
        }
//        String value = tokenName.toString();
//        if(value.isEmpty()) return true;
//
//        Expression.Type type = Expression.Type.NONE;
//
//        switch (state)
//        {
//            case NUMBER:
//                wasThereRadixPoint = false;
//                type = Expression.Type.OPERAND;
//                break;
//            case OPERATION:
//                type = Expression.Type.OPERATION;
//                break;
//            case WORD:
//                String word = value.toLowerCase();
//                if( OperationsSet.isFuncrion( word ) )
//                    type = Expression.Type.OPERATION;
//                else if( OperationsSet.isConstant( word) )
//                    type = Expression.Type.CONST;
//                else return false;
//                break;
//            case OPEN_BRACKET:
//                type = Expression.Type.OPEN_BRACKET;
//                break;
//            case CLOSE_BRACKET:
//                type = Expression.Type.CLOSE_BRACKET;
//                break;
//            default:
//                System.err.println("Ложное срабатываение Tokenizer.addWord()"); // Throw
//                System.exit(1);
//        }
//
//        Expression p = new Expression( type, value );
//        tokens.add(p);
//        tokenName = new StringBuilder();
//
        return true;
    }
}


//    private boolean switchState(Character c)
//    {
//        boolean isOk = true;
//
//        if( Character.isDigit(c) )
//        {
//            if( state != State.NUMBER)
//            {
//                isOk = switchState(State.NUMBER, c);
//            }
//        }
//        else if(c == '.')
//        {
//            if(state == State.NUMBER && !wasThereRadixPoint)
//                wasThereRadixPoint = true;
//            else
//            {
//                System.err.println("Входное выражение некорректно"); // Throw
//                return false;
//            }
//        }
//        else if( c == '(' || c == ')' || c == '[' || c == ']' )
//        {
//            isOk = switchState( ExpressionsSet.getBracketTypeByLiteral(c) );
//        }
//
//        if( Character.isDigit(c) )
//        {
//            if( state != State.NUMBER)
//            {
//                isOk = switchState(State.NUMBER, c);
//            }
//        }
//        else if(c == '.')
//        {
//            if(state == State.NUMBER && !wasThereRadixPoint)
//                wasThereRadixPoint = true;
//            else
//            {
//                System.err.println("Входное выражение некорректно"); // Throw
//                return false;
//            }
//        }
//        else if( c == ' ' || c == ';')
//        {
//            isOk = switchState(State.NONE, c);
//        }
//        else if( c == '(' || c == '[')
//        {
//            isOk = switchState(State.OPEN_BRACKET, c);
//        }
//        else if( c == ')' || c == ']')
//        {
//            isOk = switchState(State.CLOSE_BRACKET, c);
//        }
//        else
//        {
//            if( state != State.WORD)
//            {
//                isOk = switchState(State.WORD, c);
//            }
//        }
//
//        return isOk;
//    }
//
//    /**
//     * Меняет текущее состояние
//     * @param newState новое состояние
//     * @param character символ, на котором происходит смена состояния
//     * @return true, если состояние было успешно изменено.
//     * В противном случае возвращает false
//     */
//    private boolean switchState(State newState, Character character)
//    {
//        if( isNewStateExprected(newState, character) )
//        {
//            boolean isOk = addWord();
//            state = newState;
//
//            return isOk;
//        }
//        else return false;
//    }

//
//    /**
//     * Проверяет особые случаи, когда изменение состояния
//     * должно быть предсказуемо
//     * @param newState потенциальное новое состояние
//     * @param character символ, на котором меняется состояние
//     * @return true, если состояние может быть изменено на новое.
//     * В противном случае возвращает false
//     */
//    private boolean isNewStateExprected(State newState, Character character)
//    {
//        if(state == State.WORD &&
//           OperationsSet.isFuncrion( tokenName.toString() ))
//        {
//            // Операнды функций должны быть в круглых скобках
//            return newState == State.OPEN_BRACKET && character.equals('(');
//        }
//        else return true;
//    }
