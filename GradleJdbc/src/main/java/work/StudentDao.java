package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {
	private Connection con;

	// コンストラクタ
	public StudentDao(Connection c) {
		this.con = c;
	}

	public ArrayList<StudentDto> getStudentList() {

		ArrayList<StudentDto> list = new ArrayList<StudentDto>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// データベースから生徒一覧データを取得
		StringBuffer buf = new StringBuffer();

		buf.append(" SELECT                                 ");
		buf.append("   stu.student_id,                      ");
		buf.append("   stu.student_name,                    ");
		buf.append("   stu.gender,                          ");
		buf.append("   stu.age,                             ");
		buf.append("   stu.branch_id,                       ");
		buf.append("   stu.career_mon,                      ");
		buf.append("   stu.course_id,                       ");
		buf.append("   brn.branch_name,                     ");
		buf.append("   crs.course_name                      ");
		buf.append(" FROM                                   ");
		buf.append("   uzuz_student stu                     ");
		buf.append("   LEFT OUTER JOIN branch brn           ");
		buf.append("     ON stu.branch_id = brn.branch_id   ");
		buf.append("   LEFT OUTER JOIN course crs           ");
		buf.append("     ON stu.course_id = crs.course_id   ");
		buf.append(" ORDER BY                               ");
		buf.append("   stu.student_id                       ");

		try {
			ps = con.prepareStatement(buf.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				StudentDto dto = new StudentDto();
				dto.setStudentId(rs.getInt(     "student_id"  ));
				dto.setStudentName(rs.getString("student_name"));
				dto.setGender(rs.getInt(        "gender"      ));
				dto.setAge(rs.getInt(           "age"         ));
				dto.setBranchId(rs.getInt(      "branch_id"   ));
				dto.setCareerMon(rs.getInt(     "career_mon"  ));
				dto.setCourseId(rs.getInt(      "course_id"   ));
				dto.setBranchName(rs.getString( "branch_name" ));
				dto.setCourseName(rs.getString( "course_name" ));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			// オブジェクトのクローズ
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
