package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.monad.ShamblingMonad;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface EMonads {
    RegisterTask<Monad> task = Earendel.MAIN.createTask(RegisterTask::new, ERegistries.MONAD);

    Monad SHAMBLING = task.register("shambling", new ShamblingMonad());
}
