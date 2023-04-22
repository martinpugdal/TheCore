package org.leux.theapi.command;

import org.leux.theapi.enums.Result;

public class CommandResult {

    private ISubCommand subCommand;
    private final Result result;

    public CommandResult(ISubCommand subCommand, Result result) {
        this.subCommand = subCommand;
        this.result = result;
    }

    public ISubCommand getSubCommand() {
        return subCommand;
    }

    public Result getResult() {
        return result;
    }
}