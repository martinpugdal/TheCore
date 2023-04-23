package org.leux.theapi.enums;

import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.SubCommand;

public enum Result {
    NO_PERMISSION,
    NO_SUB_COMMAND_FOUND,
    SUCCESS,
    WRONG_USAGE;

    public static CommandResult getCommandResult(SubCommand subCommand, Result result) {
        return new CommandResult(subCommand, result);
    }
}
