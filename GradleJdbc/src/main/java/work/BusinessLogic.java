package work;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusinessLogic {
	// 以下のパスにファイル出力を行う
	private static final String OUTPUT_FILE_PATH = "C:\\ForDevelop\\OutPutFiles\\studentList.csv";

	// 出力ヘッダーの用意
	private static final String HEADER_STUDENT_LIST = "#--受講生一覧--";
	private static final String HEADER_STUDENT_LIST_COLUMN = "#名前,性別,年齢,職歴,登録支店,受講講座";

	// 出力用の定数の用意
	private static final String NEW_LINE = System.getProperty("line.separator"); // 改行コードの取得
	private static final String COMMA = ",";
	private static final String MALE = "男性";
	private static final String FEMALE = "女性";
	private static final String YEAR = "年";
	private static final String MONTH = "ヶ月";
	private static final String NO_CAREER = "職歴なし";
	private static final String NO_COURSE = "受講なし";

	public void createStudentListFile() {

		// これも定数化しても良い
		String driverName = "com.mysql.jdbc.Driver";
		String jdbcUrl = "jdbc:mysql://192.168.33.11/test_db?useSSL=false";
		String userId = "test_user";
		String userPass = "test_pass";

		Connection con = null;
		ArrayList<StudentDto> studentList = new ArrayList<StudentDto>();

		try {
			// Connectionの発行
			Class.forName(driverName);
			con = DriverManager.getConnection(jdbcUrl, userId, userPass);

			// データベースにアクセスして生徒一覧データを取得
			StudentDao studentDao = new StudentDao(con);
			studentList = studentDao.getStudentList();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					//接続の解除
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// カレッジ生一覧のデータをcsvファイルに出力する
		try {
			File studentListFile = new File(OUTPUT_FILE_PATH);
			FileWriter fileWriter = new FileWriter(studentListFile);

			// ヘッダ部分の書き込み
			fileWriter.write(HEADER_STUDENT_LIST);
			fileWriter.write(NEW_LINE);
			fileWriter.write(HEADER_STUDENT_LIST_COLUMN);
			fileWriter.write(NEW_LINE);

			// 取得した生徒数分のデータのファイル出力処理
			for (int i = 0; i < studentList.size(); i++) {
				StudentDto studentDto = studentList.get(i);
				// 名前
				fileWriter.write(studentDto.getStudentName());
				fileWriter.write(COMMA);

				// 性別
				if (studentDto.getGender() == 1) {
					fileWriter.write(MALE);
				} else {
					fileWriter.write(FEMALE);
				}
				fileWriter.write(COMMA);

				// 年齢
				fileWriter.write(String.valueOf(studentDto.getAge()));
				fileWriter.write(COMMA);

				// 職歴
				int careerMonth = studentDto.getCareerMon();
				if (careerMonth == 0) {
					fileWriter.write(NO_CAREER);
				} else {
					int year = careerMonth / 12;
					int month = careerMonth % 12;

					if (year > 0) {
						fileWriter.write(String.valueOf(year));
						fileWriter.write(YEAR);
					}
					if (month > 0) {
						fileWriter.write(String.valueOf(month));
						fileWriter.write(MONTH);
					}
				}
				fileWriter.write(COMMA);

				// 登録支店
				fileWriter.write(studentDto.getBranchName());
				fileWriter.write(COMMA);

				// 受講講座
				if (studentDto.getCourseId() != 0) {
					fileWriter.write(studentDto.getCourseName());
				} else {
					fileWriter.write(NO_COURSE);
				}

				/* または
				//受講講座
				if (dto.getCourseName() != null){
					filewriter.write(dto.getCourseName());
				} else {
					filewriter.write(NO_COURSE);
				}
				*/

				fileWriter.write(NEW_LINE);
			}
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
