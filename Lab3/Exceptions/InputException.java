package com.company.Exceptions;

import com.company.AbstractClasses.*;

import java.io.IOException;

public class InputException extends IOException {
    public InputException(){}

    public InputException(String str){
        super(str);
    }

    @Override
    public String toString() {
        return "Неправильный ввод";
    }
}