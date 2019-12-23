package com.company.Exceptions;

public class OutOfItemException extends RuntimeException{
    public OutOfItemException(){}

    public OutOfItemException(String str){
        super(str);
    }

    @Override
    public String toString() {
        return "Предмет отсутствует";
    }
}
