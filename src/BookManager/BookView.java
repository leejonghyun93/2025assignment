package BookManager;

public class BookView {
    public void view() {
        System.out.println("도서 항목");
        System.out.println("==================================================================");
        System.out.println("1. 도서 추가 | 2. 도서 목록 및 검색 | 3. 도서 수정  | 4. 도서 삭제 | 5. 종료");
        System.out.print("선택>");
    }

    public void updateSelectView() {
        System.out.println("수정할 항목을 선택하세요 (1. ISBN | 2. 제목 | 3. 가격 | 4. 저자 | 5. 출판일 | 6. 종료)");
        System.out.print("선택> ");
    }

    public void printBookListHeader() {
        System.out.printf("%-12s | %-20s | %-10s | %-10s | %-10s%n", "도서번호", "도서 제목", "도서 가격", "도서 저자", "출판일");
    }

    public void searchChoiceBook() {
        System.out.println("도서 검색을 선택하셨습니다. 검색하실 항목을 선택해주세요.");
        System.out.println("1. ISBN | 2. 제목 | 3. 가격 | 4. 저자 | 5. 출판일 | 6. 종료");
    }
}
