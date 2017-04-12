package my_calculator.view;

import my_calculator.model.Calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sm4ll_3gg on 12.04.17.
 */
public class MainFrame extends AbstractFrame
{
    private JPanel srcPanel;
    private JLabel printExprLabel;
    private JTextArea exprArea;
    private JLabel postfixExprLabel;
    private JTextArea postfixExprArea;
    private JLabel resultLabel;
    private JTextField reusltField;
    private JButton calculateButton;

    private Calculator calculator = new Calculator();

    public MainFrame()
    {
        super();

        init();

        setContentPane(srcPanel);
        setVisible(true);
    }

    private void init()
    {
        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String expression = exprArea.getText();
                System.out.println(expression);

                calculator.setInfixExpression(expression);
                calculator.calculate();

                postfixExprArea.setText(calculator.getPostfixExpression());
                reusltField.setText(calculator.getResult().toString());
            }
        });
    }
}
