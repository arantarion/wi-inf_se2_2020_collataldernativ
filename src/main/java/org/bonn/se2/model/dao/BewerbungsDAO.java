package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Bewerbung;
import org.bonn.se2.model.objects.dto.JobOffer;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.util.List;

public class BewerbungsDAO extends AbstractDAO<JobOffer> implements DAOInterface<Bewerbung>{

    public BewerbungsDAO() throws DatabaseException {
    }

    @Override
    protected JobOffer create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Bewerbung retrieve(int id) throws Exception {
        return null;
    }

    @Override
    public Bewerbung retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<Bewerbung> retrieveAll() throws Exception {
        return null;
    }

    @Override
    public Bewerbung create(Bewerbung dto) throws Exception {
        return null;
    }

    @Override
    public Bewerbung update(Bewerbung item) throws Exception {
        return null;
    }

    @Override
    public Bewerbung delete(Bewerbung item) throws Exception {
        return null;
    }
}
