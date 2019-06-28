package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Usuario;
import model.UsuarioDAO;
import session.SessionContext;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @Inject
    private Usuario usuario;

    public UsuarioBean() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String autenticar() throws SQLException {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario _usuario;
        if ((_usuario = dao.autenticar(usuario.getEmail(), usuario.getSenha())) != null) {
            usuario = _usuario;
            SessionContext session = SessionContext.getInstance();
            session.setAttribute("usuario", usuario);
            if (usuario.getTipoUsuario() == 1) {
                return "/telaADM";
            } else {
                return "/telaPerfil";
            }
        }
        FacesMessage message = new FacesMessage("Login/senha inválidos!");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage("form:entrar", message);
        return null;
    }

    public String cadastrar() throws SQLException {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario _usuario;
        usuario.setTipoUsuario(0);
        if ((_usuario = dao.adicionar(usuario)) != null) {
            usuario = _usuario;
            SessionContext session = SessionContext.getInstance();
            session.setAttribute("usuario", usuario);
            if (usuario.getTipoUsuario() == 1) {
                return "/telaLogin";
            } else {
                return "/telaLogin";
            }
        }

        FacesMessage message = new FacesMessage("Usuário já existente");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage("form:entrar", message);
        return null;
    }

    public String sair() {
        SessionContext session = SessionContext.getInstance();
        session.encerrarSessao();
        return "/telaLogin";
    }
}
