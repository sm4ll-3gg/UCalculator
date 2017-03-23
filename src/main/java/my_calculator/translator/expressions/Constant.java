package my_calculator.translator.expressions;

/**
 * Класс, описывающий константу математического выражения
 */
public class Constant implements Expression
{
    enum ConstantType {NONE, E, PI}

    ConstantType type = ConstantType.NONE;

    Constant(String literal)
    {
        type = ExpressionsSet.getConstantTypeByLiteral(literal);
    }

    @Override
    public TypeOfExpression getType()
    {
        return TypeOfExpression.CONSTANT;
    }

    @Override
    public Double getValue()
    {
        switch (type)
        {
            case E:
                return StrictMath.E;
            case PI:
                return StrictMath.PI;
        }

        return 0.0;
    }
}
