package com.company;

import java.io.Serializable;

public interface Command extends Serializable {
    long serialVersionUID = 2L;
    void execute();
}