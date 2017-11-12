//AI- FOL Resolution
//Author: Chufan Deng

import java.io.*;
import java.util.*;
public class homework {
    public static void main(String args[]) throws IOException {

        File inFile = new File ("input4.txt");
        Scanner sc = new Scanner (inFile);

        int n = 0;
        if(sc.hasNextLine()){
            n = sc.nextInt();
        }
        ArrayList<String> checkList = new ArrayList<String>();
        int checkAmount = 0;
        String line = "";
        while(sc.hasNextLine() && checkAmount < n){
            line = sc.next();
            checkList.add(line);
            checkAmount++;
        }
        // printList(checkList);

        int p = 0;
        if(sc.hasNextLine()){
            p = sc.nextInt();
        }
        sc.nextLine();
        ArrayList<ArrayList<String>> knowledgeList = new ArrayList<ArrayList<String>>();
        ArrayList<String> knowledge = new ArrayList<String>();
        Queue<String> qConstant = new LinkedList<String>();

        while(sc.hasNextLine()){
            line = sc.nextLine();
            if(line.indexOf("|") < 0){
                String sub = line.substring(line.indexOf("("), line.indexOf(")"));
                if(containsUpper(sub) || countComma(sub) == 0){
                    qConstant.add(line);
                }else {
                    knowledge.add(line);
                    knowledgeList.add(knowledge);
                }
            }else {
                Scanner sc_knowledge = new Scanner (line);
                while(sc_knowledge.hasNext()){
                    String k = sc_knowledge.next();
                    if(!k.equals("|")){
                        knowledge.add(k);
                    }
                }
                knowledgeList.add(knowledge);
            }
            
            // System.out.println(knowledge.size());

            knowledge = new ArrayList<String>();

        }
        // System.out.println(knowledgeList.toString());
        // System.out.println(qConstant.toString());

        sc.close();

        int count = 100;
        while(!qConstant.isEmpty() && count > 0){
            String reso = qConstant.poll();
            reso = getReverse(reso);
            String funcReso = reso.substring(0, reso.indexOf("("));
            for(int i = 0; i < knowledgeList.size(); i++){
                knowledge = knowledgeList.get(i);
                for(int j = 0; j < knowledge.size(); j++){
                    String know = knowledge.get(j);
                    if(funcReso.equals(know.substring(0, know.indexOf("(")))){
                        boolean flag = false;
                        String sub = know.substring(know.indexOf("(") + 1, know.indexOf(")"));
                        ArrayList<String> variables = new ArrayList<String>(Arrays.asList(sub.split(",")));
                        for(int k = 0; k < variables.size(); k++){
                            if(!containsUpper(variables.get(k))){
                                flag = true;
                            }
                        }
                        if(reso.equals(know)){
                            flag = true;
                        }
                        if(flag){
                            ArrayList<String> newKnowledge = getReso(knowledge, reso, j);
                            knowledgeList.add(newKnowledge);
                            if(newKnowledge.size() == 1){
                        // print(newKnowledge.get(0));
                                qConstant.add(newKnowledge.get(0));
                            }

                        }
                    }
                }
            }
            count--;
            // for(int i = 0; i < knowledgeList.size(); i++){
            //     if(knowledge.get(i).size() == 1)
            // }
            
            reso = getReverse(reso);
            knowledge = new ArrayList<String>();
            knowledge.add(reso);
            knowledgeList.add(knowledge);
            qConstant.add(reso);
        }

        // print(knowledgeList.toString());
        
        // System.out.println(knowledgeList.size());

        Set<String> set = new HashSet<String>();
        for(int i = 0; i < knowledgeList.size(); i++){
            if(knowledgeList.get(i).size() == 1){
                set.add(knowledgeList.get(i).get(0));
            }
        }

        print(set.toString());

        // System.out.println(set.size());

        for(int i = 0; i < n; i++){
            String check = checkList.get(i);
            String funcReso = check.substring(0, check.indexOf("("));

            if(set.contains(check)){
                print("TRUE");
            }
            else{
                boolean right = false;
                for(String s : set){
                    String sub = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                    String sFunc = s.substring(0, s.indexOf("("));
                    if(sFunc.equals(funcReso) && !containsUpper(sub)){
                        right = true;
                        print("TRUE");
                    }
                }
                if(!right){
                    print("FALSE");
                }
                
            }
        }

        // File outFile = new File ("output.txt");
        // FileWriter fWriter = new FileWriter (outFile);
        // PrintWriter pWriter = new PrintWriter (fWriter);
    }

    public static ArrayList<String> getReso(ArrayList<String> knowledge, String s, int i){
        ArrayList<String> res = new ArrayList<String>();
        String sub = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
        ArrayList<String> constants = new ArrayList<String>(Arrays.asList(sub.split(",")));
        sub = knowledge.get(i).substring(knowledge.get(i).indexOf("(") + 1, knowledge.get(i).indexOf(")"));
        ArrayList<String> variables = new ArrayList<String>(Arrays.asList(sub.split(",")));
        for(int k = 0; k < variables.size(); k++){
            if(containsUpper(variables.get(k)) && !variables.get(k).equals(constants.get(k))){
                variables.remove(k);
                constants.remove(k);
            }
        }
        for(int j = 0; j < knowledge.size(); j++){
            if(j != i){
                String know = knowledge.get(j);
                sub = know.substring(know.indexOf("(") + 1, know.indexOf(")"));
                ArrayList<String> newChange = new ArrayList<String>(Arrays.asList(sub.split(",")));
                for(int m = 0; m < newChange.size(); m++){
                    for(int n = 0; n < variables.size(); n++){
                        if(variables.get(n).equals(newChange.get(m))){
                            newChange.set(m, constants.get(n));
                        }
                    }
                }
                String func = knowledge.get(j).substring(0, knowledge.get(j).indexOf("("));

                String changed = newChange.toString();
                changed = changed.replace("[", "(").replace("]",")").replace(" ", "");
                changed = func.concat(changed);
                res.add(changed);
            }
        }
        return res;
    }

    public static int countComma(String s){
        int commaCount = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.substring(i, i+1).equals(",")){
                commaCount++;
            }
        }
        return commaCount;
    }

    public static boolean containsUpper(String s){
        int l = s.length();
        char[] chars = s.toCharArray();
        for(int i = 0; i < l; i++){
            if(Character.isUpperCase(chars[i])){
                return true;
            }
        }
        return false;
    }

    public static String getReverse(String s){
        String output = "";
        if(s.substring(0,1).equals("~")){
            output = s.substring(1, s.length());
        }
        else {
            output = "~".concat(s);
        }
        return output;
    }

    // if assumption is wrong 
    public static boolean isWrong(ArrayList<ArrayList<String>> knowledgeList){
        int l = knowledgeList.size();
        for(int i = 0; i < l; i++){
            if(knowledgeList.get(i).size() == 0){
                return false;
            }
        }
        return true;
    }

    public static void removeKnowledge(ArrayList<ArrayList<String>> knowledgeList, String s){
        int l = knowledgeList.size();
        for(int i = 0; i < l; i++){
            ArrayList<String> knowledge = knowledgeList.get(i);
            int size = knowledge.size();
            for(int j = 0; j < size; j++){
                if(s.equals(knowledge.get(j))){
                    knowledge.remove(j);
                    size--;
                }
            }
        }
    }

    public static void printList(ArrayList<String> list){
        for(String elem : list){
            System.out.println(elem);
        }
    }

    public static void print(String s){
        System.out.println(s);
    }
}