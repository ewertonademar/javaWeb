package beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Jogo;
import model.JogoCliente;
import model.JogoClienteDAO;
import model.JogoDAO;
import model.Usuario;
import session.SessionContext;

@Named(value = "avaliacaoBean")
@SessionScoped
public class AvaliacaoBean implements Serializable {

    private static final Map<String, Object> VALORESDOSATRIBUTOS;

    static {
        VALORESDOSATRIBUTOS = new HashMap<>();
        VALORESDOSATRIBUTOS.put("type", "number");
        VALORESDOSATRIBUTOS.put("min", "0");
        VALORESDOSATRIBUTOS.put("max", "5");
        VALORESDOSATRIBUTOS.put("required", "required");
        VALORESDOSATRIBUTOS.put("title",
                "Insira uma nota de 0 a 5");
    }

    @Inject
    private JogoCliente jogoCliente;
    @Inject
    private Jogo jogo;
    private List<Jogo> jogos = new ArrayList<>();
    private List<JogoCliente> jogosC = new ArrayList<>();

    public AvaliacaoBean() throws SQLException {
        JogoClienteDAO dao_jc = new JogoClienteDAO();
        JogoDAO dao_j = new JogoDAO();

        for (Object o : dao_jc.listarUsuario(getIdUsuario())) {
            jogosC.add((JogoCliente) o);
        }

        for (Object o : dao_j.listar()) {
            jogos.add((Jogo) o);
        }
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public JogoCliente getJogoCliente() {
        return jogoCliente;
    }

    public void setJogoCliente(JogoCliente jogoCliente) {
        this.jogoCliente = jogoCliente;
    }

    public List<JogoCliente> getJogosC() throws SQLException {
        return jogosC;
    }

    public void setJogosC(List<JogoCliente> jogosC) {
        this.jogosC = jogosC;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public Map<String, Object> getValoresDosAtributos() {
        return VALORESDOSATRIBUTOS;
    }

    public String carregaTelaJogo(Long idJogo) throws SQLException {
        JogoDAO dao_j = new JogoDAO();
        JogoClienteDAO dao_jc = new JogoClienteDAO();

        Long idUsuario = getIdUsuario();

        jogo = dao_j.buscar(idJogo);
        if ((jogoCliente = dao_jc.consultar(idUsuario, jogo.getId())) == null) {
            jogoCliente = new JogoCliente();
            jogoCliente.setIdUsuario(idUsuario);
            jogoCliente.setIdJogo(jogo.getId());
            jogoCliente.setFavorito(false);
        }
        return "/telaJogo";
    }

    public void alterarNota() throws SQLException {
        JogoClienteDAO dao_jc = new JogoClienteDAO();
        if (jogoCliente.getId() == null) {
            jogoCliente = dao_jc.adicionar(jogoCliente);
        } else {
            dao_jc.alterar(jogoCliente);
        }
        double media = 0;

        List<JogoCliente> jcs = new ArrayList<JogoCliente>();
        for (Object o : dao_jc.listarJogo(jogo.getId())) {
            jcs.add((JogoCliente) o);
        }

        for (JogoCliente jc : jcs) {
            media += jc.getNota();
        }
        System.out.println("soma:" + media);
        media /= jcs.size();
        System.out.println("media:" + media);

        jogo.setMedia(media);
        System.out.println("media:" + jogo.getMedia());
        JogoDAO dao = new JogoDAO();
        dao.alterar(jogo);
    }

    public Long getIdUsuario() {
        SessionContext session = SessionContext.getInstance();
        Usuario _usuario = (Usuario) session.getAttribute("usuario");
        return _usuario.getId();
    }

    public String atualizar() throws SQLException {
        JogoClienteDAO dao_jc = new JogoClienteDAO();
        JogoDAO dao_j = new JogoDAO();

        jogosC.clear();
        jogos.clear();

        for (Object o : dao_jc.listarUsuario(getIdUsuario())) {
            jogosC.add((JogoCliente) o);
        }

        for (Object o : dao_j.listar()) {
            jogos.add((Jogo) o);
        }

        return "/telaUser";
    }
}
