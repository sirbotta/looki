/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import it.malbot.greenbay.model.Auction;
import it.malbot.greenbay.model.Auction_Bid;
import it.malbot.greenbay.model.Category;
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
@ManagedBean(name = "dbmanager")
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

    public List<Auction> getAuctionByCategory(int category_id) throws SQLException {
        List<Auction> auctionList = new ArrayList<Auction>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.category_id = ?");

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
                + "WHERE LOWER(AUCTIONS.description) like ? "
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
                + "WHERE AUCTIONS.user_id = ? "
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

    public Auction findAuctionById(int auction_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "SELECT AUCTIONS.*,CATEGORIES.name as category_name,USERS.username as username "
                + "FROM AUCTIONS "
                + "INNER JOIN CATEGORIES on AUCTIONS.category_id=CATEGORIES.id "
                + "INNER JOIN USERS on AUCTIONS.user_id=USERS.id "
                + "WHERE AUCTIONS.category_id = ?");
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
}
