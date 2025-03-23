package BookManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class BookManager {
    private Map<String, Book> bookMap = new HashMap<>();
    private static BookView bookView = new BookView();
    private static Scanner sc = new Scanner(System.in);
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private int getUserInput(int min, int max) {
        int input = readInt();
        if (!isValidRange(input, min, max)) {
            throw new IllegalArgumentException(min + "부터 " + max + " 사이의 숫자를 입력해주세요.");
        }
        return input;
    }

    private int readInt() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("숫자가 아닌 값이 입력되었습니다. 숫자를 입력해주세요.");
        } finally {
            sc.nextLine();
        }
    }


    private boolean executeMenu(int select) {
        switch (select) {
            case 1 -> inputBook();
            case 2 -> showBookList();
            case 3 -> bookUpdate();
            case 4 -> bookDelete();
            case 5 -> {
                return false;
            }
            default -> throw new IllegalArgumentException("잘못된 메뉴 선택입니다.");
        }
        return true;
    }

    public void run() {
        boolean running = true;

        while (running) {
            bookView.view();
            int select = getUserInput(1, 5); // 1~5 범위 입력 받기
            running = executeMenu(select);
        }
        System.out.println("프로그램 종료");
    }

    public void inputBook() {
        System.out.println("1. 도서 추가를 누르셨습니다.");
        System.out.println("아래 항목들을 입력해주세요.");

        String isbn = getValidIsbn();
        String title = updateBookField("도서 제목");
        int price = getValidPrice();
        String author = updateBookField("도서 저자");
        LocalDate publicationDate = getValidDate();

        createBook(isbn, title, price, author, publicationDate);
    }

    private String updateBookField(String fieldName) {
        System.out.print(fieldName + "을(를) 입력해주세요: ");
        return sc.nextLine();
    }

    private void createBook(String isbn, String title, int price, String author, LocalDate publicationDate) {
        Book book = new Book(isbn, title, price, author, publicationDate);
        bookMap.put(isbn, book);
        System.out.println("도서가 추가되었습니다.");
    }

    public void showBookList() {
        System.out.println("목록을 보실려면 L 검색을 보실려면 S로 눌러주세요.");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("L")) {
            displayBookList();
        } else if (choice.equalsIgnoreCase("S")) {
            searchBooks();
        }
    }

    public void displayBookList() {
        System.out.println("도서 리스트를 선택하셨습니다.");

        if (bookMap.isEmpty()) {
            System.out.println("현재 도서 목록에 아무것도 없습니다.");
        } else {
            bookView.printBookListHeader();
            printBookDetails();
        }
    }

    private void printBookDetails() {
        for (Book book : bookMap.values()) {
            System.out.printf("%-12s | %-20s | %-10d | %-10s | %-10s%n", book.getIsbn(), book.getTitle(),
                    book.getPrice(), book.getAuthor(), book.getPublicationDate());
        }
    }

    public void bookUpdate() {
        System.out.println("수정할 도서의 ISBN을 입력하세요.");
        String isbn = sc.nextLine();

        if (!bookMap.containsKey(isbn)) {
            System.out.println("해당 ISBN은 존재하지 않습니다.");
            return;
        }
        Book book = bookMap.get(isbn);
        boolean updated = false;

        while (true) {
            bookView.updateSelectView();
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    updated = updateIsbn(book);
                    break;
                case 2:
                    updated = updateTitle(book);
                    break;
                case 3:
                    updated = updatePrice(book);
                    break;
                case 4:
                    updated = updateAuthor(book);
                    break;
                case 5:
                    updated = updatePublicationDate(book);
                    break;
                case 6:
                    displayUpdateResult(updated);
                    return;
                default:
                    System.out.println("올바른 옵션을 선택하세요.");
                    break;
            }
        }
    }

    // ISBN 업데이트 처리
    private boolean updateIsbn(Book book) {
        String newIsbn = getValidIsbn();
        if (newIsbn != null) {
            book.setIsbn(newIsbn);
            return true;
        }
        return false;
    }

    private boolean updateTitle(Book book) {
        String newTitle = updateBookField(book.getTitle());
        book.setTitle(newTitle);
        return true;
    }

    private boolean updatePrice(Book book) {
        int newPrice = getValidPrice();
        if (newPrice != -1) {
            book.setPrice(newPrice);
            return true;
        }
        return false;
    }

    private boolean updateAuthor(Book book) {
        String newAuthor = updateBookField(book.getAuthor());
        book.setAuthor(newAuthor);
        return true;
    }

    private boolean updatePublicationDate(Book book) {
        LocalDate newDate = getValidDate();
        if (newDate != null) {
            book.setPublicationDate(newDate);
            return true;
        }
        return false;
    }

    private void displayUpdateResult(boolean updated) {
        if (updated) {
            System.out.println("도서 정보가 성공적으로 업데이트되었습니다.");
        } else {
            System.out.println("변경된 내용이 없습니다.");
        }
    }

    // 도서 검색 메서드
    public void searchBooks() {
        boolean found = false;

        while (true) {
            bookView.searchChoiceBook();
            int searchBookChoice = sc.nextInt();
            sc.nextLine();

            String searchIsbn = "";
            String searchTitle = "";
            Integer searchPrice = null;
            String searchAuthor = "";
            String searchPublicationDate = "";

            // 사용자 입력에 따라 검색 조건 설정
            switch (searchBookChoice) {
                case 1:
                    System.out.println("ISBN 검색을 선택하셨습니다. 검색하실 ISBN을 작성해주세요.");
                    searchIsbn = sc.nextLine();
                    break;

                case 2:
                    System.out.println("제목 검색을 선택하셨습니다. 검색하실 제목을 작성해주세요.");
                    searchTitle = sc.nextLine();
                    break;

                case 3:
                    System.out.println("가격 검색을 선택하셨습니다. 가격을 입력해주세요.");
                    searchPrice = sc.nextInt();
                    sc.nextLine();
                    break;

                case 4:
                    System.out.println("저자 검색을 선택하셨습니다. 검색하실 저자명을 작성해주세요.");
                    searchAuthor = sc.nextLine();
                    break;

                case 5:
                    System.out.println("출판일 검색을 선택하셨습니다. 검색하실 출판일을 작성해주세요.");
                    searchPublicationDate = sc.nextLine();
                    break;

                case 6:
                    System.out.println("검색을 종료합니다.");
                    return;

                default:
                    System.out.println("올바른 옵션을 선택하세요.");
                    continue;
            }

            found = searchBooksByConditions(searchIsbn, searchTitle, searchPrice, searchAuthor, searchPublicationDate);

            if (!found) {
                System.out.println("검색 결과가 없습니다.");
            }
            return;
        }
    }

    private boolean searchBooksByConditions(String searchIsbn, String searchTitle, Integer searchPrice,
                                            String searchAuthor, String searchPublicationDate) {
        boolean found = false;

        for (Book book : bookMap.values()) {
            // 검색 조건에 맞는지 확인
            if (validateSearchConditions(book, searchIsbn, searchTitle, searchPrice, searchAuthor,
                    searchPublicationDate)) {
                System.out.println("검색된 도서: ");
                System.out.println(book);
                found = true;

                // 조건에 맞는 도서를 삭제할지 묻기
                if (confirmDelete(book)) {
                    bookMap.remove(book.getIsbn());
                    System.out.println("도서가 삭제되었습니다.");
                } else {
                    System.out.println("도서 삭제가 취소되었습니다.");
                }
                break;
            }
        }
        return found;
    }

    private boolean confirmDelete(Book book) {
        System.out.println("해당 도서를 삭제하시겠습니까? Y: 삭제, N: 취소");
        String confirmDelete = sc.nextLine();

        return confirmDelete.equalsIgnoreCase("Y");
    }

    // 도서 삭제 메서드
    public void bookDelete() {
        System.out.println("4. 도서 삭제를 누르셨습니다.");
        System.out.println("1개 삭제 시 O, 전체 삭제시 A를 눌러주세요.");
        String deleteChoice = sc.nextLine();

        if (deleteChoice.equalsIgnoreCase("O")) {
            handleSingleBookDeletion();
        } else if (deleteChoice.equalsIgnoreCase("A")) {
            handleAllBooksDeletion();
        } else {
            System.out.println("잘못된 선택입니다. 다시 선택해 주세요.");
        }
    }
    public String[] getBookSearchInput() {
        // ISBN 입력
        System.out.print("ISBN을 입력하세요: ");
        String searchIsbn = sc.nextLine();

        // 제목 입력
        System.out.print("제목을 입력하세요: ");
        String searchTitle = sc.nextLine();

        // 저자 입력
        System.out.print("저자를 입력하세요: ");
        String searchAuthor = sc.nextLine();

        // 출판일 입력
        System.out.print("출판일을 입력하세요 (예: 2023-01-01): ");
        String searchPublicationDate = sc.nextLine();

        return new String[] { searchIsbn, searchTitle, searchAuthor, searchPublicationDate };
    }

    // 1개 도서 삭제 처리 메서드
    private void handleSingleBookDeletion() {
        System.out.println("도서 삭제를 위해 조건을 모두 입력해주세요.");

        // 입력 받기
        String[] searchInputs = getBookSearchInput();
        String searchIsbn = searchInputs[0];
        String searchTitle = searchInputs[1];
        String searchAuthor = searchInputs[2];
        String searchPublicationDate = searchInputs[3];

        // 모든 조건을 만족하는 도서가 존재하는지 체크
        boolean foundToDelete = false;

        for (Book book : bookMap.values()) {
            if (validateSearchConditions(book, searchIsbn, searchTitle, null, searchAuthor, searchPublicationDate)) {
                bookMap.remove(book.getIsbn());
                System.out.println("도서가 삭제되었습니다.");
                foundToDelete = true;
                break;
            }
        }

        if (!foundToDelete) {
            System.out.println("조건에 맞는 도서를 찾을 수 없습니다. 삭제할 수 없습니다.");
        }
    }

    private void handleAllBooksDeletion() {
        System.out.println("전체 도서 삭제를 누르셨습니다. 정말로 등록된 도서 목록을 삭제하시겠습니까? Y : N");
        String yn = sc.nextLine();

        if (yn.equalsIgnoreCase("Y")) {
            bookMap = new HashMap<>();
            System.out.println("모든 도서가 삭제되었습니다.");
        } else {
            System.out.println("전체 도서 삭제를 취소하였습니다.");
        }
    }
    public boolean isValidRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public String getValidIsbn() {
        String isbn = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.print("ISBN (10자리) 입력해주세요: ");
            isbn = sc.nextLine();

            if (!isIsbnValid(isbn)) {
                continue;
            }

            isValid = true;
        }
        return isbn;
    }

    public boolean isIsbnValid(String isbn) {
        if (isbn.length() != 10) {
            System.out.println("정확히 10자로만 입력해주세요.");
            return false;
        } else if (!isbn.matches("\\d+")) {
            System.out.println("ISBN은 숫자만 포함되어야 합니다.");
            return false;
        } else if (bookMap.containsKey(isbn)) {
            System.out.println("이미 존재하는 ISBN입니다.");
            return false;
        }
        return true;
    }

    public boolean isValidPrice(int price) {
        return price >= 5000 && price <= 50000;
    }

    public int getValidPrice() {
        int price;
        do {
            System.out.print("새로운 가격을 입력하세요 (5,000원 ~ 50,000원): ");
            price = sc.nextInt();
            sc.nextLine();
        } while (!isValidPrice(price));

        return price;
    }

    public LocalDate getValidDate() {
        LocalDate publicationDate = getValidDateFromUser();
        if (publicationDate == null) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }
        return publicationDate;
    }

    public LocalDate getValidDateFromUser() {
        LocalDate minDate = LocalDate.of(1980, 1, 1); // 1980-01-01
        LocalDate currentDate = LocalDate.now(); // 현재 날짜

        return getValidPublicationDate(minDate, currentDate); // 검증 메서드 호출
    }

    public LocalDate getValidPublicationDate(LocalDate minDate, LocalDate currentDate) {
        while (true) {
            System.out.print("새로운 출판일을 yyyy-MM-dd 형식으로 입력해주세요.: ");
            String dateStr = sc.nextLine();

            try {
                LocalDate publicationDate = LocalDate.parse(dateStr, formatter);

                if (publicationDate.isBefore(minDate) || publicationDate.isAfter(currentDate)) {
                    System.out.println("출판일은 1980-01-01 이후이고, 현재 날짜를 초과할 수 없습니다.");
                    continue;
                }

                return publicationDate;
            } catch (DateTimeParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. yyyy-MM-dd 형식으로 입력하세요.");
                continue;
            }
        }
    }

    public boolean validateSearchConditions(Book book, String searchIsbn, String searchTitle, Integer searchPrice,
                                            String searchAuthor, String searchPublicationDate) {
        boolean matches = true;

        if (!searchIsbn.isEmpty() && !book.getIsbn().equals(searchIsbn)) {
            matches = false;
        }

        if (!searchTitle.isEmpty() && !book.getTitle().contains(searchTitle)) {
            matches = false;
        }

        if (!searchAuthor.isEmpty() && !book.getAuthor().contains(searchAuthor)) {
            matches = false;
        }
        if (!searchPublicationDate.isEmpty() && !book.getPublicationDate().toString().equals(searchPublicationDate)) {
            matches = false;
        }

        return matches;
    }



}
