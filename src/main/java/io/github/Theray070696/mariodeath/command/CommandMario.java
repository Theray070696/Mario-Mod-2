package io.github.Theray070696.mariodeath.command;

import io.github.Theray070696.mariodeath.dev.MarioDevStats;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 8/29/2017
 */
public class CommandMario extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "mario";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "mario2:command.mario.usage";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length == 0)
        {
            sender.addChatMessage(new TextComponentString(getCommandUsage(sender)));
        } else
        {
            if(args[0].equalsIgnoreCase("worldgen"))
            {
                if(args[1].equalsIgnoreCase("questionmark"))
                {
                    sender.addChatMessage(new TextComponentString("Generated " + MarioDevStats.questionMarksGenerated + " Question Mark Blocks this" +
                            " session"));
                } else if(args[1].equalsIgnoreCase("invisible"))
                {
                    sender.addChatMessage(new TextComponentString("Generated " + MarioDevStats.invisibleBlocksGenerated + " Invisible Blocks this " +
                            "session"));
                } else if(args[1].equalsIgnoreCase("castle"))
                {
                    sender.addChatMessage(new TextComponentString("Generated " + MarioDevStats.castlesGenerated + " Castles this session"));
                }
            }
        }
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        if(args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "worldgen");
        } else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("worldgen"))
            {
                return getListOfStringsMatchingLastWord(args, "questionmark", "invisible", "castle");
            } else
            {
                return Collections.emptyList();
            }
        } else
        {
            return Collections.emptyList();
        }
    }
}
