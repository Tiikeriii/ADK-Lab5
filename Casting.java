import java.util.BitSet;

public class Casting {
    Kattio io;
    int n, s, k;
    BitSet[] compatible;
    BitSet[] canPlay;
    BitSet notCasted;

    void readInput() {
        n = io.getInt();
        s = io.getInt();
        k = io.getInt();
        compatible = new BitSet[n + 1];
        canPlay = new BitSet[k + 1];
        notCasted = new BitSet(n + 1);
        notCasted.set(1, n + 1);

        for (int a = 1; a <= k; a++) {
            canPlay[a] = new BitSet(n + 1);
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
            for (int j = 0; j < a; j++) {
                roles[j] = io.getInt();
                bitMask.set(roles[j]);
            }
            for (int j = 0; j < a; j++) {
                compatible[roles[j]].andNot(bitMask);
            }
        }
    }
}
