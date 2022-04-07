/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

package parser;

import config.Config;
import exception.SKNFException;

public class ExpressionTree {

    private final String expression;
    private final PCNF pcnf;

    private String operation;
    private ExpressionTree left;
    private ExpressionTree right;

    public ExpressionTree(String expression, PCNF pcnf) throws SKNFException {
        this.expression = withoutBrackets(expression);
        this.pcnf = pcnf;
        if (!createLeaf()) {
            parse();
        }
    }

    private String withoutBrackets(String expression) {
        String withoutBrackets = expression;
        while (withoutBrackets.startsWith("(") && searchSignOutsideBrackets(withoutBrackets) == -1) {
            withoutBrackets = withoutBrackets.substring(1, withoutBrackets.length() - 1);
        }
        return withoutBrackets;
    }

    private boolean createLeaf() throws SKNFException {
        if (expression.length() == 1) {
            left = right = null;
            pcnf.addElement(expression);
            return true;
        } else if (expression.startsWith("!")) {
            this.operation = "!";
            left = new ExpressionTree(expression.substring(1), pcnf);
            right = null;
            return true;
        }
        return false;
    }

    private void parse() throws SKNFException {
        int pointerSign = searchSignOutsideBrackets(expression);
        if (pointerSign == -1) {
            throw new SKNFException("Syntax violated!");
        }
        operation = searchSign(expression, pointerSign);
        String leftExpression = expression.substring(0, pointerSign);
        String rightExpression = expression.substring(pointerSign + 2);
        left = new ExpressionTree(leftExpression, pcnf);
        right = new ExpressionTree(rightExpression, pcnf);
    }

    private int searchSignOutsideBrackets(String expression) {
        int check = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) != '(' && expression.charAt(i) != ')' && check == 0) {
                String sign = searchSign(expression, i);
                if (Config.SIGNS.contains(sign)) {
                    return i;
                }
            }
            if (expression.charAt(i) == '(') {
                check++;
            } else if (expression.charAt(i) == ')') {
                check--;
            }
        }
        return -1;
    }

    private String searchSign(String expression, int pointer) {
        if (expression.charAt(pointer) == '!' || expression.charAt(pointer) == '~') {
            return expression.charAt(pointer) + "";
        }
        else if (expression.charAt(pointer) == '/' || expression.charAt(pointer) == '\\') {
            return "" + expression.charAt(pointer) + expression.charAt(pointer + 1);
        } else {
            return "";
        }
    }

    public String getExpression() {
        return expression;
    }

    public ExpressionTree getLeft() {
        return left;
    }

    public ExpressionTree getRight() {
        return right;
    }

    public String getOperation() {
        return operation;
    }
}