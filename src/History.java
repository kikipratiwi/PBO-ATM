import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Nuha
 */

public class History {
    private final String fileName = "history.txt";
    private Date date;
    private static final DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
    
    private static final int WITHDRAWAL = 1;
    private static final int DEPOSIT = 2;
    private static final int TRANSFER = 3;

    public void saveToFile(int type, int userAccountNumber, double amount) {
        // TODO code application logic here
        switch (type) {
            case WITHDRAWAL: 
            case DEPOSIT: 
            case TRANSFER: 
                save(type, userAccountNumber, amount);
                break;
        }

    }
    
    private void save(int type, int userAccountNumber, double amount){
        date = new Date();
        
        Integer.toString(userAccountNumber);
        Double.toString(amount);
        Integer.toString(type);
        LocalDate ldate = LocalDate.from(date.toInstant().atZone(ZoneOffset.UTC));
        String s;
        s = DateTimeFormatter.ISO_DATE.format(ldate);
        String appendText = "";
        appendText += s;
        appendText += "/";
        appendText += type;
        appendText += "/";
        appendText += userAccountNumber;
        appendText += "/";
        appendText += amount;
        appendText += "\n";
        appendUsingFileWriter(fileName, appendText);
    }
    
    private static void appendUsingFileWriter(String fileName, String text) {
        File file = new File(fileName);
        FileWriter fr = null;
        try {
                // Below constructor argument decides whether to append or override
                fr = new FileWriter(file, true);
                fr.write(text);

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        fr.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
    }
}
