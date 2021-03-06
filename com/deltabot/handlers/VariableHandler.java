package com.deltabot.handlers;

import java.util.ArrayList;
import java.util.List;

public class VariableHandler {

    private static List<Variable> varList = new ArrayList<Variable>();

    public static void setVariable(String name, String value) {
        //Check if the variable exists first..
        if (variableExists(name)) {
            //   System.out.println("Variable '" + name + "' already exists. Changing value to '" + value + "'.");
            adjustVariableValue(name, value);
        } else {
            //    System.out.println("Adding new variable '" + name + "' with a value of '" + value + "'.");
            Variable var = new Variable(name, value);
            varList.add(var);
        }
    }

    public static String getVariableValue(String name) {
        if (variableExists(name)) {
            for (Variable var : varList) {
                if (var.name.toLowerCase().equals(name.toLowerCase())) return var.value;
            }
        }

        return "null";
    }

    private static void adjustVariableValue(String name, String value) {
        for (Variable var : varList) {
            if (var.name.toLowerCase().equals(name.toLowerCase())) {
                var.value = value;
            }
        }
    }

    public static boolean variableExists(String name) {
        for (Variable var : varList) {
            if (var.name.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static void removeVariable(String name) {
        if (variableExists(name)) {
            for (Variable var : varList) {
                if (var.name.toLowerCase().equals(name.toLowerCase())) {
                    varList.remove(var);
                }
            }
        }
    }

}

class Variable {

    public String name;
    public String value;

    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
