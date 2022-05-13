package Store;

import Components.*;
import Enums.*;
import Pc.*;

import java.util.Random;

//interface na vytvorenie objednavky
public interface CreateOrder {

    //default metoda na vytvorenie objednavky pre Novy PC podla zadaneho parametra -> performance
    public default void createOrder(Store store, Performance performance) {
        NewPc newPc = new NewPc(performance);

        //urcenie druhu komponentov na zaklade parametra -> performance
        newPc.setRamSize((int) (16 * performance.getMultiplier()));
        newPc.setHddSize((int) (1000 * performance.getMultiplier()));
        newPc.setPsuPower((int) (200 + (400 * performance.getMultiplier())));

        //Vytvorenie novej objednavky na Novy PC
        Order newOrder = new Order(newPc, store.getOrders().size());
        store.getOrders().add(newOrder);
    }

    //default metoda na vytvorenie objednavky s nahodne vygenerovanym Pokazenym PC
    public default void createOrder(Store store, boolean pcCleanup, boolean pcBackup) {

        //Urcenie nahodneho vykonu
        Performance performance = Performance.getRandomPerformance();
        BrokenPc brokenPc = new BrokenPc(performance, pcCleanup, pcBackup);
        Random random = new Random();
        boolean working;

        //urcenie druhu komponentov na zaklade parametra -> performance
        int memCapacity = (int) (16 * performance.getMultiplier());
        int hddCapacity = (int) (1000 * performance.getMultiplier());
        int psuPower = (int) (200 + (400 * performance.getMultiplier()));

        //Nahodne pridelenie PC virusu
        boolean virus = random.nextBoolean();
        brokenPc.setVirus(virus);

        //Vytvaranie vsetkych komponentov s parametrami,
        // ktore boli vyssie nahodne vygenerovane
        working = random.nextBoolean();
        Cpu cpu = new Cpu(50, working, performance, Manufacturer.getRandomManufacturerCpu());

        working = random.nextBoolean();
        Gpu gpu = new Gpu(50, working, performance, Manufacturer.getRandomManufacturerGpu());

        working = random.nextBoolean();
        Ram ram = new Ram(50, working, memCapacity);

        working = random.nextBoolean();
        Mbo mbo = new Mbo(50, working, cpu.getManufacturer());

        working = random.nextBoolean();
        Hdd hdd = new Hdd(50, working, hddCapacity);

        working = random.nextBoolean();
        Psu psu = new Psu(50, working, psuPower);

        //Pridelenie vsetkych komponentov k PC
        brokenPc.setCpu(cpu);
        brokenPc.setGpu(gpu);
        brokenPc.setRam(ram);
        brokenPc.setMbo(mbo);
        brokenPc.setHdd(hdd);
        brokenPc.setPsu(psu);

        //Nastavenie ovladacov PC
        brokenPc.setGpuDriver(gpu.getManufacturer());
        brokenPc.setChipsetDriver(mbo.getSocket());

        //Vytvorenie novej objednavky
        Order newOrder = new Order(brokenPc, store.getOrders().size());
        store.getOrders().add(newOrder);
    }
}
