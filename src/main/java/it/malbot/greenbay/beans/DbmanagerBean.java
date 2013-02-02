/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Auction_Bid;
import it.malbot.greenbay.model.Category;
import it.malbot.greenbay.model.Sell;
import it.malbot.greenbay.model.User;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;

/**
 *
 * @author simone
 */
@ManagedBean(name = "dbmanager", eager = true)
@ApplicationScoped
public class DbmanagerBean implements Serializable {

    @Resource(name = "jdbc/greenbay")
    private DataSource ds;
    private Connection con;

    /**
     * Creates a new instance of dbmanagerBean
     */
    @PostConstruct
    public void connect() throws SQLException {
        if (ds == null) {
            throw new SQLException("Can't get data source");
        }

        //get database connection
        con = ds.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
    }

    public List<User> getUserList() throws SQLException {

        PreparedStatement ps = con.prepareStatement(
                "select id, username, password from USERS");

        //get customer data from database
        ResultSet result = ps.executeQuery();

        List<User> list = new ArrayList<User>();

        while (result.next()) {
            User u = new User();

            u.setId(result.getInt("id"));
            u.setUsername(result.getString("username"));
            u.setPassword(result.getString("password"));

            //store all data into a List
            list.add(u);
        }

        return list;
    }

    public User findUser(String username, String password) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM USERS WHERE username = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAddress(rs.getString("address"));
                    user.setMail(rs.getString("mail"));
                    user.setRegistration_date(rs.getTimestamp("registration_date"));
                    user.setAdmin_role(rs.getBoolean("admin_role"));
                    return user;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
    
