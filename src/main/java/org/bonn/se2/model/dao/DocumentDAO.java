package org.bonn.se2.model.dao;


import org.bonn.se2.model.objects.dto.Document;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class DocumentDAO extends AbstractDAO<Document> implements DAOInterface<Document>{

    protected DocumentDAO() throws DatabaseException {
    }

    @Override
    public Document retrieve(int fileID) throws Exception {
        //language=PostgreSQL
        final String sql =
                "SELECT * FROM \"collDB\".file " +
                        "WHERE file.\"fileID\" = '" + fileID + "';";

        List<Document> result = execute(sql);
        if (result.size() < 1) {
            throw new InvalidCredentialsException();
        }
        Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO, "Das Document-Objekt mit der fileID: " + fileID + " wurde abgerufen.");
        return result.get(0);
    }

    @Override
    public Document retrieve(String attribute) throws Exception {
        return null;
    }

    @Override
    public List<Document> retrieveAll() throws Exception {
        final String sql = "SELECT * FROM \"collDB\".file";
        Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO, "Alle Documents wurden abgerufen.");
        return execute(sql);
    }

    @Override
    public Document create(Document dto) throws Exception {
        return null;
    }

    @Override
    protected Document create(ResultSet resultSet) throws DatabaseException {
        return null;
    }

    @Override
    public Document update(Document item) throws Exception {
        return null;
    }

    @Override
    public Document delete(Document file) throws Exception {
        //language=PostgreSQL
        final String deleteQuery = " DELETE FROM \"collDB\".file WHERE \"fileID\" = " + file.getDocumentID() + " RETURNING * ";
        List<Document> result = execute(deleteQuery);
        if (result.size() < 1) {
            throw new DatabaseException("delete(Document file) failed");
        }
        return result.get(0);
    }

    public Document delete(int fileID) throws Exception{
        //language=PostgreSQL
        final String deleteQuery = "DELETE FROM \"collDB\".file WHERE \"fileID\" = " + fileID + " RETURNING *";
        List<Document> result = execute(deleteQuery);
        if (result.size() < 1){
            throw new DatabaseException("delete(int fileID) failed");
        }
        return result.get(0);
    }
}
