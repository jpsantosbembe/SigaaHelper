package util;



import java.io.IOException;
import java.io.Serializable;

import model.Carteira;
import model.User;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Logon implements Serializable {
    private User user;

    private Carteira carteira;

    public Logon(User user) throws IOException {
        this.user = user;
        criarSessao();
        facaLogin();
        aposLogon();
        getProfilePicture();
    }

    private void criarSessao() throws IOException {
        Network network = new Network();
        Response response = network.getRequest("https://sipac.ufopa.edu.br/sipac/");
        int index = response.headers().get("Set-Cookie").indexOf(";");
        this.getUser().getCredenciais().setSessao(response.headers().get("Set-Cookie").substring(0,index));
    }
    private void facaLogin() throws IOException {
        Network network = new Network();
        network.postRequest("https://sipac.ufopa.edu.br/sipac/logon.do;" + getUser().getCredenciais().getSessao().toLowerCase(),
                "width=1920&height=1080&login=" + getUser().getCredenciais().getUsuario() + "&senha=" + getUser().getCredenciais().getSenha(),
                getUser().getCredenciais().getSessao());
    }

    private void aposLogon() throws IOException {
        Network network = new Network();
        String response = network.postRequest("https://sipac.ufopa.edu.br/sipac/portal_aluno/index.jsf",
                "formmenuadm=formmenuadm&jscook_action=formmenuadm_menuaaluno_menu%3AA%5D%23%7BsaldoCartao.iniciar%7D&javax.faces.ViewState=j_id1",
                getUser().getCredenciais().getSessao());
        Document document = Jsoup.parse(response);
        Elements tabela = document.select("tr");

        try {
            getUser().setNome(tabela.get(1).select("td").text());
            getUser().setVinculo(tabela.get(3).select("td").text().split(" ")[0]);
            getUser().setCarteira(new Carteira(tabela.get(2).select("td").text().split(" ")[0], tabela.get(3).select("td").text().split(" ")[1]));
            getUser().getCarteira().setStatus(tabela.get(2).select("td").text().split(" ")[1]);
            getUser().getCarteira().setQRCode(tabela.select("img").first().attr("src").replace("/sipac/QRCode?codigo=", "").replace("&tamanho=200", ""));
            //return response;
        } catch (Exception e) {
            getUser().setNome(null);
            getUser().setVinculo(null);
            getUser().setCarteira(new Carteira(null, null));
            getUser().getCarteira().setStatus(null);
            getUser().getCarteira().setQRCode(null);
            //return response;
        }
    }

    public void getProfilePicture() throws IOException {
        Network network = new Network();
        String response = network.getRequestWithCookie("https://sipac.ufopa.edu.br/sipac/portal_aluno/index.jsf", getUser().getCredenciais().getSessao());
        Document document = Jsoup.parse(response);
        Elements tabela = document.select("img");
        getUser().setProfileURL(tabela.get(1).attr("src"));

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}