package my_calculator.translator.expressions;

/**
 * Класс, описывающий операнд математического выражения
 */
public class Operand implements Expression
{
    private double value = 0;

    Operand(double oValue)
    {
        value = oValue;
    }

    @Override
    public TypeOfExpression getType() { return TypeOfExpression.OPERAND; }

    @Override
    public Double getValue() { return value; }
}
