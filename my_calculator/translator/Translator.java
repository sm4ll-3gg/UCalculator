package my_calculator.translator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Переводит выражение из инфиксной формы записи в постфиксную
 */
public class Translator
{
    private String  inputExpression;
    private Stack<String> operators;

    /**
     * @param expression Исходное выражение в инфиксной форме записи
     */
    public Translator(String expression) { inputExpression = expression; };

    /**
     * @return Возвращает выражение в постфиксной форме записи
     */
    public String translate()
    {
        StringBuilder outputExpression = new StringBuilder();

        Splitter splitter = new Splitter(inputExpression);
        ArrayList<Pair> l = splitter.getWords();
        for(Pair p: l)
        {
            System.out.print(p.getString() + " ");
        }

        return outputExpression.toString();
    }

}
