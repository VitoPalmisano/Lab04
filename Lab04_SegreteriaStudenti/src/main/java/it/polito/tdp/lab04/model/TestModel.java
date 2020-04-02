package it.polito.tdp.lab04.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		/*
		 * 	Write here your test model
		 */

		System.out.println(model.getIscrittiCorso(new Corso("01OVYPG", 0, null, 0)));
		
		System.out.println(model.cercaStudenteNelCorso(161245, new Corso("01KSUPG", 0, null, 0)));
		System.out.println(model.cercaStudenteNelCorso(1612, new Corso("01KSUPG", 0, null, 0)));
		
		// System.out.println(model.iscriviStudenteACorso(161245, new Corso("01NATPG", 8, "Gestione dell'innovazione e sviluppo prodotto ICT", 2)));
		
		Corso c1 = new Corso("01KSUPG", 0, null, 0);
		Corso c2 = new Corso("01KSUPG", 0, null, 0);
		if(c1.equals(c2))
			System.out.println("appoooo");
	}

}
