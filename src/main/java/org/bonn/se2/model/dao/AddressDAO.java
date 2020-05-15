package org.bonn.se2.model.dao;

import org.bonn.se2.model.objects.dto.Address;
import org.bonn.se2.process.control.exceptions.DatabaseException;
import org.bonn.se2.process.control.exceptions.DontUseException;

import java.sql.ResultSet;
import java.util.List;

public class AddressDAO extends AbstractDAO<Address> implements DAOInterface<Address>{

    public AddressDAO() throws DatabaseException {
    }

    @Override
    public Address retrieve(int id) throws DatabaseException {
        final String sql =
                "SELECT strasse, hausnummer, plz, stadt, land, addressID\n" +
                "FROM \"CollDB\".address\n" +
                "WHERE addressID = ?;";

        List<Address> result = executePrepared(sql, id);
        if (result.size() < 1) {
            throw new DatabaseException("Something's wrong I can feel it");
        }
        return result.get(0);
    }

    @Override
    public Address retrieve(String attribute) throws DontUseException {
        throw new DontUseException();
    }

    @Override
    public List<Address> retrieveAll() throws DatabaseException  {
        final String sql =
                "SELECT strasse, hausnummer, plz, stadt, land, addressID\n" +
                "FROM \"CollDB\".address;";
        return execute(sql);
    }

    @Override
    public Address create(Address dto) throws Exception {
        return null;
    }

    @Override
    protected Address create(ResultSet resultSet) throws DatabaseException {
        return null;
    }


    @Override
    public Address update(Address item) throws Exception {
        return null;
    }

    @Override
    public Address delete(Address address) throws Exception {
        final String sql =
                "DELETE FROM \"CollDB\".address\n" +
                "WHERE addressID = ? " +
                "RETURNING *;";
        
        List<Address> list = executePrepared(sql, address.getID());
        if (list.size() == 0)
            throw new DatabaseException("There are no mistakes. Just happy little accidents!");
        return list.get(0);
    }
}
