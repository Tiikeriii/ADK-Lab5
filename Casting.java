import java.util.BitSet;

public class Casting {
    Kattio io;
    int n, s, k;
    BitSet[] compatible;
    BitSet[] canPlay;
    BitSet[] casted;
    BitSet notCasted;

    void readInput() {
        n = io.getInt();
        s = io.getInt();
        k = io.getInt();
        compatible = new BitSet[n + 1];
        canPlay = new BitSet[k + 1];
        casted = new BitSet[n + k + 1];
        notCasted = new BitSet(n + 1);
        notCasted.set(1, n + 1);
        
        for (int a = 1; a <= k; a++) {
            canPlay[a] = new BitSet(n + 1);
        }

        for (int a = 1; a < n + k + 1; a++) {
            casted[a] = new BitSet(n + 1);
        }

        for (int r = 1; r <= n; r++) {
            int cnt = io.getInt();
            for (int j = 0; j < cnt; j++) {
                int actor = io.getInt();
                canPlay[actor].set(r);
            }
        }

        for (int r = 1; r <= n; r++) {
            compatible[r] = new BitSet(n + 1);
            compatible[r].set(1, n + 1);
        }

        for (int sc = 1; sc <= s; sc++) {
            int a = io.getInt();
            BitSet bitMask = new BitSet(n + 1);
            int[] roles = new int[a];
            for (int j = 0; j <  a; j++) {
                roles[j] = io.getInt();
                bitMask.set(roles[j]);
            }
            for (int j = 0; j < a; j++) {
                compatible[roles[j]].andNot(bitMask);
            }
        }
    }

    static BitSet BitSetAnd(BitSet a, BitSet b) {
        BitSet candidate = (BitSet) a.clone();
        candidate.and(b);
        return candidate;
    }

    BitSet cast(BitSet candidate, int a) {
        int r = candidate.nextSetBit(0);
        casted[a].set(r);
        notCasted.clear(r);
        candidate.and(compatible[r]);
        candidate.clear(r);
        return candidate;
    }

    void castDivas() {
        BitSet candidate1 = BitSetAnd(canPlay[1], notCasted);
        BitSet candidate2 = BitSetAnd(canPlay[2], notCasted);
        while (!candidate1.isEmpty()) {
            int r = candidate1.nextSetBit(0);
            BitSet temp = BitSetAnd(candidate2, compatible[r]);
            temp.clear(r);
            if (temp.isEmpty()) {
                candidate1.clear(r);
                continue;
            }
            casted[1].set(r);
            candidate1.and(compatible[r]);
            candidate1.clear(r);
            notCasted.clear(r);
            candidate2 = temp;
            
        }
        while (!candidate2.isEmpty()) {
            cast(candidate2, 2);
        }
    }

    void castActors() {
        int a = 3;
        while (!notCasted.isEmpty()) {
            if (a <= k) {
                BitSet candidate = BitSetAnd(canPlay[a], notCasted);
                while (!candidate.isEmpty()) {
                    cast(candidate, a);
                }
            } else {
                int r = notCasted.nextSetBit(0);
                casted[a].set(r);
                notCasted.clear(r);
            }
            a++;
        }
    }

    void printOutput() {
        int used = 0;
        for (int a = 1; a < casted.length; a++) {
            if (!casted[a].isEmpty()) {
            used++;
            }
        }
        io.println(used);
        for (int a = 1; a <= casted.length - 1; a++) {
            if (casted[a].isEmpty()) {
                continue;
            }
            int roles = casted[a].cardinality();
            io.print(a + " " + roles);
            for (int role = casted[a].nextSetBit(0); role >= 0; role = casted[a].nextSetBit(role + 1)) {
                io.print(" " + role);
            }
            io.println();
        }
        io.flush();
    }
        Casting() {
            io = new Kattio(System.in, System.out);
            readInput();

            castDivas();

            castActors();

            printOutput();

            io.close();
        }
    public static void main(String args[]) {
        new Casting();
    }
}
