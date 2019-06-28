package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO implements FactoryDAO {

    private static final String SQL_INSERIR_JOGO = "insert into jogo (nomeJogo, plataforma, descricao, mediaJogo) values(?,?,?,?)";
    private static final String SQL_LISTAR_JOGOS = "select * from jogo order by nomeJogo";
    private static final String SQL_CONSULTAR_JOGO = "select * from jogo where nomeJogo like ? order by nomeJogo";
    private static final String SQL_BUSCAR_JOGO = "select * from jogo where idJogo = ? order by nomeJogo";
    private static final String SQL_EXCLUIR_JOGO = "delete from jogo where idJogo = ?";
    private static final String SQL_ALTERAR_JOGO = "update jogo set nomeJogo=?, plataforma=?, descricao=?, mediaJogo=? where idJogo=?";

    private Connection connection;

    @Override
    public Jogo adicionar(Object obj) throws SQLException {
        Jogo jogo = (Jogo) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_INSERIR_JOGO, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, jogo.getNome());
                stmt.setString(2, jogo.getPlataforma());
                stmt.setString(3, jogo.getDescricao());
                stmt.setDouble(4, 0);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    jogo.setId(rs.getLong(1));
                    rs.close();
                }
                stmt.close();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return jogo;
    }

    @Override
    public List<Object> listar() throws SQLException {
        List<Object> jogos = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_LISTAR_JOGOS);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Jogo j = new Jogo();
                    j.setId(rs.getLong("idJogo"));
                    j.setNome(rs.getString("nomeJogo"));
                    j.setPlataforma(rs.getString("plataforma"));
                    j.setDescricao(rs.getString("descricao"));
                    j.setMedia(rs.getDouble("mediaJogo"));
                    jogos.add(j);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jogos;
    }

    @Override
    public List<Object> consultar(String nome) throws SQLException {
        List<Object> jogos = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_CONSULTAR_JOGO);
                stmt.setString(1, '%' + nome + '%');
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Jogo j = new Jogo();
                    j.setId(rs.getLong("idJogo"));
                    j.setNome(rs.getString("nomeJogo"));
                    j.setPlataforma(rs.getString("plataforma"));
                    j.setDescricao(rs.getString("descricao"));
                    j.setMedia(rs.getDouble("media"));
                    jogos.add(j);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return jogos;
    }

    public Jogo buscar(Long id) throws SQLException {
        Jogo j = new Jogo();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_BUSCAR_JOGO);
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                j.setId(rs.getLong("idJogo"));
                j.setNome(rs.getString("nomeJogo"));
                j.setPlataforma(rs.getString("plataforma"));
                j.setDescricao(rs.getString("descricao"));
                j.setMedia(rs.getDouble("mediaJogo"));
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return j;
    }

    @Override
    public void excluir(Long id) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_EXCLUIR_JOGO);
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

    @Override
    public void alterar(Object obj) throws SQLException {
        Jogo jogo = (Jogo) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_ALTERAR_JOGO);
                stmt.setString(1, jogo.getNome());
                stmt.setString(2, jogo.getPlataforma());
                stmt.setString(3, jogo.getDescricao());
                stmt.setDouble(4, jogo.getMedia());
                stmt.setLong(5, jogo.getId());
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
