package examination;

import java.util.Scanner;

public class BookView {
    private Scanner sc = new Scanner(System.in);
    private Book book;

    public void run() {
        boolean input = true;

        while (input) {
            System.out.println("메뉴 선택");
            System.out.println("1. 책 입력 | 2. 책 정보 조회 | 3. 종료");
            System.out.print("선택> ");

            int select = sc.nextInt();
            sc.nextLine();

            switch (select) {
                case 1:
                    createBook();
                    break;
                case 2:
                    viewBook();
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

    private void createBook() {
        if (book != null) {
            System.out.println("이미 책이 출판되어 다시 입력할 수 없습니다.");
            return;
        }
        System.out.print("책 이름을 입력하세요: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("책 이름은 비어 있을 수 없습니다. 다시 입력하세요: ");
            name = sc.nextLine().trim();
        }

        int price = 0;
        while (true) {
            System.out.print("책 가격을 입력하세요: ");
            price = sc.nextInt();
            if (price > 0) {
                break;
            }
            System.out.println("책 가격은 0보다 커야 합니다.");
        }
        book = new Book(name, price);
        System.out.println("\n책이 출판되었습니다.");
    }

    public void viewBook() {
        if (book == null) {
            System.out.println("출판된 책이 없습니다. 먼저 책을 입력하세요.");
        } else {
            System.out.println("책 이름 : " + book.getName());
            System.out.println("책 가격 : " + book.getPrice());
        }
    }

    public static void main(String[] args) {
        BookView bookView = new BookView();
        bookView.run(); // 실행
    }
}