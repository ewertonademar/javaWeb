package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements FactoryDAO {

    private static final String SQL_INSERIR_USUARIO = "insert into usuario (nomeUsuario, email, senha, tipoUsuario) values(?,?,?,?)";
    private static final String SQL_LISTAR_USUARIOS = "select * from usuario order by idUsuario";
    private static final String SQL_CONSULTAR_USUARIO = "select * from usuario where nomeUsuario like ? order by nomeUsuario";
    private static final String SQL_AUTENTICAR_USUARIO = "select * from usuario where email = ? and senha = ?";
    private static final String SQL_EXCLUIR_USUARIO = "delete from usuario where idUsuario = ?";
    private static final String SQL_ALTERAR_USUARIO = "update usuario set nomeUsuario=?, email=?, senha=?, tipoUsuario=? where idUsuario=?";

    private Connection connection;

    @Override
    public Usuario adicionar(Object obj) throws SQLException {
        Usuario usuario = (Usuario) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_INSERIR_USUARIO, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, String.valueOf(usuario.getTipoUsuario()));
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    usuario.setId(rs.getLong(1));
                    rs.close();
                }
                stmt.close();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return usuario;
    }

    @Override
    public List<Object> listar() throws SQLException {
        List<Object> usuarios = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_LISTAR_USUARIOS);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("idUsuario"));
                    u.setNome(rs.getString("nomeUsuario"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipoUsuario(Integer.valueOf(rs.getString("tipoUsuario")));
                    usuarios.add(u);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return usuarios;
    }

    @Override
    public List<Object> consultar(String nome) throws SQLException {
        List<Object> usuarios = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_CONSULTAR_USUARIO);
                stmt.setString(1, '%' + nome + '%');
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("idUsuario"));
                    u.setNome(rs.getString("nomeUsuario"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipoUsuario(Integer.valueOf(rs.getString("tipoUsuario")));
                    usuarios.add(u);
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return usuarios;
    }

    public Usuario autenticar(String login, String senha) throws SQLException {
        Usuario u = null;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.
                        prepareStatement(SQL_AUTENTICAR_USUARIO);
                stmt.setString(1, login);
                stmt.setString(2, senha);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getLong("idUsuario"));
                    u.setNome(rs.getString("nomeUsuario"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipoUsuario(rs.getInt("tipoUsuario"));
                }
                stmt.close();
                rs.close();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
        return u;
    }

    @Override
    public void excluir(Long id) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_EXCLUIR_USUARIO);
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
        Usuario usuario = (Usuario) obj;
        try {
            connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL_ALTERAR_USUARIO);
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getSenha());
                stmt.setLong(4, usuario.getId());
                stmt.setString(5, String.valueOf(usuario.getTipoUsuario()));
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
