package my_calculator.translator.expressions;

/**
 * Вычисляемое выражение
 */
public interface Computational extends Expression
{
    /**
     * Устанавливает массив параметров parametres
     * для вычисляемого выражения
     * @param parametres массив параметров
     */
    void setParametres(double[] parametres);

    /**
     * Проверяет корректное ли количество параметров count
     * передано для данной операции
     * @return true, если количество параметров коректно.
     * В противном случае возвращает false
     */
    boolean isCorrectParametresCount(int count);

    /**
     * Рассчитывает результат выражение
     * @return результат
     */
    double calculateResult();

}
