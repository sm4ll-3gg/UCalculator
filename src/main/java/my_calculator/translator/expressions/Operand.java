package my_calculator.translator.expressions;

/**
 * Класс, описывающий операнд математического выражения
 */
public class Operand implements Expression
{
    private double value = 0;

    public Operand(String oValue)
    {
        value = Double.parseDouble(oValue);
    }

    @Override
    public TypeOfExpression getType() { return TypeOfExpression.OPERAND; }

    @Override
    public Double getValue() { return value; }

    @Override
    public String toString()
    {
        return getType() + " " + getValue().toString();
    }
}
