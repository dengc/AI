//AI- Fruit Rage
//Author: Chufan Deng

import java.io.*;
import java.util.*;
public class fruit {
    public static void main(String args[]) throws IOException {
        File inFile = new File ("input.txt");
        Scanner sc = new Scanner (inFile);

        //scan first 3 lines
        int n = 0;
        if(sc.hasNextLine()){
            n = sc.nextInt();
        }
        int p = 0;
        if(sc.hasNextLine()){
            p = sc.nextInt();
        }
        double t = 0;
        if(sc.hasNextLine()){
            t = sc.nextDouble();
        }

        //scan the datas
        String[][] pos = new String[n][n];
        int inputRow = 0;
        String line = "";
        while(sc.hasNextLine() && inputRow < n){
            line = sc.next();
            for(int i = 0; i < n; i++){
                String fruit = line.substring(i,i+1);
                pos[inputRow][i] = fruit;
            }
            inputRow++;
        }
        sc.close();


        
        String[][] newPos = new String[n][n];
        copyPos(pos, newPos, n);

        dfs(pos, pos[7][6], 7, 6, n);

        printArray(formatPos(pos, n), n);
        // System.out.println(p);
        // System.out.println(t);
        //printList(sameType(pos, type, 0, 6, n));
    }

    public static String[][] formatPos(String[][] pos, int n){
        for(int i = 0; i < n; i++){
            for(int j = n-1; j > -1; j--){
                if(pos[j][i].equals("*")){
                    for(int w = j - 1; w > -1; w--){
                        if(!pos[w][i].equals("*")){
                            // indexToPut = w;
                            pos[j][i] = new String(pos[w][i]);
                            pos[w][i] = "*";
                            w = -1;
                        }
                    }
                }
            }
        }
        return pos;
    }

    public static void dfs(String[][] pos, String fruit, int r, int c, int n){
        pos[r][c] = "*";
        ArrayList<Integer> res = sameType(pos, fruit, r, c, n);
        for(int i = 0; i < res.size(); i = i + 2){
            int rr = res.get(i);
            int cc = res.get(i+1);
            dfs(pos, fruit, rr, cc, n);
        }
    }

    public static ArrayList<Integer> sameType(String[][] pos, String fruit, int row, int col, int n){
        ArrayList<Integer> res = new ArrayList<Integer>();

        int i;
        // String fruit = pos[row][col];

        //left
        i = col - 1;
        if(i >= 0 && pos[row][i].equals(fruit)){
            res.add(row);
            res.add(i);
        }
        //up
        i = row - 1;
        if(i >= 0 && pos[i][col].equals(fruit)){
            res.add(i);
            res.add(col);
        }
        //right
        i = col + 1;
        if(i < n && pos[row][i].equals(fruit)){
            res.add(row);
            res.add(i);
        }
        //down
        i = row + 1;
        if(i < n && pos[i][col].equals(fruit)){
            res.add(i);
            res.add(col);
        }
        return res;
    }

    public static void copyPos(String[][] pos, String[][] newPos, int n){
        for(int k = 0; k < n; k++){
            for(int l = 0; l < n; l++){
                newPos[k][l] = pos[k][l];
            }
        }
    }

    public static void printArray(String[][] pos, int n){
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                System.out.print(pos[i][j]);
            }
            System.out.println();
        }
    }

    public static void printList(ArrayList<Integer> list){
        for(int elem : list){
            System.out.println(elem);
        }
    }


}