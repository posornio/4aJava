import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testdatabase {

	@Test
	void test() {
			DatabaseManager Db = new DatabaseManager();
		   //
		   Db.connectionusers();
		   //Db.connectionmessage();  
		   //Db.createtablemessage();
		   Db.createtableusers();
		   //System.out.println("Table message created successfully");
		   //Date D = new Date(System.currentTimeMillis());
		   //Db.insertmessage(1, 1, 2, "Coucou premier message", D);
		   //Db.insertuser(3, "xxRaveauxx");
		   //Db.insertuser(4, "xxOsornioxx");

		   //System.out.println("Date OK and message added to DB");
		   //Db.selectHistorywithX(1, 2);
		   Db.getAnnuaire();
		   Db.changerPseudo(1, "xxMatthosxx");
		   Db.getAnnuaire();
		   Db.getIdByLogin("xxMatthisxx");
		   Db.getAnnuaire();
	}

}
