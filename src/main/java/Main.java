/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import config.Config;
import parser.PCNF;
import test.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StringBuilder expression = new StringBuilder();
        try (FileInputStream fin = new FileInputStream(Config.IN_FILE_PATH)) {
            while (fin.available() > 0) {
                int oneByte = fin.read();
                expression.append(((char) oneByte));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!!!");
        }


        System.out.println();
        PCNF pcnf = new PCNF();
        System.out.println(expression);
        System.out.println();
        if (pcnf.isPCNF(expression.toString())){
            System.out.println("Formula is Perfect Conjunctive Normal Form");
        } else {
            System.out.println("Formula is NOT Perfect Conjunctive Normal Form");
            System.out.println(pcnf.getErrorMessage());
        }

        Test test = new Test();
        test.run();
    }
}
