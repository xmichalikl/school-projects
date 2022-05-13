package Controllers;

import Components.*;
import Enums.Manufacturer;
import Enums.Performance;
import Exceptions.ConfigurationException;
import Pc.BrokenPc;
import Pc.NewPc;
import Persons.AssemblyTechnician;
import Persons.ConfigurationTechnician;
import Persons.Employee;
import Store.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigurationController extends EmpController implements Initializable {

    private final List<Text> textArr = new ArrayList<>();

    private ObservableList<Cpu> obsCpu = FXCollections.observableArrayList();
    private ObservableList<Gpu> obsGpu = FXCollections.observableArrayList();
    private ObservableList<Ram> obsRam = FXCollections.observableArrayList();
    private ObservableList<Mbo> obsMbo = FXCollections.observableArrayList();
    private ObservableList<Hdd> obsHdd = FXCollections.observableArrayList();
    private ObservableList<Psu> obsPsu = FXCollections.observableArrayList();

    @FXML private Text cpuText, gpuText, ramText, mboText, hddText, psuText;

    @FXML private Button cpuOrdButton;
    @FXML private Button gpuOrdButton;
    @FXML private Button ramOrdButton;
    @FXML private Button mboOrdButton;
    @FXML private Button hddOrdButton;
    @FXML private Button psuOrdButton;

    @FXML private ComboBox<Cpu> cpuChoice;
    @FXML private ComboBox<Gpu> gpuChoice;
    @FXML private ComboBox<Ram> ramChoice;
    @FXML private ComboBox<Mbo> mboChoice;
    @FXML private ComboBox<Hdd> hddChoice;
    @FXML private ComboBox<Psu> psuChoice;


    @Override
    public void confirm() {
        if (checkComponents())
            super.confirm();
    }

    public boolean checkComponents() {
        Cpu newCpu = cpuChoice.getSelectionModel().getSelectedItem();
        Gpu newGpu = gpuChoice.getSelectionModel().getSelectedItem();
        Ram newRam = ramChoice.getSelectionModel().getSelectedItem();
        Mbo newMbo = mboChoice.getSelectionModel().getSelectedItem();
        Hdd newHdd = hddChoice.getSelectionModel().getSelectedItem();
        Psu newPsu = psuChoice.getSelectionModel().getSelectedItem();

        try {
            if (this.pc instanceof BrokenPc) {
                if (!this.pc.getCpu().getWorking() && newCpu == null)
                    throw new ConfigurationException("Treba vybrat novy procesor!");
                else if (!this.pc.getGpu().getWorking() && newGpu == null)
                    throw new ConfigurationException("Treba vybrat novu graficku kartu!");
                else if (!this.pc.getRam().getWorking() && newRam == null)
                    throw new ConfigurationException("Treba vybrat novu ram pamat!");
                else if (!this.pc.getMbo().getWorking() && newMbo == null)
                    throw new ConfigurationException("Treba vybrat novu zakladnu dosku!");
                else if (!this.pc.getHdd().getWorking() && newHdd == null)
                    throw new ConfigurationException("Treba vybrat novy pevny disk!");
                else if (!this.pc.getPsu().getWorking() && newPsu == null)
                    throw new ConfigurationException("Treba vybrat novy zdroj!");
            }
            else if (this.pc instanceof NewPc) {
                if (newCpu == null)
                    throw new ConfigurationException("Treba vybrat novy procesor!");
                else if (newGpu == null)
                    throw new ConfigurationException("Treba vybrat novu graficku kartu!");
                else if (newRam == null)
                    throw new ConfigurationException("Treba vybrat novu ram pamat!");
                else if (newMbo == null)
                    throw new ConfigurationException("Treba vybrat novu zakladnu dosku!");
                else if (newHdd == null)
                    throw new ConfigurationException("Treba vybrat novy pevny disk!");
                else if (newPsu == null)
                    throw new ConfigurationException("Treba vybrat novy zdroj!");
            }

            if (employeesComboBox.getSelectionModel().getSelectedItem() != null) {
                if (newCpu != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newCpu);
                    this.store.getWarehouse().getCpus().remove(newCpu);
                }
                if (newGpu != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newGpu);
                    this.store.getWarehouse().getGpus().remove(newGpu);
                }
                if (newRam != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newRam);
                    this.store.getWarehouse().getRams().remove(newRam);
                }
                if (newMbo != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newMbo);
                    this.store.getWarehouse().getMbos().remove(newMbo);
                }
                if (newHdd != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newHdd);
                    this.store.getWarehouse().getHdds().remove(newHdd);
                }
                if (newPsu != null) {
                    ((ConfigurationTechnician) this.loggedEmp).getNewComponents().add(newPsu);
                    this.store.getWarehouse().getPsus().remove(newPsu);
                }

            }
            return true;
        }
        catch (ConfigurationException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            return false;
        }

    }

    public void cpuOrd() {
        if (this.pc instanceof BrokenPc) {
            Cpu newCpu = this.store.getWarehouse().orderComponent(this.pc.getCpu());
            this.store.getWarehouse().getCpus().add(newCpu);
            this.cpuChoice.getSelectionModel().select(newCpu);
        }
        else if (this.pc instanceof NewPc) {
            Cpu newCpu = this.store.getWarehouse().orderComponent(new Cpu(1, true, this.pc.getPerformance(), Manufacturer.getRandomManufacturerCpu()));
            this.store.getWarehouse().getCpus().add(newCpu);
            this.cpuChoice.getSelectionModel().select(newCpu);
        }
        this.cpuChoice.setDisable(true);
        this.cpuOrdButton.setDisable(true);
    }

    public void gpuOrd() {
        if (this.pc instanceof BrokenPc) {
            Gpu newGpu = this.store.getWarehouse().orderComponent(this.pc.getGpu());
            this.store.getWarehouse().getGpus().add(newGpu);
            this.gpuChoice.getSelectionModel().select(newGpu);
        }
        else if (this.pc instanceof NewPc) {
            Gpu newGpu = this.store.getWarehouse().orderComponent(new Gpu(1, true, this.pc.getPerformance(), Manufacturer.getRandomManufacturerGpu()));
            this.store.getWarehouse().getGpus().add(newGpu);
            this.gpuChoice.getSelectionModel().select(newGpu);
        }
        this.gpuChoice.setDisable(true);
        this.gpuOrdButton.setDisable(true);
    }

    public void ramOrd() {
        if (this.pc instanceof BrokenPc) {
            Ram newRam = this.store.getWarehouse().orderComponent(this.pc.getRam());
            this.store.getWarehouse().getRams().add(newRam);
            this.ramChoice.getSelectionModel().select(newRam);
        }
        else if (this.pc instanceof NewPc) {
            Ram newRam = this.store.getWarehouse().orderComponent(new Ram(1, true, ((NewPc) this.pc).getRamSize()));
            this.store.getWarehouse().getRams().add(newRam);
            this.ramChoice.getSelectionModel().select(newRam);
        }
        this.ramChoice.setDisable(true);
        this.ramOrdButton.setDisable(true);
    }

    public void mboOrd() {
        if (this.pc instanceof BrokenPc) {
            Mbo newMbo = this.store.getWarehouse().orderComponent(this.pc.getMbo());
            this.store.getWarehouse().getMbos().add(newMbo);
            this.mboChoice.getSelectionModel().select(newMbo);
        }
        else if (this.pc instanceof NewPc) {
            Mbo newMbo = this.store.getWarehouse().orderComponent(new Mbo(1, true, this.cpuChoice.getSelectionModel().getSelectedItem().getManufacturer()));
            this.store.getWarehouse().getMbos().add(newMbo);
            this.mboChoice.getSelectionModel().select(newMbo);
        }
        this.mboChoice.setDisable(true);
        this.mboOrdButton.setDisable(true);
    }

    public void hddOrd() {
        if (this.pc instanceof BrokenPc) {
            Hdd newHdd = this.store.getWarehouse().orderComponent(this.pc.getHdd());
            this.store.getWarehouse().getHdds().add(newHdd);
            this.hddChoice.getSelectionModel().select(newHdd);
        }
        else if (this.pc instanceof NewPc) {
            Hdd newHdd = this.store.getWarehouse().orderComponent(new Hdd(1, true, ((NewPc) this.pc).getHddSize()));
            this.store.getWarehouse().getHdds().add(newHdd);
            this.hddChoice.getSelectionModel().select(newHdd);
        }
        this.hddChoice.setDisable(true);
        this.hddOrdButton.setDisable(true);
    }

    public void psuOrd() {
        if (this.pc instanceof BrokenPc) {
            Psu newPsu = this.store.getWarehouse().orderComponent(this.pc.getPsu());
            this.store.getWarehouse().getPsus().add(newPsu);
            this.psuChoice.getSelectionModel().select(newPsu);
        }
        else if (this.pc instanceof NewPc) {
            Psu newPsu = this.store.getWarehouse().orderComponent(new Psu(1, true, ((NewPc) this.pc).getPsuPower()));
            this.store.getWarehouse().getPsus().add(newPsu);
            this.psuChoice.getSelectionModel().select(newPsu);
        }
        this.psuChoice.setDisable(true);
        this.psuOrdButton.setDisable(true);
    }



    public void cpuChoiceSwitch() {
        if (cpuChoice.getSelectionModel().getSelectedItem() != null) {
            setChoiceMbo(cpuChoice.getSelectionModel().getSelectedItem().getManufacturer());
        }
        if (this.pc instanceof NewPc) {
            this.gpuChoice.setDisable(false);
            this.gpuOrdButton.setDisable(false);
        }
    }

    public void gpuChoiceSwitch() {
        if (this.pc instanceof NewPc) {
            this.ramChoice.setDisable(false);
            this.ramOrdButton.setDisable(false);
        }
    }

    public void ramChoiceSwitch() {
        if (this.pc instanceof NewPc) {
            this.mboChoice.setDisable(false);
            this.mboOrdButton.setDisable(false);
        }
    }

    public void mboChoiceSwitch() {

        if (this.pc instanceof NewPc) {
            this.hddChoice.setDisable(false);
            this.hddOrdButton.setDisable(false);
        }
    }

    public void hddChoiceSwitch() {
        if (this.pc instanceof NewPc) {
            this.psuChoice.setDisable(false);
            this.psuOrdButton.setDisable(false);
        }
    }


    public void setChoiceCpu(Performance performance) {
        for (Cpu cpu : this.store.getWarehouse().getCpus())
            if (cpu.getPerformance() == performance)
                this.obsCpu.add(cpu);
        this.cpuChoice.setItems(this.obsCpu);
    }

    public void setChoiceCpu(Performance performance, Manufacturer manufacturer) {
        for (Cpu cpu : this.store.getWarehouse().getCpus())
            if (cpu.getPerformance() == performance && cpu.getManufacturer() == manufacturer)
                this.obsCpu.add(cpu);
        this.cpuChoice.setItems(this.obsCpu);
    }

    public void setChoiceGpu(Performance performance) {
        for (Gpu gpu : this.store.getWarehouse().getGpus())
            if (gpu.getPerformance() == performance)
                this.obsGpu.add(gpu);
        this.gpuChoice.setItems(this.obsGpu);
    }

    public void setChoiceRam(int ramSize) {
        for (Ram ram : this.store.getWarehouse().getRams())
            if (ram.getCapacity() == ramSize)
                this.obsRam.add(ram);
        this.ramChoice.setItems(this.obsRam);
    }

    public void setChoiceMbo() {
        this.obsMbo.addAll(this.store.getWarehouse().getMbos());
        this.mboChoice.setItems(this.obsMbo);
    }

    public void setChoiceMbo(Manufacturer manufacturer) {
        this.obsMbo.clear();
        for (Mbo mbo : this.store.getWarehouse().getMbos())
            if (mbo.getSocket() == manufacturer)
                this.obsMbo.add(mbo);
        this.mboChoice.setItems(this.obsMbo);
    }

    public void setChoiceHdd(int hddSize) {
        for (Hdd hdd : this.store.getWarehouse().getHdds())
            if (hdd.getCapacity() == hddSize)
                this.obsHdd.add(hdd);
        this.hddChoice.setItems(this.obsHdd);
    }

    public void setChoicePsu(int psuPower) {
        for (Psu psu : this.store.getWarehouse().getPsus())
            if (psu.getPower() == psuPower)
                this.obsPsu.add(psu);
        this.psuChoice.setItems(this.obsPsu);
    }

    @Override
    public void clearScreen() {
        for (Text t : this.textArr)
            t.setText("Nie je k dispozicii");

        this.cpuChoice.setDisable(true);
        this.gpuChoice.setDisable(true);
        this.ramChoice.setDisable(true);
        this.mboChoice.setDisable(true);
        this.hddChoice.setDisable(true);
        this.psuChoice.setDisable(true);

        this.cpuOrdButton.setDisable(true);
        this.gpuOrdButton.setDisable(true);
        this.ramOrdButton.setDisable(true);
        this.mboOrdButton.setDisable(true);
        this.hddOrdButton.setDisable(true);
        this.psuOrdButton.setDisable(true);
    }

    @Override
    public void setData(Store store, Employee loggedEmp) {
        super.setData(store, loggedEmp);

        if (this.pc != null) {
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);

            for (Employee emp : this.store.getEmployees()) {
                if (emp instanceof AssemblyTechnician && emp.getIsAvailable())
                    this.obsEmployees.add(emp);
            }
            this.employeesComboBox.setItems(this.obsEmployees);


            if (this.pc instanceof BrokenPc) {

                for (Component com : ((ConfigurationTechnician) this.loggedEmp).getBrokenComponents()) {
                    if (com instanceof Cpu) {
                        this.cpuText.setText(this.pc.getCpu().toString());
                        this.cpuChoice.setDisable(false);
                        this.cpuOrdButton.setDisable(false);
                        setChoiceCpu(((Cpu) com).getPerformance(), ((Cpu) com).getManufacturer());
                    }
                    else if (com instanceof Gpu) {
                        this.gpuText.setText(this.pc.getGpu().toString());
                        this.gpuChoice.setDisable(false);
                        this.gpuOrdButton.setDisable(false);
                        setChoiceGpu(((Gpu) com).getPerformance());
                    }
                    else if (com instanceof Ram) {
                        this.ramText.setText(this.pc.getRam().toString());
                        this.ramChoice.setDisable(false);
                        this.ramOrdButton.setDisable(false);
                        setChoiceRam(((Ram) com).getCapacity());
                    }
                    else if (com instanceof Mbo) {
                        this.mboText.setText(this.pc.getMbo().toString());
                        this.mboChoice.setDisable(false);
                        this.mboOrdButton.setDisable(false);
                        setChoiceMbo(((Mbo) com).getSocket());
                    }
                    else if (com instanceof Hdd) {
                        this.hddText.setText(this.pc.getHdd().toString());
                        this.hddChoice.setDisable(false);
                        this.hddOrdButton.setDisable(false);
                        setChoiceHdd(((Hdd) com).getCapacity());
                    }
                    else if (com instanceof Psu) {
                        this.psuText.setText(this.pc.getPsu().toString());
                        this.psuChoice.setDisable(false);
                        this.psuOrdButton.setDisable(false);
                        setChoicePsu(((Psu) com).getPower());
                    }
                }
            }
            else if (this.pc instanceof NewPc) {
                NewPc newPc = (NewPc) this.pc;

                setChoiceCpu(newPc.getPerformance());
                setChoiceGpu(newPc.getPerformance());
                setChoiceRam(newPc.getRamSize());
                setChoiceMbo();
                setChoiceHdd(newPc.getHddSize());
                setChoicePsu(newPc.getPsuPower());

                this.cpuChoice.setDisable(false);
                this.cpuOrdButton.setDisable(false);

                this.cpuText.setText("Vyber CPU [" + newPc.getPerformance() + "]");
                this.gpuText.setText("Vyber GPU [" + newPc.getPerformance() + "]");
                this.ramText.setText("Vyber RAM [" + newPc.getRamSize() + "GB]");
                this.mboText.setText("Vyber MBO [podla CPU]");
                this.hddText.setText("Vyber HDD [" + newPc.getRamSize() + "GB]");
                this.psuText.setText("Vyber PSU [" + newPc.getPsuPower() + "W]");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.textArr.add(cpuText);
        this.textArr.add(gpuText);
        this.textArr.add(ramText);
        this.textArr.add(mboText);
        this.textArr.add(hddText);
        this.textArr.add(psuText);

        if (this.pc == null) {
            for (Text t : this.textArr)
                t.setText("Nie je k dispozicii");
        }

        this.cpuChoice.setDisable(true);
        this.gpuChoice.setDisable(true);
        this.ramChoice.setDisable(true);
        this.mboChoice.setDisable(true);
        this.hddChoice.setDisable(true);
        this.psuChoice.setDisable(true);

        this.cpuOrdButton.setDisable(true);
        this.gpuOrdButton.setDisable(true);
        this.ramOrdButton.setDisable(true);
        this.mboOrdButton.setDisable(true);
        this.hddOrdButton.setDisable(true);
        this.psuOrdButton.setDisable(true);

    }
}
