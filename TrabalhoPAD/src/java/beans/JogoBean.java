package beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Jogo;
import model.JogoDAO;
import org.primefaces.model.UploadedFile;

@Named(value = "jogoBean")
@SessionScoped
public class JogoBean implements Serializable {

    @Inject
    private Jogo jogo;
    private UploadedFile arquivo;

    public JogoBean() {
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public void adicionarJogo() throws SQLException, IOException {
        JogoDAO dao = new JogoDAO();
        dao.adicionar(jogo);

        jogo = new Jogo();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Jogo adicionado com sucesso", jogo.getDescricao());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void alterar(Jogo j) throws SQLException, IOException {
        JogoDAO dao = new JogoDAO();
        dao.alterar(j);
        FacesMessage msg = new FacesMessage("Jogo atualizado", jogo.getDescricao());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
