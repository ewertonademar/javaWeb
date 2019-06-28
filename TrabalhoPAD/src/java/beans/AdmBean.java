package beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Jogo;
import model.JogoDAO;
import model.Usuario;
import model.UsuarioDAO;

@Named(value = "admBean")
@SessionScoped
public class AdmBean implements Serializable {

    @Inject
    private Usuario usuario;
    @Inject
    private Jogo jogo;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Jogo> jogos = new ArrayList<>();

    public AdmBean() throws SQLException {
        UsuarioDAO dao_u = new UsuarioDAO();
        JogoDAO dao_j = new JogoDAO();

        for (Object o : dao_u.listar()) {
            usuarios.add((Usuario) o);
        }

        for (Object o : dao_j.listar()) {
            jogos.add((Jogo) o);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public String deleteUser(Usuario u) throws SQLException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        dao.excluir(u.getId());
        return atualizar();
    }

    public String deleteJogo(Jogo j) throws SQLException, IOException {
        JogoDAO dao = new JogoDAO();
        dao.excluir(j.getId());
        return atualizar();
    }

    public String alterarJogo(Long idJogo) throws SQLException {
        JogoDAO dao_j = new JogoDAO();

        jogo = dao_j.buscar(idJogo);
        return "/alterarJogo";
    }

    public String atualizar() throws SQLException {
        UsuarioDAO dao_u = new UsuarioDAO();
        JogoDAO dao_j = new JogoDAO();

        usuarios.clear();
        jogos.clear();

        for (Object o : dao_u.listar()) {
            usuarios.add((Usuario) o);
        }

        for (Object o : dao_j.listar()) {
            jogos.add((Jogo) o);
        }

        return "/telaADM";
    }
}
