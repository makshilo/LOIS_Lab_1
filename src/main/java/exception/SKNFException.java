/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Шило М.Ю.

package exception;

public class SKNFException extends Exception {
    public SKNFException(String message) {
        super(message);
    }
}
