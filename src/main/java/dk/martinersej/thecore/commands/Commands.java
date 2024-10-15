package dk.martinersej.thecore.commands;

import dk.martinersej.thecore.TheCore;
import dk.martinersej.theapi.command.Command;
import dk.martinersej.theapi.command.SubCommand;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Commands {

    private static final List<Command> commands = new ArrayList<>();

    private static Set<Class<? extends Command>> findAllCommands() {
        Reflections reflections = new Reflections("dk.martinersej.theCore.commands");
        // Get all classes that extend Command class
        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        // Remove subcommands
        commands.removeAll(reflections.getSubTypesOf(SubCommand.class)); // remove subcommands
        // check if the command is abstract
        commands.removeIf(command -> {
            try {
                command.getDeclaredConstructor().newInstance();
                return false;
            } catch (Exception e) {
                return true;
            }
        });
        return commands;
    }

    public static void register() {
        TheCore.get().getLogger().info("Registering commands...");

        Set<Class<? extends Command>> commandClazz = findAllCommands();
        commandClazz.forEach(commandClazz_ -> {
            try {
                Command command = commandClazz_.getDeclaredConstructor().newInstance();
                TheCore.get().getLogger().info("Registered command: " + command.getName());
                commands.add(command);
            } catch (Exception e) {
                TheCore.get().getLogger().warning("Failed to register command: " + commandClazz_.getCanonicalName());
                e.printStackTrace();
            }
        });

        commands.forEach(command -> command.inject(TheCore.get()));
    }

    public static void unregister() {
        TheCore.get().getLogger().info("Unregistering commands...");

        commands.forEach(Command::uninject);
        commands.clear();
    }
}
