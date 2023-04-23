package org.leux.theapi.command;

import org.leux.theapi.enums.Result;

public class CommandResult {

    private SubCommand subCommand;
    private final Result result;

    public CommandResult(SubCommand subCommand, Result result) {
        this.subCommand = subCommand;
        this.result = result;
    }

    public SubCommand getSubCommand() {
        return subCommand;
    }

    public Result getResult() {
        return result;
    }
}