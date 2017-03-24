package my_calculator.translator;

import my_calculator.translator.expressions.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Переводит выражение из инфиксной формы записи в постфиксную
 */
public class Translator
{
    private String  inputExpression = "";
    private Stack<Expression> operators = new Stack<>();
    private ArrayList<Expression> outputExpression = new ArrayList<>();

    /**
     * @param expression Исходное выражение в инфиксной форме записи
     */
    public Translator(String expression) { inputExpression = expression; }

    /**
     * @return Возвращает выражение в постфиксной форме записи0
     */
    public ArrayList<Expression> translateToPostfixNotation()
    {
        outputExpression = new ArrayList<>();

        Tokenizer tokenizer = new Tokenizer(inputExpression);
        ArrayList<Expression> tokens = tokenizer.getTokens();

        for(Expression token: tokens) processToken( token );

        while(!operators.empty())
            outputExpression.add( operators.pop() );

        return outputExpression;
    }

    /**
     * В зависимости от значения токена,
     * добавляет его на стек или в выходное выражение
     * @param token Обрабатываемый токен
     */
    private void processToken(Expression token)
    {
        Expression.Type tokenType = token.getType();

        if(tokenType == Expression.Type.OPERAND)
        {
            outputExpression.add( token );
        }
        else if(tokenType == Expression.Type.CONSTANT)
        {
            Double value = token.getValue();

            outputExpression.add( new Operand(value));
        }
        else if(tokenType == Expression.Type.OPERATION)
        {
            if(token instanceof Operation)
            {
                Operation oToken = (Operation) token;
                Computational topOperation = (Computational) operators.peek();

                if(oToken.getPriority() < topOperation.getPriority())
                    outputExpression.add( operators.pop() );
            }
            operators.push( token );
        }
        else if(tokenType == Expression.Type.FUNCTION)
        {
            operators.push( token );
        }
        else if(tokenType == Expression.Type.BRACKET)
        {
            Bracket bracket = (Bracket) token;
            if( bracket.getBracketType() == Bracket.BracketType.OPEN )
                operators.push( token );
            else if( bracket.getBracketType() == Bracket.BracketType.CLOSE )
                processCloseBracketToken();
        }
    }

    /**
     * Обработать токен закрывающейся скобки
     */
    private void processCloseBracketToken()
    {
        Expression top = operators.pop();
        while(!isOpenBracketToken(top) )
        {
            if(operators.empty())
            {
                System.err.println("Входное выражение " + inputExpression + " некорректно");
                System.exit(1);
            }

            outputExpression.add( top );
            top = operators.pop();
        }
    }

    private boolean isOpenBracketToken(Expression token)
    {
        if( token instanceof Bracket )
        {
            Bracket bracket = (Bracket) token;

            return  bracket.getBracketType() == Bracket.BracketType.OPEN;
        }

        return  false;
    }
}
