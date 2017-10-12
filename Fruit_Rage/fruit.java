//AI- Fruit Rage
//Author: Chufan Deng

import java.io.*;
import java.util.*;
public class fruit {
    public static void main(String args[]) throws IOException {
        File inFile = new File ("input1.txt");
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

        int preStar = getStars(newPos,n);

        // dfStars(pos, pos[2][2], 2, 2, n);

        // printArray(formatPos(pos, n), n);

        // int curStar = getStars(pos, n);
        // int point = curStar - preStar;
        // point = point * point;

        // System.out.println(point);
        //printList(sameType(pos, type, 7, 6, n));
        //
        Queue<String[][]> q = new LinkedList<String[][]>();
        q = bfs(pos, n);
      
        Queue<Integer> qPoint = new LinkedList<Integer>();

        for(String[][] item : q){
            int curStar = getStars(item, n);
            int point = curStar - preStar;
            point = point * point;
            qPoint.add(point);
        }

        Queue<Integer> realPoint = new LinkedList<Integer>();
        int l = qPoint.size();
        int count = 0;
        boolean flag = true;
        while(!q.isEmpty()){
            Queue<String[][]> newQ = new LinkedList<String[][]>();
            preStar = getStars(q.peek(), n);
            int curPoint = qPoint.peek();
            realPoint.add(curPoint);
            count++;
            //System.out.println(count + "    "+ qPoint.size() +"    "+ l);
            if(preStar < n * n){

                newQ = bfs(q.peek(),n);
                for(String[][] item : newQ){
                    q.add(item);
                    int point = getStars(item, n) - preStar;
                    point = point * point;

                    if(count > l){
                        l = qPoint.size();
                        flag = !flag;
                        count = 0;
                    }
                    if(flag){
                        point = curPoint - point;
                    }else {
                        point = curPoint + point;
                    }
                    //point = curPoint - point;
                    qPoint.add(point);
                }
                //printArray(q.peek(), n);
            }

            q.remove();
            qPoint.remove();
            //qPoint.add(curPoint);
        }  

        for(int point : qPoint){
            realPoint.add(point);
        }

        for(int point : realPoint){
            System.out.println(point);
        }
    }

    public static Queue<String[][]> bfs(String[][] pos, int n){

        Queue<String[][]> q = new LinkedList<String[][]>();
        Queue<String[][]> res = new LinkedList<String[][]>();
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                String[][] newPos = new String[n][n];
                copyPos(pos, newPos, n);

                if(!newPos[i][j].equals("*")){
                 
                    dfStars(newPos, newPos[i][j], i, j, n);
                
                    q.add(formatPos(newPos, n));
                }
                                                
            }
        }

        res.add(q.poll());
        while(!q.isEmpty()){
            boolean flag = true;
            for(String[][] ele : res){
                if(Arrays.deepEquals(q.peek(), ele)){
                    //res.add(q.poll());
                    flag = false;
                }
            }
            if(flag){
                res.add(q.poll());
            }else{
                q.remove();
            }
        }

        // for(String[][] ele : queueSet){
        //     q.add(ele);
        // }

        return res;
      
    }

    public static int getStars(String[][] pos, int n){
        int point = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(pos[i][j].equals("*")){
                    point++;
                }
            }
        }
        return point;
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

    public static void dfStars(String[][] pos, String fruit, int r, int c, int n){
        pos[r][c] = "*";
        ArrayList<Integer> res = sameType(pos, fruit, r, c, n);
        for(int i = 0; i < res.size(); i = i + 2){
            int rr = res.get(i);
            int cc = res.get(i+1);
            dfStars(pos, fruit, rr, cc, n);
        }        
    }

    public static ArrayList<Integer> sameType(String[][] pos, String fruit, int row, int col, int n){
        ArrayList<Integer> res = new ArrayList<Integer>();

        int i;

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