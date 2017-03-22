package my_calculator.translator.expressions;

import java.util.HashMap;

/**
 * Класс, описывающий множество всех выражений, поддерживаемых программой
 */
public class ExpressionsSet
{
    enum ExpressionType { NONE, OPERATION, FUNCTION, OPERAND, BRACKET }

    private static final HashMap<String, Operation.OperationType> operations = new HashMap<String, Operation.OperationType>()
    {{
       put("+", Operation.OperationType.PLUS);
       put("-", Operation.OperationType.MINUS);
       put("*", Operation.OperationType.MULTIPLICATION);
       put("/", Operation.OperationType.DIVISION);
       put("^", Operation.OperationType.POWER);
    }};

    /**
     * Возвращает тип операции за счет ее литерала
     * @param literal литерал
     * @return тип операции
     */
    static public Operation.OperationType getOperationTypeByLiteral(String literal)
    {
        if( operations.containsKey(literal) )
            return operations.get(literal);
        else
            System.err.println("Переданная операция не содерживается " + literal);

        return Operation.OperationType.NONE;
    }

    /**
     * Возвращает тип выражения по литералу literal
     * @param literal литерал, проверяемого выражения
     * @return если выражение поддерживается, то возвращает его тип.
     * В противном случае возвращает NONE;
     */
    static public ExpressionType getExpressionType(String literal)
    {
        if( operations.containsKey(literal) )
            return ExpressionType.OPERATION;

        return ExpressionType.NONE;
    }
}
