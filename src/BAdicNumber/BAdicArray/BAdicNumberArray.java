package BAdicNumber.BAdicArray;

import BAdicNumber.BAdicNumber;

import java.util.Arrays;
import java.util.Iterator;

public class BAdicNumberArray implements BAdicNumber {

    private final int base;
    private int[] num;
    private final int initialSize;
    private int currentBase10Value;

    public BAdicNumberArray(int base, int initialValue){
        this.base = base;
        this.initialSize = 8;
        this.num = new int[this.initialSize];
        this.add(initialValue);
        this.currentBase10Value = initialValue;
    }

    public BAdicNumberArray(int base){
        this.base = base;
        this.initialSize = 8;
        this.num = new int[this.initialSize];
        this.currentBase10Value = 0;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for(int a : this.num){
            s.append(a);
        }

        return s.toString();
    }

    public int base10(){
        /*
        int sum = 0;
        int p = 1;

        for(int i=this.num.length-1; i>=0; i--){
            sum += p * this.num[i];
            p *= this.base;
        }

        return sum;*/

        return this.currentBase10Value;
    }

    private void one(boolean increase){
        for(int i=this.num.length-1; i>=0; i--){
            if(this.num[i] + 1 == this.base){
                this.num[i] = 0;
            }
            else{
                this.insert(increase ? 1 : -1, i);
                return;
            }
        }

        if(this.num[0] == 0){
            int oldLength = this.num.length;
            this.num = new int[2*oldLength];
            this.num[oldLength - 1] = 1;
        }
    }


    public void add(int k){
        int i = 0;
        int q = 1;

        this.currentBase10Value += k;

        while(q*this.base <= k){
            q *= this.base;
            i++;
        }

        while(k > 0){

            int entry = Math.floorDiv(k, q);

            if(entry >= this.num.length){
                this.extend();
            }

            this.insert(entry, i);

            k -= entry * q;
            q /= this.base;
            i--;
        }
    }

    @Override
    public int getLength() {
        return this.num.length;
    }

    private void extend(){
        int[] tmp = this.num;
        this.num = new int[tmp.length * 2];

        for(int i=0; i<tmp.length; i++){
            this.num[i+tmp.length] = tmp[i];
        }
    }

    public void reduce(){
        if(this.num.length == this.initialSize){
            return;
        }

        int[] tmp = this.num;
        int newLength = tmp.length/2;
        this.num = new int[newLength];

        for(int i=0; i<this.num.length; i++){
            this.num[i] = tmp[newLength + i];
        }
    }

    public void insert(int k, int i){

        while(true) {
            if(k == 0){
                return;
            }

            int index = this.num.length - i - 1;

            if(index < 0){
                this.extend();
                continue;
            }

            int rest = 0;
            int currentEntry = this.num[index];

            if(currentEntry == this.base-1){
                this.num[index] = 0;
            }
            else if (currentEntry + k >= this.base) {
                rest = this.base - 1 - currentEntry;
                this.num[index] = this.base - 1;
                k -= rest;
            }
            else{
                this.num[index] += k;
                return;
            }

            i++;
        }
    }


    @Override
    public boolean equals(Object other){
        if(other instanceof BAdicNumber){
            return ((BAdicNumberArray) other).base10() == this.base10();
        }
        else{
            return false;
        }
    }

    @Override
    public int getBase(){
        return this.base;
    }

    @Override
    public Iterator<Integer> iterator(){
        return Arrays.stream(this.num).iterator();
    }

    @Override
    public void shiftLeft(int k) throws IllegalArgumentException{
        if(k < 0){
            throw new IllegalArgumentException();
        }

        if(k == 0){
            return;
        }

        if(this.lastEqualZero(k)){
            for(int i=k; i<this.num.length; i++){
                this.num[i-k] = this.num[i];
            }
        }
        else{
            int n = (int) Math.ceil(Math.log(1 + (k+0.0)/this.num.length) / Math.log(2));

            for(int i=0; i<n; i++){
                this.extend();
            }

            for(int i=k; i<this.num.length; i++){
                this.num[i-k] = this.num[i];
                this.num[i] = 0;
            }
        }
    }

    @Override
    public void shiftRight(int k) {
        if(k < 0){
            throw new IllegalArgumentException();
        }

        if(k == 0){
            return;
        }

        int leadingZeros = this.leadingZerosCount();
        int numLength = this.num.length;

        if(k >= numLength || leadingZeros+k >= numLength){
            Arrays.fill(this.num, 0);
            return;
        }


        for(int i=numLength-k-1; i>=0; i--){
            this.num[i+k] = this.num[i];
            this.num[i] = 0;
        }
    }

    private boolean lastEqualZero(int n){

        for(int i=0; i<n; i++){
            if(this.num[i] != 0){
                return false;
            }
        }

        return true;
    }

    private int leadingZerosCount(){
        int c = 0;

        for(int i=0; i<this.num.length; i++){
            if(this.num[i] == 0){
                c++;
            }
            else{
                return c;
            }
        }

        return c;
    }
}
