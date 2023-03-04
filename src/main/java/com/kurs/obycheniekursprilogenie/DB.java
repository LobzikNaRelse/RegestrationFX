package com.kurs.obycheniekursprilogenie;

import java.sql.*;

public class DB
{
    private final String HOST = "localhost"; // заменить на свое
    private final String PORT = "3307"; // заменить на свое
    private final String DBNAME = "kurs-prilogenie"; // заменить на свое
    private final String LOGIN = "root"; // заменить на свое
    private final String PASS = ""; // заменить на свое

    private Connection dbConn = null;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connectionString = "jdbc:mysql://" + HOST + ":"
                + PORT + "/" + DBNAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connectionString, LOGIN,
                PASS);
        return dbConn;
    }

    public void isConnected () throws SQLException, ClassNotFoundException // функция
    {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public void changeUser(String login, String email, String password)
    {
        String sql = "UPDATE `users` SET `login` = ?, `email` = ?, `password` = ? WHERE " +
                "`login` = ?";
        try
        {
            User user1 = getUser();
            PreparedStatement prst = getDbConnection().prepareStatement(sql);
            prst.setString(1, login);
            prst.setString(2, email);
            prst.setString(3, password);
            prst.setString(4, user1.getLogin());
            prst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}
    }

    public User getUser()
    {
        String select = "SELECT * FROM `users` WHERE `login` = 'Admin'";
        User user = new User();
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet res = statement.executeQuery(select);
            res.next();
            user.login = res.getString("login");
            user.email = res.getString("email");
            user.password = res.getString("password");
            return user;
        } catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}

        return null;
    }

    public boolean thisExistUser(String login)
    {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ?";
        try
        {
            PreparedStatement prst = getDbConnection().prepareStatement(sql);
            prst.setString(1, login);
            ResultSet res = prst.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}
        return false;
    }

}
