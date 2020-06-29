package org.bonn.se2.model.objects.dto;

public class CollAldernativDTOFactory extends AbstractDTOFactory {

    @Override
    public Bewerbung createBewerbung() {
        return new CollAldernativBewerbung();
    }
}
