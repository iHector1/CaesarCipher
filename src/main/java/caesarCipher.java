public class caesarCipher {
    private String frase;
    caesarCipher(String frase){
        this.frase=frase;
    }

    //metodo de encriptacion para cifrado cesar
    public String encrypt(int key) {
        String encrypted = "";
        for (int i = 0; i < frase.length(); i++) {
            char c = frase.charAt(i);
            if (Character.isLetter(c)) {
                // Si el caracter es una letra, se aplica el cifrado César
                c = (char) (c + key);
                if (!Character.isLetter(c)) {
                    c -= 26;
                }
            }
            encrypted += c;
        }
        return encrypted;
    }
    caesarCipher(){

    }
    //MEtodo para pruebas
    public String encrypt(String frase2,int llave) {
        String encrypted = "";
        for (int i = 0; i < frase2.length(); i++) {
            char c = frase2.charAt(i);
            if (Character.isLetter(c)) {
                // verificacion de que sea una letra para aplicar cifrado
                c = (char) (c + llave);
                if (!Character.isLetter(c)) {
                    c -= 26;
                }
            }
            encrypted += c;
        }
        return encrypted;
    }
}
