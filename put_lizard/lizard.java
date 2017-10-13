//AI- Lizard
//Author: Chufan Deng

import java.io.*;
import java.util.*;
public class lizard {
    public static void main(String args[]) throws IOException {
        File inFile = new File ("input.txt");
        Scanner sc = new Scanner (inFile);

        //scan first 3 lines
        String search_method = "";
        if(sc.hasNextLine()){
            search_method = sc.nextLine();
        }
        int n = 0;
        if(sc.hasNextLine()){
            n = sc.nextInt();
        }
        int p = 0;
        if(sc.hasNextLine()){
            p = sc.nextInt();
        }

        //scan the datas
        int[][] pos = new int[n][n];
        int inputRow = 0;
        String line = "";
        while(sc.hasNextLine() && inputRow < n){
            line = sc.next();
            for(int i = 0; i < n; i++){
                int num = Integer.parseInt(line.substring(i,i+1));
                pos[inputRow][i] = num;
            }
            inputRow++;
        }
        sc.close();

        //search
        if(search_method.equals("BFS")){
            bfs(n,p,0,0,pos);
        }else if(search_method.equals("DFS")) {
            dfs(n, p, 0, 0, pos);
        }else {
            sa(n,p, pos);
        }

        //sa(n,p, pos);
        //bfs(n,p,0,0,pos);
        //dfs(n, p, 0, 0, pos);
    }

    //sa search
    public static void sa(int n, int p, int[][] pos)throws IOException{
        File outFile = new File ("output.txt");
        FileWriter fWriter = new FileWriter (outFile);
        PrintWriter pWriter = new PrintWriter (fWriter);

        int count = 0;
        int T = 3000;
        while(T > 0){
            int[][] newPos = new int[n][n];
            copyPos(pos, newPos, n);
            for(int i = 0; i < T; i++){
                int r = (int) Math.floor(Math.random() * n);
                int c = (int) Math.floor(Math.random() * n);

                if(isValidSa(newPos, r, c, n)){
                    newPos[r][c] = 1;
                }
                count = countOnes(newPos, n);
                if(count == p){
                    writeRes(n, newPos, pWriter);
                    //System.exit(1);
                    return;
                }
            }
            T--;
        }
        failOutput(pWriter);
    }

