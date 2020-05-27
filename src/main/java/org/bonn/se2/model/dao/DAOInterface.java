package org.bonn.se2.model.dao;

import java.util.List;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public interface DAOInterface<T> {

    T retrieve(int id) throws Exception;

    T retrieve(String attribute) throws Exception;

    List<T> retrieveAll() throws Exception;

    T create(T dto) throws Exception;

    T update(T item) throws Exception;

    T delete(T item) throws Exception;

}
