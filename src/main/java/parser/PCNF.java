/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом группы 921701 БГУИР Соловьёв А.М. Шило М.Ю
// Класс предназначен для проверки формулы и для проверки знаний пользователя

package parser;

import config.Config;
import exception.SKNFException;

import java.util.*;

public class PCNF {

    private static final String CONJUNCTION = "/\\";

    private final Set<String> ELEMENTS = new HashSet<>();
    private final List<String> ATOMS = new ArrayList<>();

    private String errorMessage = "";

    public boolean isPCNF(String expression) {
        try {
            Validator.checkSymbols(expression);
            Validator.checkBrackets(expression);
            ExpressionTree tree = new ExpressionTree(expression, this);
            checkNegation(tree, 0);
            searchAtoms(tree);
            checkAtomsForAllElements();
            checkAtomsForOperations();
            checkAtomsUnique();
            return true;
        } catch (SKNFException e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    public void searchAtoms(ExpressionTree tree) {
        if (CONJUNCTION.equals(tree.getOperation())) {
            searchAtoms(tree.getLeft());
            searchAtoms(tree.getRight());
        } else {
            ATOMS.add(tree.getExpression());
        }
    }

    public void checkNegation(ExpressionTree tree, int code) throws SKNFException {
        if (code == 0) {
            if ("!".equals(tree.getOperation())) {
                if (Objects.isNull(tree.getRight())) {
                    checkNegation(tree.getLeft(), 1);
                    return;
                } else {
                    throw new SKNFException("Negation is not with the element!");
                }
            }
            if (Objects.nonNull(tree.getLeft())) {
                checkNegation(tree.getLeft(), 0);
            }
            if (Objects.nonNull(tree.getRight())) {
                checkNegation(tree.getRight(), 0);
            }
        } else {
            if (Objects.nonNull(tree.getRight())) {
                throw new SKNFException("Negation is not with the element!");
            }
            if ("!".equals(tree.getOperation())) {
                throw new SKNFException("Double negation");
            }
            if (Objects.nonNull(tree.getLeft())) {
                checkNegation(tree.getLeft(), 1);
            }
        }
    }

    public void checkAtomsForAllElements() throws SKNFException {
        for (String atom : ATOMS) {
            for (String element : ELEMENTS) {
                if (!atom.contains(element)) {
                    throw new SKNFException("Disjunction does not consist of all the variables in the list!");
                }
                int count = 0;
                for (int i = 0; i < atom.length(); i++) {
                    if (element.equals("" + atom.charAt(i))) {
                        count++;
                    }
                }
                if (count > 1) throw new SKNFException("Elements are repeated in the disjunction!");
            }
        }
    }

    public void checkAtomsForOperations() throws SKNFException {
        for (String atom : ATOMS) {
            if (atom.contains(CONJUNCTION)) {
                throw new SKNFException("Disjunction has a conjunction operation!");
            }
        }
    }

    public void checkAtomsUnique() throws SKNFException {
        List<String> newList = new ArrayList<>(ATOMS);
        List<List<String>> elements = new ArrayList<>(new ArrayList<>());
        for (String s : newList) {
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < s.length(); j++) {
                String temp = "" + s.charAt(j);
                if ("!".equals(temp)) {
                    j++;
                    temp += s.charAt(j);
                    tempList.add(temp);
                    continue;
                }
                if (Config.SYMBOLS.contains(temp)) {
                    tempList.add(temp);
                }
            }
            elements.add(tempList);
        }
        for (List<String> atom : elements) {
            atom.sort(Collections.reverseOrder());
        }
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = 0; j < elements.size(); j++) {
                if (i != j) {
                    if (elements.get(i).equals(elements.get(j))) {
                        throw new SKNFException("Repeated disjunction!");
                    }
                }
            }
        }
    }

    public void addElement(String element) {
        ELEMENTS.add(element);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
