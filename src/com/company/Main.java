package com.company;

import java.util.*;

class Optimal_tree_dynamic {

    Subtree[][] optimal_sub;
    int n;
    float[] array_w1;
    float[] arr_w2;


    Optimal_tree_dynamic(float[] x, float[] y) {
        array_w1 = x;
        arr_w2 = y;
        n = array_w1.length;
    }

    class Subtree {
        int root;
        int level;
        float price;
        float add_price;
        Subtree right;
        Subtree left;


        float set_price(int i) {
            float prices = 0;
            if (left != null) {
                prices += left.set_price(i + 1);
            } else {
                prices += arr_w2[root] * (i);
            }
            if (right != null) {
                prices += right.set_price(i + 1);
            } else {
                prices += arr_w2[root + 1] * (i);
            }
            prices += array_w1[root] * i;
            return prices;
        }

        void level(int p) {

            level = p;
            if (left != null)
                left.level(p + 1);
            if (right != null)
                right.level(p + 1);
        }

    }

    void optimal_tree(int x, int y) {
        float minimum = 0;
        float contemporary = 0;

        for (int i = x; i <= x + y; i++) {

            if (i == x)
                contemporary += arr_w2[i];
            else
                contemporary += optimal_sub[x][i - x - 1].add_price;

            if (i == x + y)
                contemporary += arr_w2[i + 1];
            else
                contemporary += optimal_sub[i + 1][x + y - i - 1].add_price;

            contemporary += array_w1[i];
            if (contemporary < minimum || i == x) {
                minimum = contemporary;
                optimal_sub[x][y].price = minimum;
                optimal_sub[x][y].root = i;
                if (i != x)
                    optimal_sub[x][y].left = optimal_sub[x][i - x - 1];
                else
                    optimal_sub[x][y].left = null;
                if (i != x + y)
                    optimal_sub[x][y].right = optimal_sub[i + 1][x + y - i - 1];
                else
                    optimal_sub[x][y].right = null;
            }
            contemporary = 0;
        }
        optimal_sub[x][y].add_price = optimal_sub[x][y].set_price(2);
    }

    void find_tree() {
        create_tab();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                optimal_tree(j, i);

            }
        }
    }


    void print_result(String[] napisy) {
        LinkedList<Subtree> queue = new LinkedList<Subtree>();
        optimal_sub[0][n - 1].level(0);
        queue.addLast(optimal_sub[0][n - 1]);
        while (!queue.isEmpty()) {
            Subtree x = queue.removeFirst();
            if (x.left != null)
                queue.addLast(x.left);
            if (x.right != null)
                queue.addLast(x.right);
            System.out.println(x.level + "->" + (x.root + 1) + "=" + napisy[x.root]);
        }
    }

    void create_tab() {
        optimal_sub = new Subtree[n][];
        for (int i = 0; i < n; i++) {
            optimal_sub[i] = new Subtree[n - i];
            for (int j = 0; j < n - i; j++)
                optimal_sub[i][j] = new Subtree();
        }
    }


}


public class Main {

    public static void main(String[] args) {

        Scanner inScan = new Scanner(System.in);
        int z;
        z = read_number(inScan.nextLine());

        while (z-- > 0) {

            int number_of_words = read_number(inScan.nextLine());
            int arr1[] = new int[number_of_words];
            String[] arrayofwords;
            arrayofwords = new String[number_of_words];
            int arr2[];
            arr2 = new int[number_of_words + 1];
            int s = 0;

            for (int i = 0; i < number_of_words; i++) {
                arr2[i] = read_number(inScan.nextLine());
                s += arr2[i];
                String line;


                line = inScan.nextLine();
                arr1[i] = read_number(line);
                s += arr1[i];
                arrayofwords[i] = read_word(line);

            }
            arr2[number_of_words] = read_number(inScan.nextLine());

            s += arr2[number_of_words];
            float arr_w[];
            arr_w = new float[number_of_words];
            float arr_w2[];
            arr_w2 = new float[number_of_words + 1];

            for (int i = 0; i < number_of_words; i++) {
                arr_w[i] = (float) arr1[i] / s;
                arr_w2[i] = (float) arr2[i] / s;
            }
            arr_w2[number_of_words] = (float) arr2[number_of_words] / s;


            Optimal_tree_dynamic opti;
            opti = new Optimal_tree_dynamic(arr_w, arr_w2);
            opti.find_tree();
            String res;
            res = String.format("%.4f", opti.optimal_sub[0][number_of_words - 1].price);
            for (int j = 0; j < res.length(); j++) {
                if (res.charAt(j) != ',')
                    System.out.print(res.charAt(j));


                else
                    System.out.print('.');
            }
            System.out.println();

            opti.print_result(arrayofwords);
        }
    }


    static String read_word(String linia) {
        int i = 0;
        boolean boo = true, input = false;


        String line = new String();
        while (i < linia.length() && boo) {
            char ch = linia.charAt(i);
            if (('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z')) {
                line = line + ch;
                input = true;

            } else {
                if (input == true)
                    boo = false;
            }
            i++;
        }
        return line;
    }

    static int read_number(String linia) {
        boolean boo = true;
        int i = 0, s = 0;

        while (i < linia.length() && boo) {
            int ch = ((int) linia.charAt(i));
            ch = ch - 48;
            if (0 < ch && ch <= 9) {
                s = ch;
                boo = false;

            }
            i++;
        }
        boo = true;
        int ch_1;
        ch_1 = 1;
        while (i < linia.length() && boo) {
            int ch = ((int) linia.charAt(i));
            ch = ch - 48;
            if (0 <= ch && ch <= 9) {
                s = s * 10 + ch;
                ch_1 = ch_1 * 11;

            } else
                boo = false;
            i++;
        }
        return s;
    }
}

