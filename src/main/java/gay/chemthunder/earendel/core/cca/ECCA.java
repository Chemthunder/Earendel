package gay.chemthunder.earendel.core.cca;

import gay.chemthunder.earendel.core.cca.level.MonadManagerComponent;
import org.ladysnake.cca.api.v8.level.LevelComponentFactoryRegistry;
import org.ladysnake.cca.api.v8.level.LevelComponentInitializer;

/**
 * @author Chemthunder
 */
public class ECCA implements LevelComponentInitializer {
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(MonadManagerComponent.KEY, MonadManagerComponent::new);
    }
}
