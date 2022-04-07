/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static final String FILE_NAME = "2";
    public static final String FILE_FORMAT = "txt";
    public static final String IN_FILE_PATH = System.getProperty("user.dir")
            + "/src/main/resources/in/"
            + FILE_NAME + "."
            + FILE_FORMAT;
    public static final List<String> SIGNS = new ArrayList<>(Arrays.asList("!", "/\\", "\\/", "(", ")", "->", "~"));
    public static final List<String> SYMBOLS = new ArrayList<>(
            Arrays.asList(
                    "A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N",
                    "O", "P", "Q", "R", "S", "T", "U",
                    "V", "W", "X", "Y", "Z")
    );

}
