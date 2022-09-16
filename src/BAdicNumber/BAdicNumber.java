package BAdicNumber;

import java.util.Iterator;

public interface BAdicNumber{

    int base10();

    void add(int k);

    //void addFast(int k);

    int getLength();

    int getBase();

    Iterator<Integer> iterator();

    void shiftLeft(int k);

    void shiftRight(int k);

    default void shift(int k){
        if(k > 0){
            shiftLeft(k);
        }
        else if(k < 0){
            shiftRight(-k);
        }
    };
}
