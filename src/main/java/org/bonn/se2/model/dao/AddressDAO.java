package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddressDAO extends AbstractDAO<Address> implements DAOInterface<Address>{

    public AddressDAO() throws DatabaseException {
    }

    @Override
    public Address retrieve(int id) throws DatabaseException {
        //language=PostgreSQL
        final String sql =
                "SELECT strasse, hausnummer, plz, stadt, land, \"addressID\" " +
                "FROM \"collDB\".address " +
                "WHERE \"addressID\" = '" + id +"';";

        List<Address> result = execute(sql);
        if (result.size() < 1) {
            throw new DatabaseException("retrieve(int id) in AddressDAO failed");
        }
        return result.get(0);
    }

    @Override
    public Address retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Address> retrieveAll() throws DatabaseException  {
        //language=PostgreSQL
        final String sql =
                "SELECT strasse, hausnummer, plz, stadt, land, \"addressID\" " +
                "FROM \"collDB\".address;";
        return execute(sql);
    }

    @Override
    public Address create(Address dto) throws DatabaseException {
        //language=PostgreSQL
        String query = "INSERT INTO \"collDB\".address " +
                "VALUES (?, ?, ?, ?, ?, DEFAULT) RETURNING strasse, hausnummer, plz, stadt, land, \"addressID\";";

        List<Address> list = executePrepared(query,
                dto.getStrasse(),
                dto.getHausnummer(),
                dto.getPlz(),
                dto.getStadt(),
                dto.getLand()
        );

        if (list.size() == 0)
            throw new DatabaseException("create(Address dto) in AddressDAO failed");
        return list.get(0);
    }

    @Override
    protected Address create(ResultSet resultSet) throws DatabaseException {
        Address dto;
        try {
            dto = new Address();
            dto.setStrasse(resultSet.getString("strasse"));
            dto.setHausnummer((resultSet.getString("hausnummer")));
            dto.setPlz(resultSet.getString("plz"));
            dto.setStadt(resultSet.getString("stadt"));
            dto.setLand(resultSet.getString("land"));
            dto.setID(resultSet.getInt("addressID"));

        } catch (SQLException e) {
            throw new DatabaseException("[" + AddressDAO.class.toString() + "] Error creating Address: " + e.getMessage());
        }
        return dto;
    }


    @Override
    public Address update(Address item) throws Exception {
        return null;
    }

    @Override
    public Address delete(Address address) throws Exception {
        //language=PostgreSQL
        final String sql =
                "DELETE FROM \"collDB\".address " +
                "WHERE \"addressID\" = ? " +
                "RETURNING *;";
        
        List<Address> list = executePrepared(sql, address.getID());
        if (list.size() == 0)
            throw new DatabaseException("delete(Address address) in AddressDAO failed");
        return list.get(0);
    }
}
