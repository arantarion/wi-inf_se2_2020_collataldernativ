package org.bonn.se2.model.objects.dto;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class CollAldernativDTOFactory extends AbstractDTOFactory {

    @Override
    public Bewerbung createBewerbung() {
        return new CollAldernativBewerbung();
    }

}
