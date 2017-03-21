package my_calculator;

import my_calculator.translator.Token;
import my_calculator.translator.Translator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Вычисляет значение входного выражения
 */
public class Calculator
{
    //Properties
    private String              infixExpression;
    private ArrayList<Token>    postfixExpression = new ArrayList<>();
    private Stack<String>       operands = new Stack<>();
    private double              result = 0.0;

    //Methods
    Calculator() { infixExpression = ""; }
    Calculator(String expression) { infixExpression = expression; }

    public String getInfixExpression() { return infixExpression; }
    public void setInfixExpression(String infixExpression) { this.infixExpression = infixExpression; }

    /**
     * @return переданное выражение в постфиксной форме
     */
    public String getPostfixExpression() { return postfixExpression.toString(); }

    /**
     * Возвращает результат вычислений
     * @return результат
     */
    public double getResult() { return  result; }

    /**
     * Начинает вычисление
     */
    public void calculate()
    {
        Translator translator = new Translator(infixExpression);
        postfixExpression = translator.translateToPostfixNotation();

        for(Token token: postfixExpression)
        {
            processToken(token);
        }

        result = getOperandValue(); //Результат вычислений лежит на вершине стека
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
            operands.push( token.getName() );
        }
        else if(tokenType == Token.Type.OPERATION)
        {
            executeOperation( token.getName() );
        }
    }

    /**
     * Выполняет операцию и кладет результат на вершину стека
     * @param operation литерал операции или имя функции
     */
    private void executeOperation(String operation)
    {
        if( operation.equals("+") && isContainsAsManyOperands(2) )
        {
            operands.push( sum() );
        }
        else if( operation.equals("-") && isContainsAsManyOperands(2) )
        {
            operands.push( subtraction() );
        }
        else if( operation.equals("*") && isContainsAsManyOperands(2) )
        {
            operands.push( multiplication() );
        }
        else if( operation.equals("/") && isContainsAsManyOperands(2) )
        {
            operands.push( division() );
        }
        else if( operation.equals("sin") && isContainsAsManyOperands(1) )
        {
            double operand = Double.parseDouble( operands.pop() );
            Double result = StrictMath.sin(operand);

            operands.push( result.toString() );
        }
        else
        {
            System.err.println("Входное выражение " + infixExpression + " некорректно");
            System.exit(1);
        }
    }

    /**
     * Проверяет наличие данного количества операндов на стеке
     * @param count количество операндов;
     * @return true если содержится нужное количество операндов.
     * В противном случае возвращает false;
     */
    private boolean isContainsAsManyOperands(int count)
    {
        return count > 0 && operands.size() >= count;
    }

    /**
     * Возвращает вещественное значение операнда
     * @return значение операнда
     */
    private double getOperandValue()
    {
        return Double.parseDouble( operands.pop() );
    }

    /**
     * Возвращает сумму первых двух элементов стека в виде строки
     */
    private String sum()
    {
        double fstOperand = getOperandValue();
        double sndOperand = getOperandValue();

        double value = fstOperand + sndOperand;

        return Double.toString( value );
    }

    /**
     * Возвращает разность первых двух элементов стека в виде строки
     */
    private String subtraction()
    {
        double fstOperand = getOperandValue();
        double sndOperand = getOperandValue();

        double value = sndOperand - fstOperand;

        return Double.toString( value );
    }

    /**
     * Возвращает произведение первых двух элементов стека в виде строки
     */
    private String multiplication()
    {
        double fstOperand = getOperandValue();
        double sndOperand = getOperandValue();

        double value = fstOperand * sndOperand;

        return Double.toString( value );
    }

    /**
     * Возвращает частное первых двух элементов стека в виде строки
     */
    private String division()
    {
        double fstOperand = getOperandValue();
        double sndOperand = getOperandValue();

        double value = sndOperand / fstOperand;

        return Double.toString( value );
    }
}
