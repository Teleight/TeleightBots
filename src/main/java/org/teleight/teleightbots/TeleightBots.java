package org.teleight.teleightbots;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.manager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;

import java.io.IOException;

/// The TeleightBots API entry point.
///
/// This class provides access to the core components of the TeleightBots API, including the scheduler,
/// bot manager, and exception manager. It also provides methods to cleanly stop the API and release
/// resources when no longer needed.
///
/// This class cannot be instantiated.
///
public final class TeleightBots {

    @ApiStatus.Internal
    private static final TeleightBotsProcess teleightBotsProcess;

    // Initializes the TeleightBots API
    static {
        teleightBotsProcess = new TeleightBotsProcessImpl();
    }

    /// Stops the TeleightBots API.
    ///
    /// This method should be called when the TeleightBots API is no longer needed.
    /// It stops the TeleightBots API and releases all resources.
    ///
    public static void stopCleanly() {
        try {
            teleightBotsProcess.close();
        } catch (IOException e) {
            getExceptionManager().handleException(e);
        }
    }

    /// Returns the Scheduler associated with the TeleightBots process.
    ///
    /// The Scheduler is responsible for scheduling tasks to be executed at a later time or at regular intervals.
    ///
    /// @return The Scheduler associated with the TeleightBots process.
    public static @NotNull Scheduler getScheduler() {
        return teleightBotsProcess.scheduler();
    }

    /// Returns the BotManager associated with the TeleightBots process.
    ///
    /// The BotManager is responsible for managing bots, including registering new bots and getting existing bots.
    ///
    /// @return The BotManager associated with the TeleightBots process.
    public static @NotNull BotManager getBotManager() {
        return teleightBotsProcess.botManager();
    }

    /// Returns the exception manager used by the TeleightBots API.
    ///
    /// The exception manager is responsible for handling exceptions thrown by the TeleightBots API.
    /// It is also responsible for logging exceptions and notifying the user about them.
    ///
    /// @return the exception manager
    public static @NotNull ExceptionManager getExceptionManager() {
        return teleightBotsProcess.exceptionManager();
    }

}
