package my_calculator;

import my_calculator.translator.Translator;

/**
 * Вычисляет значение входного выражения
 */
public class Calculator
{
    //Properties
    private String expression;

    //Methods
    public Calculator() { expression = ""; }
    public Calculator(String expression) { this.expression = expression; }

    public String   getExpression() { return expression; }
    public void     setExpression(String expression) { this.expression = expression; }

    /**
     * Начинает вычисление
     */
    public void calculate()
    {
        Translator translator = new Translator(expression);
        translator.translate();
    }
}
