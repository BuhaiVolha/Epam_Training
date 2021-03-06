package by.epam.cattery.dao.impl;

import by.epam.cattery.dao.CatDAO;
import by.epam.cattery.dao.connection.ConnectionPool;
import by.epam.cattery.dao.connection.ConnectionPoolException;
import by.epam.cattery.dao.exception.DAOException;
import by.epam.cattery.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatDAOImpl implements CatDAO {
    private static final Logger logger = LogManager.getLogger(CatDAOImpl.class);
    private final ConnectionPool connectionPool;


    private static final String FIND_ALL_CATS = "SELECT cat_id, name, lastname, gender, " +
            "MONTH(CURDATE()) - MONTH(birth_date), description," +
            "colour_name, eyes_colour_name, parent_female, parent_male, price, " +
            "sale_status_id, user_suggested_id FROM cat JOIN cat_colour " +
            "ON (cat.body_colour_code = cat_colour.EMS_code) " +
            "LEFT JOIN cat_eyes_colour ON (cat.cat_eyes_colour_code " +
            "= cat_eyes_colour.FIFe_eyes_colour_code)";

    private static final String FIND_ALL_CATS_WITH_DISCOUNT = "SELECT cat_id, name, lastname, gender, " +
            "MONTH(CURDATE()) - MONTH(birth_date), description," +
            "colour_name, eyes_colour_name, parent_female, parent_male, price, " +
            "sale_status_id, user_suggested_id, price - (price * (SELECT discount FROM user WHERE  user_id = (?))) / 100" +
            " FROM cat JOIN cat_colour ON (cat.body_colour_code = cat_colour.EMS_code) " +
            "LEFT JOIN cat_eyes_colour ON (cat.cat_eyes_colour_code " +
            "= cat_eyes_colour.FIFe_eyes_colour_code)";

    private static final String ADD_CAT = "INSERT INTO cat (name, lastname, gender, birth_date, description, body_colour_code, " +
            "cat_eyes_colour_code, parent_female, parent_male, price,  user_suggested_id, offer_made_id) " +
            "VALUES(?,?,?,STR_TO_DATE(?, '%m/%d/%Y'),?,?,?,?,?,?,?,?)";

    private static final String UPDATE_OFFER_STATUS_FOR_USER = "UPDATE user_offer SET user_offer_status_id=? " +
            "WHERE offer_id = ? AND user_offer_status_id=?;";

    private static final String DELETE_CERTAIN_CAT = "DELETE FROM cat WHERE cat_id = ?;";

    private static final String CONDITION_BY_STATUS = " WHERE sale_status_id = ?";

    private static final String CONDITION_BY_CAT = " WHERE cat_id = ?";




    public CatDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public List<Cat> findAllCats() throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cat cat = new Cat();

                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11));
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));

                cats.add(cat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during gathering cats; You must gather your cats before venturing forth", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public List<Cat> findAllCatsByStatus(CatStatus status) throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS + CONDITION_BY_STATUS);
            ps.setString(1, status.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Cat cat = new Cat();

                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11));
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));

                cats.add(cat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during gathering cats; You must gather your cats before venturing forth", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public List<Cat> findAllCatsWithDiscount(int userId) throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS_WITH_DISCOUNT);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cat cat = new Cat();

                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11));
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));
                cat.setPriceWithDiscount(rs.getDouble(14));

                cats.add(cat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during gathering cats with discount; You must gather your cats before venturing forth", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public List<Cat> findAllCatsByStatusWithDiscount(int userId, CatStatus status) throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS_WITH_DISCOUNT + CONDITION_BY_STATUS);
            ps.setInt(1, userId);
            ps.setString(2, status.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Cat cat = new Cat();

                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11));
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));
                cat.setPriceWithDiscount(rs.getDouble(14));

                cats.add(cat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during gathering cats by status; You must gather your cats before venturing forth", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public void addCat(Cat cat) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2;

        try {
            con = connectionPool.takeConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(ADD_CAT);

            ps.setString(1, cat.getName());
            ps.setString(2, cat.getLastname());
            ps.setString(3, cat.getGender().toString());
            ps.setString(4, cat.getAge());
            ps.setString(5, cat.getDescription());
            ps.setString(6, cat.getBodyColour());
            ps.setString(7, cat.getEyesColour());
            ps.setString(8, cat.getFemaleParent());
            ps.setString(9, cat.getMaleParent());
            ps.setDouble(10, cat.getPrice());
            ps.setInt(11, cat.getUserMadeOfferId());
            ps.setInt(12, cat.getOfferMadeId());

            ps.executeUpdate();

            if (cat.getUserMadeOfferId() != 1) {

                ps2 = con.prepareStatement(UPDATE_OFFER_STATUS_FOR_USER);
                ps2.setString(1, OfferStatus.SENT.toString());
                ps2.setInt(2, cat.getOfferMadeId());
                ps2.setString(3, OfferStatus.APRVD.toString());

                int i = ps2.executeUpdate();

                if (i != 1) {
                    logger.log(Level.WARN, "No more cats! An attempt to add already added cat during sending an offer by admin");
                    throw new DAOException("Attempt to add already processed offer ");
                }
            }

            con.commit();

        } catch (ConnectionPoolException | SQLException e) {
            try {
                con.rollback();
                throw new DAOException("Exception during adding cat", e);

            } catch (SQLException e1) {

                throw new DAOException("Exception while rollback after error during adding", e);
            } finally {
                connectionPool.closeConnection(con, ps);
            }
        }
    }


    @Override
    public Cat findSingleCat(int catId) throws DAOException {
        Cat cat = new Cat();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS + CONDITION_BY_CAT);
            ps.setInt(1, catId);
            rs = ps.executeQuery();

            while (rs.next()) {
                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));  //????
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11)); // int?
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));
            }
            return cat;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during finding single cats", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public Cat findSingleCatWithDiscount(int catId, int userId) throws DAOException {
        Cat cat = new Cat();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(FIND_ALL_CATS_WITH_DISCOUNT + CONDITION_BY_CAT);
            ps.setInt(1, userId);
            ps.setInt(2, catId);

            rs = ps.executeQuery();

            while (rs.next()) {
                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));
                cat.setLastname(rs.getString(3));
                cat.setGender(Gender.valueOf(rs.getString(4)));  //????
                cat.setAge(rs.getString(5));   // отдельные константы числа
                cat.setDescription(rs.getString(6));
                cat.setBodyColour(rs.getString(7));
                cat.setEyesColour(rs.getString(8));
                cat.setFemaleParent(rs.getString(9));
                cat.setMaleParent(rs.getString(10));
                cat.setPrice(rs.getDouble(11)); // int?
                cat.setStatus(CatStatus.valueOf(rs.getString(12)));
                cat.setUserMadeOfferId(rs.getInt(13));
                cat.setPriceWithDiscount(rs.getDouble(14));
            }
            return cat;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during finding single cats", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }


    @Override
    public void deleteCat(int catId) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = connectionPool.takeConnection();

            ps = con.prepareStatement(DELETE_CERTAIN_CAT);
            ps.setInt(1, catId);
            ps.executeUpdate();

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception while deleting cat from bd ", e);

        } finally {
            connectionPool.closeConnection(con, ps);
        }
    }


    @Override
    public void updateCat(Cat cat) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = connectionPool.takeConnection();
            ps = con.prepareStatement("UPDATE cat SET name=?, lastname=?, gender=?, birth_date=STR_TO_DATE(?, '%m/%d/%Y')," +
                    " description=?, body_colour_code=?, cat_eyes_colour_code=?, parent_female=?, " +
                    "parent_male=?, price=? WHERE cat_id = ?;");

            ps.setString(1, cat.getName());
            ps.setString(2, cat.getLastname());
            ps.setString(3, cat.getGender().toString());
            ps.setString(4, cat.getAge());
            ps.setString(5, cat.getDescription());
            ps.setString(6, cat.getBodyColour());
            ps.setString(7, cat.getEyesColour());
            ps.setString(8, cat.getFemaleParent());
            ps.setString(9, cat.getMaleParent());
            ps.setDouble(10, cat.getPrice());
            ps.setDouble(11, cat.getId());

            ps.executeUpdate();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);

        } catch (SQLException e) {
            throw new DAOException("Exception during updating cat info", e);

        } finally {
            connectionPool.closeConnection(con, ps);
        }
    }


    @Override
    public List<Cat> searchForCat(Cat cat) throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();
