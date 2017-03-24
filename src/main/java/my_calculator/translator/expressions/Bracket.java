package my_calculator.translator.expressions;

/**
 * Класс, описывающий скобку в математическом выражени
 */
public class Bracket implements Expression
{
    public enum BracketType {NONE, OPEN, CLOSE}

    private BracketType bracketType = BracketType.NONE;

    public Bracket(String c) { setTypesByLiteral(c); }

    @Override
    public TypeOfExpression getType() { return TypeOfExpression.BRACKET; }

    @Override
    public Double getValue() { return null; }

    @Override
    public String toString()
    {
        return bracketType.toString();
    }

    /**
     * Возвращает тип скобки
     * @return тип скобки
     */
    public BracketType getBracketType() { return bracketType; }

    /**
     * Устанавливает тип выражения и тип скобки в зависимости от литерала
     * @param literal литерал скобки
     */
    private void setTypesByLiteral(String literal)
    {
        switch (literal)
        {
            case "(":
                bracketType = BracketType.OPEN;
                break;
            case ")":
                bracketType = BracketType.CLOSE;
                break;
            default:
                System.err.println("Неподдерживаемый тип скобок " + literal );
        }
    }
}
