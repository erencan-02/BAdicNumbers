import BAdicNumber.BAdicArray.BAdicNumberArray;
import BAdicNumber.BAdicNumber;

import java.util.Iterator;

public class Main {

    public static void main(String[] args){

        BAdicNumber a = new BAdicNumberArray(2,300);

        System.out.println(a);

        a.shift(10);

        System.out.println(a);
    }
}
