/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя
// Интернет ресурс http://www.xn--24-6kct3an.xn--p1ai/%D0%98%D0%BD%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D0%BA%D0%B0_10_%D0%BA%D0%BB_%D0%91%D0%BE%D1%81%D0%BE%D0%B2%D0%B0/20.3.html

package test;

import parser.PCNF;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Test {
    private final List<String> test;
    private final int COUNT_QUESTIONS = 10;

    public Test() {
        test = new ArrayList<>();
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            test.add(Formula.formula(2 + (int) (Math.random() * 2)));
        }
    }

    public void run() {
        int result = 0;
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            System.out.println("\t" + (i + 1) + ". " + test.get(i));
            System.out.print("1. Yes;\n2. No.\nYour choice: ");
            Scanner in = new Scanner(System.in);
            try {
                int num = in.nextInt();
                boolean choice = num == 1;
                PCNF pcnf = new PCNF();
                if (choice == pcnf.isPCNF(test.get(i))) {
                    System.out.println("Correctly!");
                    result++;
                } else {
                    System.out.println("Wrong!");
                    System.out.println(pcnf.getErrorMessage() + "\n");
                }
            } catch (InputMismatchException e) {
                i--;
                System.out.println("Please enter correct data!");
            }
        }

        System.out.printf("Your result: %d(%d of %d)", result, result, test.size());
    }
}
