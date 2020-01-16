import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Hand {
    
    int first_card;
    int second_card;
    HashMap<String, Integer> primeMap;
    HashMap<String, Integer> handMap;

    /**
    */
    public Hand(int i, int j) {
        this.first_card = i;
        this.second_card = j;
    }

    public Hand() {
        this.primeMap = new HashMap<String, Integer>();
        this.handMap = new HashMap<String, Integer>();
    }
    
    public void populateMap() {

        primeMap.put("2", 2);
        primeMap.put("3", 3);
        primeMap.put("4", 5);
        primeMap.put("5", 7);
        primeMap.put("6", 11);
        primeMap.put("7", 13);
        primeMap.put("8", 17);
        primeMap.put("9", 19);
        primeMap.put("T", 23);
        primeMap.put("J", 29);
        primeMap.put("Q", 31);
        primeMap.put("K", 37);
        primeMap.put("A", 41);
        
        handMap.put("2", 0);
        handMap.put("3", 1);
        handMap.put("4", 2);
        handMap.put("5", 3);
        handMap.put("6", 4);
        handMap.put("7", 5);
        handMap.put("8", 6);
        handMap.put("9", 7);
        handMap.put("T", 8);
        handMap.put("J", 9);
        handMap.put("Q", 10);
        handMap.put("K", 11);
        handMap.put("A", 12);
    }
    /**
    [bitHandRep s] is the integer bit representation of the string representation
    [s] of the current hand. Reference Cactus Kev's hand evaluator algorithm

    +--------+--------+--------+--------+
    |xxxbbbbb|bbbbbbbb|cdhsrrrr|xxpppppp|
    +--------+--------+--------+--------+
    
    p = prime number of rank (deuce=2,trey=3,four=5,five=7,...,ace=41) 
    r = rank of card (deuce=0,trey=1,four=2,five=3,...,ace=12)
    cdhs = suit of card
    b = bit turned on depending on rank of card

    */
    public Hand bitHandRep(String s) {
                
        char first_card = s.charAt(0);
        char second_card = s.charAt(2);
        
        populateMap();

        int primeRank1 = primeMap.get(Character.toString(first_card));
        int rank1 = handMap.get(Character.toString(first_card));
        int primeRank2 = primeMap.get(Character.toString(second_card));
        int rank2 = handMap.get(Character.toString(second_card));

        int first, second, suitrank1, suitrank2;
        
        if (s.charAt(1) == 's') {
            suitrank1 = 1;
        }
        else if (s.charAt(1) == 'h') {
            suitrank1 = 2;
        }
        else if (s.charAt(1) == 'd') {
            suitrank1 = 3;
        }
        else {
            suitrank1 = 4;
        }
        if (s.charAt(3) == 's') {
            suitrank2 = 1;
        }
        else if (s.charAt(3) == 'h') {
            suitrank2 = 2;
        }
        else if (s.charAt(3) == 'd') {
            suitrank2 = 3;
        }
        else {
            suitrank2 = 4;
        }
        
        System.out.println("rank1: " + rank1);
        System.out.println("rank2: " + rank2);
        first = primeRank1 + (rank1 << 8) + (1 << (11 + suitrank1)) 
                    + (1 << (16 + rank1));
        second = primeRank2 + (rank2 << 8) + (1 << (11 + suitrank2)) 
                    + (1 << (16 + rank2));
        
        return new Hand(first, second);
    } 

}

class Tables {

    HashMap<Integer, Integer> flushes;

    public Tables() {
        flushes = new HashMap<Integer, Integer>();
    }

    public void flushMap(String str) {
    
        List<String> temp = Arrays.asList(str.split("\\s*,\\s*"));
        List<Integer> newTemp = temp.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        
        for (int i = 0; i < newTemp.size(); i++) {
            int curr = newTemp.get(i);
            if (curr != 0) {
                System.out.println(i);     
            }       
        }
    }
}

/** 
Hand Evaluator
*/

class Reader {
    Hand hand;                  // 32 bit representation of hand
    int pos;                    // position number 1-6
    int potsize;            // total amount in pot
    int effstack;           // effective stack size i.e. lowest stack on table
    int stack;              // current stack size
    //action option
    
    void input() {
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(System.in));

            Hand start = new Hand();
            Tables t = new Tables();

            // Redo later for graphics where you can highlight the hand on a GTO table and specify suit
            System.out.println("What is your hand? Denote hearts as h, spades as s, clubs as c, diamonds as d. Ex: Ah6c");

            hand = start.bitHandRep(reader.readLine());

            System.out.println("int first: " + hand.first_card);
            System.out.println("binary first: " + Integer.toBinaryString(hand.first_card));
            System.out.println("int second: " + hand.second_card);
            System.out.println("binary second: " + Integer.toBinaryString(hand.second_card));
            
            int x = 0;
            while (x < 1000) {
                System.out.println("flushes array?");
                t.flushMap(reader.readLine());
                // System.out.println(t.flushes);
                x++;
            }

            // Click a button or something
            System.out.println("What is your position in the hand? Denote 1 as SB up to 6 as BTN");
            pos = Integer.parseInt(reader.readLine());

            System.out.println("What is the potsize?");
            potsize = Integer.parseInt(reader.readLine());
            
            System.out.println("What is the effective stack size? The lowest stack on the table");
            effstack = Integer.parseInt(reader.readLine());
            
            System.out.println("What is your current stack size?");
            stack = Integer.parseInt(reader.readLine());

            reader.close();
        }   

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Reader() {
        input();
    }

    public static void main(String [] Args) 
    {
        new Reader();
    }
}
