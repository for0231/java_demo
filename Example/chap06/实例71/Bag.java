
import java.util.Random;

/**
 * 口袋字母的简单抽象
 **/
class Bag {
  private Random rand;
  private int letter_counts[] = {
    2, 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2,
    6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
  };
  private int letter_points[] = {
    0, 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
    1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
  };
  private Letter letters[] = new Letter[100];
  private int n = 0;
  
  /** 构造函数通过传进来的参数生成一个Random对象，然后遍历
   *  数组letter_counts，构造合适数目的新的Letter对象 */
  Bag(int seed) {
    rand = new Random(seed);
    for (int i = 0; i < letter_counts.length; i++) {
      for (int j = 0; j < letter_counts[i]; j++) {
        Letter l = new Letter(i == 0 ? '*' : (char)('A' + i - 1),
                              letter_points[i]);
        putBack(l);
      }
    }
  }
  
  /** 从0 ~ n-1 之间挑选一个随机数，利用这个随机数做偏移量从letters
   *  数组中抽取字母
   **/
  synchronized Letter takeOut() {
    if (n == 0)
      return null;
    int i = (int)(rand.nextDouble() * n);
    Letter l = letters[i];
    if (i != n - 1)
      System.arraycopy(letters, i + 1, letters, i, n - i - 1);
    n--;
    return l;
  }
  
  /** 构造函数调用该函数来讲字母方块放入原始口袋中 */
  synchronized void putBack(Letter l) {
    letters[n++] = l;
  }
}