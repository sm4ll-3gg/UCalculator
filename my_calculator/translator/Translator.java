package my_calculator.translator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Переводит выражение из инфиксной формы записи в постфиксную
 */
public class Translator
{
    private String  inputExpression = "";
    private Stack<Token> operators = new Stack<>();
    private ArrayList<Token> outputExpression = new ArrayList<>();

    /**
     * @param expression Исходное выражение в инфиксной форме записи
     */
    public Translator(String expression) { inputExpression = expression; }

    /**
     * @return Возвращает выражение в постфиксной форме записи0
     */
    public ArrayList<Token> translateToPostfixNotation()
    {
        outputExpression = new ArrayList<>();

        Tokenizer tokenizer = new Tokenizer(inputExpression);
        ArrayList<Token> tokens = tokenizer.getTokens();

        for(Token token: tokens) processToken( token );

        while(!operators.empty())
            outputExpression.add( operators.pop() );

        return outputExpression;
    }

    /**
     * В зависимости от значения токена,
     * добавляет его на стек или в выходное выражение
     * @param token Обрабатываемый токен
     */
    private void processToken(Token token)
    {
        Token.Type tokenType = token.getType();

        if(tokenType == Token.Type.OPERAND)
        {
            outputExpression.add( token );
        }
        else if(tokenType == Token.Type.CONST)
        {
            String value = token.getName();
            token.setTypeAndValue( Token.Type.OPERAND,
                                    getValueOfConstant( value ).toString() );

            outputExpression.add( token );
        }
        else if(tokenType == Token.Type.OPERATION || tokenType == Token.Type.OPEN_BRACKET)
        {
            operators.push( token );
        }
        else if(tokenType == Token.Type.CLOSE_BRACKET)
        {
            processCloseBracketToken();
        }
    }

    /**
     * Обработать токен закрывающейся скобки
     */
    private void processCloseBracketToken()
    {
        Token top = operators.pop();
        while( top.getType() != Token.Type.OPEN_BRACKET )
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

    /**
     * Возвращает значение переданной констатны
     * @param constant строка, содержащая литерал константы
     * @return значение данной константы
     */
    private Double getValueOfConstant(String constant)
    {
        if( OperationsSet.isConstant(constant) )
        {
            if( constant.equals("e") )
                return StrictMath.E;
            else if( constant.equals("pi") )
                return StrictMath.PI;
        }
        else
        {
            System.err.println("Переданное значение не является константой " + constant);
            System.exit(1);
        }

        System.err.println("Переданная константа не поддерживается " + constant);
        return 0.0;
    }
}
