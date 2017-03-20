package my_calculator;

import my_calculator.translator.Translator;

import java.util.ArrayList;

/**
 * Вычисляет значение входного выражения
 */
public class Calculator
{
    //Properties
    private String infixExpression;
    private ArrayList<String> postfixExpression = new ArrayList<>();

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
     * Начинает вычисление
     */
    void calculate()
    {
        Translator translator = new Translator(infixExpression);
        postfixExpression = translator.translateToPostfixNotation();

        System.out.println(postfixExpression.toString());
    }
}