    //bfs search
    public static void bfs(int n,int p, int r,int c,  int[][] pos)throws IOException {
        File outFile = new File ("output.txt");
        FileWriter fWriter = new FileWriter (outFile);
        PrintWriter pWriter = new PrintWriter (fWriter);

        Queue<int[][]> q = new LinkedList<int[][]>();
        int count = 0;

        //insert first 1
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if (isValid(pos, i, j, n)) {
                    pos[i][j] = 1;
                    int[][] newPos = new int[n][n];
                    copyPos(pos, newPos, n);
                    q.add(newPos);
                    pos[i][j] = 0;
                }
            }
        }

        while(!q.isEmpty()){
            pos = q.remove();
            //find last 1
            int lastRow = 0;
            int lastCol = 0;
            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if(pos[row][col] == 1){
                        lastRow = row;
                        lastCol = col;
                    }
                }
            }
            //search next
            for(int i = lastRow; i < n; i++){
                if(i == lastRow){
                    for(int j = lastCol; j < n; j++){
                        if (isValid(pos, i, j, n)) {
                            pos[i][j] = 1;
                            count = countOnes(pos, n);
                            if(count == p){
                                writeRes(n, pos, pWriter);
                                //System.exit(1);
                                return;
                            }
                            int[][] newPos = new int[n][n];
                            copyPos(pos, newPos, n);
                            q.add(newPos);
                            pos[i][j] = 0;
                        }
                    }
                }else {
                    for(int j = 0; j < n; j++){
                        if (isValid(pos, i, j, n)) {
                            pos[i][j] = 1;
                            count = countOnes(pos, n);
                            if(count == p){
                                writeRes(n, pos, pWriter);
                                //System.exit(1);
                                return;
                            }
                            int[][] newPos = new int[n][n];
                            copyPos(pos, newPos, n);
                            q.add(newPos);
                            pos[i][j] = 0;
                        }
                    }
                }
            }
        }
        count = countOnes(pos, n);
        if(count == p){
            writeRes(n, pos, pWriter);
        } else{
            failOutput(pWriter);
        }
    }

    //dfs search
    public static void dfs(int n, int p, int r, int c, int[][] pos)throws IOException {
        File outFile = new File ("output.txt");
        FileWriter fWriter = new FileWriter (outFile);
        PrintWriter pWriter = new PrintWriter (fWriter);

        //Stack<Integer> s = new Stack<Integer>();
        Stack<int[][]> s = new Stack<int[][]>();
        int count = 0;

        //insert 1 in first round
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if (isValid(pos, i, j, n)) {
                    pos[i][j] = 1;
                    count = countOnes(pos, n);
                    if(count == p){
                        writeRes(n, pos, pWriter);
                        //System.exit(1);
                        return;
                    }
                    int[][] newPos = new int[n][n];
                    copyPos(pos, newPos, n);
                    s.push(newPos);
                }
            }
        }

        while(!s.isEmpty()){
            pos = s.peek();
            s.pop();

            //find last 1
            int lastRow = 0;
            int lastCol = 0;
            for(int row = 0; row < n; row++){
                for(int col = 0; col < n; col++){
                    if(pos[row][col] == 1){
                        lastRow = row;
                        lastCol = col;
                    }
                }
            }
            pos[lastRow][lastCol] = 0;

            //search next
            for(int i = lastRow; i < n; i++){
                if(i == lastRow){
                    for(int j = lastCol + 1; j < n; j++){
                        if (isValid(pos, i, j, n)) {
                            pos[i][j] = 1;
                            count = countOnes(pos, n);
                            if(count == p){
                                writeRes(n, pos, pWriter);
                                //System.exit(1);
                                return;
                            }
                            int[][] newPos = new int[n][n];
                            copyPos(pos, newPos, n);
                            s.push(newPos);
                        }
                    }
                }else {
                    for(int j = 0; j < n; j++){
                        if (isValid(pos, i, j, n)) {
                            pos[i][j] = 1;
                            count = countOnes(pos, n);
                            if(count == p){
                                writeRes(n, pos, pWriter);
                                //System.exit(1);
                                return;
                            }
                            int[][] newPos = new int[n][n];
                            copyPos(pos, newPos, n);
                            s.push(newPos);
                        }
                    }
                }
            }
        }
        failOutput(pWriter);
    }

    public static void copyPos(int[][] pos, int[][] newPos, int n){
        for(int k = 0; k < n; k++){
            for(int l = 0; l < n; l++){
                newPos[k][l] = pos[k][l];
            }
        }
    }

    public static int countOnes(int[][]pos, int n){
        int count = 0;
        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                if(pos[row][col] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    public static void writeRes(int n, int[][] pos, PrintWriter pWriter){
        pWriter.println("OK");
        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                if(col != n - 1 || row == n - 1){
                    pWriter.print(pos[row][col]);
                }
                else{
                    pWriter.println(pos[row][col]);
                }
            }
        }
        pWriter.close();
    }

    public static boolean isValid(int[][] pos, int r, int c, int n) {
        if(pos[r][c] == 2 || pos[r][c] == 1){
            return false;
        }
        return noLeft(r,c,pos) && noUp(r,c,pos) && noLeftTop(r,c,pos) && noRightTop(r,c,n,pos);
    }
    public static boolean noLeft(int r, int c, int[][] pos){
        for(int j = c - 1; j >= 0; j--){
            if(pos[r][j] == 1){
                for(int k = c - 1; k > j; k--){
                    if(pos[r][k] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noUp(int r, int c, int[][] pos){
        for(int i = r - 1; i >=0 ; i--){
            if(pos[i][c] == 1){
                for(int k = r - 1; k > i; k--){
                    if(pos[k][c] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noLeftTop(int r, int c, int[][] pos){
        for (int i = 1; i <= Math.min(r, c); i++) {
            if(pos[r - i][c - i] == 1){
                for(int k = r- i+ 1; k < r; k++){
                    if(pos[k][c+k-r] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noRightTop(int r, int c, int n, int[][] pos){
        for (int i = 1; i < Math.min(r + 1, n - c); i++) {
            if(pos[r - i][c + i] == 1){
                for(int k = r- i+ 1; k < r; k++){
                    if(pos[k][r+c-k] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    // for sa search
    public static boolean isValidSa(int[][] pos, int r, int c, int n) {
        return noRight(r,c,n,pos) && noDown(r,c,n, pos) && noRightDown(r,c,n, pos) && noLeftDown(r,c,n,pos) && isValid(pos, r, c, n);
    }
    public static boolean noRight(int r, int c,int n, int[][] pos){
        for(int j = c + 1; j < n; j++){
            if(pos[r][j] == 1){
                for(int k = c + 1; k < j; k++){
                    if(pos[r][k] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noDown(int r, int c, int n, int[][] pos){
        for(int i = r + 1; i < n; i++){
            if(pos[i][c] == 1){
                for(int k = r + 1; k < i; k++){
                    if(pos[k][c] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noRightDown(int r, int c, int n, int[][] pos){
        for (int i = 1; i < Math.min(n - r, n - c); i++) {
            if(pos[r + i][c + i] == 1){
                for(int k = r + 1; k < r + i; k++){
                    if(pos[k][c+k-r] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
    public static boolean noLeftDown(int r, int c, int n, int[][] pos){
        for (int i = 1; i <= Math.min(c, n - 1 - r); i++) {
            if(pos[r + i][c - i] == 1){
                for(int k = r + 1; k < r + i; k++){
                    if(pos[k][r+c-k] == 2){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    public static void failOutput(PrintWriter pWriter){
        pWriter.print("FAIL");
        pWriter.close();
    }

}
