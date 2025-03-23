package examination;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyDos {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Command> Map = new HashMap<>();
    private static Path path = Paths.get("C:\\");

    public static void main(String[] args) {
        initializeCommands();
        run();
    }

    private static void initializeCommands() {
        Map.put("cd", MyDos::cdCmd);
        Map.put("dir", MyDos::dirCmd);
        Map.put("type", MyDos::typeCmd);
        Map.put("rename", MyDos::renameCmd);
        Map.put("copy", MyDos::copyCmd);
    }

    private static void run() {
        while (true) {
            System.out.print(path + ">");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                System.out.println("프로그램 종료");
                break;
            }
            executeCommand(input);
        }
    }

    private static void executeCommand(String input) {
        String[] args = input.split(" ");
        Command command = Map.get(args[0]);

        if (command != null) {
            command.execute(args);
        } else {
            System.out.println("알 수 없는 명령입니다.");
        }
    }

    private static void dirCmd(String[] args) {
        File folder = path.toFile();
        File[] contents = folder.listFiles();
        if (contents == null) return;

        long totalSize = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
        DecimalFormat df = new DecimalFormat("#,###");

        for (File file : contents) {
            System.out.printf("%-25s", sdf.format(new Date(file.lastModified())));
            if (file.isDirectory()) {
                System.out.printf("%-15s%-20s", "<DIR>", file.getName());
            } else {
                System.out.printf("%-15s%-20s", df.format(file.length()), file.getName());
                totalSize += file.length();
            }
            System.out.println();
        }
        System.out.printf("        %d개 파일 %s 바이트 \n", contents.length, df.format(totalSize));
    }

    private static void cdCmd(String[] args) {
        if (args.length != 2) {
            System.out.println("사용법 : cd 이동할 경로");
            return;
        }

        Path newPath = path.resolve(args[1]);
        File file = newPath.toFile();
        if (file.exists() && file.isDirectory()) {
            path = newPath.toAbsolutePath();
        } else {
            System.out.println(args[1] + " 해당 폴더는 존재하지 않습니다.");
        }
    }

    private static void typeCmd(String[] args) {
        if (args.length != 2) {
            System.out.println("사용법 : type 파일명");
            return;
        }

        File file = path.resolve(args[1]).toFile();
        if (!file.exists() || !file.isFile()) {
            System.out.println("파일이 존재하지 않습니다.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
    }

    private static void renameCmd(String[] args) {
        if (args.length != 3) {
            System.out.println("사용법 : rename 원본파일 새파일명");
            return;
        }

        File file1 = path.resolve(args[1]).toFile();
        File file2 = path.resolve(args[2]).toFile();

        if (!file1.exists()) {
            System.out.println("원본 파일이 존재하지 않습니다.");
            return;
        }
        if (file2.exists()) {
            System.out.println("새 파일명이 이미 존재하여 변경할 수 없습니다.");
            return;
        }

        if (file1.renameTo(file2)) {
            System.out.println("파일명이 변경되었습니다.");
        } else {
            System.out.println("파일명 변경 실패.");
        }
    }

    private static void copyCmd(String[] args) {
        if (args.length != 3) {
            System.out.println("사용법 : copy 원본폴더 대상폴더");
            return;
        }

        File sourceDir = path.resolve(args[1]).toFile();
        File targetDir = path.resolve(args[2]).toFile();

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println(args[1] + " 폴더가 존재하지 않습니다.");
            return;
        }

        copyDir(sourceDir, targetDir);
        System.out.println("폴더가 성공적으로 복사되었습니다.");
    }

    private static void copyDir(File sourceDir, File targetDir) {
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            System.out.println("폴더 생성 실패: " + targetDir.getName());
            return;
        }

        File[] files = sourceDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            File newFile = new File(targetDir, file.getName());
            if (file.isDirectory()) {
                copyDir(file, newFile);
            } else {
                copyFile(file, newFile);
            }
        }
    }

    private static void copyFile(File sourceFile, File targetFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

        } catch (IOException e) {
            System.out.println("파일 복사 중 오류 발생: " + sourceFile.getName());
        }
    }


    @FunctionalInterface
    interface Command {
        void execute(String[] args);
    }
}