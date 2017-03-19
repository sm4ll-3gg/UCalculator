package my_calculator.translator;

/**
 * Класс, содержащий множество операций, поддерживаемых программой
 */
class OperationsSet
{
    private static final char[]     operations = {'+', '-', '*', '/'};
    private static final String[]   functions = {"sin"};

    /**
     * Проверяет поддерживается ли данная операция
     * @param c Проверяемая операция
     * @return Возвращает true, если операция поддерживается. В противном случае возвращает false
     */
    public static boolean   contain(char c)
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
    public static boolean   contain(String s)
    {
        for(String function : functions)
        {
            if( s.equals(function) ) return true;
        }

        return false;
    }
}
