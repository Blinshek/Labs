package Enums;

import Commands.*;

public enum Command {
    COUNT_BY_BOX_OFFICE(CountByUsaBoxOfficeCmd.class, CountByUsaBoxOfficeCmd.cmdName),
    COUNT_GREATER_THAN_BOX_OFFICE(CountGreaterThanUsaBoxOfficeCmd.class, CountGreaterThanUsaBoxOfficeCmd.cmdName),
    EXECUTE_SCRIPT(ExecuteScriptCmd.class, ExecuteScriptCmd.cmdName),
    INSERT(InsertCmd.class, InsertCmd.cmdName),
    REMOVE_GREATER_KEY(RemoveGreaterKeyCmd.class, RemoveGreaterKeyCmd.cmdName),
    REMOVE_LOWER_KEY(RemoveLowerKeyCmd.class, RemoveLowerKeyCmd.cmdName),
    REPLACE_IF_GREATER(ReplaceIfGreaterCmd.class, ReplaceIfGreaterCmd.cmdName);

    Class cmdClass;
    String cmdName;

    Command(Class cmdClass, String cmdName){
        this.cmdClass = cmdClass;
        this.cmdName = cmdName;
    }

    public Class getCmdClass() {
        return cmdClass;
    }

    public String getCmdName() {
        return cmdName;
    }
}