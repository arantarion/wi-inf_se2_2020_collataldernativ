package org.bonn.se2.model.dao;


import org.bonn.se2.model.objects.dto.Document;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;
import org.bonn.se2.process.control.exceptions.InvalidCredentialsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Anton Drees
 */

public class DocumentDAO extends AbstractDAO<Document> implements DAOInterface<Document> {

    public DocumentDAO() throws DatabaseException {
    }

    @Override
    public Document retrieve(int fileID) throws DatabaseException, InvalidCredentialsException {
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
    public Document retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Document> retrieveAll() throws DatabaseException {
        //language=PostgreSQL
        final String sql = "SELECT * FROM \"collDB\".file";
        Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO, "Alle Documents wurden abgerufen.");
        return execute(sql);
    }

    @Override
    public Document create(Document file) throws DatabaseException, SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO \"collDB\".file (\"fileID\", title, \"userID\", \"uploadDatum\", file, \"desc\") " +
                "VALUES ('" + file.getDocumentID() + "','" + file.getTitle() +
                "','" + file.getUserID() + "', '" + file.getDate() +
                "', '" + file.getFile() + "', '" + file.getDesc() + "') " +
                "RETURNING *";
        PreparedStatement pst = this.getPreparedStatement(sql);
        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            Document dto = new Document();
            dto.setDocumentID(resultSet.getInt("fileID"));
            dto.setTitle(resultSet.getString("title"));
            dto.setUserID(resultSet.getInt("userID"));
            dto.setDate(new java.sql.Date(resultSet.getDate("uploadDatum").getTime()).toLocalDate());
            dto.setFile(resultSet.getBytes("file"));
            dto.setDesc(resultSet.getString("desc"));
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO, "Document-Objekt: " + file + "wurde erfolgreich gespeichert.");
            return dto;
        } else {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, "Document-Objekt: " + file + "konnte nicht richtig gespeichert werden.");
            return null;
        }
    }

    @Override
    protected Document create(ResultSet resultSet) {
        Document file = new Document();
        try {
            file.setDocumentID(resultSet.getInt("fileID"));
            file.setTitle(resultSet.getString("title"));
            file.setUserID(resultSet.getInt("userID"));
            file.setDate(new java.sql.Date(resultSet.getDate("uploadDatum").getTime()).toLocalDate());
            file.setFile(resultSet.getBytes("file"));
            file.setDesc(resultSet.getString("desc"));
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO, "Document-Objekt: " + file + "wurde erfolgreich gespeichert.");
        } catch (SQLException e) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, "create(ResultSet resultSet) in DocumentDAO failed", e);
        }
        return file;
    }

    @Override
    public Document update(Document item) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Document delete(Document file) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery = " DELETE FROM \"collDB\".file WHERE \"fileID\" = " + file.getDocumentID() + " RETURNING * ";
        List<Document> result = execute(deleteQuery);
        if (result.size() < 1) {
            throw new DatabaseException("delete(Document file) failed");
        }
        return result.get(0);
    }

    public Document delete(int fileID) throws DatabaseException {
        //language=PostgreSQL
        final String deleteQuery = "DELETE FROM \"collDB\".file WHERE \"fileID\" = " + fileID + " RETURNING *";
        List<Document> result = execute(deleteQuery);
        if (result.size() < 1) {
            throw new DatabaseException("delete(int fileID) failed");
        }
        return result.get(0);
    }
}
