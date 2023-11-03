package com.digital.linearequationsolver;

public class Calculate {
    private int  row;  //  specify the number of variables

    /**
     * the setRow method sets the value of the row
     * the getRow method returns the value of the row
     * @param row
     */
    public void  setRow(int  row){
        this.row = row;
    }
    public int getRow(){
        return row;
    }
    // End of the getters and setters
    public double[] solver(double[][] values) {
        // perform Gauss-Jordan elimination
        if(values[0][0] == 0){
            swap(values);
        }
        for (int i  =0; i< row;i++){
            for(int j = i+1; j<row ;j++){
                double deno = values[i][i];
                double numer =  values[j][i];
                double fraction  = numer/deno;
                values[j]= subtract(values[j],multiply(fraction,values[i]));
            }
        }
        for (int i  =row-1; i>=0;i--){
            for(int j = i-1; j>=0 ;j--){
                double deno = values[i][i];
                double numer =  values[j][i];
                double fraction  = numer/deno;
                values[j]= subtract(values[j],multiply(fraction,values[i]));
            }
        }
        int j =row;
        for(int i=0;i<row;i++){
            values[i][row] = values[i][row]/values[i][i];
        }
        double[] answers =  new double[row];
        int i =0;
        for(double[] b:values){
            answers[i]= b[row];
            i++;
        }
        for(double d: answers){
            System.out.println(d);
        }
        return answers;
    }
    private double[] multiply(double fraction, double mul[]){
        double[] m = mul.clone();
        int i =  0 ;
        for(double va:m){
            m[i] = fraction*va;
            i++;
        }
        return m;
    }
    private double[] subtract(double[] sub1,double[] sub2){
        double[] s = sub1.clone();
        int i =  0;
        for(double sub: s){
            s[i] = sub-sub2[i];
            i++;
        }
        return s;
    }
    private  void swap(double[][] swapable){
        double[] g ;
        int i  =0 ;
        for(double[] s : swapable){
            if(i > 0){
                if(s[0] !=0){
                    g =  swapable[0];
                    swapable[0]= s;
                    swapable[i]= g;
                    break;
                }
            }
            i++;
        }
    }
}
