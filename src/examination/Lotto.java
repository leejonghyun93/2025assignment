package examination;

import java.util.Arrays;
import java.util.Scanner;

public class Lotto {
    Scanner sc = new Scanner(System.in);

    public void run() {
        boolean input = true;

        while (input) {
            System.out.println("----------------------------------");
            System.out.println("1. 로또 돌리기 | 2. 로또 뽑기 | 3. 종료");
            System.out.print("선택> ");

            int select = sc.nextInt();
            sc.nextLine();

            switch (select) {
                case 1:
                    roulette();
                    break;
                case 2:
                    draw();
                    break;
                case 3:
                    input = false;
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
            }
        }
        System.out.println("프로그램 종료");
    }

    public void roulette() {
        for (int i = 1; i <= 45; i++) {
            System.out.print(i + " ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public void draw() {
        int[] lotto = new int[6];

        for (int i = 0; i < 6; i++) {
            lotto[i] = (int) (Math.random() * 45) + 1;
            for (int j = 0; j < i; j++) {
                if (lotto[i] == lotto[j]) {
                    i--;
                }

            }
        }
        System.out.println(Arrays.toString(lotto));
    }
}
