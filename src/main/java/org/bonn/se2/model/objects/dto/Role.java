package org.bonn.se2.model.objects.dto;

import java.io.Serializable;

public class Role implements Serializable {

    private String bezeichnung = null;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

}
