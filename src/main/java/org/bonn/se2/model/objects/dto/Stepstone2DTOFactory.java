package org.bonn.se2.model.objects.dto;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class Stepstone2DTOFactory extends AbstractDTOFactory {

    @Override
    public StepstoneBewerbung createBewerbung() {
        return new StepstoneBewerbung();
    }
}
