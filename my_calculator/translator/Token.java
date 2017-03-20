package my_calculator.translator;

/**
 * Хранит тип и имя токена
 */
public class Token
{
    public enum Type {NONE, OPERATION, OPERAND, OPEN_BRACKET, CLOSE_BRACKET}

    private Type type;
    private String token;

    Token(Type _type, String _value)
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

    @Override
    public String toString() { return token; }
}