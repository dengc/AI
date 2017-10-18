//AI- Fruit Rage
//Author: Chufan Deng

import java.io.*;
import java.util.*;
public class homework {
    public static void main(String args[]) throws IOException {
        File inFile = new File ("input13.txt");
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

        File outFile = new File ("output.txt");
        FileWriter fWriter = new FileWriter (outFile);
        PrintWriter pWriter = new PrintWriter (fWriter);
      
        int row = -1;
        int col = -1;
        int max = -1000;
        int preStar = getStars(pos,n);

        String[][] res = new String[n][n];
        copyPos(pos, res, n);
        boolean flag = true;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                String[][] haha = new String[n][n];
                copyPos(pos, haha, n);

                if(!haha[i][j].equals("*")){
                    dfStars(haha, haha[i][j], i, j, n);
                    haha = formatPos(haha, n);
                    int curStar = getStars(haha,n);
                    int val = curStar - preStar;
                    val = val * val;
                    if(val > max && val >= getMax(haha,n)){
                        max = val;
                        row = i;
                        col = j;
                        flag = false;
                    }
                }
            }
        }
        if(flag){
            for(int i = 0; i < n && flag; i++){
                for(int j = 0; j < n; j++){
                    if(!res[i][j].equals("*")){
                        row = i;
                        col = j;
                        flag = false;
                        break;

                    }
                }
            }
        }        
        dfStars(res, res[row][col], row, col, n);
        res = formatPos(res, n);

        row++;
        col++;
        System.out.print(getCol(col));
        System.out.println(row);
        printArray(res, n);

        writeRes(n, res, pWriter, col, row);
        
    }

    public static int getMax(String[][] pos, int n){
        int max = -1000;
        int preStar = getStars(pos,n);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                String[][] haha = new String[n][n];
                copyPos(pos, haha, n);

                if(!haha[i][j].equals("*")){
                    dfStars(haha, haha[i][j], i, j, n);
                    haha = formatPos(haha, n);
                    int curStar = getStars(haha,n);
                
                    int val = curStar - preStar;
                    val = val * val;
                    if(val >= max){
                        max = val;
                    }
                }
            }
        }
        return max;
    }

    public static String getCol(int col){
        if(col == 1){
            return "A";
        }
        if(col == 2){
            return "B";
        }
        if(col == 3){
            return "C";
        }
        if(col == 4){
            return "D";
        }
        if(col == 5){
            return "E";
        }
        if(col == 6){
            return "F";
        }
        if(col == 7){
            return "G";
        }
        if(col == 8){
            return "H";
        }
        if(col == 9){
            return "I";
        }
        if(col == 10){
            return "J";
        }
        if(col == 11){
            return "K";
        }
        if(col == 12){
            return "L";
        }
        if(col == 13){
            return "M";
        }
        if(col == 14){
            return "N";
        }
        if(col == 15){
            return "O";
        }
        if(col == 16){
            return "P";
        }
        if(col == 17){
            return "Q";
        }
        if(col == 18){
            return "R";
        }
        if(col == 19){
            return "S";
        }
        if(col == 20){
            return "T";
        }
        if(col == 21){
            return "U";
        }
        if(col == 22){
            return "V";
        }
        if(col == 23){
            return "W";
        }
        if(col == 24){
            return "X";
        }
        if(col == 25){
            return "Y";
        }
        return "Z";
    }

    public static ArrayList<Integer> getPoints(String[][] pos, int n, int preStar){
        Queue<String[][]> q = new LinkedList<String[][]>();
        q = bfs(pos, n);
      
        Queue<Integer> qPoint = new LinkedList<Integer>();

        for(String[][] item : q){
            int curStar = getStars(item, n);
            int point = curStar - preStar;
            point = point * point;
            qPoint.add(point);
        }

        ArrayList<Integer> realPoint = new ArrayList<Integer>();
        int l = qPoint.size();
        int count = 0;
        boolean flag = true;
        int flagCount = 0;

        while(!q.isEmpty()){
            Queue<String[][]> newQ = new LinkedList<String[][]>();
            preStar = getStars(q.peek(), n);
            int curPoint = qPoint.peek();
            realPoint.add(curPoint);
            count++;
            
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
                        flagCount++;
                    }
                    if(flag){
                        point = curPoint - point;
                    }else {
                        point = curPoint + point;
                    }
                    qPoint.add(point);
                }
            }
            if(flagCount == 3){
                break;
            }

            q.remove();
            qPoint.remove();
        }  
        return realPoint;
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
                    flag = false;
                }
            }
            if(flag){
                res.add(q.poll());
            }else{
                q.remove();
            }
        }
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

    public static int minimax(String[][] pos, int depth, boolean isMax, int alpha, int beta, int n, int index, ArrayList<Integer> realPoint){
        if(depth == 2){
            // System.out.println("i: " + index);
            return realPoint.get(index);
        }

        int bestVal = 0;
        Queue<String[][]> q = new LinkedList<String[][]>();
        q = bfs(pos, n);

        if(isMax){
            bestVal = Integer.MIN_VALUE;
            for(String[][] item : q){
                int value = minimax(pos, depth + 1, true, alpha, beta, n, index + 1 + q.size(), realPoint);
                bestVal = Math.max(bestVal, value);
                alpha = Math.max(alpha, bestVal);
                if(beta <= alpha){
                    break;
                }
            }
            return bestVal;
        }else{
            bestVal = Integer.MAX_VALUE;
            for(String[][] item : q){
                int value = minimax(pos, depth + 1, true, alpha, beta, n, index + 1 + q.size(), realPoint);
                bestVal = Math.min(bestVal, value);
                beta = Math.min(beta, bestVal);
                if(beta <= alpha){
                    break;
                }
            }
            return bestVal;
        }
    }

    public static void writeRes(int n, String[][] pos, PrintWriter pWriter, int col, int row){
        pWriter.print(getCol(col));
        pWriter.println(row);

        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                pWriter.print(pos[i][j]);
            }
            if(i != n - 1){
                pWriter.println();
            }
        }
        pWriter.close();
    }
}