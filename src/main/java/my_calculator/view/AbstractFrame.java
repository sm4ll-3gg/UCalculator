package my_calculator.view;

import javax.swing.*;
import java.awt.*;

/**
 * Абстрактный класс для фреймов
 */
abstract class AbstractFrame extends JFrame
{
    private static int DEFAULT_WIDTH = 0;
    private static int DEFAULT_HEIGHT = 0;

    public AbstractFrame()
    {
        setDefaultSize();
        setProperties();
        //fillFrame();
    }

    /**
     * Устанавливает стандартный размер фрейма
     */
    protected void setDefaultSize()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        DEFAULT_WIDTH = screenSize.width / 3;
        DEFAULT_HEIGHT = screenSize.height / 2;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Устанавливает свойства для фрейма
     */
    protected void setProperties()
    {
        setDefaultProperties();
    }

    /**
     * Устанавливает стандартные свойства для фрейма
     */
    protected void setDefaultProperties()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationByPlatform(true);
    }

    //protected abstract void fillFrame();
}
