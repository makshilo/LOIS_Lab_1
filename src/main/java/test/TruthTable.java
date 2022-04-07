/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя
// Интернет ресурс http://www.xn--24-6kct3an.xn--p1ai/%D0%98%D0%BD%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D0%BA%D0%B0_10_%D0%BA%D0%BB_%D0%91%D0%BE%D1%81%D0%BE%D0%B2%D0%B0/20.3.html

package test;

public class TruthTable {

    private final int variableNum;
    private final int rows;
    private final int cols;
    private final boolean[][] table;

    public TruthTable(int variableNum) {
        this.variableNum = variableNum;
        rows = (int) Math.pow(2, variableNum);
        cols = variableNum + 1;
        table = new boolean[rows][cols];
        fillVariableCol();
        while (atomNum() < 2) {
            fillResultCol();
        }
    }

    private void fillVariableCol() {
        for (int i = 0, period = (int) Math.pow(2, variableNum - 1); i < cols - 1; i++, period /= 2) {
            boolean flag = true;
            for (int j = 0, currentPeriod = period; j < rows; j++, currentPeriod--) {
                if (currentPeriod == 0) {
                    flag = !flag;
                    currentPeriod = period;
                }
                table[j][i] = flag;
            }
        }
    }

    private void fillResultCol() {
        for (int i = 0; i < rows; i++) {
            int chose = (int) (Math.random() * 100);
            table[i][cols - 1] = chose % 2 == 0;
        }
    }

    public int rowsNum() {
        return rows;
    }

    public boolean getCell(int i, int j) {
        return table[i][j];
    }

    public boolean[] getRow(int i) {
        return table[i];
    }

    public int atomNum() {
        int atomNum = 0;
        for (int i = 0; i < rows; i++) {
            if (table[i][cols - 1]) {
                atomNum++;
            }
        }
        return atomNum;
    }
}