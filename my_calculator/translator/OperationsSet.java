package my_calculator.translator;

/**
 * Класс, содержащий множество операций, поддерживаемых программой
 */
class OperationsSet
{
    private static final char[]     operations = {'+', '-', '*', '/'};
    private static final String[]   functions = {"sin"};
    private static final String[]   constants = {"e", "pi"};

    /**
     * Проверяет поддерживается ли данная операция
     * @param c Проверяемая операция
     * @return Возвращает true, если операция поддерживается. В противном случае возвращает false
     */
    static boolean isOperation(char c)
    {
        for(char operation : operations)
        {
            if(c == operation) return true;
        }

        return false;
    }

    /**
     * Проверяет поддерживается ли данная функция
     * @param s Проверяемая функция
     * @return Возвращает true, если функция поддерживается. В противном случае возвращает false
     */
    static boolean isFuncrion(String s)
    {
        for(String function : functions)
        {
            if( s.equals(function) ) return true;
        }

        return false;
    }

    /**
     * Проверяет является ли переданная строка математической константой
     * @param s Проверяемая строка
     * @return Возвращает true, если строка является константой.
     * В противном случае возвращает false
     */
    static boolean   isConstant(String s)
    {
       for(String constant: constants)
       {
           if( s.equals(constant) ) return true;
       }

       return false;
    }
}
