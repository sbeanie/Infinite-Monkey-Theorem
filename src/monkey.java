/**
 * Created by Tom on 01/08/14.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Random;

public class monkey
        extends Thread
{
    public static String word;
    static int length;
    public static double charProbability = 0.03846153846153846D;
    public static int runsData;
    public static int runs;
    static String text;
    public static float lettersPerSecond;
    static int[] intAverageArray;
    static long[] longAverageArray;

    public static void start(String searchText, int runsNumber)
    {
        runs = 1;
        text = searchText;
        try
        {
            int argInt = runsNumber;
            if (argInt != 0) {
                runs = argInt;
            }
            if (text.length() >= 5) {
                longAverageArray = new long[runs];
            } else {
                intAverageArray = new int[runs];
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {}catch (NumberFormatException n) {}
    }

    public static void findWord()
    {
        boolean notFound = true;
        word = text;
        length = text.length();
        char[] list = new char[length];
        String check = null;
        int count = 1;
        double finalProbability = Math.pow(charProbability, length) * 100.0D;

        long startTime = System.currentTimeMillis();
        while (notFound)
        {
            Random r = new Random();
            char i = (char)(r.nextInt(26) + 97);



            boolean done = false;
            while (!done)
            {
                char[] tempArray = new char[length];
                for (int n = 1; n < list.length; n++) {
                    tempArray[(n - 1)] = list[n];
                }
                list = tempArray;
                list[(length - 1)] = i;

                check = new String(list);
                done = true;
            }
            if (check.equalsIgnoreCase(word))
            {
                notFound = false;
                System.out.println("Found the Word: " + check);
                long timeTaken = System.currentTimeMillis() - startTime;
                float elapsedTimeSec = (float)timeTaken / 1000.0F;
                System.out.println("\nTime taken: " + elapsedTimeSec + " seconds!");
                System.out.printf("Probability: %f%%", new Object[] { Double.valueOf(finalProbability) });
                System.out.println("\nLetters Generated: " + count);
                lettersPerSecond = count / elapsedTimeSec;
                System.out.println("Letters per second: " + lettersPerSecond);
                try
                {
                    File f = dataFileCreator.createFile();
                    FileInputStream fs = null;
                    InputStreamReader in = null;
                    BufferedReader br = null;

                    StringBuffer sb = new StringBuffer();
                    try
                    {
                        fs = new FileInputStream(f);
                        in = new InputStreamReader(fs);
                        br = new BufferedReader(in);
                        for (;;)
                        {
                            String textInLine = br.readLine();
                            if (textInLine == null) {
                                break;
                            }
                            sb.append(textInLine + "\n");
                        }
                        if (text.length() >= 5) {
                            longAverageArray[(gui.runs - 1)] = count;
                        }
                        if (text.length() < 5) {
                            intAverageArray[(gui.runs - 1)] = count;
                        }
                        if (gui.runs == runs)
                        {
                            int intAverageArrayTotal = 0;
                            long longAverageArrayTotal = 0L;
                            String textToEdit1 = new String(length + ": Average Generated: ");
                            int locationOne = sb.indexOf(textToEdit1);
                            int locationTwo = sb.indexOf("EndA" + length);
                            if (text.length() >= 5) {
                                for (int p = 0; p < gui.runs; p++)
                                {
                                    long tempTotal = longAverageArrayTotal;
                                    longAverageArrayTotal = tempTotal + longAverageArray[p];
                                }
                            } else {
                                for (int p = 0; p < gui.runs; p++)
                                {
                                    int tempTotal = intAverageArrayTotal;
                                    intAverageArrayTotal = tempTotal + intAverageArray[p];
                                }
                            }
                            if (text.length() >= 5)
                            {
                                long average = longAverageArrayTotal / gui.runs;
                                DecimalFormat df = new DecimalFormat("#.##");
                                sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + df.format(average));

                                textToEdit1 = length + ": Standard Deviation: ";
                                locationOne = sb.indexOf(textToEdit1);
                                locationTwo = sb.indexOf("EndE" + length);
                                long squareTotal = 0L;
                                for (int z = 0; z < gui.runs; z++)
                                {
                                    long tempTotal = squareTotal;
                                    squareTotal = (long) (tempTotal + Math.pow(longAverageArray[z] - average, 2.0D));
                                }
                                long stdAverage = squareTotal / (gui.runs - 1);
                                long standardDeviation = (long) (Math.pow(stdAverage, 0.5D));
                                sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + df.format(standardDeviation));

                                gui.running = false;
                                System.out.println("Stopped!");
                            }
                            else
                            {
                                double average = intAverageArrayTotal / gui.runs;
                                DecimalFormat df = new DecimalFormat("#.##");
                                sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + df.format(average));

                                textToEdit1 = length + ": Standard Deviation: ";
                                locationOne = sb.indexOf(textToEdit1);
                                locationTwo = sb.indexOf("EndE" + length);
                                double squareTotal = 0.0D;
                                for (int z = 0; z < gui.runs; z++)
                                {
                                    double tempTotal = squareTotal;
                                    squareTotal = tempTotal + Math.pow(intAverageArray[z] - average, 2.0D);
                                }
                                double stdAverage = squareTotal / (gui.runs - 1);
                                double standardDeviation = Math.pow(stdAverage, 0.5D);
                                sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + df.format(standardDeviation));

                                gui.running = false;
                                System.out.println("Stopped!");
                            }
                        }
                        String textToEdit1 = new String(length + ": Maximum Generated: ");
                        int locationOne = sb.indexOf(textToEdit1);
                        int locationTwo = sb.indexOf("EndB" + length);
                        int size = locationTwo - (locationOne + textToEdit1.length() + 1);
                        char[] maximumGenerated = new char[size];
                        sb.getChars(locationOne + textToEdit1.length(), locationTwo - 1, maximumGenerated, 0);
                        int maximGenerated = Integer.parseInt(new String(maximumGenerated));
                        int maxData = 0;
                        if ((maximGenerated < count) || (gui.runs == 1))
                        {
                            maxData = count;
                            sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + maxData);
                        }
                        textToEdit1 = new String(length + ": Minimum Generated: ");
                        locationOne = sb.indexOf(textToEdit1);
                        locationTwo = sb.indexOf("EndC" + length);
                        size = locationTwo - (locationOne + textToEdit1.length() + 1);
                        char[] minimumGenerated = new char[size];
                        sb.getChars(locationOne + textToEdit1.length(), locationTwo - 1, minimumGenerated, 0);
                        int minimGenerated = Integer.parseInt(new String(minimumGenerated));
                        int minData = 0;
                        if ((minimGenerated > count) || (minimGenerated == 0) || (gui.runs == 1))
                        {
                            minData = count;
                            sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + minData);
                        }
                        textToEdit1 = new String(length + ": Runs: ");
                        locationOne = sb.indexOf(textToEdit1);
                        locationTwo = sb.indexOf("EndD" + length);
                        size = locationTwo - (locationOne + textToEdit1.length() + 1);
                        char[] runsText = new char[size];
                        sb.getChars(locationOne + textToEdit1.length(), locationTwo - 1, runsText, 0);
                        int newRuns = Integer.parseInt(new String(runsText));
                        runsData = newRuns + 1;
                        if (gui.runs == 1) {
                            runsData = 1;
                        }
                        sb.replace(locationOne + textToEdit1.length(), locationTwo - 1, "" + runsData);

                        fs.close();
                        in.close();
                        br.close();
                        Thread.sleep(100L);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error: " + e.getMessage());
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
                catch (Exception e)
                {
                    System.out.println("Error Writing Data");
                }
            }
            else
            {
                count++;
            }
        }
    }
}
