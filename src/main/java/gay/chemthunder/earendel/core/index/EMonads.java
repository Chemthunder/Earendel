package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.monad.ShamblingMonad;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EMonads {
    Index<Monad> index = Earendel.MAIN.createIndex(ERegistries.MONAD);

    Monad SHAMBLING = index.register("shambling", new ShamblingMonad());

    static void init() {}
}
