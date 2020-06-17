package org.bonn.se2.model.dao;


import org.bonn.se2.model.objects.dto.Document;
import org.bonn.se2.process.control.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class DocumentDAO extends AbstractDAO<Document> implements DAOInterface<Document>{

    protected DocumentDAO() throws DatabaseException {
    }

    @Override
    protected Document create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Document retrieve(int id) throws Exception {
        return null;
    }

    @Override
    public Document retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<Document> retrieveAll() throws Exception {
        return null;
    }

    @Override
    public Document create(Document dto) throws Exception {
        return null;
    }

    @Override
    public Document update(Document item) throws Exception {
        return null;
    }

    @Override
    public Document delete(Document item) throws Exception {
        return null;
    }
}
