package com.jrott.java.jdbc.DAO;

public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();

    private final UsuariosDAO usuariosDAO;
    private final UserRoleDAO userRoleDAO;
    private final FacturasDAO facturasDAO;

    private DAOFactory() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");

        usuariosDAO = new MySQLUsuariosDAO(connection);
        userRoleDAO = new MySQLUserRoleDAO(connection);
        facturasDAO = new MySQLFacturasDAO(connection);
    }

    public static DAOFactory getInstance() throws SQLException {
        return INSTANCE;
    }

    public UsuariosDAO getUsuariosDAO() {
        return usuariosDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return userRoleDAO;
    }

    public FacturasDAO getFacturasDAO() {
        return facturasDAO;
    }
}
