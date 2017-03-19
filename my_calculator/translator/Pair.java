package my_calculator.translator;

/**
 * Хранит тип и слово
 */
class Pair
{
    public enum Type {NONE, OPERATION, OPERAND, OPEN_BRACKET, CLOSE_BRACKET};

    private Type type;
    private String value;

    Pair(Type _type, String _value)
    {
        type = _type;
        value = _value;
    }

    public Type getType() { return type; }
    public String getString() { return value; }
}