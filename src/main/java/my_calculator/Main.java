package my_calculator;

import my_calculator.view.MainFrame;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new MainFrame();
            }
        });
//        Scanner in = new Scanner( new File("input.txt") );
//        Calculator calculator = new Calculator(in.nextLine() );
//        calculator.calculate();
//
//        System.out.println( "Результат вычислений равен: " + calculator.getResult() );
    }
}
