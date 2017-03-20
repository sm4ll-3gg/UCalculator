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
    private ArrayList<String> outputExpression = new ArrayList<>();

    /**
     * @param expression Исходное выражение в инфиксной форме записи
     */
    public Translator(String expression) { inputExpression = expression; }

    /**
     * @return Возвращает выражение в постфиксной форме записи
     */
    public ArrayList<String> translateToPostfixNotation()
    {
        outputExpression = new ArrayList<>();

        Tokenizer tokenizer = new Tokenizer(inputExpression);
        ArrayList<Token> tokens = tokenizer.getTokens();

        for(Token token: tokens) processToken( token );

        while(!operators.empty())
            outputExpression.add( operators.pop().getName() );

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
            outputExpression.add( token.getName() );
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

            outputExpression.add( top.getName() );
            top = operators.pop();
        }
    }
}
