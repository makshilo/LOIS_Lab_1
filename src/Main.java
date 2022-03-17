/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import config.Config;
import exception.SKNFException;
import parser.Parser;
import test.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String expression = "";
        StringBuilder builder = new StringBuilder(expression);
        try (FileInputStream fin = new FileInputStream(Config.IN_FILE_PATH)) {
            while (fin.available() > 0) {
                int oneByte = fin.read();
                builder.append(((char) oneByte));
            }
            expression = builder.toString();
        } catch (FileNotFoundException ex) {
            System.out.println("File not find!!!");
        }


        System.out.println();

        try {
            new Parser(expression);
            System.out.println(expression);
            System.out.println();
            System.out.println("Formula is Perfect Conjunctive Normal Form!");
        } catch (SKNFException e) {
            System.out.println(e);
        }

        Test test = new Test();
        test.run();
    }
}
