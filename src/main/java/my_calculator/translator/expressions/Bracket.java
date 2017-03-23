package my_calculator.translator.expressions;

/**
 * Класс, описывающий скобку в математическом выражени
 */
public class Bracket implements Expression
{
    enum TypeOfBracket {NONE, SQUARE, CIRCLE}

    private TypeOfBracket typeOfBracket = TypeOfBracket.NONE;
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
    public TypeOfBracket getTypeOfBracket() { return typeOfBracket; }

    /**
     * Устанавливает тип выражения и тип скобки в зависимости от литерала
     * @param literal литерал скобки
     */
    private void setTypesByLiteral(char literal)
    {
        switch (literal)
        {
            case '(':
                typeOfBracket = TypeOfBracket.CIRCLE;
                typeOfExpression = TypeOfExpression.OPEN_BRACKET;
                break;
            case ')':
                typeOfBracket = TypeOfBracket.CIRCLE;
                typeOfExpression = TypeOfExpression.CLOSE_BRACKET;
                break;
            case '[':
                typeOfBracket = TypeOfBracket.SQUARE;
                typeOfExpression = TypeOfExpression.OPEN_BRACKET;
                break;
            case ']':
                typeOfBracket = TypeOfBracket.SQUARE;
                typeOfExpression = TypeOfExpression.CLOSE_BRACKET;
                break;
            default:
                System.err.println("Неподдерживаемый тип скобок " + literal );
        }
    }
}
