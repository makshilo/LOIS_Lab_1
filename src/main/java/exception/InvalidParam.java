/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Шило М.Ю.

package exception;

public class InvalidParam extends Exception{
    public InvalidParam(String message) {
        super(message);
    }
}