    public User findUser(int user_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM USERS WHERE id = ?");
        try {
            stm.setInt(1, user_id);
            
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setAddress(rs.getString("address"));
                    user.setMail(rs.getString("mail"));
                    user.setRegistration_date(rs.getTimestamp("registration_date"));
                    user.setAdmin_role(rs.getBoolean("admin_role"));
                    return user;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }

    public String findUsernameById(int user_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT username FROM USERS WHERE id = ?");
        try {
            stm.setInt(1, user_id);

            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {

                    return rs.getString("username");
                } else {
                    return "Nessuno";
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }

    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<Category>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM CATEGORIES");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setDescription(rs.getString("description"));
                    categoryList.add(c);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return categoryList;
    }
    
     public List<Auction> getAuctionOpen() throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");
 

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }
    
    public List<Auction> getAuctionByCategory(int category_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.category_id = ? AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, category_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(category_id);
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByQuery(String query) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        if (query.isEmpty()) {
            return auctionList;
        }
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE LOWER(AUCTIONS.description) like ? AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setString(1, "%" + query.toLowerCase() + "%");

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByUserId(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.user_id = ? AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionClosedByUserId(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.user_id = ? AND AUCTIONS.closed = TRUE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByUserIdWithBids(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.user_id = ? AND AUCTIONS.winner_id IS NOT NULL AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByUserIdWithoutBids(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.user_id = ? AND AUCTIONS.winner_id IS NULL AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getOnDueAuction() throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByBidderId(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,"
                + "CATEGORIES.name as category_name,"
                + "USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES "
                + "on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS "
                + "on AUCTIONS.user_id=USERS.id "
                + "INNER JOIN "
                + "(SELECT DISTINCT auction_id,user_id from AUCTIONS_BIDS where user_id = ?) as BIDDERS "
                + "on BIDDERS.auction_id=AUCTIONS.id "
                + "WHERE AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByBidderIdWinner(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,"
                + "CATEGORIES.name as category_name,"
                + "USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES "
                + "on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS "
                + "on AUCTIONS.user_id=USERS.id "
                + "INNER JOIN "
                + "(SELECT DISTINCT auction_id,user_id from AUCTIONS_BIDS where user_id = ?) as BIDDERS "
                + "on BIDDERS.auction_id=AUCTIONS.id "
                + "WHERE AUCTIONS.winner_id=BIDDERS.user_id AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionByBidderIdLoser(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,"
                + "CATEGORIES.name as category_name,"
                + "USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES "
                + "on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS "
                + "on AUCTIONS.user_id=USERS.id "
                + "INNER JOIN "
                + "(SELECT DISTINCT auction_id,user_id from AUCTIONS_BIDS where user_id = ?) as BIDDERS "
                + "on BIDDERS.auction_id=AUCTIONS.id "
                + "WHERE AUCTIONS.winner_id!=BIDDERS.user_id AND AUCTIONS.closed = FALSE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction> getAuctionClosedByBidderId(int user_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,"
                + "CATEGORIES.name as category_name,"
                + "USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES "
                + "on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS "
                + "on AUCTIONS.user_id=USERS.id "
                + "INNER JOIN "
                + "(SELECT DISTINCT auction_id,user_id from AUCTIONS_BIDS where user_id = ?) as BIDDERS "
                + "on BIDDERS.auction_id=AUCTIONS.id "
                + "WHERE AUCTIONS.closed = TRUE "
                + "ORDER BY AUCTIONS.due_date ASC");

        stm.setInt(1, user_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));

                    auctionList.add(a);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionList;
    }

    public List<Auction_Bid> getAuctionBidByAuctionId(int auction_id) throws SQLException {
        List<Auction_Bid> auctionBidList = new ArrayList<Auction_Bid>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM AUCTIONS_BIDS WHERE auction_id = ? ORDER BY offer DESC");

        stm.setInt(1, auction_id);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction_Bid auction_bid = new Auction_Bid();

                    auction_bid.setId(rs.getInt("id"));
                    auction_bid.setAuction_id(rs.getInt("auction_id"));
                    auction_bid.setUser_id(rs.getInt("user_id"));
                    auction_bid.setBid_date(rs.getTimestamp("bid_date"));
                    auction_bid.setOffer(rs.getDouble("offer"));

                    auctionBidList.add(auction_bid);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionBidList;
    }
    
    public List<Auction_Bid> getAuctionBidByAuctionIdBelowOffer(int auction_id, double offer) throws SQLException {
        List<Auction_Bid> auctionBidList = new ArrayList<Auction_Bid>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM AUCTIONS_BIDS WHERE auction_id = ?  AND offer < ? ORDER BY offer DESC");

        stm.setInt(1, auction_id);
        stm.setDouble(2,offer);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Auction_Bid auction_bid = new Auction_Bid();

                    auction_bid.setId(rs.getInt("id"));
                    auction_bid.setAuction_id(rs.getInt("auction_id"));
                    auction_bid.setUser_id(rs.getInt("user_id"));
                    auction_bid.setBid_date(rs.getTimestamp("bid_date"));
                    auction_bid.setOffer(rs.getDouble("offer"));

                    auctionBidList.add(auction_bid);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return auctionBidList;
    }
    
    public List<Sell> getSell() throws SQLException{
        List<Sell> sellList = new ArrayList<Sell>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM SELLS ORDER BY SELLS.sell_date DESC");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Sell sell = new Sell();

                    sell.setId(rs.getInt("id"));
                    sell.setAuction_id(rs.getInt("auction_id"));
                    sell.setSeller_id(rs.getInt("seller_id"));
                    sell.setBuyer_id(rs.getInt("buyer_id"));
                    sell.setSell_date(rs.getTimestamp("sell_date"));
                    sell.setFinal_price(rs.getDouble("final_price"));
                    sell.setTax(rs.getDouble("tax"));

                    sellList.add(sell);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return sellList;
    }
    
    public List<Sell> getSellBySeller_id(int seller_id) throws SQLException{
        List<Sell> sellList = new ArrayList<Sell>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM SELLS WHERE SELLS.seller_id = ? ORDER BY SELLS.sell_date DESC");
        stm.setInt(1, seller_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Sell sell = new Sell();

                    sell.setId(rs.getInt("id"));
                    sell.setAuction_id(rs.getInt("auction_id"));
                    sell.setSeller_id(rs.getInt("seller_id"));
                    sell.setBuyer_id(rs.getInt("buyer_id"));
                    sell.setSell_date(rs.getTimestamp("sell_date"));
                    sell.setFinal_price(rs.getDouble("final_price"));
                    sell.setTax(rs.getDouble("tax"));

                    sellList.add(sell);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return sellList;
    }

    public Auction findAuctionById(int auction_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.id = ?");
        try {
            stm.setInt(1, auction_id);

            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    Auction a = new Auction();
                    a.setId(rs.getInt("id"));
                    a.setUser_id(rs.getInt("user_id"));
                    a.setUsername(rs.getString("username"));
                    a.setDescription(rs.getString("description"));
                    a.setCategory_id(rs.getInt("category_id"));
                    a.setCategory_name(rs.getString("category_name"));
                    a.setInitial_price(rs.getDouble("initial_price"));
                    a.setMin_increment(rs.getDouble("min_increment"));
                    a.setActual_price(rs.getDouble("actual_price"));
                    a.setWinner_id(rs.getInt("winner_id"));
                    a.setClosed(rs.getBoolean("closed"));
                    a.setUrl_image(rs.getString("url_image"));
                    a.setDelivery_price(rs.getDouble("delivery_price"));
                    a.setDue_date(rs.getTimestamp("due_date"));
                    a.setInsertion_date(rs.getTimestamp("insertion_date"));
                    return a;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }

    public int insertUser(User user) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO USERS (username,password,address,mail,admin_role) "
                + "VALUES (?,?,?,?,FALSE)");

        stm.setString(1, user.getUsername());
        stm.setString(2, user.getPassword());
        stm.setString(3, user.getAddress());
        stm.setString(4, user.getMail());

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public int insertBid(int user_id, int auction_id, double offer) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO AUCTIONS_BIDS (auction_id,user_id,offer) "
                + "VALUES (?,?,?)");

        stm.setInt(1, auction_id);
        stm.setInt(2, user_id);
        stm.setDouble(3, offer);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    //ritorna l'id se funziina oppure 0 se ha fallito
    public int insertAuction(Auction auction) throws SQLException {
        ResultSet gK;
        int key;
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO AUCTIONS (user_id,description,category_id,initial_price,min_increment,actual_price,url_image,delivery_price,due_date) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");
        
        
        

        stm.setInt(1, auction.getUser_id());
        stm.setString(2, auction.getDescription());
        stm.setInt(3, auction.getCategory_id());
        stm.setDouble(4, auction.getInitial_price());
        stm.setDouble(5, auction.getMin_increment());
        stm.setDouble(6, auction.getActual_price());
        stm.setString(7, auction.getUrl_image());
        stm.setDouble(8, auction.getDelivery_price());
        stm.setTimestamp(9, auction.getDue_date());
        
        PreparedStatement stmR = con.prepareStatement(
                "SELECT id FROM AUCTIONS "
                +"WHERE AUCTIONS.user_id = ? AND AUCTIONS.due_date = ? ");
        
        stmR.setInt(1, auction.getUser_id());
        stmR.setTimestamp(2, auction.getDue_date());
        
        try {
            stm.executeUpdate();
            gK = stmR.executeQuery();
            if (gK.next()) {
                key = gK.getInt(1);
                return key;
            } else {
                return 0;
            }
        } finally {
            stm.close();
        }
    }

    public int updateAuction(int auction_id, int winner_id, double actual_price) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "UPDATE AUCTIONS "
                + "SET actual_price = ?, winner_id=? "
                + "WHERE id= ?");
        stm.setDouble(1, actual_price);
        stm.setInt(2, winner_id);
        stm.setInt(3, auction_id);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
    public int insertSell(Sell sell) throws SQLException{
        ResultSet gK;
        int key;
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO SELLS (seller_id,buyer_id,auction_id,final_price,tax) "
                + "VALUES (?,?,?,?,?)");
        

        stm.setInt(1, sell.getSeller_id());
        stm.setInt(2, sell.getBuyer_id());
        stm.setInt(3, sell.getAuction_id());
        stm.setDouble(4, sell.getFinal_price());
        stm.setDouble(5, sell.getTax());
        
        
        PreparedStatement stmR = con.prepareStatement(
                "SELECT id FROM SELLS "
                +"WHERE SELLS.seller_id = ? AND SELLS.buyer_id = ? AND SELLS.auction_id = ? ");
        
        stmR.setInt(1, sell.getSeller_id());
        stmR.setInt(2, sell.getBuyer_id());
        stmR.setInt(3, sell.getAuction_id());
        
        try {
            stm.executeUpdate();
            gK = stmR.executeQuery();
            if (gK.next()) {
                key = gK.getInt(1);
                return key;
            } else {
                return 0;
            }
        } finally {
            stm.close();
        }
    }
    
    public int closeAuction(int auction_id) throws SQLException{
        PreparedStatement stm = con.prepareStatement(
                "UPDATE AUCTIONS "
                + "SET closed = TRUE "
                + "WHERE id= ?");
        stm.setInt(1, auction_id);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    

    //trova la puntata massima
    public Auction_Bid findMaxAuctionBidByAuctionID(int auction_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM AUCTIONS_BIDS WHERE auction_id = ? ORDER BY offer DESC");

        try {
            stm.setInt(1, auction_id);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {

                    Auction_Bid auction_bid = new Auction_Bid();

                    auction_bid.setId(rs.getInt("id"));
                    auction_bid.setAuction_id(rs.getInt("auction_id"));
                    auction_bid.setUser_id(rs.getInt("user_id"));
                    auction_bid.setBid_date(rs.getTimestamp("bid_date"));
                    auction_bid.setOffer(rs.getDouble("offer"));


                    return auction_bid;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }

    }

    //trova la puntata massima di un utente e un asta
    public Auction_Bid findMaxAuctionBidByAuctionIDAndByUserId(int auction_id, int user_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT * FROM AUCTIONS_BIDS WHERE auction_id = ? AND user_id = ? ORDER BY offer DESC");

        try {
            stm.setInt(1, auction_id);
            stm.setInt(2, user_id);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {

                    Auction_Bid auction_bid = new Auction_Bid();

                    auction_bid.setId(rs.getInt("id"));
                    auction_bid.setAuction_id(rs.getInt("auction_id"));
                    auction_bid.setUser_id(rs.getInt("user_id"));
                    auction_bid.setBid_date(rs.getTimestamp("bid_date"));
                    auction_bid.setOffer(rs.getDouble("offer"));


                    return auction_bid;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }

    }
}
