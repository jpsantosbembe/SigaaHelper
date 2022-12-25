import model.Credenciais;
import model.User;
import util.Logon;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Usuario e senha
        Credenciais credenciais = new Credenciais("user@teste", "1234qwerty");
        User user = new User(credenciais);
        try {
            Logon logon = new Logon(user);
            System.out.println("Vinculo: " + user.getVinculo());
            System.out.println("Nome: " + user.getNome());
            System.out.println("Saldo: " + user.getCarteira().getSaldo());
            System.out.println("Codigo RU: " + user.getCarteira().getCodigo());
            System.out.println("QRCode string: " + user.getCarteira().getQRCode());
            System.out.println("Situação: " + user.getCarteira().getStatus());
            System.out.println("Profile Pic URL:" + user.getProfileURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}