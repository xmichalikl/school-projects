package Main;

import Controllers.*;
import Store.Store;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

	private Store store;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Image icon = new Image("/Resources/icon.png");
		this.store = loadData();

		Scene scene = new Scene(new HomeControllerNew(primaryStage, this.store).getView(), 400, 400);
		primaryStage.setTitle("CompExpress");
		primaryStage.getIcons().add(icon);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() {
		System.out.println("Stage is closing");
		saveData(this.store);
	}

	//Metoda na serializaciu
	public void saveData(Store store) {
		try
		{
			FileOutputStream file = new FileOutputStream("store.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(store);
			out.close();
			file.close();
			System.out.println("Store has been serialized");
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
	}

	//Metoda na deserializaciu
	public Store loadData() {
		try
		{
			FileInputStream file = new FileInputStream("store.ser");
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			Store loadedStore = (Store)in.readObject();
			in.close();
			file.close();
			System.out.println("Store has been deserialized");
			return loadedStore;
		}

		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}

		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught");
		}

		return new Store();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
