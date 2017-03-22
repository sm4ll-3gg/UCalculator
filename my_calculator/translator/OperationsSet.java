package my_calculator.translator;

/**
 * Класс, содержащий множество операций, функций, и констант, поддерживаемых программой
 */
class OperationsSet {
    private static final char[] operations = {'+', '-', '*', '/', '^'};
    private static final String[] functions = {"sin", "pow"};
    private static final String[] constants = {"e", "pi"};

    /**
     * Проверяет поддерживается ли данная операция
     *
     * @param c Проверяемая операция
     * @return Возвращает true, если операция поддерживается. В противном случае возвращает false
     */
    static boolean isOperation(char c) {
        for (char operation : operations) {
            if (c == operation) return true;
        }

        return false;
    }

//    /**
//     * Возвращает приоретет операции operation.
//     * Приоритет ранжируется от 0 до N, где N максимально возможный приоритет
//     * @param operation операция, для которой определяется приоритет
//     * @return приоритет
//     */
//    static int getOperationPriority(char operation)
//    {
//
//    }

    /**
     * Проверяет поддерживается ли данная функция
     *
     * @param s Проверяемая функция
     * @return Возвращает true, если функция поддерживается. В противном случае возвращает false
     */
    static boolean isFuncrion(String s) {
        for (String function : functions) {
            if (s.equals(function)) return true;
        }

        return false;
    }

    /**
     * Проверяет является ли переданная строка математической константой
     *
     * @param s Проверяемая строка
     * @return Возвращает true, если строка является константой.
     * В противном случае возвращает false
     */
    static boolean isConstant(String s) {
        for (String constant : constants) {
            if (s.equals(constant)) return true;
        }

        return false;
    }

//    /**
//     * Проверяет сколько требуется операндов для функции
//     * @param s проверяемая функция
//     * @return количество операндов
//     */
//    static int howManyOperandsNeed(String s)
//    {
//        if( !isFuncrion(s) )
//        {
//            System.err.println("Параметр фунции howManyOperandsNeed() не является функцией " + s);
//            System.exit(1);
//        }
//
//        if( s.equals("pow") )
//            return 2;
//        else if( s.equals("sin") )
//            return 1;
//
//        System.err.println("Функция не поддерживается (howManyOperandsNeed()) " + s);
//        return 0;
//    }

}
