/**
 * Created by Tom on 01/08/14.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class dataFileCreator
{
    public static File createFile()
    {
        FileInputStream fs = null;
        InputStreamReader in = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();

        File f = new File("MEData.txt");
        if (!f.exists()) {
            try
            {
                f.createNewFile();
            }
            catch (Exception g)
            {
                System.out.println("Could not create data file! :/");
            }
        }
        if (f.exists())
        {
            try
            {
                fs = new FileInputStream(f);
                in = new InputStreamReader(fs);
                br = new BufferedReader(in);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            try
            {
                if (fs.read() == -1)
                {
                    for (int b = 1; b <= 8; b++)
                    {
                        sb.append(b + ": Average Generated: 0 EndA" + b + "\n");
                        sb.append(b + ": Maximum Generated: 0 EndB" + b + "\n");
                        sb.append(b + ": Minimum Generated: 0 EndC" + b + "\n");
                        sb.append(b + ": Runs: 0 EndD" + b + "\n");
                        sb.append(b + ": Standard Deviation: 0 EndE" + b + "\n\n");
                    }
                    try
                    {
                        FileWriter fstream = new FileWriter(f);
                        BufferedWriter outobj = new BufferedWriter(fstream);
                        outobj.write(sb.toString());
                        outobj.close();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
            catch (Exception h)
            {
                System.out.println("Error: " + h.getMessage());
            }
        }
        return f;
    }
}
