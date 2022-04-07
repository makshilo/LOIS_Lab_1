/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

package parser;

import config.Config;
import exception.SKNFException;

public class Validator {

    private Validator() {
    }

    public static void checkSymbols(String expression) throws SKNFException {
        if (expression.length() == 1) {
            if (!Config.SYMBOLS.contains(expression)) {
                throw new SKNFException("Unknown character used!");
            }
        }
        for (int i = 0; i < expression.length(); i++) {
            if (!(Config.SYMBOLS.contains("" + expression.charAt(i)) || Config.SIGNS.contains("" + expression.charAt(i)))) {
                String sign = searchSign(expression, i);
                if (!Config.SIGNS.contains(sign)) {
                    throw new SKNFException("Unknown character used!");
                } else {
                    if (sign.length() == 2) {
                        i++;
                    }
                }
            }
        }
    }

    public static void checkBrackets(String expression) throws SKNFException {
        if (expression.contains(")(")
                || expression.contains("()")
                || expression.charAt(0) == ')'
                || expression.charAt(0) != '(' && expression.length() != 1
                || expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) != ')') {
            throw new SKNFException("Syntax violated!");
        }
        int checkOpen = 0;
        int checkClose = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                checkOpen++;
            } else if (expression.charAt(i) == ')') {
                checkClose++;
            }
        }
        if (checkOpen > checkClose) {
            throw new SKNFException("More open brackets!");
        }
        if (checkClose > checkOpen) {
            throw new SKNFException("More closing brackets!");
        }
    }

    private static String searchSign(String expression, int pointer) {
        if (expression.charAt(pointer) == '!' || expression.charAt(pointer) == '~')
            return expression.charAt(pointer) + "";
        return "" + expression.charAt(pointer) + expression.charAt(pointer + 1);
    }
}
