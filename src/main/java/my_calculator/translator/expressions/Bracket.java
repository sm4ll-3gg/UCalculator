package my_calculator.translator.expressions;

/**
 * Класс, описывающий скобку в математическом выражени
 */
public class Bracket implements Expression
{
    enum BracketType {NONE, SQUARE, CIRCLE}

    private BracketType bracketType = BracketType.NONE;
    private TypeOfExpression typeOfExpression = TypeOfExpression.NONE;

    Bracket(char c) { setTypesByLiteral(c); }

    @Override
    public TypeOfExpression getType() { return typeOfExpression; }

    @Override
    public Double getValue() { return null; }

    /**
     * Возвращает тип скобки
     * @return тип скобки
     */
    public BracketType getBracketType() { return bracketType; }

    /**
     * Устанавливает тип выражения и тип скобки в зависимости от литерала
     * @param literal литерал скобки
     */
    private void setTypesByLiteral(char literal)
    {
        switch (literal)
        {
            case '(':
                bracketType = BracketType.CIRCLE;
                typeOfExpression = TypeOfExpression.OPEN_BRACKET;
                break;
            case ')':
                bracketType = BracketType.CIRCLE;
                typeOfExpression = TypeOfExpression.CLOSE_BRACKET;
                break;
            case '[':
                bracketType = BracketType.SQUARE;
                typeOfExpression = TypeOfExpression.OPEN_BRACKET;
                break;
            case ']':
                bracketType = BracketType.SQUARE;
                typeOfExpression = TypeOfExpression.CLOSE_BRACKET;
                break;
            default:
                System.err.println("Неподдерживаемый тип скобок " + literal );
        }
    }
}
