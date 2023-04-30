import java.util.concurrent.RecursiveTask;

public class CaesarCipherT extends RecursiveTask<String> {

    private String frase;
    private int key;
    private static final int limit = 500000;
    public CaesarCipherT(String frase, int key) {
        this.frase = frase.toLowerCase();
        if(key<0){
            this.key=26-Math.abs(key);
        }
    }

    public static String cipher(String frase, int key) {
        StringBuilder result = new StringBuilder();
        frase = frase.toLowerCase();
        
        if(key<0){
            key=26-Math.abs(key);
        }
        for (char character : frase.toCharArray()) {
            if (character >= 'a' && character <= 'z') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + key) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);

                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }


    @Override
    protected String compute() {
        if (frase.length() > limit) {
            int mid = frase.length() / 2;
            CaesarCipherT firstSubtask = new CaesarCipherT(frase.substring(0, mid), key);
            CaesarCipherT secondSubtask = new CaesarCipherT(frase.substring(mid), key);

            secondSubtask.fork();

            return firstSubtask.compute() + secondSubtask.join();
        } else{
            return cipher(frase, key);
        }
    }
}