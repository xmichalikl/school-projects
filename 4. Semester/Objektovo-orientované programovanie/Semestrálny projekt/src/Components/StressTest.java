package Components;
import Pc.*;

//Rozhranie na testovanie komponentov
public interface StressTest {

    //Metoda na zatazovy test pre komponent
    //implementovana v kazdom type komponente zvlast
    public boolean stressTest(Pc pc);
}
