package my_calculator.translator.expressions;

/**
 * Класс, описывающий операцию математического выражения
 */
public class Operation implements Computational
{
    enum OperationType {NONE, PLUS, MINUS, MULTIPLICATION, DIVISION, POWER}

    private OperationType   type = OperationType.NONE;
    private double[]        parametres = null;

    /**
     * @param literal литерал данной операции
     */
    Operation(String literal)
    {
        type = ExpressionsSet.getOperationTypeByLiteral(literal);
    }

    @Override
    public TypeOfExpression getType() { return TypeOfExpression.OPERATION; }

    @Override
    public Double getValue()
    {
        if( parametres != null )
            return calculateResult();
        else
        {
            System.err.println("Параметры для операции не были установлены до расчета");
            return 0.0;
        }
    }

    @Override
    public void setParametres(double[] parametres)
    {
        if( isCorrectParametresCount(parametres.length) )
        {
            this.parametres = parametres;
        }
    }

    /**
     * Проверяет корректное ли количество параметров count
     * передано для данной операции
     * @return true, если количество параметров коректно.
     * В противном случае возвращает false
     */
    private boolean isCorrectParametresCount(int count)
    {
        return count == 2; // Все операции бинарные
    }

    /**
     * Рассчитывает результат выражение
     * @return результат
     */
    private double calculateResult()
    {
        switch (type)
        {
            case PLUS:
                return parametres[0] + parametres[1];
            case MINUS:
                return parametres[1] - parametres[0];
            case MULTIPLICATION:
                return parametres[0] * parametres[1];
            case DIVISION:
                return parametres[1] / parametres[0];
            case POWER:
                return StrictMath.pow( parametres[1], parametres[0] );
            default:
                System.err.println("Ошибка при вычислении результата выражения");
                return 0;
        }
    }
}
