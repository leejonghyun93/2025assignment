package sec06;

public class Account {
//    private int balance;
//    final int MIN_BALANCE = 0;
//    final int MAX_BALANCE = 1000000;
//
//    public Account() {
//    }
//
//
//    public Account(int balance) {
//        this.balance = balance;
//    }
//
//
//    public void setBalance(int balance) {
//        if (MIN_BALANCE <= balance && balance <= MAX_BALANCE) {
//            this.balance = balance;
//        } else {
//            this.balance = 0;
//        }
//    }
//
//
//    public int getBalance() {
//        return balance;
//    }
private String anb; // 계좌번호

    private String aname; // 계좌 이름
    private int balance;  // 금액


    public Account(String anb, String aname, int balance) {
        this.anb = anb;
        this.aname = aname;
        this.balance = balance;
    }

    public String getAnb() {
        return anb;
    }

    public void setAnb(String anb) {
        this.anb = anb;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

// 계좌 목록 출력 메서드

    public void printInfo() {
        System.out.printf(" %s  %s  %d\n", anb, aname, balance);
    }

// 계좌 예금 + 기능 메서드

    public void deposit(int balan) {
        balance += balan;
    }

// 계좌 출금 - 기능 메서드

    public void withdrawal(int balan) {
        balance -= balan;

    }
}