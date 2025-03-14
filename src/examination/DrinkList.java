package examination;

import java.util.Scanner;

public class DrinkList {

	private Drink[] drinks = new Drink[0]; // 동적 배열 사용
	private int balance = 0; // 투입한 금액
	private Scanner sc = new Scanner(System.in);

	public void run() {
		boolean input = true;

		while (input) {
			System.out.println("메뉴 선택");
			System.out.println("1. 음료 목록 보기 | 2. 금액 투입 | 3. 음료 구매| 4. 거스름돈 반환| 5. 음료 및 가격 입력| 6. 재고 추가| 7. 종료");
			System.out.print("선택> ");

			int select = sc.nextInt();
			sc.nextLine();

			// 잘못된 입력을 버퍼에서 처리
			switch (select) {
			case 1:
				drinkList(); // 음료 목록 보기
				break;
			case 2:
				insertMoney(); // 금액 투입
				break;
			case 3:
				buyDrink(); // 음료 구매
				break;
			case 4:
				returnBalance(); // 금액 반환
				break;
			case 5:
				addDrink(); // 음료 및 가격 입력
				break;
			case 6:
				addStock(); // 재고 추가
				break;
			case 7:
				input = false; // 종료
				break;
			default:

			}
			System.out.println("프로그램 종료");
		}
	}

	public void drinkList() {
		if (drinks.length == 0) {
			System.out.println("등록된 음료가 없습니다.");
		} else {
			System.out.println("음료 목록:");
			for (int i = 0; i < drinks.length; i++) {
				System.out.println(drinks[i].getName() + " | " + drinks[i].getPrice() + " | " + drinks[i].getStock());
			}
		}
	}

	public void insertMoney() {
		System.out.println("투입 할 금액을 입력하세요. ");
		int inputMoney = sc.nextInt();
		sc.nextLine();

		if (inputMoney > 0) {
			balance += inputMoney;
			System.out.println("현재 금액 : " + balance);
		} else {
			System.out.println("올바른 금액을 입력하세요.");
		}
	}

	public void addDrink() {
		System.out.print("음료 이름: ");
		String name = sc.nextLine();

		System.out.print("음료 가격: ");
		int price = sc.nextInt();

		System.out.print("음료 재고: ");
		int stock = sc.nextInt();
		sc.nextLine();

		Drink[] newDrinks = new Drink[drinks.length + 1];
		for (int i = 0; i < drinks.length; i++) {
			newDrinks[i] = drinks[i];
		}
		newDrinks[drinks.length] = new Drink(name, price, stock);
		drinks = newDrinks;

		System.out.println(name + " 추가 완료");
	}

	public void buyDrink() {
		while (true) {
			if (drinks.length == 0) {
				System.out.println("현재 등록된 음료가 없습니다.");
				return;
			}

			System.out.println("구매하실 음료를 선택하세요.");
			String name = sc.nextLine();

			for (int i = 0; i < drinks.length; i++) {
				System.out.println((i + 1) + ". " + drinks[i].getName() + " (" + drinks[i].getPrice() + ", 재고: "
						+ drinks[i].getStock() + ")");
			}
			System.out.print("번호 입력> ");

			int choice = sc.nextInt();
			sc.nextLine();

			if (choice < 1 || choice > drinks.length) {
				System.out.println("잘못된 선택입니다.");
				continue;
			}

			Drink selectedDrink = drinks[choice - 1];

			if (selectedDrink.getStock() <= 0) {
				System.out.println("현재 재고가 없습니다.");
				continue;
			} else if (balance < selectedDrink.getPrice()) {
				System.out.println("잔액이 부족합니다.");
				return;
			}

			balance -= selectedDrink.getPrice();
			selectedDrink.setStock(selectedDrink.getStock() - 1);

			System.out.println(selectedDrink.getName() + " 구매 완료");

			System.out.println("남은 금액을 받으시겠다면 Y 다른 음료를 선택하시겠다면 N 선택해주세요. ");
			String yn = sc.nextLine();

			if (yn.equalsIgnoreCase("Y")) {
				returnBalance();
				return;
			} else {
				System.out.println("다른 음료를 선택해주세요.");
				continue;
			}

		}
	}

	public void addStock() {
		System.out.println("추가할 음료수 이름을 입력해주세요.");
		String name = sc.nextLine();

		boolean found = false;

		for (int i = 0; i < drinks.length; i++) {
			if (drinks[i].getName().equals(name)) {
				System.out.println("추가할 재고 수량을 입력하세요: ");
				int addAmount = sc.nextInt();
				sc.nextLine();

				if (addAmount > 0) {
					drinks[i].setStock(drinks[i].getStock() + addAmount);
					System.out
							.println(name + " 재고가 " + addAmount + "개 추가되었습니다. 현재 재고는 " + drinks[i].getStock() + "입니다.");
				} else {
					System.out.println("잘못된 입력입니다. 1이상 입력해주세요.");
				}
				found = true;
			}
		}
		if (!found) {
			System.out.println("현재 음료수는 존재하지 않습니다.");
		}
	}


	public void returnBalance() {
		System.out.println("거스름돈을 반환을 원하시면 Y 아니시면 N을 입력해주세요.");
		String yn = sc.nextLine();

		if (yn.equalsIgnoreCase("Y")) {
			balance = 0;
			System.out.println("반환이 완료되었습니다. 현재 잔액은 " + balance + "입니다.");

		} else {
			System.out.println("거부하셨습니다.");
		}
	}
}
