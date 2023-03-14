package org.leux.theapi.command;

public class CommandResult {

    public enum ResultType {
        NO_SUB_COMMAND_FOUND(null),
        NO_PERMISSION(subCommand_),
        WRONG_USAGE(subCommand_),
        SUCCESS(subCommand_);

        private final CommandResult commandResult;
        private static SubCommand subCommand;

        ResultType(SubCommand subCommand) {
            commandResult = new CommandResult(subCommand, this);
        }

        public ResultType getResult() {
            return this;
        }

        public SubCommand getCommand() {
            return subCommand;
        }
    }

    private static SubCommand subCommand_;
    private final ResultType result;

    private CommandResult(SubCommand subCommand, ResultType result) {
        CommandResult.subCommand_ = subCommand;
        this.result = result;
    }

    public ResultType getResult() {
        return result.getResult();
    }
}
