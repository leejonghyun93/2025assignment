package sec05;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookManager {
	private Map<String, Book> bookMap = new HashMap<>();
	private Scanner sc = new Scanner(System.in);
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private SimpleDateFormat sdf;

	public void run() {
		boolean input = true;

		while (input) {
			System.out.println("도서 항목");
			System.out.println("==================================================================");
			System.out.println("1. 도서 추가 | 2. 도서 목록 및 검색 | 3. 도서 수정  | 4. 도서 삭제 | 5. 종료");
			System.out.print("선택>");

			int select = sc.nextInt();
			sc.nextLine();

			switch (select) {
			case 1 -> inputBook();
			case 2 -> showBookList();
			case 3 -> bookUpdate();
			case 4 -> bookDelete();
			case 5 -> input = false;
			}
		}
		System.out.println("프로그램 종료");
	}

	public void inputBook() {
		sdf = new SimpleDateFormat(DATE_FORMAT);

		System.out.println("1. 도서 추가를 누르셨습니다.");
		System.out.println("아래 항목들을 입력해주세요.");

		String isbn;
		while (true) {
			System.out.print("ISBN (10자리) 입력해주세요.: ");
			isbn = sc.nextLine();

			if (isbn.length() > 10) {
				System.out.println("10자 초과는 입력할 수 없습니다.");
				continue;
			} else if (!isbn.matches("\\d+")) {
				System.out.println("ISBN은 숫자만 포함되어야 합니다. 다시 입력해주세요.");
				continue;
			} else if (bookMap.containsKey(isbn)) {
				System.out.println("이미 존재하는 ISBN입니다.");
				continue;
			}
			break;
		}

		String title = updateBookField("도서 제목");

		int price;
		while (true) {
			price = getValidPrice();
			if (price != -1)
				break;
		}

		String author = updateBookField("도서 저자");

		String publicationDate;
		while (true) {
			publicationDate = getValidDate();
			if (publicationDate != null)
				break;
		}

		createBook(isbn, title, price, author, publicationDate);
	}

	public boolean createBook(String isbn, String title, int price, String author, String publicationDate) {

		Book book = new Book(isbn, title, price, author, publicationDate);
		bookMap.put(isbn, book);
		System.out.println("도서가 추가되었습니다.");
		return true;
	}

	public void showBookList() {
		System.out.println("목록을 보실려면 L 검색을 보실려면 S로 눌러주세요.");
		String choise = sc.nextLine();

		if (choise.equalsIgnoreCase("L")) {
			System.out.println("도서 리스트를 선택하셨습니다.");

			if (bookMap.isEmpty()) {
				System.out.println("현재 도서 목록에 아무것도 없습니다.");
			} else {
				System.out.printf("%-12s | %-20s | %-10s | %-10s | %-10s%n", "도서번호", "도서 제목", "도서 가격", "도서 저자", "출판일");
				for (Book book : bookMap.values()) {
					System.out.printf("%-12s | %-20s | %-10d | %-10s | %-10s%n", book.getIsbn(), book.getTitle(),
							book.getPrice(), book.getAuthor(), book.getPublicationDate());
				}
			}
		} else if (choise.equalsIgnoreCase("S")) {
			System.out.println("도서 검색을 선택하셨습니다.");
			System.out.println("검색 항목 또는 내용을 입력해주세요.");
			String search = sc.nextLine();

			boolean found = false;
			for (Book book : bookMap.values()) {
				if (book.getIsbn().contains(search) || book.getTitle().contains(search)
						|| book.getAuthor().contains(search) || book.getPublicationDate().contains(search)) {

					System.out.println("검색하신 도서는: " + book);
					found = true;
				}
			}
			if (!found) {
				System.out.println("검색 결과가 없습니다.");
			}
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
			System.out.println("수정할 항목을 선택하세요 (1. ISBN |2. 제목 | 3. 가격 | 4. 저자 | 5. 출판일 | 6. 종료)");
			System.out.print("선택> ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				String newIsbn = getValidIsbn();
				if (newIsbn != null) {
					book.setIsbn(updateBookField("새로운 ISBN"));
					updated = true;
				}
				break;
			case 2:
				book.setTitle(updateBookField("새로운 제목"));
				updated = true;
				break;
			case 3:
				int newPrice = getValidPrice();
				if (newPrice != -1) {
					book.setPrice(newPrice);
					updated = true;
				}
				break;
			case 4:
				book.setAuthor(updateBookField("새로운 저자"));
				updated = true;
				break;
			case 5:
				String newDate = getValidDate();
				if (newDate != null) {
					book.setPublicationDate(newDate);
					updated = true;
				}
				break;
			case 6:
				if (updated) {
					System.out.println("도서 정보가 성공적으로 업데이트되었습니다.");
				} else {
					System.out.println("변경된 내용이 없습니다.");
				}
				return;
			default:
				System.out.println("올바른 옵션을 선택하세요.");
			}
		}
	}

	public void bookDelete() {
		System.out.println("4. 도서 삭제를 누르셨습니다.");
		System.out.println("1개 삭제 시 O 전체 삭제시 A를 눌러주세요.");
		String deleteChoise = sc.nextLine();

		if (deleteChoise.equalsIgnoreCase("O")) {
			System.out.print("삭제할 도서의 ISBN을 입력하세요: ");
			String isbn = sc.nextLine();

			if (bookMap.containsKey(isbn)) {
				bookMap.remove(isbn);
				System.out.println("도서가 삭제되었습니다.");
			} else {
				System.out.println("존재하지 않는 ISBN입니다. 삭제할 수 없습니다.");
			}
		} else if (deleteChoise.equalsIgnoreCase("A")) {
			System.out.println("전체 도서 삭제를 누르셨습니다. 정말로 등록된 도서 목록을 삭제하시겠습니까? Y : N");
			String yn = sc.nextLine();

			if (yn.equalsIgnoreCase("Y")) {
				bookMap.clear();
				System.out.println("모든 도서가 삭제되었습니다.");
			} else {
				System.out.println("전체 도서 삭제를 취소하였습니다.");
			}
		}
	}

	private String updateBookField(String prompt) {
		System.out.print(prompt + "을(를) 입력하세요: ");
		return sc.nextLine();
	}

	private String getValidIsbn() {
		while (true) {
			System.out.println("이미 사용중인 ISBN입니다.다른 ISBN으로 입력해주세요.");
			continue;
		}
	}

	private int getValidPrice() {
		while (true) {
			System.out.print("새로운 가격을 입력하세요 (5,000원 ~ 50,000원): ");
			int price = sc.nextInt();
			sc.nextLine();

			if (price < 5000 || price > 50000) {
				System.out.println("가격은 5,000원 ~ 50,000원 사이여야 합니다.");
				continue;
			}
			return price;
		}
	}

	private String getValidDate() {
		while (true) {
			System.out.print("새로운 출판일을 yyyy-MM-dd 형식으로 입력해주세요.: ");
			String dateStr = sc.nextLine();

			try {
				Date date = sdf.parse(dateStr);
				if (date.after(new Date())) {
					System.out.println("출판일은 현재 날짜를 초과할 수 없습니다.");
					continue;
				}
				return dateStr;
			} catch (ParseException e) {
				System.out.println("날짜 형식이 잘못되었습니다. yyyy-MM-dd 형식으로 입력하세요.");
				continue;
			}
		}
	}
}