//FIND_ALL_CATS replace
            String SEARCH_FOR_CAT = "SELECT cat_id, name, lastname, gender, " +
                    "MONTH(CURDATE()) - MONTH(birth_date), description," +
                    "colour_name, eyes_colour_name, parent_female, parent_male, price, " +
                    "sale_status_id, user_suggested_id FROM cat JOIN cat_colour " +
                    "ON (cat.body_colour_code = cat_colour.EMS_code) " +
                    "LEFT JOIN cat_eyes_colour ON (cat.cat_eyes_colour_code " +
                    "= cat_eyes_colour.FIFe_eyes_colour_code) WHERE ";

            StringBuffer condition = new StringBuffer();

            if (cat.getGender() != null) {
                condition.append(" gender = '" + cat.getGender().toString() + "' AND ");
            }
            if (cat.getStatus() != null) {
                condition.append(" sale_status_id = '" + cat.getStatus().toString() + "' AND ");
            }

            if (!cat.getBodyColour().isEmpty()) {
                condition.append(" body_colour_code = '" + cat.getBodyColour() + "' AND ");
            }

            if (!cat.getEyesColour().isEmpty()) {
                condition.append(" cat_eyes_colour_code = '" + cat.getEyesColour() + "' AND ");
            }

            if (cat.getPrice() != 0.0) {
                condition.append(" price <= " + cat.getPrice() + " AND ");
            }
            condition.append(" 1 = 1;");

            ps = con.prepareStatement(SEARCH_FOR_CAT + condition);

            rs = ps.executeQuery();

            while (rs.next()) {
                Cat foundCat = new Cat();

                foundCat.setId(rs.getInt(1));
                foundCat.setName(rs.getString(2));
                foundCat.setLastname(rs.getString(3));
                foundCat.setGender(Gender.valueOf(rs.getString(4)));
                foundCat.setAge(rs.getString(5));   // отдельные константы числа
                foundCat.setDescription(rs.getString(6));
                foundCat.setBodyColour(rs.getString(7));
                foundCat.setEyesColour(rs.getString(8));
                foundCat.setFemaleParent(rs.getString(9));
                foundCat.setMaleParent(rs.getString(10));
                foundCat.setPrice(rs.getDouble(11));
                foundCat.setStatus(CatStatus.valueOf(rs.getString(12)));
                foundCat.setUserMadeOfferId(rs.getInt(13));

                cats.add(foundCat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during searching for cat", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }

// повторяется - в один метод
    // убрать user_suggested_id
    // цену сделать необязат

    @Override
    public List<Cat> searchForCatWithDiscount(Cat cat, int userId) throws DAOException {
        List<Cat> cats = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = connectionPool.takeConnection();
//FIND_ALL_CATS replace

            String SEARCH_FOR_CAT_WITH_DISCOUNT = "SELECT cat_id, name, lastname, gender, " +
                    "MONTH(CURDATE()) - MONTH(birth_date), description," +
                    "colour_name, eyes_colour_name, parent_female, parent_male, price, " +
                    "sale_status_id, user_suggested_id, price - (price * (SELECT discount FROM user WHERE user_id = (?))) / 100" +
                    " FROM cat JOIN cat_colour ON (cat.body_colour_code = cat_colour.EMS_code) " +
                    "LEFT JOIN cat_eyes_colour ON (cat.cat_eyes_colour_code " +
                    "= cat_eyes_colour.FIFe_eyes_colour_code) WHERE";

            StringBuffer condition = new StringBuffer();

            if (cat.getGender() != null) {
                condition.append(" gender = '" + cat.getGender().toString() + "' AND ");
            }
            if (cat.getStatus() != null) {
                condition.append(" sale_status_id = '" + cat.getStatus().toString() + "' AND ");
            }

            if (!cat.getBodyColour().isEmpty()) {
                condition.append(" body_colour_code = '" + cat.getBodyColour() + "' AND ");
            }

            if (!cat.getEyesColour().isEmpty()) {
                condition.append(" cat_eyes_colour_code = '" + cat.getEyesColour() + "' AND ");
            }

            if (cat.getPrice() != 0.0) {
                condition.append(" price - (price * (SELECT discount FROM user WHERE user_id = "
                        + userId + ")) / 100 <= " + cat.getPrice() + " AND ");
            }
            condition.append(" 1 = 1;");

            ps = con.prepareStatement(SEARCH_FOR_CAT_WITH_DISCOUNT + condition);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Cat foundCat = new Cat();

                foundCat.setId(rs.getInt(1));
                foundCat.setName(rs.getString(2));
                foundCat.setLastname(rs.getString(3));
                foundCat.setGender(Gender.valueOf(rs.getString(4)));
                foundCat.setAge(rs.getString(5));   // отдельные константы числа
                foundCat.setDescription(rs.getString(6));
                foundCat.setBodyColour(rs.getString(7));
                foundCat.setEyesColour(rs.getString(8));
                foundCat.setFemaleParent(rs.getString(9));
                foundCat.setMaleParent(rs.getString(10));
                foundCat.setPrice(rs.getDouble(11));
                foundCat.setStatus(CatStatus.valueOf(rs.getString(12)));
                foundCat.setUserMadeOfferId(rs.getInt(13));
                foundCat.setPriceWithDiscount(rs.getDouble(14));

                cats.add(foundCat);
            }
            return cats;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception while connecting via pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during searching for cat with discount", e);
        } finally {
            connectionPool.closeConnection(con, ps, rs);
        }
    }
}