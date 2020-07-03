package com.company;

import Commands.*;
import Exceptions.CmdValidationException;

import java.util.concurrent.ConcurrentHashMap;

public final class CmdValidator {

    private static ConcurrentHashMap<Integer, Movie> collection;

    public static void setMap(ConcurrentHashMap<Integer, Movie> films) {
        collection = films;
    }

    public static boolean validateCmd(AbsCommand inpAbsCommand) throws CmdValidationException {
        if (inpAbsCommand == null)
            return false;
        if (ReplaceIfGreaterCmd.class.equals(inpAbsCommand.getClass())) {
            ReplaceIfGreaterCmd replaceIfGreaterCmd = (ReplaceIfGreaterCmd) inpAbsCommand;
            int K = replaceIfGreaterCmd.getK();
            if (collection.get(K) != null) {
                return true;
            } else
                throw new CmdValidationException("элемента с таким ключом не существует");
        } else
            return true;
    }
}