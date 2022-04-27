import java.util.Scanner;

public class GradeStudent {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        begin();
        // yêu cầu nhập trọng số của điểm giữa kỳ
        System.out.println("Midterm:");
        System.out.print("Weight (0-100): ");
        // Nhập trọng số của môn học bằng hàm ValidateInt
        int weigthmd = ValidateInt(sc, 100);
        // gọi hàm tính điểm giữa kỳ
        double mt = midterm(sc, weigthmd);

        System.out.println("Final:");
        System.out.print("Weight (0-" + (100 - weigthmd) + "): ");
        // Nhập trọng số của môn học bằng hàm ValidateInt
        int weigthfn = ValidateInt(sc, 100 - weigthmd);
        // gọi hàm tính điểm cuối kỳ
        double ft = finalterm(sc, weigthfn);

        // tự động tính ra trọng số của bài về nhà bằng 100 trừ trọng số của hai bài
        // trước để đảm bảo tổng trọng số là 100
        int weigthhw = 100 - weigthfn - weigthmd;
        System.out.println("Homework:");
        System.out.println("Weight : " + weigthhw);
        // gọi hàm tính điểm bài về nhà
        double hw = homework(sc, weigthhw);
        report(mt, ft, hw);
    }

    public static void begin() {
        System.out.println("This program reads exam/homework scores and reports your overall course grade\n");
    }

    // hàm tính điẻm giữa kỳ
    public static double midterm(Scanner sc, int weigth) {
        System.out.print("Score earned: ");
        double scoreearn = ValidateDouble(sc);

        // kiểm tra có điểm cộng hay không
        System.out.print("Were scores shifted (1 = yes, 2 = no): ");
        double score = 0.0;
        String shifted = sc.next();
        if (shifted.equals("1")) {
            System.out.print("Shift amount: ");
            score = ValidateDouble(sc);
        }
        // tinh điểm tổng bằng điểm đạt được cộng với điểm cộng, nếu lớn hơn 100 gán lại
        // bằng 100
        double point = scoreearn + score;
        if (point > 100)
            point = 100;
        System.out.println("Total points = " + point + " / 100");
        double wscore = Math.floor((point / 100 * weigth) * 10) / 10;
        System.out.println("Weight score = " + wscore + " / " + weigth + "\n");
        return wscore;
    }

    // điểm cuối kỳ
    public static double finalterm(Scanner sc, int weigth) {
        System.out.print("Score earned: ");
        double scoreearn = ValidateDouble(sc);
        System.out.print("Were scores shifted (1 = yes, 2 = no): ");
        double score = 0.0;
        String shifted = sc.next();
        if (shifted.equals("1")) {
            System.out.print("Shift amount: ");
            score = ValidateDouble(sc);
        }

        // điểm tổng bằng điểm đạt được cộng với điểm cộng, nếu lớn hơn 100 gán lại bằng
        // 100
        double point = scoreearn + score;
        if (point > 100)
            point = 100;
        System.out.println("Total points = " + point + " / 100");
        double wscore = Math.floor((point / 100 * weigth) * 10) / 10;
        System.out.println("Weight score = " + wscore + " / " + weigth + "\n");
        return wscore;
    }

    // điểm về nhà
    public static double homework(Scanner sc, int weigth) {
        System.out.print("Number of assignments: ");
        int assm;
        while (true)
            try {
                assm = sc.nextInt();
                break;
            } catch (Exception e) {
                sc.nextLine();
                System.out.print("Not a number.Re-enter: ");
            }
        // mảng hai chiều lưu thông tin của từng bài assigment
        double[][] scorearr = new double[assm][2];
        // duyệt mảng theo hàng giá trị đầu tiên là điểm đạt được, giá trị thứ 2 là điểm
        // tối đa
        for (int i = 0; i < scorearr.length; i++) {
            System.out.print("Assignment " + (i + 1) + " score and max: ");
            scorearr[i][0] = sc.nextInt();
            scorearr[i][1] = sc.nextInt();
            // điểm đạt được không được lớn hơn điểm tối đa
            while (scorearr[i][0] > scorearr[i][1]) {
                System.out.print("The score achieved cannot be greater than the maximum score. Please re-enter: ");
                scorearr[i][0] = sc.nextInt();
                scorearr[i][1] = sc.nextInt();
            }
        }
        System.out.print("How many sections did you attend: ");
        int attend = ValidateInt(sc, 100) * 5;
        // gán lại điểm chuyên cần bằng 30 nếu lớn hơn 30
        if (attend > 30)
            attend = 30;
        double score = attend;
        // duyệt mảng theo hàng cộng tất cả cá giá trị đầu tiên hàng để tính tổng điểm
        // đạt được
        for (int i = 0; i < scorearr.length; i++) {
            score += scorearr[i][0];
        }
        // điểm đạt được bằng 150 nếu lớn hơn 150
        if (score > 150)
            score = 150;
        double maxscore = 30;
        // duyệt mảng theo hàng cộng các giá trị thứ 2 của hàng để tính tổng điểm tối đa
        for (int i = 0; i < scorearr.length; i++) {
            maxscore += scorearr[i][1];
        }
        // tổng điểm bằng 150 nếu lớn hơn 150
        if (maxscore > 150)
            maxscore = 150;
        double wscore = Math.floor((score / maxscore * weigth) * 10) / 10;
        System.out.println("Section points = " + attend + " / 30");
        System.out.println("Total points = " + score + " / " + maxscore);
        System.out.println("Weighted score = " + wscore + " / " + weigth + "\n");
        return wscore;
    }

    // tính gpa và đưa ra thông báo
    public static void report(double mid, double ft, double hw) {
        double gpa;
        double grade = mid + ft + hw;
        if (grade < 60.00) {
            gpa = 0.0;
        } else if (grade < 75.00) {
            gpa = 0.7;
        } else if (grade < 85.00) {
            gpa = 2.0;
        } else {
            gpa = 3.0;
        }
        System.out.println("Overall percentage = " + grade);
        System.out.println("Your grade will be at least = " + gpa);
        System.out.print("<< your custom  grade  masage here>>");
    }

    // kiểm tra xem có phải là số nguyên hay không
    public static int ValidateInt(Scanner sc, int a) {
        int number = 0;
        boolean isInt = false;
        while (isInt == false) {
            try {
                int check = sc.nextInt();
                if (check >= 0 && check <= a) {
                    number = check;
                    isInt = true;
                } else
                    System.out.print("Betwen 0-" + a + " Re-enter: ");
            } catch (Exception e) {
                sc.nextLine();
                System.out.print("Not a number.Re-enter: ");
            }
        }
        return number;
    }

    // kiểm tra xem có phải số thực hay không
    public static double ValidateDouble(Scanner sc) {
        double number = 0;
        boolean isInt = false;
        while (isInt == false) {
            try {
                number = sc.nextInt();
                if (number >= 0) {
                    isInt = true;
                } else
                    System.out.print("Must not be less than 0 Re-enter: ");
            } catch (Exception e) {
                sc.nextLine();
                System.out.print("Not a number, Re-enter ");
            }
        }
        return number;
    }
}
