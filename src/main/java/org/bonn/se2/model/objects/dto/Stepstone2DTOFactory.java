package org.bonn.se2.model.objects.dto;

public class Stepstone2DTOFactory extends AbstractDTOFactory {

    @Override
    public StepstoneBewerbung createBewerbung() {
        return new StepstoneBewerbung();
    }
}
