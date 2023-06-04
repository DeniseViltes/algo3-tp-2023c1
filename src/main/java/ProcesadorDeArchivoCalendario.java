import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProcesadorDeArchivoCalendario {
    public static void guardarCalendarioEnArchivo(Calendario calendario,String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        calendario.serializar(fos);
        fos.close();
    }
    public static Calendario leerCalendarioDeArchivo(String fileName) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        Calendario calendario = Calendario.deserializar(fis);
        fis.close();
        return calendario;
    }
}
