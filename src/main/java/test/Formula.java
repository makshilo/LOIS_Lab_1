/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

package test;

import config.Config;

public class Formula {

    private static final String CONJUNCTION = "/\\";
    private static final String DISJUNCTION = "\\/";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String NEGATION = "!";
    private static final String EMPTY_STRING = "";

    public static String formula(int variableNum) {
        String formula = generate(variableNum);
        int right = (int) (Math.random() * 100);
        if ((right % 2 == 0)) {
            return formula;
        } else {
            return makeError(formula);
        }
    }

    private static String generate(int variableNum) {
        TruthTable truthTable = new TruthTable(variableNum);
        StringBuilder formula = new StringBuilder();
        formula.append(OPEN_BRACKET.repeat(Math.max(0, truthTable.atomNum() - 1)));
        for (int i = 0, flag = 0; i < truthTable.rowsNum(); i++) {
            if (truthTable.getCell(i, variableNum)) {
                formula.append(createAtom(variableNum, truthTable.getRow(i)));
                if (flag != 0) {
                    formula.append(CLOSE_BRACKET);
                }
                formula.append(CONJUNCTION);
                flag++;
            }
        }
        formula.setLength(formula.length() - 2);
        return formula.toString();
    }

    private static String createAtom(int countElements, boolean[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        atom.append(OPEN_BRACKET.repeat(Math.max(0, countElements - 1)));
        for (int i = 0, flag = 0; i < countElements; i++) {
            atom.append(rowTruthTable[i]
                    ? Config.SYMBOLS.get(i)
                    : (OPEN_BRACKET + NEGATION + Config.SYMBOLS.get(i) + CLOSE_BRACKET));
            if (flag != 0) {
                atom.append(CLOSE_BRACKET);
            }
            atom.append(DISJUNCTION);
            flag++;
        }
        atom.setLength(atom.length() - 2);
        return atom.toString();
    }

    private static String makeError(String expression) {
        int typeError = (int) (Math.random() * 3);
        return switch (typeError) {
            case 0 -> conjunctionToDisjunction(expression);
            case 1 -> disjunctionToConjunction(expression);
            case 2 -> deleteBrackets(expression);
            default -> expression;
        };
    }

    private static String conjunctionToDisjunction(String expression) {
        int conjunctionNum = count(expression, CONJUNCTION);
        int changeConjunction = 1 + (int) (Math.random() * conjunctionNum);
        return changeSign(expression, CONJUNCTION, DISJUNCTION, changeConjunction);
    }

    private static String disjunctionToConjunction(String expression) {
        int countDisjunction = count(expression, DISJUNCTION);
        int changeDisjunction = 1 + (int) (Math.random() * countDisjunction);
        return changeSign(expression, DISJUNCTION, CONJUNCTION, changeDisjunction);
    }

    private static int count(String str, String target) {
        return (str.length() - str.replace(target, EMPTY_STRING).length()) / target.length();
    }

    private static String changeSign(String expression, String character, String newCharacter, int position) {
        int positionSign = findSignForCount(expression, character, position);
        if (positionSign != -1) {
            return copy(expression, 0, positionSign)
                    + newCharacter
                    + copy(expression, positionSign + 2, expression.length());
        }
        return expression;
    }

    private static String deleteBrackets(String expression) {
        StringBuilder newExpression = new StringBuilder();
        int openBracketsNum = countOpenBrackets(expression);
        int numberDeletedBracket = 1 + (int) (Math.random() * openBracketsNum);
        int positionDeletedOpenBracket = searchPositionDeletedOpenBracket(expression, numberDeletedBracket);
        int positionDeletedClosedBracket = searchPositionDeletedClosedBracket(expression, positionDeletedOpenBracket);
        if (positionDeletedOpenBracket == -1 || positionDeletedClosedBracket == -1) {
            return expression;
        }
        newExpression.append(copy(expression, 0, positionDeletedOpenBracket)).
                append(copy(expression, positionDeletedOpenBracket + 1, positionDeletedClosedBracket)).
                append(copy(expression, positionDeletedClosedBracket + 1, expression.length()));
        return newExpression.toString();
    }

    private static int countOpenBrackets(String expression) {
        return (expression.length() - expression.replace(OPEN_BRACKET, EMPTY_STRING).length()) / OPEN_BRACKET.length();
    }

    private static int searchPositionDeletedOpenBracket(String expression, int numberDeletedBrackets) {
        for (int i = 0, bracketNum = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == OPEN_BRACKET.charAt(0)) {
                bracketNum++;
            }
            if (numberDeletedBrackets == bracketNum) {
                return i;
            }
        }
        return -1;
    }

    private static int searchPositionDeletedClosedBracket(String expression, int positionOpenClosed) {
        int check = 0;
        int saveCheck = -1;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == OPEN_BRACKET.charAt(0)) {
                check++;
            }
            if (expression.charAt(i) == CLOSE_BRACKET.charAt(0)) {
                check--;
            }
            if (i == positionOpenClosed) {
                saveCheck = check - 1;
            }
            if (saveCheck != -1 && check == saveCheck) {
                return i;
            }
        }
        return -1;
    }

    private static String copy(String expression, int start, int end) {
        StringBuilder subString = new StringBuilder();
        for (int i = start; i < end; i++) {
            subString.append(expression.charAt(i));
        }
        return subString.toString();
    }

    private static int findSignForCount(String expression, String sign, int number) {
        for (int i = 0, count = 0; i < expression.length() - 1; i++) {
            if (sign.equals(EMPTY_STRING + expression.charAt(i) + expression.charAt(i + 1))) {
                count++;
                if (count == number) {
                    return i;
                }
            }
        }
        return -1;
    }
}
