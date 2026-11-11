package gay.chemthunder.earendel.core.command;

import com.mojang.brigadier.CommandDispatcher;
import gay.chemthunder.earendel.core.util.EUtil;
import gay.chemthunder.earendel.core.util.SignalPos;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * @author Chemthunder
 */
public class ECommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(literal("earendel").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(literal("signal_network")
                        .then(literal("create")
                                .then(argument("pos", BlockPosArgument.blockPos())
                                        .executes(context -> {
                                            ServerLevel level = context.getSource().getLevel();
                                            EUtil.pushPoint(level, BlockPosArgument.getBlockPos(context, "pos"));
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("remove")
                                .then(argument("pos", BlockPosArgument.blockPos())
                                        .executes(context -> {
                                            ServerLevel level = context.getSource().getLevel();
                                            EUtil.popPoint(level, BlockPosArgument.getBlockPos(context, "pos"));
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("list")
                                .executes(context -> {
                                    ServerLevel level = context.getSource().getLevel();

                                    for (SignalPos origin : EUtil.getPoints(level)) {
                                        context.getSource().sendSystemMessage(
                                                Component.literal("| ").withStyle(ChatFormatting.LIGHT_PURPLE)
                                                        .append(Component.literal(origin.pos().dimension().identifier() + " "))
                                                        .append(Component.literal(origin.pos().pos().toShortString() + " "))
                                        );
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}
