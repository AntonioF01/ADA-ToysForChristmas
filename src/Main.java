import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader((System.in)));
        String[] numGiftsTypes_numKids = in.readLine().split(" ");

        int toys = Integer.parseInt(numGiftsTypes_numKids[0]);
        int children = Integer.parseInt(numGiftsTypes_numKids[1]);

        ToysForChristmas t = new ToysForChristmas(toys, children);

        for (int i = 0; i < toys; i++)
            t.handleConnectionToys(i, Integer.parseInt(in.readLine()));

        for (int i = 0; i < children; i++) {
            String[] tokens = in.readLine().split(" ");
            for (int j = 1; j <= Integer.parseInt(tokens[0]); j++)
                t.handleConnectionChildren(i, Integer.parseInt(tokens[j]));
        }
        System.out.println(t.edmondsKarp());
    }
}