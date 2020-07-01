package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Rating;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingDAO extends AbstractDAO<Rating> implements DAOInterface<Rating> {


    public RatingDAO() throws DatabaseException {
        super();
    }

    @Override
    protected Rating create(ResultSet resultSet) {
        return null;
    }

    @Override
    public Rating retrieve(int id) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Rating retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Rating> retrieveAll() throws DatabaseException, SQLException {
        final String sql = "SELECT * FROM \"collDB\".rating";
        PreparedStatement statement = getPreparedStatement(sql);
        ResultSet set = statement.executeQuery();
        List<Rating> ratings = new ArrayList<>();
        while (set.next()) {

            Rating bewertung = new Rating();
            bewertung.setUserId(set.getInt("userid"));
            bewertung.setCompanyId(set.getInt("companyid"));
            bewertung.setRating(set.getInt("rating"));
            bewertung.setComment(set.getString("comment"));
            bewertung.setDate(new Date(set.getDate("date").getTime()));
            ratings.add(bewertung);
        }
        return ratings;
    }

    public List<Rating> retrieveAllByCompany(Integer companyId) throws DatabaseException, SQLException {
        final String sql = "SELECT * FROM \"collDB\".rating WHERE companyid = ?";
        PreparedStatement statement = getPreparedStatement(sql);
        statement.setInt(1, companyId);
        ResultSet set = statement.executeQuery();
        List<Rating> ratings = new ArrayList<>();
        while (set.next()) {

            Rating rating = new Rating();
            rating.setUserId(set.getInt("userid"));
            rating.setCompanyId(set.getInt("companyid"));
            rating.setRating(set.getInt("rating"));
            rating.setComment(set.getString("comment"));
            rating.setDate(new Date(set.getDate("date").getTime()));
            ratings.add(rating);
        }
        return ratings;
    }

    public boolean isExist(int companyId, int userId) throws DatabaseException {
        final String sql = "SELECT * FROM \"collDB\".rating WHERE companyid = ? AND userid = ?";
        List<Rating> ratings = executePrepared(sql, companyId, userId);
        return !ratings.isEmpty();
    }

    @Override
    public Rating create(Rating rating) throws DatabaseException, SQLException {
        final String insertQuery = "INSERT INTO \"collDB\".rating (userid, companyid, rating, comment, date) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING *";

        Date today = new Date();

        PreparedStatement statement = getPreparedStatement(insertQuery);
        statement.setInt(1, rating.getUserId());
        statement.setInt(2, rating.getCompanyId());
        statement.setInt(3, rating.getRating());
        statement.setString(4, rating.getComment());
        statement.setDate(5, new java.sql.Date(today.getTime()));
        ResultSet set = statement.executeQuery();
        List<Rating> ratings = new ArrayList<>();

        while (set.next()) {
            Rating savedRating = new Rating();
            savedRating.setUserId(set.getInt("userid"));
            savedRating.setCompanyId(set.getInt("companyid"));
            savedRating.setRating(set.getInt("rating"));
            savedRating.setComment(set.getString("comment"));
            savedRating.setDate(new Date(set.getDate("date").getTime()));
            ratings.add(rating);
        }

        if (ratings.isEmpty()) {
            throw new DatabaseException("create(User user) did not return a DTO");
        } else {
            return ratings.get(0);
        }
    }

    @Override
    public Rating update(Rating item) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public Rating delete(Rating item) throws DontUseException {
        throw new DontUseException();
    }
}
