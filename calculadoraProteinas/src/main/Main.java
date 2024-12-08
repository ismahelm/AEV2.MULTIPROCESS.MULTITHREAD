package main;

import controller.Controller;
import view.MainView;
import model.Model;
public class Main {

	public static void main(String[] args) {
		MainView vistaprincipal = new MainView();
		Model modelo = new Model(); 
		Controller controlador = new Controller (vistaprincipal, modelo);
	}
}
