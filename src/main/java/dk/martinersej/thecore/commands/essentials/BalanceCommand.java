package dk.martinersej.thecore.commands.essentials;

//import dk.martinersej.theCore.ecnomy.Economy;
import dk.martinersej.theapi.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("balance", new String[]{"bal"}, "Check your balance", "/balance");

        setPlayerOnly(true);
    }

    @Override
    public boolean run(CommandSender commandSender, String s, String... strings) {
//        double balance = Economy.get().getWalletOwner((Player) commandSender).getWallet().getBalanceDouble();
//        commandSender.sendMessage("§aYour balance is §f" + balance + "§a.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String s, String[] strings) {
        return Collections.emptyList();
    }
}
