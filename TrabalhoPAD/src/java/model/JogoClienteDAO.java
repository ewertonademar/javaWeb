package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JogoClienteDAO {

    private static final String SQL_INSERIR_JOGOCLIENTE = "insert into jogocliente (notaUsuario, favoritar, idUsuario, idJogo) values(?,?,?,?)";
    private static final String SQL_LISTAR_JOGOSCLIENTES_USUARIO = "select jc.*, j.nomeJogo, j.plataforma from jogoCliente as jc inner join jogo as j on (jc.idJogo = j.idJogo) where jc.idUsuario = ?";
    private static final String SQL_LISTAR_JOGOSCLIENTES_JOGO = "select * from jogocliente where idJogo = ? order by idUsuario";
    private static final String SQL_ALTERAR_JOGOCLIENTE = "update jogocliente set notaUsuario=?, favoritar=?, idUsuario=?, idJogo=? where idUsuario = ? and idJogo = ?";
    private static final String SQL_CONSULTAR_JOGOCLIENTE = "select * from jogoCliente where idUsuario = ? and idJogo = ?";
    private static final String SQL_EXCLUIR_JOGOCLIENTE = "delete from jogocliente where jogoCliente = ?";

    private Connection connection;

    public JogoCliente adicionar(Object obj) throws SQLException {
        JogoCliente jc = (JogoCliente) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_INSERIR_JOGOCLIENTE, Statement.RETURN_GENERATED_KEYS);
                stmt.setDouble(1, jc.getNota());
                stmt.setBoolean(2, jc.getFavorito());
                stmt.setLong(3, jc.getIdUsuario());
                stmt.setLong(4, jc.getIdJogo());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    jc.setId(rs.getLong(1));
                    rs.close();
                }
                stmt.close();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return jc;
    }

    public List<Object> listarUsuario(Long idUsuario) throws SQLException {
        List<Object> jcs = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_LISTAR_JOGOSCLIENTES_USUARIO);
                stmt.setLong(1, idUsuario);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    JogoCliente jc = new JogoCliente();
                    jc.setId(rs.getLong("jc.jogoCliente"));
                    jc.setIdJogo(rs.getLong("jc.idJogo"));
                    jc.setIdUsuario(rs.getLong("jc.idUsuario"));
                    jc.setNota(rs.getDouble("jc.notaUsuario"));
                    jc.setFavorito(rs.getBoolean("jc.favoritar"));
                    jc.setNomeJogo(rs.getString("j.nomeJogo"));
                    jc.setPlataformaJogo(rs.getString("j.plataforma"));
                    jcs.add(jc);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jcs;
    }

    public List<Object> listar() throws SQLException {
        List<Object> jcs = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_LISTAR_JOGOSCLIENTES_USUARIO);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    JogoCliente jc = new JogoCliente();
                    jc.setId(rs.getLong("jogoCliente"));
                    jc.setIdJogo(rs.getLong("idJogo"));
                    jc.setIdUsuario(rs.getLong("idUsuario"));
                    jc.setNota(rs.getDouble("notaUsuario"));
                    jc.setFavorito(rs.getBoolean("favoritar"));
                    jcs.add(jc);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jcs;
    }

    public List<Object> listarJogo(Long idJogo) throws SQLException {
        List<Object> jcs = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_LISTAR_JOGOSCLIENTES_JOGO);
                stmt.setLong(1, idJogo);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    JogoCliente jc = new JogoCliente();
                    jc.setId(rs.getLong("jogoCliente"));
                    jc.setIdJogo(rs.getLong("idJogo"));
                    jc.setIdUsuario(rs.getLong("idUsuario"));
                    jc.setNota(rs.getDouble("notaUsuario"));
                    jc.setFavorito(rs.getBoolean("favoritar"));
                    jcs.add(jc);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jcs;
    }

    public JogoCliente consultar(Long idUsuario, Long idJogo) throws SQLException {
        JogoCliente jc = null;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_CONSULTAR_JOGOCLIENTE);
                stmt.setLong(1, idUsuario);
                stmt.setLong(2, idJogo);
                ResultSet rs = stmt.executeQuery();
                if (rs != null && rs.next()) {
                    jc = new JogoCliente();
                    jc.setId(rs.getLong("jogoCliente"));
                    jc.setIdJogo(rs.getLong("idJogo"));
                    jc.setIdUsuario(rs.getLong("idUsuario"));
                    jc.setNota(rs.getDouble("notaUsuario"));
                    jc.setFavorito(rs.getBoolean("favoritar"));
                    stmt.close();
                    rs.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jc;
    }

    public void excluir(Long id) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_EXCLUIR_JOGOCLIENTE);
                stmt.setLong(1, id);
                stmt.execute();
                stmt.close();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void alterar(Object obj) throws SQLException {
        JogoCliente jc = (JogoCliente) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_ALTERAR_JOGOCLIENTE);
                stmt.setDouble(1, jc.getNota());
                stmt.setBoolean(2, jc.getFavorito());
                stmt.setLong(3, jc.getIdUsuario());
                stmt.setLong(4, jc.getIdJogo());
                stmt.setLong(5, jc.getIdUsuario());
                stmt.setLong(6, jc.getIdJogo());
                stmt.execute();
                stmt.close();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

}
