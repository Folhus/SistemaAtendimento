package sistema;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Banco {
    public DB db;
    public HTreeMap<String, Cliente> clientes;
    public HTreeMap<String, Procedimento> procedimentos;
    public HTreeMap<Integer, Sessao> sessoes;

    public Banco() {
        java.io.File dbFile = new java.io.File("sistema.db");
        if (dbFile.exists() && dbFile.length() == 0) {
            dbFile.delete();
        }
        
        this.db = DBMaker.fileDB("sistema.db").transactionEnable().make();
        this.clientes = this.db.hashMap("clientes", Serializer.STRING, Serializer.JAVA).createOrOpen();
        this.procedimentos = this.db.hashMap("procedimentos", Serializer.STRING, Serializer.JAVA).createOrOpen();
        this.sessoes = this.db.hashMap("sessoes", Serializer.INTEGER, Serializer.JAVA).createOrOpen();
    }

    public void abrir() {
        if (this.db.isClosed()) {
            this.db = DBMaker.fileDB("sistema.db").transactionEnable().make();
            this.clientes = this.db.hashMap("clientes", Serializer.STRING, Serializer.JAVA).createOrOpen();
            this.procedimentos = this.db.hashMap("procedimentos", Serializer.STRING, Serializer.JAVA).createOrOpen();
            this.sessoes = this.db.hashMap("sessoes", Serializer.INTEGER, Serializer.JAVA).createOrOpen();
        }
    }

}
