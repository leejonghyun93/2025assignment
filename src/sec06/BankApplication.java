package sec06;

import java.util.Scanner;

public class BankApplication {
    private static Scanner sc = new Scanner(System.in);
    private static Account[] account = new Account[100];  //  최대 100개 저장할 수 있는 배열
    private static int accountCount = 0;

    public static void main(String[] args) {
        boolean input = true;
        while (input) {
            System.out.println("-------------------------------------------");
            System.out.println("1.계좌생성 | 2.계좌목록 | 3.예금 | 4.출금 | 5.종료");
            System.out.println("-------------------------------------------");
            System.out.print("선택> ");
            int strNum = sc.nextInt();
            sc.nextLine();

            switch (strNum) {
                case 1:
                    accountCreate(); // 계좌생성 함수 호출
                    break;
                case 2:
                    accountList(); // 계좌목록 함수 호출
                    break;
                case 3:
                    deposit(); // 예금 함수 호출
                    break;
                case 4:
                    withdrawal(); // 출금 함수 호출
                    break;
                case 5:
                    input = false;
                    break;
            }
        }
        System.out.println("프로그램 종료");
    }

// 계좌 생성 메서드

    private static void accountCreate() {
        System.out.println("------------");
        System.out.println("계좌생성");
        System.out.println("------------");

        System.out.println("계좌번호: ");
        String anb = sc.nextLine();


        System.out.println("계좌주: ");
        String aname = sc.nextLine();

        System.out.println("초기 입금액:");
        int balance = Integer.parseInt(sc.nextLine());

        addAccount(anb, aname, balance);
    }



// 계좌 생성 후 1씩 증가하는 메서드

    public static void addAccount(String anb, String aname, int balance) {
        if (accountCount >= account.length) {
            System.out.println("더 이상 계좌를 생성할 수 없습니다.");
            return;
        }

        account[accountCount] = new Account(anb, aname, balance);
        accountCount++;
        System.out.println("결과: 계좌가 생성되었습니다.");
    }


// 계좌 목록 보여주는 메서드

    private static void accountList() {
        if (accountCount == 0) {
            System.out.println("등록된 계좌가 없습니다.");
            return;
        }

        System.out.println("------------");
        System.out.println("계좌목록");
        System.out.println("------------");
        for (int i = 0; i < accountCount; i++) {
            account[i].printInfo();
        }
    }

// 계좌 예금 메서드

    private static void deposit() {

        System.out.println("------------");
        System.out.println("예금");
        System.out.println("------------");

        System.out.println("계좌번호: ");
        String anb = sc.nextLine();

        Account bc = findAccount(anb);
        if (bc == null) {
            System.out.println("계좌를 찾을 수 없습니다.");
            return;
        }

        System.out.println("예금액:");
        int balan = Integer.parseInt(sc.nextLine());
        bc.deposit(balan);
    }

// 계좌 출금 메서드

    private static void withdrawal() {
        System.out.println("------------");
        System.out.println("출금");
        System.out.println("------------");

        System.out.println("계좌번호: ");
        String anb = sc.nextLine();

        Account bc = findAccount(anb);

        if (bc == null) {
            System.out.println("계좌를 찾을 수 없습니다.");
            return;
        }


        System.out.println("예금액:");
        int balan = Integer.parseInt(sc.nextLine());
        bc.withdrawal(balan);
    }

// 예금 또는 출금시 같은 계좌인지 확인하는 메서드

    private static Account findAccount(String anb) {
        for (int i = 0; i < accountCount; i++) {

            if (account[i].getAnb().equals(anb)) {
                return account[i];
            }
        }
        return null;
    }
}
