package Pc;

/***
 * Rozhranie na zapnutie PC a otestovanie jeho kazdeho komponentu
 */
public interface TurnOnPc {

    /***
     * Zapnutie konkretneho PC
     * @param pc Pc na zapnutie/testovanie
     * @return Vrati status uspesneho testovania
     */
    public default boolean turnOnPc(Pc pc) {
        if (!pc.getCpu().stressTest(pc))
            return false;

        else if (!pc.getGpu().stressTest(pc))
            return false;

        else if (!pc.getRam().stressTest(pc))
            return false;

        else if (!pc.getMbo().stressTest(pc))
            return false;

        else if (!pc.getHdd().stressTest(pc))
            return false;

        else if (!pc.getPsu().stressTest(pc))
            return false;

        else
            return true;
    }
}
