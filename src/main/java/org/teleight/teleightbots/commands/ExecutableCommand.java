package org.teleight.teleightbots.commands;

import com.github.javaparser.quality.NotNull;
import org.teleight.teleightbots.api.objects.User;

public interface ExecutableCommand {

    void execute(@NotNull User from);

}
