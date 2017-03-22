package my_calculator.translator;

/**
 * Хранит тип и имя токена
 */
public class Expression
{
    public enum Type {NONE, OPERATION, OPERAND, CONST, OPEN_BRACKET, CLOSE_BRACKET}

    private Type type;
    private String token;

    Expression(Type _type, String _value)
    {
        type = _type;
        token = _value;
    }

    /**
     * @return тип токена
     */
    public Type getType() { return type; }

    /**
     * @return имя токена
     */
    public String getName() { return token; }

    /**
     * Устанавливает тип и значение токена
     * @param newType устанавливаемый тип
     * @param newValue устанавливаемое значение
     */
    void setTypeAndValue(Type newType, String newValue)
    {
        type = newType;
        token = newValue;
    }


    @Override
    public String toString() { return token; }
}