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
}
