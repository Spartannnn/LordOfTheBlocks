package lotb.world.capabilities.relationship;

import java.util.concurrent.Callable;

public class PlayerNpcRelationShipFactory implements Callable<IPlayerNpcRelationShip> {

    @Override
    public IPlayerNpcRelationShip call() throws Exception {
        return new PlayerNpcRelationShipImpl();
    }
}
