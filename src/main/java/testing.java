import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testing {
    @Test
    void testing(){
        caesarCipher c = new caesarCipher();
        Assertions.assertEquals(c.encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ",6),"GHIJKLMNOPQRSTUVWXYZABCDEF");
    }

}
